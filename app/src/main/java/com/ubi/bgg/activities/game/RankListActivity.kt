package com.ubi.bgg.activities.game

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.database.entities.Rank
import com.ubi.bgg.databinding.ActivityRankListBinding
import com.ubi.bgg.databinding.LiRankBinding


class RankAdapter(private val context: Activity, private val list: List<Rank>) :
  ArrayAdapter<Rank>(context, R.layout.li_rank, list) {
  private lateinit var binding: LiRankBinding

  @SuppressLint("ViewHolder", "SetTextI18n")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    binding = LiRankBinding.inflate(LayoutInflater.from(context), parent, false)

    binding.tvOrderNo.text = (position + 1).toString()
    binding.tvRank.text = list[position].position.toString()
    binding.tvScore.text = score(list[position].score)
    binding.tvDate.text = list[position].date

    return binding.root
  }

  override fun getCount() = list.size

  private fun score(score: Double?) = score?.let { "%.2f".format(score) } ?: "-"
}

class RankListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRankListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRankListBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)

    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    val gameId = intent.getLongExtra("game_id", -1)
    val ranks = Common.Database.games().ranks(gameId)

    binding.listview.adapter = RankAdapter(this, ranks)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }
}