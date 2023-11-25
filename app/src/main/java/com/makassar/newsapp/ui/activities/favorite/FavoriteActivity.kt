package com.makassar.newsapp.ui.activities.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makassar.newsapp.R
import com.makassar.newsapp.databinding.ActivityFavoriteBinding
import com.makassar.newsapp.ui.adapter.NewsAdapter
import com.makassar.newsapp.utils.Mapper
import com.makassar.newsapp.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[FavoriteViewModel::class.java]

        val adapter = NewsAdapter {}

        with(binding) {
            rvArticles.setHasFixedSize(true)
            rvArticles.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvArticles.adapter = adapter
        }

        viewModel.getArticleFavorites().observe(this) {
            val list = Mapper.listArticleEntityToArticles(it)
            adapter.submitList(list)
        }

    }

}