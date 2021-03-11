package com.adnahcodes.truebeats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.TrackRecyclerAdapter.MyViewHolder.TrackClickHandler
import com.adnahcodes.truebeats.databinding.TrackItemBinding
import com.adnahcodes.truebeats.model.Track
import com.squareup.picasso.Picasso

class TrackRecyclerAdapter(val trackList: Array<Track>, val trackClickHandler: TrackClickHandler) : RecyclerView.Adapter<TrackRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)

        return MyViewHolder(view, trackClickHandler)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(trackList.get(position), position)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    class MyViewHolder(val item: View, val trackClickHandler: TrackClickHandler) : RecyclerView.ViewHolder(item) {
        fun bindData(track: Track, position: Int) {
            item.setOnClickListener { trackClickHandler.onTrackItemClick(track, position) }

            val binding = TrackItemBinding.bind(item)

            Picasso.get()
                .load(track.album.albumPicture)
                .into(binding.trackImage)

            if (track.isTrackReadable){
                binding.trackTitle.text = track.title
            } else {
                binding.trackTitle.text = track.title + item.context.getString(R.string.no_preview_available)
            }
            binding.trackArtistName.text = track.artist.artistName
            binding.albumTitle.text = track.album.albumName
        }

        interface TrackClickHandler {
            fun onTrackItemClick(track: Track, position: Int) {}
        }
    }
}
