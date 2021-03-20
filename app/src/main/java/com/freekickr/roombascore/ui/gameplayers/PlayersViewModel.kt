package com.freekickr.roombascore.ui.gameplayers

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freekickr.roombascore.ui.gameplayers.menu.PlayersNamesListAdapter

private const val TAG = "PlayersViewModel"

class PlayersViewModel : ViewModel() {

    val adapter = PlayersNamesListAdapter()

    private val _eventStartGame = MutableLiveData<Boolean>()
    val eventStartGame: LiveData<Boolean>
        get() = _eventStartGame

    fun setNumberOfPlayers(numberOfPlayers: Int) {
        adapter.setNumberOfPlayers(numberOfPlayers)
    }

    fun checkNamesForFilling() = adapter.checkNamesForFilling()

    fun collectNames() = adapter.getPlayerNames().map { it.name }.toTypedArray()

    fun onStartGameClicked() {
        _eventStartGame.postValue(true)
    }

    fun onGameScreenEventReceived() {
        _eventStartGame.postValue(false)
    }

}