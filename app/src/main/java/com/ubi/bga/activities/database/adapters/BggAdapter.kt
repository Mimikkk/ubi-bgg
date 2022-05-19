package com.ubi.bga.activities.database.adapters

import android.util.Xml
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

fun migrate(xml: String) {
  val spf = SAXParserFactory.newInstance()
  val sp = spf.newSAXParser()
  val xr = sp.xmlReader

  xr. parse(xml)
}

class BggAdapter {
}