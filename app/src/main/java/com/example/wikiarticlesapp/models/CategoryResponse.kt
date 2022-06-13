package com.example.wikiarticlesapp.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @SerializedName("batchcomplete" ) var batchcomplete : String?   = null,
    @SerializedName("continue"      ) var continueArticles      : ContinueCat? = ContinueCat(),
    @SerializedName("query"         ) var query         : QueryCat = QueryCat()
)

data class ContinueCat(

    @SerializedName("accontinue" ) var accontinue : String? = null,
    @SerializedName("continue"   ) var continueCat   : String? = null
)
class QueryCat {

    @SerializedName("allcategories" ) var allcategories : MutableList<Allcategories> = mutableListOf()
}

data class Allcategories (
    @SerializedName("category" ) var category : String? = null
)
