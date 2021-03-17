package com.freekickr.roombascore.ui.gameplay

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
import com.freekickr.roombascore.databinding.FragmentGameplayBinding
import com.freekickr.roombascore.ui.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "GameplayFragment"

class GameplayFragment : Fragment() {

    private lateinit var binding: FragmentGameplayBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameplayViewModel::class.java)
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
        binding = FragmentGameplayBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val args = GameplayFragmentArgs.fromBundle(requireArguments())
        with(args.savedGameId) {
            if (this == -1L) {
                viewModel.createGame(args.players)
            } else {
                viewModel.loadGame(this)
            }
        }

        observeCurrentGame()

        observePreviousRound()

        observeGameFinishEvent()

        return binding.root
    }

    private fun observeCurrentGame() {
        viewModel.currentGame.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeCurrentGame: $it")
        })
    }

    private fun observePreviousRound() {
        viewModel.previousRound.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observePreviousRound: $it")
        })
    }

    private fun observeGameFinishEvent() {
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(GameplayFragmentDirections.actionGameplayFragmentToGameOverFragment())
                viewModel.onGameOverNavigated()
            }
        })
    }


}