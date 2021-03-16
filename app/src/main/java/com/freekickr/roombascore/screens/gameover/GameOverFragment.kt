package com.freekickr.roombascore.screens.gameover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.databinding.FragmentGameoverBinding

class GameOverFragment: Fragment() {

    private lateinit var binding: FragmentGameoverBinding
    private lateinit var viewModel: GameOverViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameoverBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val viewModelFactory = GameOverViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameOverViewModel::class.java)

        binding.viewModel = viewModel

        observeQuitGame()

        return binding.root
    }

    private fun observeQuitGame() {
        viewModel.eventRestartGame.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameSetupFragment())
                viewModel.onGameRestartNavigated()
            }
        })
    }
}