package com.sudansh.music.presentation.lyrics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudansh.music.databinding.ListItemLyricsBinding
import com.sudansh.music.model.Lyric

class LyricsAdapter : RecyclerView.Adapter<LyricsAdapter.LyricsViewHolder>() {

    private var lyricsItemList = emptyList<Lyric>()
    private var currentPosition = 0

    class LyricsViewHolder(val binding: ListItemLyricsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Lyric, currentPosition: Boolean) {
            binding.title = item.value
            binding.isCurrent = currentPosition
        }
    }

    override fun onBindViewHolder(holder: LyricsViewHolder, position: Int) {
        holder.onBind(lyricsItemList[position], position == currentPosition)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LyricsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ListItemLyricsBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return LyricsViewHolder(itemBinding)
    }

    override fun getItemCount() = lyricsItemList.size

    internal fun setCurrentPosition(position: Int) {
        if (currentPosition == position) return
        currentPosition = position
        notifyDataSetChanged()
    }

    fun setData(lyricItems: List<Lyric>) {
        lyricsItemList = lyricItems
        notifyDataSetChanged()
    }
}