package com.freekickr.roombascore.ui.gameplay

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.database.entities.GameInfo
import com.freekickr.roombascore.database.entities.GameScores
import com.freekickr.roombascore.database.entities.RoundHistory
import kotlinx.coroutines.launch

private const val TAG = "GameplayViewModel"

class GameplayViewModel(private val database: RoombaDatabase) : ViewModel() {

    val etScore1 = ObservableField<String>()
    val etScore2 = ObservableField<String>()
    val etScore3 = ObservableField<String?>()
    val etScore4 = ObservableField<String?>()
    val etScore5 = ObservableField<String?>()
    val etScore6 = ObservableField<String?>()
    val etScore7 = ObservableField<String?>()
    val etScore8 = ObservableField<String?>()

    private val _currentGame = MutableLiveData<GameInfo>()
    val currentGame: LiveData<GameInfo>
        get() = _currentGame

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val _eventPlayerLost = MutableLiveData<List<Int>>()
    val eventPlayerLost: LiveData<List<Int>>
        get() = _eventPlayerLost

    fun onGameFinished() {
        currentGame.value?.let { currentGame ->
                currentGame.finished = true
                viewModelScope.launch {
                    database.gamesDao.update(currentGame)
                }
                _eventGameFinished.postValue(true)
            }
    }

    fun onGameOverNavigated() {
        _eventGameFinished.postValue(false)
    }

    fun loadGame(gameId: Long) {
        viewModelScope.launch {
            val game = database.gamesDao.getGameById(gameId)
            if (game != null) {
                _currentGame.postValue(game)
            } else {
                throw IllegalStateException("Unable to load game")
            }
        }
    }

    fun getGameScores(gameId: Long) = database.gameScoresDao.getGameScoresByGameId(gameId)

    fun checkPlayersForFinish(gameScore: GameScores) {
        val result = mutableListOf<Int>()

        gameScore.score1.let { if (it > 100) result.add(1) }
        gameScore.score2.let { if (it > 100) result.add(2) }
        gameScore.score3?.let { if (it > 100) result.add(3) }
        gameScore.score4?.let { if (it > 100) result.add(4) }
        gameScore.score5?.let { if (it > 100) result.add(5) }
        gameScore.score6?.let { if (it > 100) result.add(6) }
        gameScore.score7?.let { if (it > 100) result.add(7) }
        gameScore.score8?.let { if (it > 100) result.add(8) }

        _eventPlayerLost.postValue(result)
    }

    fun clearEditTexts() {
        currentGame.value?.let {
            etScore1.set("")
            etScore2.set("")
            etScore3.set(if (it.name3 != null) "" else null)
            etScore4.set(if (it.name4 != null) "" else null)
            etScore5.set(if (it.name5 != null) "" else null)
            etScore6.set(if (it.name6 != null) "" else null)
            etScore7.set(if (it.name7 != null) "" else null)
            etScore8.set(if (it.name8 != null) "" else null)
        }
    }

    fun saveRound(gameScores: GameScores) {
        currentGame.value?.let { currentGame ->
            gameScores.let { scores ->
                val round = RoundHistory(
                    gameId = currentGame.id,
                    round = scores.roundCount,
                    score1 = scores.score1,
                    score2 = scores.score2,
                    score3 = scores.score3,
                    score4 = scores.score4,
                    score5 = scores.score5,
                    score6 = scores.score6,
                    score7 = scores.score7,
                    score8 = scores.score8,
                )
                viewModelScope.launch {
                    database.roundsDao.insert(round)
                }
            }
        }
    }

    fun createGame(playersNum: Int, players: Array<String>) {
        val newGame = GameInfo(
            numberOfPlayers = playersNum,
            name1 = players[0],
            name2 = players[1],
            name3 = if (players.size > 2) players[2] else null,
            name4 = if (players.size > 3) players[3] else null,
            name5 = if (players.size > 4) players[4] else null,
            name6 = if (players.size > 5) players[5] else null,
            name7 = if (players.size > 6) players[6] else null,
            name8 = if (players.size > 7) players[7] else null
        )
        Log.d(TAG, "createGame: $newGame")
        viewModelScope.launch {
            val newGameId = database.gamesDao.insert(newGame)
            val insertedGame = database.gamesDao.getGameById(newGameId)
            Log.d(TAG, "createGame: $insertedGame")
            if (insertedGame != null) {
                val newScores = GameScores(
                    gameId = newGameId,
                    score1 = 0,
                    score2 = 0,
                    score3 = if (insertedGame.name3 != null) 0 else null,
                    score4 = if (insertedGame.name4 != null) 0 else null,
                    score5 = if (insertedGame.name5 != null) 0 else null,
                    score6 = if (insertedGame.name6 != null) 0 else null,
                    score7 = if (insertedGame.name7 != null) 0 else null,
                    score8 = if (insertedGame.name8 != null) 0 else null,
                    roundCount = 0
                )
                val insertedScoresId = database.gameScoresDao.insert(newScores)
                val insertedScores = database.gameScoresDao.getGameScoresById(insertedScoresId)
                if (insertedScores != null) {
                    _currentGame.postValue(insertedGame)
                } else {
                    throw IllegalArgumentException("Game was created and saved but scores not")
                }
            } else {
                throw IllegalArgumentException("Game was created but database return null")
            }
        }
    }


    fun onRoundFinished() {
        currentGame.value?.let {
            viewModelScope.launch {
                updateScores()
            }
        }
    }

    private fun updateScores() {
        currentGame.value?.let { game ->
            viewModelScope.launch {
               database.gameScoresDao.getGameScoresByGameIdSimple(game.id)?.let {
                   Log.d(TAG, "updateScores: $it")
                   it.score1 += (etScore1.get()?.toIntOrNull() ?: 0)
                   it.score2 += (etScore2.get()?.toIntOrNull() ?: 0)
                   it.score3 = it.score3?.plus((etScore3.get()?.toIntOrNull() ?: 0))
                   it.score4 = it.score4?.plus((etScore4.get()?.toIntOrNull() ?: 0))
                   it.score5 = it.score5?.plus((etScore5.get()?.toIntOrNull() ?: 0))
                   it.score6 = it.score6?.plus((etScore6.get()?.toIntOrNull() ?: 0))
                   it.score7 = it.score7?.plus((etScore7.get()?.toIntOrNull() ?: 0))
                   it.score8 = it.score8?.plus((etScore8.get()?.toIntOrNull() ?: 0))
                   it.roundCount += 1
                   database.gameScoresDao.update(it)
                }
            }
        }
    }

    fun checkRoundForEndGame(scores: GameScores) {
//        var winningName: String
//
//        var winningCount: Int = 0
//        scores.let { highScores ->
//            if (highScores.score1 < 100) {
////                winningName = highScores.name1
//                winningCount++
//            }
//            if (highScores.score2 < 100) {
////                winningName = highScores.name2
//                winningCount++
//            }
//            if (highScores.score3 != null && highScores.score3!! < 100) {
////                winningName = highScores.name3 ?: "unknown player 3"
//                winningCount++
//            }
//            if (highScores.score4 != null && highScores.score4!! < 100) {
////                winningName = highScores.name3 ?: "unknown player 4"
//                winningCount++
//            }
//            if (highScores.score5 != null && highScores.score5!! < 100) {
////                winningName = highScores.name4 ?: "unknown player 5"
//                winningCount++
//            }
//            if (highScores.score6 != null && highScores.score6!! < 100) {
////                winningName = highScores.name5 ?: "unknown player 6"
//                winningCount++
//            }
//            if (highScores.score7 != null && highScores.score7!! < 100) {
////                winningName = highScores.name5 ?: "unknown player 7"
//                winningCount++
//            }
//            if (highScores.score8 != null && highScores.score8!! < 100) {
////                winningName = highScores.name5 ?: "unknown player 8"
//                winningCount++
//            }
//        }
//        if (winningCount == 1) {
//            currentGame.value?.let { currentGame ->
//                currentGame.finished = true
//                viewModelScope.launch {
//                    database.gamesDao.update(currentGame)
//                }
//                _eventGameFinished.postValue(true)
//            }
//        }
    }

}