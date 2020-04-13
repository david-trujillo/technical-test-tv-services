package es.davidtrujillo.orangetv.ui.channels

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.davidtrujillo.orangetv.R
import es.davidtrujillo.orangetv.data.db.entity.Channel
import kotlinx.android.synthetic.main.activity_channels.*

class ChannelsActivity : AppCompatActivity() {

    private val TAG = ChannelsActivity::class.java.simpleName

    private lateinit var channelsViewModel: ChannelsViewModel
    private lateinit var adapter: ChannelsAdapter
    private var channels = mutableListOf<Channel>()
    private var films = mutableListOf<Channel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels)

        adapter = ChannelsAdapter(this, channels)
        gv_channels.adapter = adapter

        sh_filter.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                films = channels.filter { p -> p.category == "Cine" }.toMutableList()
                adapter.updateItems(films)
            }else{
                adapter.updateItems(channels)
            }
        }

        channelsViewModel = ViewModelProvider(this).get(ChannelsViewModel::class.java)
        channelsViewModel.allChannels.observe(this, Observer { allChannels ->
            channels = allChannels.toMutableList()
            adapter.updateItems(channels)
        })

    }

}