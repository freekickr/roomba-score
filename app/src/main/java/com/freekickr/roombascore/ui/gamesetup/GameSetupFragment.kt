package com.freekickr.roombascore.ui.gamesetup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.database.entities.Game
import com.freekickr.roombascore.databinding.FragmentGameSetupBinding
import com.freekickr.roombascore.ui.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "GameSetupFragment"

class GameSetupFragment: Fragment() {

    private lateinit var binding: FragmentGameSetupBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameSetupViewModel::class.java)
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
        binding = FragmentGameSetupBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        observeUnfinishedGameEvent()
        
        observeEnterPlayersClicked()

        return binding.root
    }
    
    private fun observeUnfinishedGameEvent() {
        viewModel.eventSavedGameFound.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeUnfinishedGameEvent: $it")
            if (it != null) {
                openUnfinishedGameAlertDialog(it)
            }
        })
    }

    private fun observeEnterPlayersClicked() {
        viewModel.eventEnterPlayersNames.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                this.findNavController().navigate(GameSetupFragmentDirections.actionGameSetupFragmentToPlayersFragment(it))
                viewModel.onEnterPlayersNamesNavigated()
            }
        })
    }

    private fun openUnfinishedGameAlertDialog(game: Game) {
        AlertDialog.Builder(requireContext())
            .setMessage("Есть незаконченная игра")
            .setTitle("Продолжить?")
            .setPositiveButton("Конечно!") { dialogInterface, i ->
                Log.d(TAG, "openUnfinishedGameAlertDialog: yes")
                continueUnfinishedGame(game.id, game.numberOfPlayers)
                viewModel.savedGameEventReceived()
            }
            .setNegativeButton("Ни за что") { dialogInterface, i ->
                Log.d(TAG, "openUnfinishedGameAlertDialog: no")
                viewModel.finishOpenedGame(game)
                viewModel.savedGameEventReceived()
            }
            .setCancelable(false)
            .show()
    }

    private fun continueUnfinishedGame(gameId: Long, numberOfPlayers: Int) {
        this.findNavController().navigate(GameSetupFragmentDirections.actionGameSetupFragmentToGameplayFragment(
            arrayOf(), gameId, numberOfPlayers)
        )
    }
}