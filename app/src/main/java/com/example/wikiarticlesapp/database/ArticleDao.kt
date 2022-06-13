package com.example.wikiarticlesapp.database
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.wikiarticlesapp.models.Item
//
//@Dao
//interface ArticleDao {
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun upsert(article:Item):Long
//
//    @Query("SELECT * FROM articles")
//    fun getAllArticles(): LiveData<List<Item>>
//
//    @Delete
//    suspend fun deleteArticles(article: Item)
//}