package com.freekickr.roombascore.ui.gameover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameOverViewModel : ViewModel() {

    private val _eventRestartGame = MutableLiveData<Boolean>()
    val eventRestartGame: LiveData<Boolean>
        get() = _eventRestartGame

    fun onGameRestartClicked() {
        _eventRestartGame.postValue(true)
    }

    fun onGameRestartNavigated() {
        _eventRestartGame.postValue(false)
    }

}