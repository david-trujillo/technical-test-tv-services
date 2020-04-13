package es.davidtrujillo.orangetv.ui.channels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import es.davidtrujillo.orangetv.data.db.entity.Channel
import es.davidtrujillo.orangetv.data.db.repository.ChannelsRepository
import es.davidtrujillo.orangetv.data.db.room.ChannelsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChannelsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChannelsRepository

    val allChannels: LiveData<List<Channel>>

    init {
        val wordsDao = ChannelsDatabase.getDatabase(application, viewModelScope)!!.channelsDao()
        repository = ChannelsRepository(wordsDao)
        allChannels = repository.allChannels
    }

    fun insert(channel: Channel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(channel)
    }
}