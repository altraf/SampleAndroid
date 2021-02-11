package com.example.sampleandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleandroid.R
import com.example.sampleandroid.databinding.ViewAlbumRowBinding
import com.example.sampleandroid.model.Album
import com.example.sampleandroid.util.setAlbumCover
import com.example.sampleandroid.util.toDisplay

class AlbumAdapter(
    private val albums: ArrayList<Album>,
    private val context: Context,
    private val albumListener: ItemClickListener
) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.view_album_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums.getOrNull(position)
        album?.let {
            holder.card.setOnClickListener {
                albumListener.onItemClicked(album)
            }
            holder.name.text = album.collectionName
            setAlbumCover(album.artworkUrl100, holder.artwork)
            holder.releaseDate.text = album.releaseDate.toDisplay("yyyy")
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val albumRowBinding = ViewAlbumRowBinding.bind(view)
        val artwork = albumRowBinding.albumIv
        val name = albumRowBinding.albumNameTv
        val releaseDate = albumRowBinding.albumReleaseDateTv
        val card = albumRowBinding.albumMcv
    }

    interface ItemClickListener {
        fun onItemClicked(album: Album)
    }
}
