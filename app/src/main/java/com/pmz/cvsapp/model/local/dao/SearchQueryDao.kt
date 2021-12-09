package com.pmz.cvsapp.model.local.dao

import androidx.room.*
import com.pmz.cvsapp.model.local.entity.SearchQuery
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchQueryDao {

    @Query("SELECT * FROM search_query")
    abstract fun getSearchQueries(): Flow<List<SearchQuery>>

    @Insert
    abstract suspend fun insertQuery(query: SearchQuery)

    @Query("DELETE FROM search_query where id NOT IN (SELECT id from search_query ORDER BY id DESC LIMIT 5)")
    abstract suspend fun deleteOldestQueries()

    @Transaction
    open suspend fun insertLatestQuery(query: SearchQuery) {
        insertQuery(query)
        deleteOldestQueries()
    }
}