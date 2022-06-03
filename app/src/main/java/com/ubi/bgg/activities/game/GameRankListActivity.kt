package com.ubi.bgg.activities.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityRankListBinding

class GameRankListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRankListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Common.initialize(applicationContext)
    binding = ActivityRankListBinding.inflate(layoutInflater)
  }
}