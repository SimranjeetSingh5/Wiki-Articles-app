package com.example.wikiarticlesapp.database

import androidx.room.TypeConverter
import com.example.wikiarticlesapp.models.Imageinfo

class Convertors {

    @TypeConverter
    fun fromSource(imageInfo:Imageinfo): String? {

        return imageInfo.url

    }
    @TypeConverter
    fun toSource(imageUrl:String): Imageinfo? {

        return Imageinfo(null,null,imageUrl,null,null)

    }
}