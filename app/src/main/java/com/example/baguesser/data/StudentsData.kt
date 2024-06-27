package com.example.baguesser.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.baguesser.R


const val GAME_MAX = 5


data class Student(
    @DrawableRes val imageResourceId: Int,
    val name: String,
)


val allNames = setOf<Student>(
Student(R.drawable.haruka__new_year_, "Haruka"),
    Student(R.drawable.hatsune_miku, "Hatsune Miku"),
    Student(R.drawable.hare__camping_, "Hare"),
    Student(R.drawable.hifumi__swimsuit_, "Hifumi"),
    Student(R.drawable.hinata__swimsuit_, "Hinata")
    )