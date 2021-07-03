package com.sudansh.music.presentation.playlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.sudansh.music.R
import com.sudansh.music.databinding.FragmentPlaylistBinding
import com.sudansh.music.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlaylistFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private val playListViewModel: PlayListViewModel by sharedViewModel()

    private lateinit var binding: FragmentPlaylistBinding

    private lateinit var upNextAdapter: PlayListAdapter
    private lateinit var currentPlayingAdapter: PlayListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }

        upNextAdapter = PlayListAdapter {
            onMediaClick(it)
        }
        currentPlayingAdapter = PlayListAdapter {
            onMediaClick(it)
        }
        binding.rvSongs.adapter = ConcatAdapter(upNextAdapter, currentPlayingAdapter)

        viewModel.currentItem.observe(viewLifecycleOwner) { current ->
            val playing = listOf(
                PlaylistItem.Heading(getString(R.string.now_playing)), PlaylistItem.Media(
                    PlaylistItem.Song(
                        mediaId = current.mediaId,
                        artist = current.mediaMetadata.artist.toString(),
                        artwork = current.mediaMetadata.artworkUri,
                        title = current.mediaMetadata.title.toString()
                    )
                )
            )
            currentPlayingAdapter.submitList(playing)
        }
        playListViewModel.mediaItems.observe(viewLifecycleOwner) {
            upNextAdapter.submitList(it)
        }
    }

    private fun onMediaClick(it: PlaylistItem.Song) {
        viewModel.play(it.mediaId)
        binding.btnClose.performClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.isPlayingVisible.value = false
    }

    override fun onDetach() {
        viewModel.isPlayingVisible.value = true
        super.onDetach()
    }
}