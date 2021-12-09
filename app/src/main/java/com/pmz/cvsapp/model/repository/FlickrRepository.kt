package com.pmz.cvsapp.model.repository

import android.content.Context
import android.util.Log
import com.pmz.cvsapp.R
import com.pmz.cvsapp.di.IoDispatcher
import com.pmz.cvsapp.model.local.dao.SearchQueryDao
import com.pmz.cvsapp.model.local.entity.SearchQuery
import com.pmz.cvsapp.utils.Query
import com.pmz.cvsapp.model.remote.retrofitservice.FlickrService
import com.pmz.cvsapp.utils.ApiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class FlickrRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val searchQueryDao: SearchQueryDao,
    private val flickrService: FlickrService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getAllSearchQueries() = searchQueryDao.getSearchQueries().flowOn(ioDispatcher)

    fun fetchImages(query: String) = flow {
        emit(ApiState.Loading)
        val apiState = try {
            val response = flickrService.getImages(Query(query).asMap())
            if (response.isSuccessful && response.body() != null) {
                // Insert latest query if successful
                searchQueryDao.insertLatestQuery(SearchQuery(query = query))

                ApiState.Success(response.body()!!)
            } else {
                ApiState.Failure(context.getString(R.string.failed_to_fetch_data))
            }
        } catch (ex: Exception) {
            Log.d("Repo", ex.toString())
            ApiState.Failure(context.getString(R.string.unexpected_eror))
        }
        emit(apiState)
    }.flowOn(ioDispatcher)

}