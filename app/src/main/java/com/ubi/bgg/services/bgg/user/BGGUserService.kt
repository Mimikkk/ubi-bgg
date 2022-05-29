package com.ubi.bgg.services.bgg.user

import com.ubi.bgg.services.Connection

object BGGUserService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2/collection"

  fun get(username: String) = Connection.get("$Api?username='$username'&stats=1")
}
