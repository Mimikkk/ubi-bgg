package com.ubi.bgg.views.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ubi.bgg.R
import com.ubi.bgg.database.entities.Rank
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
