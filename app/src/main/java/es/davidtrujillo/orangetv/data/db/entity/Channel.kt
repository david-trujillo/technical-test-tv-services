package es.davidtrujillo.orangetv.data.db.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import es.davidtrujillo.orangetv.data.db.converter.AttachmentsConverter

data class ChannelResponse(
    @SerializedName("response")
    val response: List<Channel>?
)

@Entity(tableName = Channel.TABLE_NAME)
data class Channel(
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String = "",
    @SerializedName("category")
    @ColumnInfo(name = "category")
    val category: String = "",
    @SerializedName("attachments")
    @TypeConverters(AttachmentsConverter::class)
    val attachments: List<Attachments> = emptyList()
) {
    companion object {
        const val TABLE_NAME = "channels"
    }
}

data class Attachments(
    @SerializedName("value")
    val value: String = ""
)