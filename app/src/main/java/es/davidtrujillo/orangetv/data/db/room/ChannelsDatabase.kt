package es.davidtrujillo.orangetv.data.db.room

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import es.davidtrujillo.orangetv.data.db.converter.AttachmentsConverter
import es.davidtrujillo.orangetv.data.db.dao.ChannelsDao
import es.davidtrujillo.orangetv.data.db.entity.Channel
import es.davidtrujillo.orangetv.data.db.entity.ChannelResponse
import es.davidtrujillo.orangetv.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream


@Database(entities = [Channel::class], version = 1)
@TypeConverters(value = [AttachmentsConverter::class])
abstract class ChannelsDatabase : RoomDatabase() {

    abstract fun channelsDao(): ChannelsDao

    companion object {

        private const val DATABASE_NAME = "channels_database"

        @Volatile
        private var INSTANCE: ChannelsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ChannelsDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ChannelsDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(ChannelsDatabaseCallback(context, scope))
                    .build()
            }
            return INSTANCE
        }
    }

    private class ChannelsDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.channelsDao())
                }
            }
        }

        fun populateDatabase(channelsDao: ChannelsDao) {
            channelsDao.deleteAll()

            val assetManager: AssetManager = context.assets
            val inputStream: InputStream = assetManager.open(Constants.CHANNELS_FILE_NAME)

            JsonReader(inputStream.reader()).use { jsonReader ->
                val channelsData: ChannelResponse = Gson().fromJson(
                    jsonReader,
                    ChannelResponse::class.java
                )

                channelsData.response?.let {
                    channelsDao.insertAll(it)
                };
            }
        }
    }

}