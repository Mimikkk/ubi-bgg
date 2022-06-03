package com.ubi.bgg.activities.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityGameListBinding

class GameListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGameListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameListBinding.inflate(layoutInflater)
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