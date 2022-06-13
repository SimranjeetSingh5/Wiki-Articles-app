package com.example.wikiarticlesapp.repository

//import com.example.wikiarticlesapp.database.ArticleDatabase
import com.example.wikiarticlesapp.utils.Constants
import com.example.wikiarticlesapp.network.RetrofitInstance


class ArticlesRepository constructor(
//    private val db:ArticleDatabase
){
    suspend fun getArticles(gcm:String,conti:String) = RetrofitInstance.api.getArticles(gcm,conti)

    suspend fun getRandomArticles(imconti:String,grnconti:String,conti:String) = RetrofitInstance.api.getRandomArticles(imconti,grnconti,conti)

    suspend fun getCategory(acconti:String,conti:String) = RetrofitInstance.api.getCategory((Constants.CATEGORY_URL),acconti, conti)

}