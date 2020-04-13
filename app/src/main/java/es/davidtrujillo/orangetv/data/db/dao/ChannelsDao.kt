package es.davidtrujillo.orangetv.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import es.davidtrujillo.orangetv.data.db.entity.Channel

@Dao
interface ChannelsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(channels: Channel)

    @Update
    suspend fun update(vararg channelEntities: Channel)

    @Delete
    suspend fun delete(vararg channelEntities: Channel)

    @Query("DELETE FROM " + Channel.TABLE_NAME)
    fun deleteAll()

    @Query("SELECT * FROM " + Channel.TABLE_NAME)
    fun getAllChannels(): LiveData<List<Channel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(channels: List<Channel>)
}