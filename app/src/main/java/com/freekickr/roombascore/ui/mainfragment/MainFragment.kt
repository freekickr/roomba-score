package com.freekickr.roombascore.ui.mainfragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.databinding.FragmentMainBinding
import com.freekickr.roombascore.ui.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "MainFragment"

class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

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