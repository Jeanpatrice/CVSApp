package com.pmz.cvsapp.model.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.pmz.cvsapp.model.local.dao.SearchQueryDao
import com.pmz.cvsapp.model.local.entity.SearchQuery
import junit.framework.TestCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlickrDatabaseTest : TestCase(){

    private lateinit var db: FlickrDatabase
    private lateinit var dao: SearchQueryDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, FlickrDatabase::class.java ).build()
        dao = db.getSearchQueryDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    public fun getSearchQueryDao() = runBlocking {
        val search = SearchQuery(1, "worm")
        dao.insertQuery(search)
        val history = dao.getSearchQueries()
        assertThat(history.firstOrNull()).contains(search)
    }
}