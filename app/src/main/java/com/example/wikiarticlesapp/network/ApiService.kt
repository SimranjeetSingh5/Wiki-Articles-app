package com.example.wikiarticlesapp.network

import com.example.wikiarticlesapp.utils.Constants
import com.example.wikiarticlesapp.models.ArticlesResponse
import com.example.wikiarticlesapp.models.CategoryResponse
import com.example.wikiarticlesapp.models.RandomResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.*

interface ApiService {

        @GET(Constants.FEATURED_URL)
        suspend fun getArticles(

                @Query("gcmcontinue") gcm:String?,
                @Query("continue") conti:String?
        ): Response<ArticlesResponse>

        @GET
        suspend fun getRandomArticles(
            @Url url:String?)
        :Response<RandomResponse>

        @GET
        suspend fun getCategory(@Url url:String,
                                @Query("accontinue") acconti:String?,
                                @Query("continue") conti:String?
        ):Response<CategoryResponse>

     }