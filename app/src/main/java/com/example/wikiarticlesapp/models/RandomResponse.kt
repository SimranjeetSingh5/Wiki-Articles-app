package com.example.wikiarticlesapp.models

import com.google.gson.annotations.SerializedName

data class RandomResponse (

    @SerializedName("continue" ) var continueArticles : ContinueRan? = ContinueRan(),
    @SerializedName("warnings" ) var warnings : Warnings? = Warnings(),
    @SerializedName("query"    ) var query    : QueryRan?    = QueryRan()
        )

data class ContinueRan (

    @SerializedName("imcontinue"  ) var imcontinue  : String? = null,
    @SerializedName("grncontinue" ) var grncontinue : String? = null,
    @SerializedName("continue"    ) var continueRan    : String? = null
)

data class Main (

    @SerializedName("*" ) var starMain : String? = null

)
data class Warnings (

    @SerializedName("main"      ) var main      : Main?      = Main(),
    @SerializedName("revisions" ) var revisions : Revisions? = Revisions()

)
data class Images (

    @SerializedName("ns"    ) var ns    : Int?    = null,
    @SerializedName("title" ) var title : String? = null

)
data class Revisions (

    @SerializedName("contentformat" ) var contentformat : String? = null,
    @SerializedName("contentmodel"  ) var contentmodel  : String? = null,
    @SerializedName("*"             ) var starRev             : String? = null

)
data class QueryRan (

    @SerializedName("pages" ) var pages : MutableMap<String,RanItem> = mutableMapOf()

)

data class RanItem (

    @SerializedName("pageid"    ) var pageid    : Int?                 = null,
    @SerializedName("ns"        ) var ns        : Int?                 = null,
    @SerializedName("title"     ) var title     : String?              = null,
    @SerializedName("revisions" ) var revisions : MutableList<Revisions> = mutableListOf(),
    @SerializedName("images" ) var images : MutableList<Images> = mutableListOf()

    )
