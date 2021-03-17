package com.freekickr.roombascore.ui.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.database.entities.Game
import com.freekickr.roombascore.database.entities.Round
import kotlinx.coroutines.launch

class GameplayViewModel(private val database: RoombaDatabase) : ViewModel() {

    private val _currentGame = MutableLiveData<Game>()
    val currentGame: LiveData<Game>
        get() = _currentGame

    private val _previousRound = MutableLiveData<Round>()
    val previousRound: LiveData<Round>
        get() = _previousRound

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    fun onGameFinished() {
        _eventGameFinished.postValue(true)
    }

    fun onGameOverNavigated() {
        _eventGameFinished.postValue(false)
    }

    fun loadGame(gameId: Long) {
        viewModelScope.launch {
            val lastFoundRound = database.roundsDao.getLastRoundForGame(gameId.toLong())
            if (lastFoundRound != null) {
                _previousRound.postValue(lastFoundRound)
//                insertRound(lastFoundRound.round + 1)
            }
        }
    }

    private fun insertRound(roundNumber: Int) {
        val round = generateRound(roundNumber)
        if (round == null) {
            throw IllegalArgumentException("Round wasn't generated")
        } else {
            viewModelScope.launch {
                database.roundsDao.insert(round)
            }
        }
    }

    private fun generateRound(roundNumber: Int): Round? {
        val game = _currentGame.value
        game?.let {
            if (roundNumber == 1) {
                return Round(
                    gameId = game.id,
                    round = roundNumber,
                    score1 = 0,
                    score2 = 0,
                    score3 = if (game.name3.isNullOrEmpty()) null else 0,
                    score4 = if (game.name4.isNullOrEmpty()) null else 0,
                    score5 = if (game.name5.isNullOrEmpty()) null else 0,
                    score6 = if (game.name6.isNullOrEmpty()) null else 0,
                    score7 = if (game.name7.isNullOrEmpty()) null else 0,
                    score8 = if (game.name8.isNullOrEmpty()) null else 0,
                )
            } else {
                return Round()
            }
        }
        return null
    }

    fun createGame(players: Array<String>) {
        val newGame = Game(
            name1 = if (players.size > 0) players[0] else "unknown player 1",
            name2 = if (players.size > 1) players[1] else "unknown player 2",
            name3 = if (players.size > 2) players[2] else null,
            name4 = if (players.size > 3) players[3] else null,
            name5 = if (players.size > 4) players[4] else null,
            name6 = if (players.size > 5) players[5] else null,
            name7 = if (players.size > 6) players[6] else null,
            name8 = if (players.size > 7) players[7] else null
        )
        viewModelScope.launch {
            val gameId = database.gamesDao.insert(newGame)
            val insertedGame = database.gamesDao.getGameById(gameId)
            if (insertedGame == null) {
                throw IllegalArgumentException("Game was created but database return null")
            } else {
                _currentGame.postValue(insertedGame)
            }
        }
    }


    private fun onRoundFinished() {
        //TODO Collect round
        //TODO Insert collected round
        //TODO Get inserted round
        //TODO Check all values for 100
        //TODO If 100 Find Player and finish game
        //TODO Else fill previous round and clear current
    }

}