package com.pmz.cvsapp.model.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media(
    val m : String
)
