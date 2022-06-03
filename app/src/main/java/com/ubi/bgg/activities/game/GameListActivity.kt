package com.ubi.bgg.activities.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
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
    if (ranks.isNotEmpty()) {
      val rank = ranks.first()
      binding.tvRank.text = rank(rank.position)
      binding.tvScore.text = score(rank.score)
    }

    return binding.root
  }

  override fun getCount() = list.size

  private fun thumbnail(url: String) {

    runBlocking {
      withContext(Dispatchers.IO) {
        val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        binding.ivThumbnail.setImageBitmap(bitmap)
      }
    }
  }

  private fun year(year: Int?) = year?.toString() ?: "Nieznany"
  private fun rank(rank: Int?) = "ranking - ${rank?.toString() ?: "-"}"

  private fun score(score: Double?) = score?.let { "%.2f".format(score) } ?: "-"
}

class GameListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGameListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameListBinding.inflate(layoutInflater)
    setSupportActionBar(binding.toolbar.root)

    setContentView(binding.root)
    Common.initialize(applicationContext)

    binding.listview.isClickable = true
    val items = Common.Database.games().readAll()
    binding.listview.adapter = GameAdapter(this, items)
    binding.listview.setOnItemClickListener { _, _, position, _ ->
      moveToRankList(items[position])
    }
  }

  private fun moveToRankList(game: Game) {
    val intent = Intent(this, RankListActivity::class.java)
    intent.putExtra("game_id", game.id)

    startActivity(intent)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }
}