package com.ubi.bgg.services.bgg.user

import com.ubi.bgg.services.Connection
import kotlinx.coroutines.runBlocking
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory

private class BGGLoginResponseParser : DefaultHandler() {
  var id: Int? = null

  override fun startElement(
    uri: String?,
    ignored: String?,
    query: String?,
    attributes: Attributes,
  ) {
    this.attributes = attributes
    if (query == "user") id = attr("id")?.toIntOrNull()
  }

  private var attributes: Attributes? = null
  private fun attr(name: String): String? =
    runCatching { attributes?.getValue(name) }.getOrNull()
}

private fun isUser(xml: String?) = BGGLoginResponseParser().apply {
  SAXParserFactory.newInstance().newSAXParser().parse(InputSource(StringReader(xml)), this)
}.id != null

object BGGUserService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2"

  fun exists(username: String) = runBlocking {
    return@runBlocking isUser(Connection.get("$Api/users?name=$username").orEmpty())
  }

  fun get(username: String) = runBlocking {
    Connection.get("$Api/collection?username=$username&stats=1")
  }
}
