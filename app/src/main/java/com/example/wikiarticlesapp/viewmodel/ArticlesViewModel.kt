package com.example.wikiarticlesapp.viewmodel

//import com.example.wikiarticlesapp.database.ArticleDatabase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.tools.build.jetifier.core.utils.Log
import com.example.wikiarticlesapp.models.*
import com.example.wikiarticlesapp.repository.ArticlesRepository
import com.example.wikiarticlesapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.Array
import java.util.*


class ArticlesViewModel (
     val repository: ArticlesRepository
) : ViewModel() {

    val articles: MutableLiveData<Resource<ArticlesResponse?>?> = MutableLiveData()
    var articlesResponse:ArticlesResponse? = null
    val randomArticles: MutableLiveData<Resource<RandomResponse>> = MutableLiveData()
    var randomResponse:RandomResponse? = null
    val category: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    var categoryResponse:CategoryResponse? = null
    var gcmcontinue = ""
    var continueArt = ""
    var acconti = ""
    var continueCat = ""

    init {
        getArticles()
        getRandomArticles()
        getCategory()
    }

    fun getArticles() =
        viewModelScope.launch {
            articles.postValue(Resource.Loading())
            val response = repository.getArticles(gcmcontinue, continueArt)
            articles.postValue(handleArticleResponse(response))

        }


    fun getRandomArticles() =
        viewModelScope.launch {
            randomArticles.postValue(Resource.Loading())
            val response = repository.getRandomArticles()
            randomArticles.postValue(handleRandomResponse(response))
        }

    fun getCategory() =
        viewModelScope.launch {
            category.postValue(Resource.Loading())
            val response = repository.getCategory(acconti,continueCat)
            category.postValue(handleCategoryResponse(response))
        }


    private fun handleArticleResponse(response: Response<ArticlesResponse>): Resource<ArticlesResponse?>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->

                gcmcontinue = resultResponse.continueArticles?.gcmcontinue.toString()
                continueArt = resultResponse.continueArticles?.continueArticles.toString()

                if (articlesResponse == null) {
                    articlesResponse = resultResponse
                }
                else{
                    var oldArticles = articlesResponse?.query?.pages
                    val newArticles= resultResponse.query.pages

                    oldArticles?.putAll(newArticles)
                }
                return  Resource.Success(articlesResponse ?: resultResponse)
            }
        }
        return  Resource.Error(response.message())
    }
    private fun handleRandomResponse(response: Response<RandomResponse>): Resource<RandomResponse> {
        if (response.isSuccessful){

            response.body()?.let{ranResponse->
                if (randomResponse == null) {
                    randomResponse = ranResponse
                }else{
                    var old = randomResponse!!.query?.pages
                    val new = ranResponse!!.query?.pages
                    old?.putAll(new!!)

                }

                return  Resource.Success(randomResponse ?: ranResponse)
            }
        }

        return  Resource.Error(response.message())
    }

    private fun handleCategoryResponse(response: Response<CategoryResponse>): Resource<CategoryResponse> {
        if (response.isSuccessful){
            response.body()?.let {catResponse->

                acconti = catResponse.continueArticles?.accontinue.toString()
                continueCat = catResponse.continueArticles?.continueCat.toString()

                if (categoryResponse == null){
                    categoryResponse = catResponse
                }else{
                    val oldCat = categoryResponse?.query?.allcategories
                    val newCat = catResponse.query.allcategories
                    oldCat?.addAll(newCat!!)
                }
                return  Resource.Success(categoryResponse ?: catResponse)
            }
        }
        return  Resource.Error(response.message())
    }

}
