package by.slizh.pexelsapp.data.local

import androidx.room.TypeConverter
import by.slizh.pexelsapp.data.local.entity.SrcEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SrcEntityConverter {

    val gson = Gson()

    @TypeConverter
    fun toSrcEntityToString(srcEntity: SrcEntity): String {

        val type = object : TypeToken<SrcEntity>() {}.type
        return gson.toJson(srcEntity, type)
    }

    @TypeConverter
    fun fromStringToSrcEntity(string: String): SrcEntity {

        val type = object : TypeToken<SrcEntity>() {}.type
        return gson.fromJson<SrcEntity>(string, type)

    }
}