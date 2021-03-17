package com.freekickr.roombascore.ui.gamesetup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameSetupViewModel : ViewModel() {

    private val _eventEnterPlayersNames = MutableLiveData<Int>()
    val eventEnterPlayersNames: LiveData<Int>
        get() = _eventEnterPlayersNames

    fun onPlayersNumberClicked(nubmer: Int) {
        _eventEnterPlayersNames.postValue(nubmer)
    }

    fun onEnterPlayersNamesNavigated() {
        _eventEnterPlayersNames.postValue(-1)
    }

}