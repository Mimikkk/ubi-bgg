package com.ubi.bgg.utils

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory

abstract class XMLParser : DefaultHandler() {
  companion object {
    fun <T : XMLParser> parse(parser: T, xml: String?) = parser.apply {
      SAXParserFactory.newInstance().newSAXParser().parse(InputSource(StringReader(xml)), this)
    }
  }

  fun parse (xml: String?) = parse(this, xml)

  open fun start(query: String?) {}

  open fun end(query: String?) {}

  override fun characters(characters: CharArray?, start: Int, length: Int) {
    element.append(characters, start, length)
  }

  override fun startElement(
    uri: String?,
    ignored: String?,
    query: String?,
    attributes: Attributes,
  ) = attributes.let { this.attributes = it }.also {
    start(query)
  }

  override fun endElement(uri: String?, ignored: String?, query: String?) = end(query)

  protected fun attr(name: String): String? =
    runCatching { attributes?.getValue(name) }.getOrNull()

  protected fun consumeText() = "$element".trim().also { element.clear() }
  private var attributes: Attributes? = null

  private var element: StringBuilder = StringBuilder()
}
