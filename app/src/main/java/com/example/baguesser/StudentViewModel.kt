package com.example.baguesser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.baguesser.data.GAME_MAX
import com.example.baguesser.data.Student
import com.example.baguesser.data.allNames
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String
    private lateinit var currentPic: String

    var userGuess by mutableStateOf("")
        private set

    var usedWords: MutableSet<String> = mutableSetOf()

    var current = allNames.random()


    private fun changer(){
        current = allNames.random()
    }

    private fun setRandomWord(): String {
        changer()
        currentWord = current.name

        if (usedWords.contains(currentWord)) {
            return setRandomWord()
        } else {
            usedWords.add(currentWord)
            return (currentWord)
        }
    }

    private fun setRandomPic(): Int {
        currentPic = current.imageResourceId.toString()

        return currentPic.toInt()


    }


    fun reset() {
        usedWords.clear()
        _uiState.value = StudentUiState(
            studentName = setRandomWord(),
            imageID = (setRandomPic())
        )

    }

    init {
        reset()
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val newScore = _uiState.value.score.plus(1)
            updateState(newScore)
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    guessedNameWrong = true
                )
            }
        }
        updateUserGuess("")
    }

    private fun updateState(newScore: Int) {
        if (usedWords.size == GAME_MAX) {
            _uiState.update { currentState ->
                currentState.copy(
                    guessedNameWrong = false,
                    score = newScore,
                    gameDone = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    guessedNameWrong = false,
                    studentName = setRandomWord(),
                    score = newScore,
                    gameProgress = currentState.gameProgress.inc(),
                    imageID = setRandomPic()
                )
            }
        }


    }


}