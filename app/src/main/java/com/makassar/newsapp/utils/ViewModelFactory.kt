package com.makassar.newsapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makassar.newsapp.data.repository.ArticleRepository
import com.makassar.newsapp.data.repository.ObjectDetectionRepository
import com.makassar.newsapp.injection.Injection
import com.makassar.newsapp.ui.activities.favorite.FavoriteViewModel
import com.makassar.newsapp.ui.activities.main.MainViewModel
import com.makassar.newsapp.ui.activities.objectDetection.ObjectDetectionViewModel

class ViewModelFactory private constructor(
    private val articleRepository: ArticleRepository,
    private val objectDetectionRepository: ObjectDetectionRepository,
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(articleRepository) as T
        }
        else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(articleRepository) as T
        }
        else if(modelClass.isAssignableFrom(ObjectDetectionViewModel::class.java)) {
            return ObjectDetectionViewModel(objectDetectionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.providerArticleRepository(context),
                Injection.provideObjectDetectionRepository(context)
            )
        }.also {
            instance = it
        }
    }
}