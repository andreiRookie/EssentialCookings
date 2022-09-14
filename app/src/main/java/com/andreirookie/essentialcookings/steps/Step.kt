package com.andreirookie.essentialcookings.steps

import java.io.Serializable

data class Step(
    //val id: Long,
    val content: String,
    val image: String? = null
) : Serializable