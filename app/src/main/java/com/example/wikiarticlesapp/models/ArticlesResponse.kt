package com.example.wikiarticlesapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList


data class ArticlesResponse(
    @SerializedName("batchcomplete" ) var batchcomplete : String?   = null,
    @SerializedName("continue"      ) var continueArticles      : Continue? = Continue(),
    @SerializedName("query"         ) var query         : Query = Query()
)

data class Query(

    @SerializedName("pages") var pages: MutableMap<String,Item> = mutableMapOf()
    )



data class Continue (

    @SerializedName("gcmcontinue" ) var gcmcontinue : String? = null,
    @SerializedName("continue"    ) var continueArticles    : String? = null
)

//@Entity(tableName = "articles")
data class Item (

    @PrimaryKey(autoGenerate = true)
    @SerializedName("pageid"          ) var pageid          : Int?                 = null,

    @SerializedName("ns"              ) var ns              : Int?                 = null,
//    @ColumnInfo(name = "title")
    @SerializedName("title"           ) var title           : String?              = null,
    @SerializedName("imagerepository" ) var imagerepository : String?              = null,
//    @ColumnInfo(name = "myData_array")
    @SerializedName("imageinfo"       ) var imageinfo       : MutableList<Imageinfo>           = mutableListOf()
)

data class Imageinfo (

    @SerializedName("timestamp"           ) var timestamp           : String? = null,
    @SerializedName("user"                ) var user                : String? = null,
    @SerializedName("url"                 ) var url                 : String? = null,
    @SerializedName("descriptionurl"      ) var descriptionurl      : String? = null,
    @SerializedName("descriptionshorturl" ) var descriptionshorturl : String? = null
)
