package com.androiddevs.mvvmnewsapp.ui.DB

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.ui.models.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source):String{
        return  source.name
    }

    @TypeConverter
    fun toSource(name:String):Source{
        return  Source(name,name)
    }
}