package com.ubi.bgg.utils

import android.view.Gravity
import android.widget.Toast
import com.ubi.bgg.Common

fun showToast(text: String) {
  val toast = Toast.makeText(Common.Context, text, Toast.LENGTH_SHORT)
  toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
  toast.show()
}
