package com.sudansh.music.presentation.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sudansh.music.R
import com.sudansh.music.databinding.ItemHeadingBinding
import com.sudansh.music.databinding.ListItemAlbumBinding

class PlayListAdapter(private val clickedMedia: (PlaylistItem.Song) -> Unit) :
    ListAdapter<PlaylistItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        itemViewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (itemViewType) {
            R.layout.item_heading -> {
                val itemBinding = ItemHeadingBinding.inflate(layoutInflater, parent, false)
                HeadingViewHolder(itemBinding)
            }
            R.layout.list_item_album -> {
                val itemBinding = ListItemAlbumBinding.inflate(layoutInflater, parent, false)
                MusicViewHolder(itemBinding)
            }
            else -> throw IllegalStateException("Unknown type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MusicViewHolder -> {
                val mediaItem = item as PlaylistItem.Media
                holder.itemView.setOnClickListener {
                    clickedMedia(mediaItem.media)
                }
                holder.bind(mediaItem.media)
            }
            is HeadingViewHolder -> {
                val heading = item as PlaylistItem.Heading
                holder.bind(heading.title)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PlaylistItem.Heading -> R.layout.item_heading
            is PlaylistItem.Media -> R.layout.list_item_album
        }
    }

    class MusicViewHolder(val binding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: PlaylistItem.Song) {
            binding.title.text = media.title
            binding.artist.text = media.artist
            binding.artworkImageView.load(media.artwork) {
                crossfade(true)
                placeholder(R.drawable.music_placeholder)
            }
        }
    }

    class HeadingViewHolder(val binding: ItemHeadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.title.text = item
        }
    }

    companion object {

        object DiffCallback : DiffUtil.ItemCallback<PlaylistItem>() {
            override fun areItemsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem): Boolean {
                val isSameEntry = oldItem is PlaylistItem.Media
                        && newItem is PlaylistItem.Media
                        && oldItem.media.title == newItem.media.title

                val isSameTitle = oldItem is PlaylistItem.Heading
                        && newItem is PlaylistItem.Heading
                        && oldItem.title == newItem.title

                return isSameEntry || isSameTitle
            }

            override fun areContentsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem) =
                oldItem == newItem
        }
    }
}