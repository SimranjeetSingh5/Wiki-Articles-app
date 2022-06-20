package com.example.wikiarticlesapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleCacheEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pageid")
    var pageId:String,

    @ColumnInfo(name = "title")
    var title:String,

    @ColumnInfo(name = "description")
    var description:String


)