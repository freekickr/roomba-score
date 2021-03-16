package com.freekickr.roombascore.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _playGameEvent = MutableLiveData<Boolean>()
    val playGameEvent: LiveData<Boolean>
        get() = _playGameEvent

    private val _highscoresEvent = MutableLiveData<Boolean>()
    val highscoreEvent: LiveData<Boolean>
        get() = _highscoresEvent

    fun onPlayGameClicked() {
        _playGameEvent.postValue(true)
    }

    fun onHighscoresClicked() {
        _highscoresEvent.postValue(true)
    }

    fun onPlayGameNavigated() {
        _playGameEvent.postValue(false)
    }

    fun onHighscoresNavigated() {
        _highscoresEvent.postValue(false)
    }

}