package com.adnahcodes.truebeats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.databinding.PlaylistItemBinding
import com.adnahcodes.truebeats.model.Playlist
import com.squareup.picasso.Picasso

class PlaylistRecylerAdapter(val listOfPlaylists: List<Playlist>, val playlistClickHandler: PlaylistClickHandler) :
    RecyclerView.Adapter<PlaylistRecylerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)

       return MyViewHolder(view, playlistClickHandler)
    }

    override fun getItemCount(): Int {
       return listOfPlaylists.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(listOfPlaylists.get(position))
    }

    class MyViewHolder(val item: View, val playlistClickHandler: PlaylistClickHandler) : RecyclerView.ViewHolder(item) {

        fun bindData(playlist: Playlist) {
            item.setOnClickListener { playlistClickHandler.onPlaylistItemClick(playlist) }

            val binding: PlaylistItemBinding = PlaylistItemBinding.bind(item)

//          Load cover image with picasso
            Picasso.get()
                .load(playlist.picture)
                .into(binding.trackCoverImgv)

            binding.trackTitleTxtv.text = playlist.title
            binding.trackArtistTxtv.text = playlist.dateOfCreation
        }

    }

    interface PlaylistClickHandler {
        fun onPlaylistItemClick(playlist: Playlist) {}
    }
}