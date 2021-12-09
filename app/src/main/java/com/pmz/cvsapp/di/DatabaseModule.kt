package com.pmz.cvsapp.di

import android.content.Context
import androidx.room.Room
import com.pmz.cvsapp.model.local.FlickrDatabase
import com.pmz.cvsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesFlickrDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FlickrDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesSearchQueryDao(db: FlickrDatabase) = db.getSearchQueryDao()

}