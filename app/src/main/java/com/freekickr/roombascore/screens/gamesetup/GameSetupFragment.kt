package com.freekickr.roombascore.screens.gamesetup

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
import com.freekickr.roombascore.databinding.FragmentGameSetupBinding

class GameSetupFragment: Fragment() {

    private lateinit var binding: FragmentGameSetupBinding
    private lateinit var viewModel: GameSetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameSetupBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val viewModelFactory = GameSetupViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameSetupViewModel::class.java)

        binding.viewModel = viewModel

        observeEnterPlayersClicked()

        return binding.root
    }

    private fun observeEnterPlayersClicked() {
        viewModel.eventEnterPlayersNames.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GameSetupFragmentDirections.actionGameSetupFragmentToPlayersFragment())
                viewModel.onEnterPlayersNamesNavigated()
            }
        })
    }
}