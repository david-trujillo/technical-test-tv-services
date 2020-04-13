package es.davidtrujillo.orangetv.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.davidtrujillo.orangetv.data.db.entity.Attachments

class AttachmentsConverter {
    @TypeConverter
    fun toJson(item: List<Attachments>) : String = Gson().toJson(item)

    @TypeConverter
    fun fromJson(json: String) : List<Attachments> {
        val type = object : TypeToken<List<Attachments>>() { }.type
        return Gson().fromJson(json, type)
    }
}