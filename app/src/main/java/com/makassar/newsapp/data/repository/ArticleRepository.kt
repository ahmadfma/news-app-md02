package com.makassar.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.makassar.newsapp.data.Result
import com.makassar.newsapp.data.source.local.room.article.ArticleDao
import com.makassar.newsapp.data.source.local.room.article.ArticleEntity
import com.makassar.newsapp.data.source.remote.ApiService
import com.makassar.newsapp.data.source.remote.response.GetNewsResponse

class ArticleRepository(private val apiService: ApiService, private val articleDao: ArticleDao) {

    fun getAllFavoriteArticles() = articleDao.getAllFavoriteArticles()

    suspend fun insertNewArticle(articleEntity: ArticleEntity) = articleDao.insertNewArticle(articleEntity)

    suspend fun deleteArticle(articleEntity: ArticleEntity) = articleDao.deleteArticle(articleEntity)

    suspend fun checkIfExist(publishedAt: String): Boolean {
        val amount = articleDao.checkIfExist(publishedAt)
        return amount > 0
    }

    fun getNews(country: String, category: String): LiveData<Result<GetNewsResponse?>> = liveData {
        emit(Result.Loading())
        try {
            val response = apiService.getNews(country, category)
            if(response.code() == 200) {
                emitSource(MutableLiveData(Result.Success(response.body())))
            } else {
                emit(Result.Error("Terjadi kesalahan saat memuat berita"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun insertNews(articleEntity: ArticleEntity) = articleDao.insertNewArticle(articleEntity)

}