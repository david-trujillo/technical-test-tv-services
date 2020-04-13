package es.davidtrujillo.orangetv.ui.channels

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import es.davidtrujillo.orangetv.R
import es.davidtrujillo.orangetv.data.db.entity.Channel
import es.davidtrujillo.orangetv.utils.Constants
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelsAdapter(private var context: Context, private var channels: MutableList<Channel>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = convertView ?: inflater.inflate(R.layout.item_channel, parent, false)
        val url  = if (channels[position].attachments.isNotEmpty()) Constants.IMAGE_URL + channels[position].attachments[0].value else ""

        view.tv_name.text = channels[position].name
        Glide.with(context)
            .load(url)
            .centerCrop()
            .into(view.iv_icon)
        return view
    }

    fun updateItems(mChannels: MutableList<Channel>){
        channels = mChannels
        this.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return channels[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return channels.size
    }

}