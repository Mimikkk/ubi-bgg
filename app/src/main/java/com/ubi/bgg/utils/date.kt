package com.ubi.bgg.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Date {
  private const val Format: String = "yyyy-MM-dd"

  fun local(): LocalDate = LocalDate.now()

  fun format(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern(Format))
}

