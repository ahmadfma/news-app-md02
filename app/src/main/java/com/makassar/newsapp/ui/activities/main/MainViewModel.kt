package com.makassar.newsapp.ui.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makassar.newsapp.data.models.Article
import com.makassar.newsapp.data.repository.ArticleRepository
import com.makassar.newsapp.utils.Mapper
import kotlinx.coroutines.launch

class MainViewModel(private val articleRepository: ArticleRepository): ViewModel() {

    fun getNews(country: String, category: String) = articleRepository.getNews(country, category)

    fun insertNewArticle(article: Article) = viewModelScope.launch {
        articleRepository.insertNews(Mapper.articleToEntity(article))
    }

}