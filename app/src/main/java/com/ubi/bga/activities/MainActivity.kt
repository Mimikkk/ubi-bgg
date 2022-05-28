package com.ubi.bga.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ubi.bga.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import javax.xml.parsers.SAXParserFactory

class BGGThingResponseParser : DefaultHandler() {
  data class Rank(
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

object Connection {
  fun get(url: String): String? {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    return connection.connect().runCatching {
      return when (connection.responseCode) {
        HTTP_OK -> connection.inputStream.bufferedReader().use { it.readText() }
        else -> null
      }
    }.getOrNull().also {
      connection.disconnect()
    }
  }
}

object BggThingService {
  private const val Api = "https://www.boardgamegeek.com/xmlapi2/thing"

  fun get(id: Int) = Connection.get("$Api?id=$id")
}

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Common.initialize(applicationContext)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.parseButton.setOnClickListener {
      binding.thingId.text.toString().toIntOrNull()?.let {
        runBlocking {
          withContext(Dispatchers.IO) {
            BggThingService.get(it)?.let {
              parseXml(it)
            }
          }
        }
      }

    }
  }
}
