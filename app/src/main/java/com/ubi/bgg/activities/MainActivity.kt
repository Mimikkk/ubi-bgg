package com.ubi.bgg.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.activities.config.SynchronizationActivity
import com.ubi.bgg.databinding.ActivityMainBinding
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.Date

fun moveToList() {}

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)
    Common.initialize(applicationContext)

    if (Common.contains("last_sync").not())
      startActivity(Intent(this, SynchronizationActivity::class.java))

    rehydrate()
  }

  override fun onActivityReenter(resultCode: Int, data: Intent?) {
    super.onActivityReenter(resultCode, data)
    rehydrate()
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_synchronize ->
      startActivity(Intent(this, SynchronizationActivity::class.java)).run { true }
    R.id.action_clear -> Common.clear().also { finish() }.run { true }
    R.id.action_list -> moveToList().run { true }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun rehydrate() {
    binding.GameCount.text = Common.get<Int>("game_count").toString()
    binding.ExpansionCount.text = Common.get<Int>("expansion_count").toString()
    binding.LastSyncDate.text = Common.get<String>("last_sync")
    binding.Username.text = Common.get<String>("username")
  }
}
