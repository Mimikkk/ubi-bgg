package com.ubi.bgg.activities.game

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.databinding.ActivityGameListBinding
import com.ubi.bgg.views.adapters.GameAdapter

class ExpansionListActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameListBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    println("items: ${Common.Database.games().readAll()}")
    items = Common.Database.games().readAll().filter { it.isExpansion }.toMutableList()
    adapter = GameAdapter(this, items)
    binding.listview.adapter = adapter
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_sort_alphabetically ->
      rehydrate(SortType.Alphabet).run { true }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    menu?.getItem(0)?.isVisible = false
    menu?.getItem(1)?.isVisible = false
    return super.onPrepareOptionsMenu(menu)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_list_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun rehydrate(sortType: SortType) {
    when (sortType) {
      SortType.Alphabet -> items.sortBy { it.title }
    }
    adapter.notifyDataSetChanged()
  }

  private lateinit var binding: ActivityGameListBinding
  private lateinit var adapter: GameAdapter
  private lateinit var items: MutableList<Game>

  private enum class SortType { Alphabet }
}