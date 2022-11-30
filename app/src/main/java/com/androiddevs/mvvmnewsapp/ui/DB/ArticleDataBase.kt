package com.androiddevs.mvvmnewsapp.ui.DB

import android.content.Context
import androidx.room.*
import com.androiddevs.mvvmnewsapp.ui.models.Article
import java.security.AccessControlContext


@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converter::class)

 abstract  class ArticleDataBase : RoomDatabase() {

     abstract fun getArticleDao():ArticleDao

     companion object{
         @Volatile
         private var instance: ArticleDataBase?=null
         private val Lock = Any()

         operator fun invoke(context: Context) = instance?: synchronized(Lock){

             instance?:CreateDataBase(context).also{ instance = it}
         }


         private fun  CreateDataBase(context: Context) =
             Room.databaseBuilder(
                 context.applicationContext,
                 ArticleDataBase::class.java,
                 "article_db.db"
             ).build()
     }
}