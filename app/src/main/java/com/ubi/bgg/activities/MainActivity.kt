package com.ubi.bgg.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityMainBinding
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.Date

fun synchronize() {
  val collection = BGGUserService.collection(Common.get("username")!!)

  Common.set("last_sync", Date.format(Date.local()))
}

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    binding = ActivityMainBinding.inflate(layoutInflater)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    println("Hello World!")
    binding.GameCount.text = Common.get<Int>("game_count")?.toString() ?: "0"
    println("Hello World!")
    binding.ExpansionCount.text = Common.get<Int>("expansion_count")?.toString() ?: "0"
    println("Hello World!")
    binding.LastSyncDate.text = Common.get<String>("last_sync")
    println("Hello World!")
    binding.Username.text = Common.get<String>("username")
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }
}
