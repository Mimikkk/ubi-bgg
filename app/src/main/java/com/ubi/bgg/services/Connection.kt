package com.ubi.bgg.services

import java.net.HttpURLConnection
import java.net.URL

object Connection {
  fun get(url: String): String? {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    return connection.connect().runCatching {
      return when (connection.responseCode) {
        HttpURLConnection.HTTP_OK -> connection.inputStream.bufferedReader().use { it.readText() }
        else -> null
      }
    }.getOrNull().also {
      connection.disconnect()
    }
  }
}

