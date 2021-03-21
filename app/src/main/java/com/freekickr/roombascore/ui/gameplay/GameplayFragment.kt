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
                viewModel.createGame(args.numberOfPlayers, args.players)
            } else {
                viewModel.loadGame(args.numberOfPlayers, this)
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
            viewModel.initAdapter(it)
//            fillNames(it)
        })
    }

//    private fun observeNumberOfPlayers() {
//        viewModel.numberOfPlayers.observe(viewLifecycleOwner, Observer {
//            if (it < 8) binding.cvPlayer8.visibility = View.INVISIBLE
//            if (it < 7) binding.cvPlayer7.visibility = View.INVISIBLE
//            if (it < 6) binding.cvPlayer6.visibility = View.INVISIBLE
//            if (it < 5) binding.cvPlayer5.visibility = View.INVISIBLE
//            if (it < 4) binding.cvPlayer4.visibility = View.INVISIBLE
//            if (it < 3) binding.cvPlayer3.visibility = View.INVISIBLE
//        })
//    }
//
//    private fun observePlayerLost() {
//        viewModel.eventPlayerLost.observe(viewLifecycleOwner, Observer {
//            when(it) {
//                1 -> {binding.cvPlayer1.visibility = View.GONE}
//                2 -> {binding.cvPlayer2.visibility = View.GONE}
//                3 -> {binding.cvPlayer3.visibility = View.GONE}
//                4 -> {binding.cvPlayer4.visibility = View.GONE}
//                5 -> {binding.cvPlayer5.visibility = View.GONE}
//                6 -> {binding.cvPlayer6.visibility = View.GONE}
//                7 -> {binding.cvPlayer7.visibility = View.GONE}
//                8 -> {binding.cvPlayer8.visibility = View.GONE}
//            }
//        })
//    }

    private fun observePreviousRound() {
        viewModel.previousRound.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observePreviousRound: $it")
            viewModel.fillAdapter(it)
//            fillPreviousRound(it)
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

//    private fun fillPreviousRound(round: Round) {
//        binding.tv1PlayerHistory.text = round.score1.toString()
//        binding.tv2PlayerHistory.text = round.score2.toString()
//        round.score3?.let { binding.tv3PlayerHistory.text = it.toString() }
//        round.score4?.let { binding.tv4PlayerHistory.text = it.toString() }
//        round.score5?.let { binding.tv5PlayerHistory.text = it.toString() }
//        round.score6?.let { binding.tv6PlayerHistory.text = it.toString() }
//        round.score7?.let { binding.tv7PlayerHistory.text = it.toString() }
//        round.score8?.let { binding.tv8PlayerHistory.text = it.toString() }
//    }
//
//    private fun fillNames(game: Game) {
//        binding.tv1PlayerName.text = game.name1
//        binding.tv2PlayerName.text = game.name2
//        game.name3?.let { binding.tv3PlayerName.text = it }
//        game.name4?.let { binding.tv4PlayerName.text = it }
//        game.name5?.let { binding.tv5PlayerName.text = it }
//        game.name6?.let { binding.tv6PlayerName.text = it }
//        game.name7?.let { binding.tv7PlayerName.text = it }
//        game.name8?.let { binding.tv8PlayerName.text = it }
//    }

}