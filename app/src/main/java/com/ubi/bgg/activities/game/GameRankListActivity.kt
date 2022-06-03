package com.ubi.bgg.activities.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityRankListBinding

class GameRankListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRankListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRankListBinding.inflate(layoutInflater)
    setSupportActionBar(binding.toolbar.root)

    setContentView(binding.root)
    Common.initialize(applicationContext)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }
}