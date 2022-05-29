package com.ubi.bgg.services.bgg.search

import com.ubi.bgg.services.Connection

object BGGSearchService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2/search"

  fun search(query: String) = Connection.get("$Api?query='$query'&type=boardgame")
}
