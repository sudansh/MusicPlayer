package com.sudansh.music.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sudansh.music.R
import com.sudansh.music.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        subscribeUi()
    }

    private fun subscribeUi() {
        binding.nextSongTitle.setOnClickListener {
            navController.navigate(R.id.toPlaylist)
        }
        binding.playerControlView.setProgressUpdateListener { position, _ ->
            viewModel.setPlaybackProgress(position)
        }
        viewModel.setPlayer(binding.playerControlView)
    }
}