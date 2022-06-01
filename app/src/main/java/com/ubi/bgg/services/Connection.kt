package com.ubi.bgg.services

import kotlinx.coroutines.Dispatchers
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
        HttpURLConnection.HTTP_OK -> connection.inputStream.bufferedReader().readText()
        else -> null
      }
    }.getOrNull().also {
      connection.disconnect()
    }
  }
}
