package com.freekickr.roombascore.ui.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameplayViewModel : ViewModel() {

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    fun onGameFinished() {
        _eventGameFinished.postValue(true)
    }

    fun onGameOverNavigated() {
        _eventGameFinished.postValue(false)
    }

}