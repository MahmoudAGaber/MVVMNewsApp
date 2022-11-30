package com.androiddevs.mvvmnewsapp.ui.repository

import com.androiddevs.mvvmnewsapp.ui.DB.ArticleDataBase
import com.androiddevs.mvvmnewsapp.ui.api.RetrofitInstance

class NewsRepository(
    val db:ArticleDataBase
) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int) =
        RetrofitInstance.api.getBrakingNews(countryCode,pageNumber)

    suspend fun seachNews(searchQuery:String,pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)
}