package com.pmz.cvsapp.utils

data class Query(
    var tags : String,
    val format: String = "json",
    val nojsoncallback: String = "1"
) {
    fun asMap(): Map<String, String> {
        return mapOf("format" to format, "nojsoncallback" to nojsoncallback, "tags" to tags)
    }
}