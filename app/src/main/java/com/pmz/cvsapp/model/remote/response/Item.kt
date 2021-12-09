package com.pmz.cvsapp.model.remote.response

import android.os.Build
import android.text.Html
import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.jsoup.Jsoup

@JsonClass(generateAdapter = true)
data class Item(
    val title: String,
    val link : String,
    val media : Media,
    @get:Json(name = "date_taken")
    val dateTaken: String,
    val description: String,
    val published : String,
    val author: String,
    @get:Json(name = "author_id")
    val authorId: String,
    val tags: String
) {

    fun getDecodedDescription(): Map<String, String>? {
        val html = description
        val document = Jsoup.parse(html)
        val elements = document.select("body *")
        for (element in elements) {
            if ("img" == element.tagName()) {
                return mapOf(
                    "link" to element.attr("src"),
                    "height" to element.attr("height"),
                    "width" to element.attr("width"),
                    "alt" to element.attr("alt")
                )
            }
        }
        return null
    }
}
