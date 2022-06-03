package com.ubi.bgg.activities.config

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

fun synchronize() {
  val collection = BGGUserService.collection(Common.get("username")!!)

  Common.set("last_sync", Date.format(Date.local()))
}

class SynchronizationActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_synchronize)
    Common.initialize(applicationContext)

    if (!Common.contains("last_sync") || !Date.hasOneDayDifference(
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