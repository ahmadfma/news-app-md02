package com.makassar.newsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makassar.newsapp.data.models.Article
import com.makassar.newsapp.data.repository.ArticleRepository
import com.makassar.newsapp.databinding.ItemNewsBinding

class NewsAdapter(private val onFavClick: (Article) -> Unit): RecyclerView.Adapter<NewsAdapter.Holder>() {

    private var items = mutableListOf<Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<Article>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) = with(binding) {
            Glide.with(this.root)
                .load(article.urlToImage)
                .into(newsImage)
            newsTitleTV.text = article.title
            newsDescTV.text = article.description
            favBtn.setOnClickListener {
                onFavClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(items[position])
    }


}