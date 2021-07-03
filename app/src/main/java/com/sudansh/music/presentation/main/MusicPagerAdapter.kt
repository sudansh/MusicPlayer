package com.sudansh.music.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sudansh.music.R
import com.sudansh.music.presentation.details.DetailFragment
import com.sudansh.music.presentation.lyrics.LyricsFragment
import com.sudansh.music.presentation.visuals.VisualsFragment

class MusicPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailFragment()
            1 -> LyricsFragment()
            2 -> VisualsFragment()
            else -> throw IllegalArgumentException("Invalid fragmnt position")
        }
    }

    fun getTitle(position: Int): Int {
        return when (position) {
            0 -> R.string.tab_detail
            1 -> R.string.tab_lyrics
            2 -> R.string.tab_visualization
            else -> throw IllegalArgumentException("Invalid fragmnt position")
        }
    }
}
