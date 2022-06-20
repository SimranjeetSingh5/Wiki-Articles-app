package com.example.wikiarticlesapp.database

import androidx.room.TypeConverter
import com.example.wikiarticlesapp.models.Imageinfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Convertors {

//    @TypeConverter
//    fun fromSource(imageInfo:Imageinfo): String? {
//
//        return imageInfo.url
//
//    }
//    @TypeConverter
//    fun toSource(imageUrl:String): Imageinfo? {
//
//        return Imageinfo(null,null,imageUrl,null,null)
//
//    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Imageinfo?>? {
        val listType: Type = object : TypeToken<ArrayList<Imageinfo?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Imageinfo?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}