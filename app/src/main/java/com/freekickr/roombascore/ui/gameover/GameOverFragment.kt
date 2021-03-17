package com.freekickr.roombascore.ui.gameover

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
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.databinding.FragmentGameoverBinding
import com.freekickr.roombascore.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "GameoverFragment"

class GameOverFragment: Fragment() {

    private lateinit var binding: FragmentGameoverBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameOverViewModel::class.java)
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
        binding = FragmentGameoverBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        observeQuitGame()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${viewModelFactory} ")
    }

    private fun observeQuitGame() {
        viewModel.eventRestartGame.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameSetupFragment())
                viewModel.onGameRestartNavigated()
            }
        })
    }
}