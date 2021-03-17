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
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.databinding.FragmentGameplayBinding
import com.freekickr.roombascore.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "GameplayFragment"

class GameplayFragment: Fragment() {

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

        observeGameEnded()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${viewModelFactory} ")
    }

    private fun observeGameEnded() {
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GameplayFragmentDirections.actionGameplayFragmentToGameOverFragment())
                viewModel.onGameOverNavigated()
            }
        })
    }


}