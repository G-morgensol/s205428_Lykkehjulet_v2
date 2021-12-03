package com.example.s205428lykkehjuletv2.Model

data class Player(val name: String) {

    var points: Int = 0
    val guessedLetters: ArrayList<Char> =ArrayList()
    var lives = 5
    var currentSpinWord: String = ""
}