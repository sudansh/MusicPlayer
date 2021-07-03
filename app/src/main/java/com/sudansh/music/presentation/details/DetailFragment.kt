package com.sudansh.music.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.sudansh.music.R
import com.sudansh.music.databinding.FragmentDetailsBinding
import com.sudansh.music.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.currentItem.observe(viewLifecycleOwner) {
            binding.artwork.load(it.mediaMetadata.artworkUri) {
                crossfade(true)
                placeholder(R.drawable.music_placeholder)
            }
        }

    }
}


