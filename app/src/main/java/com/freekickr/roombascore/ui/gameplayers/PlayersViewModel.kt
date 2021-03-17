package com.freekickr.roombascore.ui.gameplayers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayersViewModel : ViewModel() {

    private val _eventStartGame = MutableLiveData<Boolean>()
    val eventStartGame: LiveData<Boolean>
        get() = _eventStartGame

    fun onStartGameClicked() {
        _eventStartGame.postValue(true)
    }

    fun onGameScreenNavigated() {
        _eventStartGame.postValue(false)
    }

}