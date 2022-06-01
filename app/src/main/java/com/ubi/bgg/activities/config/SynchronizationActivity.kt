package com.ubi.bgg.activities.config

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun synchronize() {
  val collection = BGGUserService.collection(Common.get("username")!!)

  Common.set("last_sync", Date.format(Date.local()))
}

class SynchronizationActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_synchronize)
    Common.initialize(applicationContext)

    lifecycleScope.launch {
      withContext(Dispatchers.IO) {
        synchronize()
        finish()
      }
    }
  }
}