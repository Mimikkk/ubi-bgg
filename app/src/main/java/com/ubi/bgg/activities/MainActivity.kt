package com.ubi.bgg.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.R
import com.ubi.bgg.databinding.ActivityMainBinding
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import javax.xml.parsers.SAXParserFactory

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Common.initialize(applicationContext)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val toolbar = binding.toolbar.root
    setSupportActionBar(toolbar)

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_actions, menu)
    return super.onCreateOptionsMenu(menu)
  }
}
