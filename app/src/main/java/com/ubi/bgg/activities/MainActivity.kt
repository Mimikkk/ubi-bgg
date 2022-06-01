package com.ubi.bgg.activities

import android.accounts.AccountManager
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var accountManager: AccountManager

  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    binding = ActivityMainBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)
    val toolbar = binding.toolbar.root
    setSupportActionBar(toolbar)
    setContentView(binding.root)
    Common.Database.users()

  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }
}
