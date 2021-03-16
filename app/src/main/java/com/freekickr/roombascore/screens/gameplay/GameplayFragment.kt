package com.freekickr.roombascore.screens.gameplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.databinding.FragmentGameplayBinding

class GameplayFragment: Fragment() {

    private lateinit var binding: FragmentGameplayBinding
    private lateinit var viewModel: GameplayViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameplayBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val viewModelFactory = GameplayViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameplayViewModel::class.java)

        binding.viewModel = viewModel

        observeGameEnded()

        return binding.root
    }

    private fun observeGameEnded() {
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GameplayFragmentDirections.actionGameplayFragmentToGameOverFragment())
                viewModel.onGameOverNavigated()
            }
        })
    }
}