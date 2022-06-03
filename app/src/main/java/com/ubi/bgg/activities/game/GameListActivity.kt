package com.ubi.bgg.activities.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.database.entities.Game
import com.ubi.bgg.databinding.ActivityGameListBinding
import com.ubi.bgg.databinding.LiGameBinding
import java.util.concurrent.Executors

class GameAdapter(private val context: Activity, private val list: List<Game>) :
  ArrayAdapter<Game>(context, R.layout.li_game, list) {
  private lateinit var binding: LiGameBinding

  @SuppressLint("ViewHolder", "SetTextI18n")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    binding = LiGameBinding.inflate(LayoutInflater.from(context), parent, false)


    list[position].thumbnail?.let { thumbnail(it) }
    binding.tvOrderNo.text = (position + 1).toString()
    binding.tvTitle.text = list[position].title
    binding.tvYear.text = year(list[position].published)
    val rank = Common.Database.games().ranks(list[position].id).first()
    binding.tvRank.text = rank(rank.position)
    binding.tvScore.text = score(rank.score)

    return binding.root
  }

  override fun getCount() = list.size

  private fun thumbnail(url: String) {
    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

    executor.execute {
      handler.runCatching {
        post {
          binding.ivThumbnail.setImageBitmap(
            BitmapFactory.decodeStream(
              java.net.URL(url).openStream()
            )
          )
        }
      }
    }
  }

  private fun year(year: Int?) = year?.toString() ?: "Nieznany"
  private fun rank(rank: Int?) = "ranking - ${rank?.toString() ?: "-"}"

  private fun score(score: Double?) = score?.let { "%.2f".format(score) } ?: "-"
}

class GameListActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGameListBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameListBinding.inflate(layoutInflater)
    setSupportActionBar(binding.toolbar.root)

    setContentView(binding.root)
    Common.initialize(applicationContext)
    println("${Common.Database.games().readAll()}")

    binding.listview.isClickable = true
    val items = listOf(Game("abc", null, 2022, 123))
    binding.listview.adapter = GameAdapter(this, items)
    binding.listview.setOnItemClickListener { _, _, position, _ ->
      moveToRankList(items[position])
    }
  }

  private fun moveToRankList(game: Game) {
    val intent = Intent(this, RankListActivity::class.java)
    intent.putExtra("game_id", game.id)

    startActivity(intent)
  }

  override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
    menu?.clear()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    return super.onPrepareOptionsMenu(menu)
  }
}