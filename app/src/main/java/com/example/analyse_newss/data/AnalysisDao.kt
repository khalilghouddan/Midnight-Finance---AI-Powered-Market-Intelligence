package com.example.analyse_newss.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.analyse_newss.model.Analysis
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {
    @Query("SELECT * FROM saved_analyses ORDER BY timestamp DESC")
    fun getAllAnalyses(): Flow<List<Analysis>>

    @Insert
    suspend fun insertAnalysis(analysis: Analysis)

    @Delete
    suspend fun deleteAnalysis(analysis: Analysis)
}
