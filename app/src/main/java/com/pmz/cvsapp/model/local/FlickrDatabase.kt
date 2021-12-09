package com.pmz.cvsapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pmz.cvsapp.model.local.dao.SearchQueryDao
import com.pmz.cvsapp.model.local.entity.SearchQuery

@Database(entities = [SearchQuery::class], version = 1, exportSchema = true)
abstract class FlickrDatabase: RoomDatabase() {

    abstract fun getSearchQueryDao(): SearchQueryDao

}