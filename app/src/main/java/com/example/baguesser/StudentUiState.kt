package com.example.baguesser

data class StudentUiState(
    val studentName:String = "",
    val imageID:Int = 0,
    val guessedNameWrong:Boolean = false,
    val score: Int = 0,
    val gameProgress: Int = 0,
    val gameDone:Boolean = false
) {
}