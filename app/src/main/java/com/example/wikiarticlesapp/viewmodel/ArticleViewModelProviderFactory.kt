package com.example.wikiarticlesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wikiarticlesapp.repository.ArticlesRepository

class ArticleViewModelProviderFactory(
    private val articleRepository:ArticlesRepository
    ):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticlesViewModel(articleRepository) as T
    }
}