package com.itis2019.lecturerecorder.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itis2019.lecturerecorder.repository.dbEntities.DbMark

class MarksConverter {

    @TypeConverter
    fun serialize(listOfAuthors: List<DbMark>): String =
        Gson().toJson(listOfAuthors, object : TypeToken<List<DbMark>>() {}.type)

    @TypeConverter
    fun deserialize(jsonOfAuthors: String): List<DbMark> =
        Gson().fromJson(jsonOfAuthors, object : TypeToken<List<DbMark>>() {}.type)
}
