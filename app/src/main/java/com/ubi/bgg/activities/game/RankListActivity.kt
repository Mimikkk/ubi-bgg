package com.ubi.bgg.activities.game

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.databinding.ActivityRankListBinding
import com.ubi.bgg.views.adapters.RankAdapter

class RankListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRankListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRankListBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    val gameId = intent.getLongExtra("game_id", -1)
    val ranks = Common.Database.games().ranks(gameId)

    binding.listview.adapter = RankAdapter(this, ranks)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }
}
