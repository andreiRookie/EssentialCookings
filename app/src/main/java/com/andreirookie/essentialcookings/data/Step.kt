package com.andreirookie.essentialcookings.data

import android.net.Uri

data class Step(
    val title: String,
    val content: String,
    val image: Uri? = null
) {
}