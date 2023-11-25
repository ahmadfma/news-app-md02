package com.makassar.newsapp.ui.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makassar.newsapp.R
import com.makassar.newsapp.data.Result
import com.makassar.newsapp.databinding.ActivityMainBinding
import com.makassar.newsapp.ui.activities.favorite.FavoriteActivity
import com.makassar.newsapp.ui.activities.objectDetection.ObjectDetectionActivity
import com.makassar.newsapp.ui.adapter.NewsAdapter
import com.makassar.newsapp.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[MainViewModel::class.java]
        newsAdapter = NewsAdapter { article ->
            viewModel.insertNewArticle(article)
        }
        setContentView(binding.root)
        initView()
        callNews()
    }

    private fun initView() = with(binding) {
        rvNews.setHasFixedSize(true)
        rvNews.layoutManager = LinearLayoutManager(this@MainActivity)
        rvNews.adapter = newsAdapter
    }

    private fun callNews() {
        viewModel.getNews(country = "us", category = "business").observe(this) {result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvNews.visibility = View.GONE
                }
                is Result.Error -> {
                    Toast.makeText(this, result.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    val items = result.data?.articles
                    if(!items.isNullOrEmpty()) {
                        newsAdapter.submitList(items)
                    } else {
                        //TAMPILIN TIDAK ADA DATA
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.rvNews.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favMenu -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.cameraMenu -> {
                startActivity(Intent(this, ObjectDetectionActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}