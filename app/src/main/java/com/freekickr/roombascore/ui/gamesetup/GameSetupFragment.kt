package com.freekickr.roombascore.ui.gamesetup

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
import com.freekickr.roombascore.databinding.FragmentGameSetupBinding
import com.freekickr.roombascore.utils.ViewModelFactory
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

        observeEnterPlayersClicked()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $viewModelFactory")
    }

    private fun observeEnterPlayersClicked() {
        viewModel.eventEnterPlayersNames.observe(viewLifecycleOwner, Observer {
            if (it != -1) {
                this.findNavController().navigate(GameSetupFragmentDirections.actionGameSetupFragmentToPlayersFragment(it))
                viewModel.onEnterPlayersNamesNavigated()
            }
        })
    }
}