package com.ubi.bgg.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Date {
  private const val Format: String = "HH:mm:ss - yyyy-MM-dd"

  fun local(): LocalDateTime = LocalDateTime.now()

  fun format(date: LocalDateTime): String = date.format(DateTimeFormatter.ofPattern(Format))

  fun from(string: String): LocalDateTime = LocalDateTime.parse(string, DateTimeFormatter.ofPattern(Format))

  fun hasOneDayDifference(date1: LocalDateTime, date2: LocalDateTime) =
    Duration.between(date1, date2).toDays() > 0
}

