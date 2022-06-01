package com.ubi.bgg.services.bgg.user

import com.ubi.bgg.services.Connection
import com.ubi.bgg.utils.XMLParser
import kotlinx.coroutines.runBlocking
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory

private fun isUser(xml: String?): Boolean = object : XMLParser() {
  var id: Int? = null

  override fun start(query: String?) {
    if (query == "user") id = attr("id")?.toIntOrNull()
  }
}.apply { parse(xml) }.id != null

private fun handleCollectionXML(xml: String?) = object : XMLParser() {

  override fun start(query: String?) {

  }

  override fun end(query: String?) {

  }
}.apply { parse(xml) }

object BGGUserService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2"

  fun exists(username: String) = runBlocking {
    isUser(Connection.get("$Api/users?name=$username").orEmpty())
  }

  fun collection(username: String) = runBlocking {
    handleCollectionXML(Connection.get("$Api/collection?username=$username&stats=1"))
  }
}
