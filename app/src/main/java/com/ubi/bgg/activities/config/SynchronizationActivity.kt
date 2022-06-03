package com.ubi.bgg.activities.config

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.database.adapters.migrate
import com.ubi.bgg.databinding.ActivitySynchronizeBinding
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

fun synchronize() {
  val collection = BGGUserService.collection(Common.get("username")!!)
  val games = collection.games
  val expansions = collection.expansions
  val basegames = games.filter { game -> expansions.find { game.id == it.id } == null }


  migrate(games)

  Common.set("game_count", games.size)
  Common.set("basegame_count", basegames.size)
  Common.set("expansion_count", expansions.size)
  Common.set("last_sync", Date.format(Date.local()))
}

class SynchronizationActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySynchronizeBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySynchronizeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar.root)
    Common.initialize(applicationContext)

    if (!Common.contains("last_sync") || Date.hasOneDayDifference(
        Date.from(Common.get("last_sync")!!), LocalDateTime.now()
      )
    ) sync() else
      AlertDialog.Builder(this)
        .setTitle("Potwierdź synchronizację")
        .setMessage("Jesteś pewien?")
        .setCancelable(false)
        .setPositiveButton("Potwierdź") { _, _ -> sync() }
        .setNegativeButton("Anuluj") { _, _ -> finish() }
        .setIcon(android.R.drawable.ic_popup_sync)
        .show()

  }

  private fun sync() {
    lifecycleScope.launch {
      withContext(Dispatchers.IO) {
        synchronize()
        finish()
      }
    }
  }
}