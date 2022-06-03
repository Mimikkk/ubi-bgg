package com.ubi.bgg.activities.config

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bgg.Common
import com.ubi.bgg.activities.MainActivity
import com.ubi.bgg.databinding.ActivityLoginBinding
import com.ubi.bgg.services.bgg.user.BGGUserService
import com.ubi.bgg.utils.showToast


class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(icicle: Bundle?) {
    super.onCreate(icicle)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    Common.initialize(applicationContext)
    setSupportActionBar(binding.toolbar.root)
    setContentView(binding.root)

    binding.bLogin.setOnClickListener { attemptLogin() }
    if (Common.contains("username")) login()
  }

  private fun attemptLogin() {
    val username = binding.tfLogin.text.toString()
    if (username.isBlank()) return showToast("Nieprawidłowa nazwa użytkownika")
    if (BGGUserService.exists(username).not()) return showToast("Użytkownik nie istnieje")

    Common.set("username", username)
    showToast("Poprawnie zalogowano $username")
    login()
  }

  private fun login() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }
}
