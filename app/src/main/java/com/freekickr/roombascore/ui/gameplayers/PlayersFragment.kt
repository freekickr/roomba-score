package com.freekickr.roombascore.ui.gameplayers

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
import com.freekickr.roombascore.databinding.FragmentPlayersBinding
import com.freekickr.roombascore.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "PlayersFragment"

class PlayersFragment : Fragment() {

    private lateinit var binding: FragmentPlayersBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PlayersViewModel::class.java)
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
        binding = FragmentPlayersBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        observeOnStartGameClicked()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $viewModelFactory")
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