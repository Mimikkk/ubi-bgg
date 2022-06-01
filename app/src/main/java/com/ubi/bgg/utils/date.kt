package com.ubi.bgg.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Date {
  private const val Format: String = "HH:mm:ss - yyyy-MM-dd"

  fun local(): LocalDateTime = LocalDateTime.now()

  fun format(date: LocalDateTime): String = date.format(DateTimeFormatter.ofPattern(Format))
}

