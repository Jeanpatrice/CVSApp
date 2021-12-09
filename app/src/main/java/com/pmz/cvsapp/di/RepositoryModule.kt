package com.pmz.cvsapp.di

import android.content.Context
import com.pmz.cvsapp.model.local.dao.SearchQueryDao
import com.pmz.cvsapp.model.remote.retrofitservice.FlickrService
import com.pmz.cvsapp.model.repository.FlickrRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesFlickrRepository(
        @ApplicationContext context: Context,
        searchQueryDao: SearchQueryDao,
        flickrService: FlickrService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FlickrRepository {
        return FlickrRepository(context, searchQueryDao, flickrService, ioDispatcher)
    }

}