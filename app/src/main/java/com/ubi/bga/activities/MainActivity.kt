package com.ubi.bga.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bga.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class MainActivity : AppCompatActivity() {
  val scope = CoroutineScope(Job() + Dispatchers.IO)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Common.initialize(applicationContext)

    println("No maidens...")
  }
}
