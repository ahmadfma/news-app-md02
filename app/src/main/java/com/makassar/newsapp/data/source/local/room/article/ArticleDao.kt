package com.makassar.newsapp.data.source.local.room.article

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article_favorite")
    fun getAllFavoriteArticles(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewArticle(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteArticle(articleEntity: ArticleEntity)

    @Query("SELECT COUNT(*) FROM article_favorite WHERE publishedAt = :publishedAt")
    suspend fun checkIfExist(publishedAt: String): Int

}