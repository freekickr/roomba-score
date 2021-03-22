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
import com.freekickr.roombascore.database.entities.GameInfo
import com.freekickr.roombascore.database.entities.GameScores
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
                viewModel.createGame(args.numberOfPlayers, args.players)
            } else {
                viewModel.loadGame(this)
            }
        }

        observeCurrentGame()

        observeGameFinishEvent()

        observePlayerLost()

        return binding.root
    }

    private fun observeCurrentGame() {
        viewModel.currentGame.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeCurrentGame: $it")
            initGame(it)
            observeNextRoundGameScores(it.id)
        })
    }

    private fun observePlayerLost() {
        viewModel.eventPlayerLost.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observePlayerLost: $it")
//            disablePlayers(it)
        })
    }

    private fun observeNextRoundGameScores(gameId: Long) {
        viewModel.getGameScores(gameId).observe(viewLifecycleOwner, Observer {
            viewModel.saveRound(it)
            viewModel.checkRoundForEndGame(it)
            fillPreviousRound(it)
            viewModel.clearEditTexts()
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

    private fun fillPreviousRound(scores: GameScores) {
        viewModel.checkPlayersForFinish(scores)
        binding.tv1PlayerHistory.text = scores.score1.toString()
        binding.tv2PlayerHistory.text = scores.score2.toString()
        scores.score3?.let { binding.tv3PlayerHistory.text = it.toString() }
        scores.score4?.let { binding.tv4PlayerHistory.text = it.toString() }
        scores.score5?.let { binding.tv5PlayerHistory.text = it.toString() }
        scores.score6?.let { binding.tv6PlayerHistory.text = it.toString() }
        scores.score7?.let { binding.tv7PlayerHistory.text = it.toString() }
        scores.score8?.let { binding.tv8PlayerHistory.text = it.toString() }
    }

    private fun initGame(game: GameInfo) {
        fillNames(game)
        resetScores()
    }

    private fun fillNames(game: GameInfo) {
        setupCardsVisibility(game.numberOfPlayers)
        binding.tv1PlayerName.text = game.name1
        binding.tv2PlayerName.text = game.name2
        game.numberOfPlayers.let { nOp ->
            if (nOp > 2) binding.tv3PlayerName.text = game.name3 ?: "Unknown player"
            if (nOp > 3) binding.tv4PlayerName.text = game.name4 ?: "Unknown player"
            if (nOp > 4) binding.tv5PlayerName.text = game.name5 ?: "Unknown player"
            if (nOp > 5) binding.tv6PlayerName.text = game.name6 ?: "Unknown player"
            if (nOp > 6) binding.tv7PlayerName.text = game.name7 ?: "Unknown player"
            if (nOp > 7) binding.tv8PlayerName.text = game.name8 ?: "Unknown player"
        }
    }

    private fun setupCardsVisibility(numberOfPlayers: Int) {
        numberOfPlayers.let {
            if (it > 0) binding.cvPlayer1.visibility = View.VISIBLE
            if (it > 1) binding.cvPlayer2.visibility = View.VISIBLE
            if (it > 2) binding.cvPlayer3.visibility = View.VISIBLE
            if (it > 3) binding.cvPlayer4.visibility = View.VISIBLE
            if (it > 4) binding.cvPlayer5.visibility = View.VISIBLE
            if (it > 5) binding.cvPlayer6.visibility = View.VISIBLE
            if (it > 6) binding.cvPlayer7.visibility = View.VISIBLE
            if (it > 7) binding.cvPlayer8.visibility = View.VISIBLE
        }
    }

    private fun resetScores() {
        binding.tv1PlayerHistory.text = "0"
        binding.tv2PlayerHistory.text = "0"
        binding.tv3PlayerHistory.text = "0"
        binding.tv4PlayerHistory.text = "0"
        binding.tv5PlayerHistory.text = "0"
        binding.tv6PlayerHistory.text = "0"
        binding.tv7PlayerHistory.text = "0"
        binding.tv8PlayerHistory.text = "0"
    }

    private fun disablePlayers(players: List<Int>) {
        players.forEach {
            when (it) {
                1 -> {
                    binding.etPlayer1Score.isEnabled = false
                }
                2 -> {
                    binding.etPlayer2Score.isEnabled = false
                }
                3 -> {
                    binding.etPlayer3Score.isEnabled = false
                }
                4 -> {
                    binding.etPlayer4Score.isEnabled = false
                }
                5 -> {
                    binding.etPlayer5Score.isEnabled = false
                }
                6 -> {
                    binding.etPlayer6Score.isEnabled = false
                }
                7 -> {
                    binding.etPlayer7Score.isEnabled = false
                }
                8 -> {
                    binding.etPlayer8Score.isEnabled = false
                }
            }
        }
    }

}