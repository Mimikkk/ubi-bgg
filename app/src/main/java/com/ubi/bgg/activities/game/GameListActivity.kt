package com.ubi.bgg.activities.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.activities.config.SynchronizationActivity
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.databinding.ActivityGameListBinding
import com.ubi.bgg.databinding.LiGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

class GameAdapter(private val context: Activity, private val list: List<Game>) :
  ArrayAdapter<Game>(context, R.layout.li_game, list) {
  private lateinit var binding: LiGameBinding

  @SuppressLint("ViewHolder", "SetTextI18n")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    binding = LiGameBinding.inflate(LayoutInflater.from(context), parent, false)


    list[position].thumbnail?.let { thumbnail(it) }
    binding.tvOrderNo.text = (position + 1).toString()
    binding.tvTitle.text = list[position].title
    binding.tvYear.text = year(list[position].published)

    val ranks = Common.Database.games().ranks(list[position].id)
    val rank = ranks.firstOrNull()
    binding.tvRank.text = rank(rank?.position)
    binding.tvScore.text = score(rank?.score)

    return binding.root
  }

  private fun thumbnail(url: String) {

    runBlocking {
      withContext(Dispatchers.IO) {
        val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        binding.ivThumbnail.setImageBitmap(bitmap)
      }
    }
  }

  private fun year(year: Int?) = year?.toString() ?: "N/D"
  private fun rank(rank: Int?) = "ranking - ${rank?.toString() ?: "nie oceniony"}"

  private fun score(score: Double?) = score?.let { "%.2f".format(score) } ?: "-"
}

enum class SortType {
  Alphabet,
  Year,
  Rank
}

class GameListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGameListBinding
  private lateinit var items: MutableList<Game>
  private lateinit var adapter: GameAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameListBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    items = Common.Database.games().readAll().toMutableList()
    adapter = GameAdapter(this, items)
    binding.listview.isClickable = true
    binding.listview.adapter = adapter
    binding.listview.setOnItemClickListener { _, _, position, _ ->
      if (Common.Database.games().ranks(items[position].id)
          .isNotEmpty()
      ) moveToRankList(items[position])
    }
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_sort_by_ranking ->
      rehydrate(SortType.Rank).run { true }
    R.id.action_sort_by_year ->
      rehydrate(SortType.Year).run { true }
    R.id.action_sort_alphabetically ->
      rehydrate(SortType.Alphabet).run { true }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_list_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun rehydrate(sortType: SortType) {
    when (sortType) {
      SortType.Alphabet -> items.sortBy { it.title }
      SortType.Year -> items.sortByDescending { it.published }
      SortType.Rank -> items.sortBy {
        Common.Database.games().ranks(it.id).firstOrNull()?.position ?: Integer.MAX_VALUE
      }
    }
    adapter.notifyDataSetChanged()
  }

  private fun moveToRankList(game: Game) {
    val intent = Intent(this, RankListActivity::class.java)
    intent.putExtra("game_id", game.id)

    startActivity(intent)
  }
}