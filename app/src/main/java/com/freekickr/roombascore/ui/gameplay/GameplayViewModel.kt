package com.freekickr.roombascore.ui.gameplay

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.database.entities.Game
import com.freekickr.roombascore.database.entities.Round
import kotlinx.coroutines.launch

private const val TAG = "GameplayViewModel"

class GameplayViewModel(private val database: RoombaDatabase) : ViewModel() {

    val score1 = ObservableField<String>()
    val score2 = ObservableField<String>()
    val score3 = ObservableField<String?>()
    val score4 = ObservableField<String?>()
    val score5 = ObservableField<String?>()
    val score6 = ObservableField<String?>()
    val score7 = ObservableField<String?>()
    val score8 = ObservableField<String?>()

    private val _currentGame = MutableLiveData<Game>()
    val currentGame: LiveData<Game>
        get() = _currentGame

    private val _previousRound = MutableLiveData<Round>()
    val previousRound: LiveData<Round>
        get() = _previousRound

    private val _numberOfPlayers = MutableLiveData<Int>()
    val numberOfPlayers: LiveData<Int>
        get() = _numberOfPlayers

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val _eventPlayerLost = MutableLiveData<Int>()
    val eventPlayerLost: LiveData<Int>
        get() = _eventPlayerLost

    fun onGameFinished() {
        _eventGameFinished.postValue(true)
    }

    fun onGameOverNavigated() {
        _eventGameFinished.postValue(false)
    }

    fun loadGame(playersNum: Int, gameId: Long) {
        viewModelScope.launch {
            val game = database.gamesDao.getGameById(gameId)
            if (game != null) {
                _currentGame.postValue(game)
                _numberOfPlayers.postValue(defineNumberOfPlayers(game))
                val lastFoundRound = database.roundsDao.getLastRoundForGame(gameId)
                if (lastFoundRound != null) {
                    _previousRound.postValue(lastFoundRound)
                } else {
                    throw IllegalStateException("Unable to load last round")
                }
            } else {
                throw IllegalStateException("Unable to load game")
            }
        }
    }

    private fun defineNumberOfPlayers(game: Game): Int {
        var result = 2
        with(game) {
            if (name3 != null) result++
            if (name4 != null) result++
            if (name5 != null) result++
            if (name6 != null) result++
            if (name7 != null) result++
            if (name8 != null) result++
        }
        return result
    }

    private fun insertRound(roundNumber: Int) {
        val round = generateRound(roundNumber)
        if (round == null) {
            throw IllegalArgumentException("Round wasn't generated")
        } else {
            viewModelScope.launch {
                val roundId = database.roundsDao.insert(round)
                val previousRound = database.roundsDao.getRoundById(roundId)
                if (previousRound == null) {
                    throw IllegalStateException("Round was generated and inserted but cant get")
                } else {
                    _previousRound.postValue(previousRound)
                    checkPlayersForFinish()
                    clearEditTexts()
                }
            }
        }
    }

    private fun checkPlayersForFinish() {
        previousRound.value?.let {
            if (it.score1 > 100) _eventPlayerLost.postValue(2)
            if (it.score2 > 100) _eventPlayerLost.postValue(2)
            if (it.score3 != null && it.score3 > 100) _eventPlayerLost.postValue(3)
            if (it.score4 != null && it.score4 > 100) _eventPlayerLost.postValue(4)
            if (it.score5 != null && it.score5 > 100) _eventPlayerLost.postValue(5)
            if (it.score6 != null && it.score6 > 100) _eventPlayerLost.postValue(6)
            if (it.score7 != null && it.score7 > 100) _eventPlayerLost.postValue(7)
            if (it.score8 != null && it.score8 > 100) _eventPlayerLost.postValue(8)
        }
    }

    private fun clearEditTexts() {
        score1.set(null)
        score2.set(null)
        score3.set(null)
        score4.set(null)
        score5.set(null)
        score6.set(null)
        score7.set(null)
        score8.set(null)
    }

    private fun generateRound(roundNumber: Int): Round? {
        val game = currentGame.value
        game?.let { curGame ->
            val prevRound = previousRound.value
            if (prevRound == null) {
                return Round(
                    gameId = curGame.id,
                    round = roundNumber,
                    score1 = score1.get()?.toIntOrNull() ?: 0,
                    score2 = score2.get()?.toIntOrNull() ?: 0,
                    score3 = score3.get()?.toIntOrNull(),
                    score4 = score4.get()?.toIntOrNull(),
                    score5 = score5.get()?.toIntOrNull(),
                    score6 = score6.get()?.toIntOrNull(),
                    score7 = score7.get()?.toIntOrNull(),
                    score8 = score8.get()?.toIntOrNull(),
                )
            } else {
                numberOfPlayers.value?.let {
                    val prevScore1 = prevRound.score1
                    val prevScore2 = prevRound.score2
                    val prevScore3 = prevRound.score3
                    val prevScore4 = prevRound.score4
                    val prevScore5 = prevRound.score5
                    val prevScore6 = prevRound.score6
                    val prevScore7 = prevRound.score7
                    val prevScore8 = prevRound.score8
                    return Round(
                        gameId = curGame.id,
                        round = roundNumber,
                        score1 = (score1.get()?.toInt() ?: 0) + prevScore1,
                        score2 = (score2.get()?.toInt() ?: 0) + prevScore2,
                        score3 = if (it > 2) (score3.get()?.toIntOrNull()
                            ?: 0) + prevScore3!! else null,
                        score4 = if (it > 3) (score4.get()?.toIntOrNull()
                            ?: 0) + prevScore4!! else null,
                        score5 = if (it > 4) (score5.get()?.toIntOrNull()
                            ?: 0) + prevScore5!! else null,
                        score6 = if (it > 5) (score6.get()?.toIntOrNull()
                            ?: 0) + prevScore6!! else null,
                        score7 = if (it > 6) (score7.get()?.toIntOrNull()
                            ?: 0) + prevScore7!! else null,
                        score8 = if (it > 7) (score8.get()?.toIntOrNull()
                            ?: 0) + prevScore8!! else null,
                    )
                }
            }
        }
        return null
    }

    fun createGame(playersNum: Int, players: Array<String>) {
        val newGame = Game(
            numberOfPlayers = playersNum,
            name1 = if (players.size > 0) players[0] else "unknown player 1",
            name2 = if (players.size > 1) players[1] else "unknown player 2",
            name3 = if (players.size > 2) players[2] else null,
            name4 = if (players.size > 3) players[3] else null,
            name5 = if (players.size > 4) players[4] else null,
            name6 = if (players.size > 5) players[5] else null,
            name7 = if (players.size > 6) players[6] else null,
            name8 = if (players.size > 7) players[7] else null
        )
        _numberOfPlayers.postValue(defineNumberOfPlayers(newGame))
        Log.d(TAG, "createGame: $newGame")
        viewModelScope.launch {
            val gameId = database.gamesDao.insert(newGame)
            val insertedGame = database.gamesDao.getGameById(gameId)
            Log.d(TAG, "createGame: $insertedGame")
            if (insertedGame == null) {
                throw IllegalArgumentException("Game was created but database return null")
            } else {
                _currentGame.postValue(insertedGame)
            }
        }
    }


    fun onRoundFinished() {
        previousRound.value.let {
            if (it == null) {
                insertRound(1)
            } else {
                insertRound(it.round + 1)
            }
        }
        //TODO Collect round
        //TODO Insert collected round
        //TODO Get inserted round
        //TODO Check all values for 100
        //TODO If 100 Find Player and finish game
        //TODO Else fill previous round and clear current
    }

}