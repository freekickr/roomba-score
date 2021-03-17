package com.freekickr.roombascore.ui.gamesetup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.database.entities.Game
import kotlinx.coroutines.launch

class GameSetupViewModel(private val database: RoombaDatabase) : ViewModel() {

    private val _eventSavedGameFound = MutableLiveData<Game?>()
    val eventSavedGameFound: LiveData<Game?>
        get() = _eventSavedGameFound

    init {
        checkForUnfinishedGame()
    }

    private val _eventEnterPlayersNames = MutableLiveData<Int>()
    val eventEnterPlayersNames: LiveData<Int>
        get() = _eventEnterPlayersNames

    private fun checkForUnfinishedGame() {
        viewModelScope.launch {
            database.gamesDao.getLastGame().apply {
                if (this != null && !this.finished) {
                    _eventSavedGameFound.postValue(this)
                }
            }
        }
    }

    fun finishOpenedGame(game: Game) {
        viewModelScope.launch {
            game.finished = true
            database.gamesDao.update(game)
        }
    }

    fun savedGameEventReceived() {
        _eventSavedGameFound.postValue(null)
    }

    fun onPlayersNumberClicked(nubmer: Int) {
        _eventEnterPlayersNames.postValue(nubmer)
    }

    fun onEnterPlayersNamesNavigated() {
        _eventEnterPlayersNames.postValue(-1)
    }

}