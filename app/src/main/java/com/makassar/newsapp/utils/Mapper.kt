package com.makassar.newsapp.utils

import com.makassar.newsapp.data.models.Article
import com.makassar.newsapp.data.source.local.room.article.ArticleEntity

object Mapper {

    fun articleToEntity(article: Article): ArticleEntity {
        return ArticleEntity(
            id = 0,
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )
    }

    fun articleEntityToArticle(articleEntity: ArticleEntity): Article {
        return Article(
            author = articleEntity.author,
            content = articleEntity.content,
            description = articleEntity.description,
            publishedAt = articleEntity.publishedAt,
            source = null,
            title = articleEntity.title,
            url = articleEntity.url,
            urlToImage = articleEntity.urlToImage,
            isFavorite = true
        )
    }

    fun listArticleEntityToArticles(entities: List<ArticleEntity>): List<Article> {
        val result = mutableListOf<Article>()
        for(entity in entities) {
            result.add(articleEntityToArticle(entity))
        }
        return result
    }

}