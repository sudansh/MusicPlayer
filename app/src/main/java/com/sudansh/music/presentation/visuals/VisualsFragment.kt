package com.sudansh.music.presentation.visuals

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sudansh.music.databinding.FragmentVisualizationBinding
import com.sudansh.music.presentation.main.MainViewModel
import com.sudansh.music.visualizer.renderer.CircleRenderer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VisualsFragment : Fragment() {
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentVisualizationBinding
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAllowed ->
            if (isAllowed) {
                loadVisualization()
            } else {
                uiPermissionDenied()
            }
        }

    private fun uiPermissionDenied() {
        binding.visualization.isVisible = false
        binding.rationale.isVisible = true
        binding.btnAllow.isVisible = true
    }

    private fun loadVisualization() {
        binding.visualization.isVisible = true
        binding.rationale.isVisible = false
        binding.btnAllow.isVisible = false

        viewModel.audioSessionId.observe(viewLifecycleOwner) {
            binding.visualization.link(it)
            addVisualization()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVisualizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnAllow.setOnClickListener {
            checkCameraPermissions()
        }
        checkCameraPermissions()
    }

    private fun addVisualization() {
        val paint = Paint().apply {
            strokeWidth = 3f
            isAntiAlias = true
            color = Color.argb(255, 222, 92, 143)
        }

        val circleRenderer = CircleRenderer(paint, true)
        binding.visualization.addRenderer(circleRenderer)
    }

    private fun checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            loadVisualization()
        }
    }

    override fun onDestroyView() {
        binding.visualization.release()
        super.onDestroyView()

    }
}