package com.pmz.cvsapp.model.remote.retrofitservice

import com.pmz.cvsapp.model.remote.response.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FlickrService {

    @GET("services/feeds/photos_public.gne")
    suspend fun getImages(
        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ): Response<FlickrResponse>
}