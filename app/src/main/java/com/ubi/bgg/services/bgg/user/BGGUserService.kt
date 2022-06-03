package com.ubi.bgg.services.bgg.user

import com.ubi.bgg.services.Connection
import com.ubi.bgg.utils.XMLParser
import kotlinx.coroutines.runBlocking

private fun isUser(xml: String?): Boolean = object : XMLParser() {
  var id: Int? = null

  override fun start(query: String?) {
    if (query == "user") id = attr("id")?.toIntOrNull()
  }
}.apply { parse(xml) }.id != null

data class Thing(
  var id: Long? = null,
  var name: String? = null,
  var originalname: String? = null,
  var yearpublished: Int? = null,
  var image: String? = null,
  var thumbnail: String? = null,
  var bayesaverage: Double? = null,
  var rank: Int? = null,
)

private fun handleThingXML(xml: String?) = object : XMLParser() {
  val items = mutableListOf<Thing>()
  var item: Thing = Thing()

  override fun start(query: String?) {
    when (query) {
      "item" -> item = Thing(attr("objectid")?.toLong())
      "bayesaverage" -> item.bayesaverage = attr("value")?.toDoubleOrNull()
      "rank" -> if (attr("name") == "boardgame") item.rank = attr("value")?.toIntOrNull()
    }
  }

  override fun end(query: String?) {
    when (query) {
      "name" -> item.name = consumeText()
      "originalname" -> item.originalname = consumeText()
      "yearpublished" -> item.yearpublished = consumeText().toIntOrNull()
      "image" -> item.image = consumeText()
      "thumbnail" -> item.thumbnail = consumeText()
      "item" -> items.add(item)
    }
  }
}.apply { parse(xml) }

data class CollectionResponse(
  val games: List<Thing>,
  val expansions: List<Thing>
)

object BGGUserService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2"

  fun exists(username: String) = runBlocking {
    isUser(Connection.get("$Api/users?name=$username").orEmpty())
  }

  fun collection(username: String) = runBlocking {
    fun handle(xml: String?): List<Thing> = handleThingXML(xml).items
    fun url(subtype: String) = "$Api/collection?username=$username&subtype=$subtype&stats=1"

    val games = handle(Connection.get(url("boardgame"))).toMutableList()
    val expansions = handle(Connection.get(url("boardgameexpansion"))).toMutableList()

    CollectionResponse(games, expansions)
  }
}
