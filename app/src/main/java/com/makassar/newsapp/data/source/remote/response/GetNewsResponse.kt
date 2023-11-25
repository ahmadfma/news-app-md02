package com.makassar.newsapp.data.source.remote.response


import com.google.gson.annotations.SerializedName
import com.makassar.newsapp.data.models.Article

data class GetNewsResponse(
    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("totalResults")
    val totalResults: Int?
)