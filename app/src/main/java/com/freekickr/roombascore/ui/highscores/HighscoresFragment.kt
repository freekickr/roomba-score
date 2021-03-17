package com.freekickr.roombascore.ui.highscores

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.databinding.FragmentHighscoresBinding
import com.freekickr.roombascore.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val TAG = "HighscoresFragment"

class HighscoresFragment: Fragment() {

    private lateinit var binding: FragmentHighscoresBinding

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HighscoresViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHighscoresBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${viewModelFactory} ")
    }
}