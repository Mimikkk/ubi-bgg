package com.ubi.bgg.services

import com.ubi.bgg.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Suppress("BlockingMethodInNonBlockingContext")
object Connection {
  suspend fun get(url: String): String? = withContext(Dispatchers.IO) {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    return@withContext connection.connect().runCatching {
      return@runCatching when (connection.responseCode) {
        HttpURLConnection.HTTP_OK -> connection.inputStream.reader().readText()
        HttpURLConnection.HTTP_ACCEPTED -> {
          showToast("Proszę czekać, połączenie jest zaakceptowane...")
          delay(8000)
          get(url)
        }
        else -> null
      }
    }.getOrNull().also {
      connection.disconnect()
    }
  }
}
