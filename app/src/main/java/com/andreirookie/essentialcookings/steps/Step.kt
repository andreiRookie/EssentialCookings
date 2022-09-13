package com.andreirookie.essentialcookings.steps

import android.net.Uri

data class Step(
    val id: Long,
    val content: String,
    val image: String? = null
)