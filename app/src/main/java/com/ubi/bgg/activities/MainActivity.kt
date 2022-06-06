package com.ubi.bgg.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.activities.config.SynchronizationActivity
import com.ubi.bgg.activities.game.ExpansionListActivity
import com.ubi.bgg.activities.game.GameListActivity
import com.ubi.bgg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    binding = ActivityMainBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    if (Common.contains("last_sync").not())
      resultLauncher.launch(Intent(this, SynchronizationActivity::class.java))

    rehydrate()
  }

  override fun onActivityReenter(resultCode: Int, data: Intent?) {
    rehydrate()
    super.onActivityReenter(resultCode, data)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.action_synchronize ->
      resultLauncher.launch(Intent(this, SynchronizationActivity::class.java)).run { true }
    R.id.action_clear -> clear().run { true }
    R.id.action_games -> moveToGameList().run { true }
    R.id.expansion_list -> moveToExpansionList().run { true }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun rehydrate() {
    binding.GameCount.text = Common.get<Int>("game_count").toString()
    binding.BaseGameCount.text = Common.get<Int>("basegame_count").toString()
    binding.ExpansionCount.text = Common.get<Int>("expansion_count").toString()
    binding.LastSyncDate.text = Common.get<String>("last_sync")
    binding.Username.text = Common.get<String>("username")
  }

  private val resultLauncher: ActivityResultLauncher<Intent> =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      rehydrate()
    }

  private fun clear() {
    AlertDialog.Builder(this)
      .setTitle("Potwierdź wyczyszczenie")
      .setMessage("Jesteś pewien?")
      .setPositiveButton("Potwierdź") { dialog, _ ->
        Common.clear()
        dialog.dismiss()
        finish()
      }
      .setNegativeButton("Anuluj") { _, _ -> }
      .setIcon(android.R.drawable.ic_popup_disk_full)
      .show()
  }

  private fun moveToGameList() = startActivity(Intent(this, GameListActivity::class.java))
  private fun moveToExpansionList() = startActivity(Intent(this, ExpansionListActivity::class.java))
}
