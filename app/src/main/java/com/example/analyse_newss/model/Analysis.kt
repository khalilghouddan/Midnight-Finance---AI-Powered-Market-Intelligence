package com.example.analyse_newss.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_analyses")
data class Analysis(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val newsTitle: String,
    val newsSource: String,
    val summary: String,
    val timestamp: Long = System.currentTimeMillis()
)
