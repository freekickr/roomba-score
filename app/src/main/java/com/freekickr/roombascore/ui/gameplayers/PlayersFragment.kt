package com.freekickr.roombascore.ui.gameplayers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.freekickr.roombascore.databinding.FragmentPlayersBinding
import com.freekickr.roombascore.ui.ViewModelFactory
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

    private var numberOfPlayers: Int = 0

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

        val args = PlayersFragmentArgs.fromBundle(requireArguments())
        numberOfPlayers = args.numberOfPlayers
        viewModel.numberOfPlayers.set(numberOfPlayers)
        observeOnStartGameClicked()

        return binding.root
    }

    private fun observeOnStartGameClicked() {
        viewModel.eventStartGame.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (!viewModel.checkNamesForFilling(numberOfPlayers)) {
                    Toast.makeText(requireContext(), "Заполни все поля", Toast.LENGTH_SHORT).show()
                    viewModel.onGameScreenEventReceived()
                } else {
                    this.findNavController()
                        .navigate(
                            PlayersFragmentDirections.actionPlayersFragmentToGameplayFragment(
                                viewModel.collectNames(),
                                -1L
                            )
                        )
                    viewModel.onGameScreenEventReceived()
                }
            }
        })
    }
}