package com.example.wikiarticlesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikiarticlesapp.utils.Resource
import com.example.wikiarticlesapp.models.ArticlesResponse
import com.example.wikiarticlesapp.models.CategoryResponse
import com.example.wikiarticlesapp.models.RandomResponse
import com.example.wikiarticlesapp.repository.ArticlesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ArticlesViewModel (
     val repository: ArticlesRepository
) : ViewModel() {

    val articles: MutableLiveData<Resource<ArticlesResponse>> = MutableLiveData()
    var articlesResponse:ArticlesResponse? = null
    val randomArticles: MutableLiveData<Resource<RandomResponse>> = MutableLiveData()
    var randomResponse:RandomResponse? = null
    val category: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    var categoryResponse:CategoryResponse? = null
    var gcmcontinue = ""
    var continueArt = ""
    var acconti = ""
    var continueCat = ""
    var imRan = ""
    var grnRan = ""
    var continueRan = ""

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

    fun getRandomArticles()=
        viewModelScope.launch {
            randomArticles.postValue(Resource.Loading())
            val response = repository.getRandomArticles(imRan, grnRan,continueRan)
            randomArticles.postValue(handleRandomResponse(response))
        }

    fun getCategory()=
        viewModelScope.launch {
            category.postValue(Resource.Loading())
            val response = repository.getCategory(acconti,continueCat)
            category.postValue(handleCategoryResponse(response))
        }


    private fun handleArticleResponse(response: Response<ArticlesResponse>): Resource<ArticlesResponse>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->

                gcmcontinue = resultResponse.continueArticles?.gcmcontinue.toString()
                continueArt = resultResponse.continueArticles?.continueArticles.toString()

                if (articlesResponse == null)
                    articlesResponse = resultResponse
             else{
                    val oldArticles = articlesResponse?.query?.pages?.values
                    val newArticles = resultResponse.query.pages.values
                    oldArticles?.addAll(newArticles)
                }
                return  Resource.Success(articlesResponse ?: resultResponse)
            }
        }
        return  Resource.Error(response.message())
    }
    private fun handleRandomResponse(response: Response<RandomResponse>): Resource<RandomResponse> {
        if (response.isSuccessful){

            response.body()?.let{ranResponse->

                imRan = ranResponse.continueArticles?.continueRan.toString()
                grnRan = ranResponse.continueArticles?.grncontinue.toString()
                continueRan = ranResponse.continueArticles?.continueRan.toString()

                if (randomResponse == null)
                    randomResponse = ranResponse
                else{
                    val oldArticles = randomResponse?.query?.pages?.values
                    val newArticles = ranResponse.query?.pages?.values
                    if (newArticles != null) {
                        oldArticles?.addAll(newArticles)
                    }
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

                if (categoryResponse==null){
                    categoryResponse = catResponse
                }else{

                    val oldCat = categoryResponse?.query?.allcategories
                    val newCat = catResponse.query.allcategories
                    oldCat?.addAll(newCat)
                }
                return  Resource.Success(categoryResponse?:catResponse)
            }
        }
        return  Resource.Error(response.message())
    }

}
