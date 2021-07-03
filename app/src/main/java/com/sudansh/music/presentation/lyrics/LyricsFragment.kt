package com.sudansh.music.presentation.lyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sudansh.music.databinding.FragmentLyricsBinding
import com.sudansh.music.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LyricsFragment : Fragment() {
    private val viewModel: MainViewModel by sharedViewModel()
    private val lyricViewModel: LyricsViewModel by viewModel()
    private lateinit var binding: FragmentLyricsBinding

    private lateinit var lyricsAdapter: LyricsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLyricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lyricsAdapter = LyricsAdapter()
        binding.rvLyrics.apply {
            adapter = lyricsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.currentItem.observe(viewLifecycleOwner) {
            lyricViewModel.getLyrics(it.mediaId, it.mediaMetadata.title.toString())
        }

        lyricViewModel.lyrics.observe(viewLifecycleOwner) {
            lyricsAdapter.setData(it.lyricItems)
        }

        viewModel.playbackPosition.observe(viewLifecycleOwner) {
            lyricViewModel.setPlaybackPosition(it)
        }
        lyricViewModel.currentLyricsPositionData.observe(viewLifecycleOwner) { position ->
            Timber.i("xxx position: $position")
            lyricsAdapter.setCurrentPosition(position)
//            val scrollTo = if (position < 4) position else position + 4
            binding.rvLyrics.scrollToPosition(position)
            lyricsAdapter.notifyDataSetChanged()
        }
    }
}