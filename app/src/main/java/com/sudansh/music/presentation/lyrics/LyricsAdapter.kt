package com.sudansh.music.presentation.lyrics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudansh.music.databinding.ListItemLyricsBinding
import com.sudansh.music.model.Lyric

class LyricsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    data class LyricsItem(var time: Long, var lyric: String)

    private var currentPosition = 0


    class LyricsViewHolder(val binding: ListItemLyricsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: LyricsItem, currentPosition: Boolean) {
            val lyric = item.lyric
            binding.title = lyric
            binding.isCurrent = currentPosition
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == currentPosition) (holder as? LyricsViewHolder)?.onBind(
            lyricsItemList[position],
            true
        )
        else (holder as? LyricsViewHolder)?.onBind(lyricsItemList[position], false)

    }


    private var lyricsItemList = emptyList<LyricsItem>()

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
        currentPosition = position
    }

    fun setData(lyricItems: List<Lyric>) {
        lyricsItemList = lyricItems.map { LyricsItem(it.timeStampMillis, it.value) }
        notifyDataSetChanged()
    }
}