package com.freekickr.roombascore.ui.gameplayers

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "PlayersViewModel"

class PlayersViewModel : ViewModel() {

    val name1 = ObservableField<String>()
    val name2 = ObservableField<String>()
    val name3 = ObservableField<String>()
    val name4 = ObservableField<String>()
    val name5 = ObservableField<String>()
    val name6 = ObservableField<String>()
    val name7 = ObservableField<String>()
    val name8 = ObservableField<String>()

    private val _eventStartGame = MutableLiveData<Boolean>()
    val eventStartGame: LiveData<Boolean>
        get() = _eventStartGame

    fun collectNames(): Array<String?> {
        val result = arrayOf(
            name1.get(),
            name2.get(),
            name3.get(),
            name4.get(),
            name5.get(),
            name6.get(),
            name7.get(),
            name8.get()
        )
        Log.d(TAG, "collectNames: $result")
        return result
    }

    fun checkNamesForFilling(numberOfPlayers: Int): Boolean {

        if (name1.get().isNullOrEmpty()) return false
        if (name2.get().isNullOrEmpty()) return false
        if (name3.get().isNullOrEmpty() && numberOfPlayers > 2) return false
        if (name4.get().isNullOrEmpty() && numberOfPlayers > 3) return false
        if (name5.get().isNullOrEmpty() && numberOfPlayers > 4) return false
        if (name6.get().isNullOrEmpty() && numberOfPlayers > 5) return false
        if (name7.get().isNullOrEmpty() && numberOfPlayers > 6) return false
        if (name8.get().isNullOrEmpty() && numberOfPlayers > 7) return false

        return true
    }

    fun onStartGameClicked() {
        _eventStartGame.postValue(true)
    }

    fun onGameScreenEventReceived() {
        _eventStartGame.postValue(false)
    }

}