package com.makassar.newsapp.injection

import android.content.Context
import com.makassar.newsapp.data.repository.ArticleRepository
import com.makassar.newsapp.data.repository.ObjectDetectionRepository
import com.makassar.newsapp.data.source.local.room.article.AppDatabase
import com.makassar.newsapp.data.source.local.room.article.ArticleDao
import com.makassar.newsapp.data.source.remote.ApiConfig
import com.makassar.newsapp.data.source.remote.ApiService

object Injection {

    private fun provideArticleDao(context: Context): ArticleDao {
        return AppDatabase.getDatabase(context).articleDao()
    }

    private fun providerRetrofit(): ApiService {
        return ApiConfig.getApiService()
    }

    fun providerArticleRepository(context: Context): ArticleRepository {
        return ArticleRepository(providerRetrofit(),  provideArticleDao(context))
    }

    fun provideObjectDetectionRepository(context: Context): ObjectDetectionRepository {
        return ObjectDetectionRepository(context)
    }

}