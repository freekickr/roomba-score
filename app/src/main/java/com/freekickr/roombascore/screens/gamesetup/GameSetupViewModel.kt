package com.freekickr.roombascore.screens.gamesetup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameSetupViewModel : ViewModel() {

    private val _eventEnterPlayersNames = MutableLiveData<Boolean>()
    val eventEnterPlayersNames: LiveData<Boolean>
        get() = _eventEnterPlayersNames

    fun onPlayersNumberClicked() {
        _eventEnterPlayersNames.postValue(true)
    }

    fun onEnterPlayersNamesNavigated() {
        _eventEnterPlayersNames.postValue(false)
    }

}