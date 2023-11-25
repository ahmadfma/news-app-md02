package com.makassar.newsapp.ui.activities.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.makassar.newsapp.data.repository.ArticleRepository
import com.makassar.newsapp.data.source.local.room.article.ArticleEntity

class FavoriteViewModel(private val articleRepository: ArticleRepository): ViewModel() {

    fun getArticleFavorites() = articleRepository.getAllFavoriteArticles()

}