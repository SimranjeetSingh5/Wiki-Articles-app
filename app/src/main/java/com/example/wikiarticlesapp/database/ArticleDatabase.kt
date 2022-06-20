//package com.example.wikiarticlesapp.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.example.wikiarticlesapp.models.Item
//
//@Database(
//    entities = [Item::class],
//    version = 1)
//@TypeConverters(Convertors::class)
//abstract class ArticleDatabase :RoomDatabase(){
//
//    abstract fun getArticlesDao():ArticleDao
//
//    companion object{
//        @Volatile
//        private var instance :ArticleDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
//
//            instance ?: createDatabase(context).also{
//                instance = it
//            }
//
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//            context.applicationContext,
//            ArticleDatabase::class.java,
//            "articles_db_db"
//            ).build()
//    }
//}