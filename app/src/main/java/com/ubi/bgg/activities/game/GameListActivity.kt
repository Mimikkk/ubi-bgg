package com.ubi.bgg.activities.game

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.databinding.ActivityGameListBinding
import com.ubi.bgg.views.adapters.GameAdapter

class GameListActivity : AppCompatActivity() {
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

  private lateinit var binding: ActivityGameListBinding
  private lateinit var adapter: GameAdapter
  private lateinit var items: MutableList<Game>
  private enum class SortType { Alphabet, Year, Rank }
}