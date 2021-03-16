package com.freekickr.roombascore.screens.gameplayers

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
import com.freekickr.roombascore.databinding.FragmentPlayersBinding

class PlayersFragment : Fragment() {

    private lateinit var binding: FragmentPlayersBinding
    private lateinit var viewModel: PlayersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_players, container, false)
        binding.lifecycleOwner = this

        val viewModelFactory = PlayersViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayersViewModel::class.java)

        binding.viewModel = viewModel

        observeOnStartGameClicked()

        return binding.root
    }

    private fun observeOnStartGameClicked() {
        viewModel.eventStartGame.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(PlayersFragmentDirections.actionPlayersFragmentToGameplayFragment())
                viewModel.onGameScreenNavigated()
            }
        })
    }
}