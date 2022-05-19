package com.ubi.bga.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ubi.bga.R
import kotlinx.coroutines.*
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.util.stream.Collectors
import javax.xml.parsers.SAXParserFactory

class BGGThingResponseParser : DefaultHandler() {
  public data class Rank(
    var id: Int? = null,
    var name: String? = null,
    var value: Int? = null,
    var average: Double? = null,
    var type: String? = null,
  )

  var id: Int? = null
  var thumbnail: String? = null
  var image: String? = null
  var description: String? = null
  var yearpublished: Int? = null
  var names: MutableList<String> = mutableListOf()
  var alternates: MutableList<String> = mutableListOf()
  var ranks: MutableList<Rank> = mutableListOf()

  override fun characters(characters: CharArray?, start: Int, length: Int) {
    element.append(characters, start, length)
  }

  override fun startElement(
    uri: String?,
    ignored: String?,
    query: String?,
    attributes: Attributes,
  ) {
    this.attributes = attributes
    when (query) {
      "name" -> when (attr("type")) {
        "primary" -> names.add(attr("value").orEmpty())
        "alternate" -> alternates.add(attr("value").orEmpty())
      }
      "yearpublished" -> yearpublished = attr("value")?.toIntOrNull()
      "rank" -> ranks.add(Rank(
        id = attr("id")?.toIntOrNull(),
        type = attr("type"),
        name = attr("name"),
        value = attr("value")?.toIntOrNull(),
        average = attr("bayesaverage")?.toDouble(),
      ))
      "item" -> id = attr("id")?.toIntOrNull()
    }
  }

  override fun endElement(uri: String?, ignored: String?, query: String?) {
    when (query) {
      "thumbnail" -> thumbnail = consumeText()
      "image" -> image = consumeText()
      "description" -> description = consumeText()
    }
  }


  private var attributes: Attributes? = null
  private fun attr(name: String): String? = try {
    attributes?.getValue(name)
  } catch (ignored: RuntimeException) {
    null
  }

  private fun consumeText() = "$element".trim().also { element.clear() }
  private var element: StringBuilder = StringBuilder()
}

fun parseXml(xml: String): Any {
  val spf = SAXParserFactory.newInstance()
  val sp = spf.newSAXParser()
  val parser = BGGThingResponseParser()
  sp.parse(InputSource(StringReader(xml)), parser)

  println(parser.id)
  println(parser.thumbnail)
  println(parser.image)
  println(parser.description)
  println(parser.yearpublished)
  println(parser.names)
  println(parser.alternates)
  println(parser.ranks)

  return parser.thumbnail!!
}

object Service {
  fun get(api: String): String? {
    return try {
      val connection = URL(api).openConnection() as HttpURLConnection
      return when (connection.responseCode) {
        HTTP_OK -> connection.inputStream
          .bufferedReader()
          .lines()
          .collect(Collectors.joining(System.lineSeparator()))
        else -> null
      }
    } catch (ignored: IOException) {
      null
    }
  }
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Common.initialize(applicationContext)

    val api = "https://boardgamegeek.com/xmlapi2/thing?id=154204&stats=1"

    lifecycleScope.launch {
      withContext(Dispatchers.IO) { println(Service.get(api)?.let { parseXml(it) }) }
    }

    println("No maidens...")
  }

}
