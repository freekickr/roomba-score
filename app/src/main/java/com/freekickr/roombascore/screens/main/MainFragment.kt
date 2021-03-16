package com.freekickr.roombascore.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.R
import com.freekickr.roombascore.databinding.FragmentMainBinding

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        observePlayButton()
        observeHighscoresButton()

        return binding.root
    }

    private fun observePlayButton() {
        viewModel.playGameEvent.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToGameFragment())
                viewModel.onPlayGameNavigated()}
        })
    }

    private fun observeHighscoresButton() {
        viewModel.highscoreEvent.observe(viewLifecycleOwner, Observer {
            if (it){
                this.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToHighscoresFragment())
                viewModel.onHighscoresNavigated()}
        })
    }
}