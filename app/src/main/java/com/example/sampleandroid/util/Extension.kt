package com.example.sampleandroid.util

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun setAlbumCover(url: String, imageView: ImageView) {
    Picasso.get().load(url).into(imageView)
}

fun Date.toDisplay(): String {
    return toDisplay("dd/MM/yyyy")
}

fun Date.toDisplay(formatPattern: String): String {
    val simpleDate = SimpleDateFormat(formatPattern, Locale.getDefault())
    return simpleDate.format(this)
}

val Double.toPriceString: String
    get() = (NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this))
