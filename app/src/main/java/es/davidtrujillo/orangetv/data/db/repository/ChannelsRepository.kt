package es.davidtrujillo.orangetv.data.db.repository

import androidx.lifecycle.LiveData
import es.davidtrujillo.orangetv.data.db.dao.ChannelsDao
import es.davidtrujillo.orangetv.data.db.entity.Channel

class ChannelsRepository(private val channelsDao: ChannelsDao) {

    val allChannels: LiveData<List<Channel>> = channelsDao.getAllChannels()

    suspend fun insert(channel: Channel) {
        channelsDao.insert(channel)
    }
}