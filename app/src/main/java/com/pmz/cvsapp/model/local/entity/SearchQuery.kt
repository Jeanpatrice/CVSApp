package com.pmz.cvsapp.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query")
data class SearchQuery(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val query: String
)
