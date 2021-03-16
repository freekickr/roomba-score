package com.freekickr.roombascore.screens.highscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freekickr.roombascore.databinding.FragmentHighscoresBinding

class HighscoresFragment: Fragment() {

    private lateinit var binding: FragmentHighscoresBinding
    private lateinit var viewModel: HighscoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighscoresBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val viewModelFactory = HighscoresViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(HighscoresViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }
}