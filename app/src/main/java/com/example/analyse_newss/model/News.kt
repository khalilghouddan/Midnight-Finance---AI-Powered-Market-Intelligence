package com.example.analyse_newss.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val source: String,
    val description: String = "No description available",
    val url: String = "https://example.com"
) : Parcelable
