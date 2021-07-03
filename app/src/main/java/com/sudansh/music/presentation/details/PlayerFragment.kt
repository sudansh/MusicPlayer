package com.sudansh.music.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sudansh.music.databinding.FragmentPlayerBinding
import com.sudansh.music.presentation.main.MusicPagerAdapter

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding

    private lateinit var pagerAdapter: MusicPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = MusicPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabGroup, binding.viewPager) { tab, position ->
            tab.text = requireContext().getString(pagerAdapter.getTitle(position))
        }.attach()

    }
}


