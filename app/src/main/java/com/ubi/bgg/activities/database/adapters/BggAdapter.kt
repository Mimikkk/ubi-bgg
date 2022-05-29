package com.ubi.bgg.activities.database.adapters

import javax.xml.parsers.SAXParserFactory

fun migrate(xml: String) {
  val spf = SAXParserFactory.newInstance()
  val sp = spf.newSAXParser()
  val xr = sp.xmlReader

  xr. parse(xml)
}

class BggAdapter {
}