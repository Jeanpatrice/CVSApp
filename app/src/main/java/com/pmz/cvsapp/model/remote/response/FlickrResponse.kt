package com.pmz.cvsapp.model.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    val title : String,
    val description : String?,
    val items : List<Item>
)