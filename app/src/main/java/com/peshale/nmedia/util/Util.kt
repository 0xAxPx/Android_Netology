package com.peshale.nmedia.util

import android.widget.TextView

object Util {
    fun counter(textView: TextView, current: Int) {
        if (current.toString().length == 4) {
            val toDbl = current / 1000.0
            textView.text = toDbl.toString() + "K"
        } else if (current.toString().length == 5 || current.toString().length == 6) {
            val toDbl = current / 1000
            textView.text = toDbl.toString() + "K"
        } else if (current.toString().length == 7) {
            val toDbl = current / 1_000_000.0
            textView.text = toDbl.toString().substring(0, 3) + "M"
        } else {
            textView.text = current.toString()
        }
    }
}