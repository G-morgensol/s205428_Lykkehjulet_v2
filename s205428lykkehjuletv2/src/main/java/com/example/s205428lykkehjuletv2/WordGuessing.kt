package com.example.s205428lykkehjuletv2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.s205428lykkehjuletv2.Model.Player
import com.example.s205428lykkehjuletv2.databinding.FragmentWordGuessingBinding
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_win_game.*
import kotlinx.android.synthetic.main.fragment_word_guessing.*
import java.lang.Math.random
import java.util.*
import kotlin.collections.ArrayList

class WordGuessing : Fragment() {
    private var binding: FragmentWordGuessingBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordGuessingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addGuessWords()
        addSpinWords()
        var guessWordCurrent: String = guessWords[(0 until (guessWords.size - 1)).random()]
        var guessWordCurrentNoSpace = guessWordCurrent
        Log.d("PreSpace", guessWordCurrent)
        guessWordCurrent = guessWordsAddSpace(guessWordCurrent)
        textview_GuessWord.text = guessWordCurrent

        val p1 = Player("player")
        updateLivesText(p1)


        val spannable = SpannableStringBuilder(guessWordCurrent)


        // https://developer.android.com/guide/topics/text/spans documentation on spans
        guessWordCurrent.forEachIndexed { index, c ->
            (
                    if (c.compareTo(' ') != 0) {
                        spannable.setSpan(
                            BackgroundColorSpan(Color.BLACK),
                            index,
                            index + 1,
                            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                        )

                    }
                    )
        }
        textview_GuessWord.text = spannable



        binding!!.buttonSpin.setOnClickListener {
            button_spin.isEnabled = false
            button_spin.isClickable = false
            p1.currentSpinWord = spinWords[(0 until (spinWords.size - 1)).random()]
            textView_spin_word.text = p1.currentSpinWord

            when (p1.currentSpinWord) {
                "bankrupt" -> {
                    p1.points = 0
                    //p1.lives = p1.lives - 1
                    //updateLivesText(p1)
                    updatePointsText(p1)

                    //if (p1.lives < 1) {
                    //    loseGame()
                    //}
                    button_spin.isEnabled = true
                    button_spin.isClickable = true
                }
                "miss turn" -> {
                    p1.lives = p1.lives - 1
                    updateLivesText(p1)

                    if (p1.lives < 1) {
                        loseGame()
                    }
                    button_spin.isEnabled = true
                    button_spin.isClickable = true
                }
                "extra turn" -> {
                    p1.lives = p1.lives + 1
                    updateLivesText(p1)
                    button_spin.isEnabled = true
                    button_spin.isClickable = true
                }
                "1000" -> p1.pointsOnTheLine = 1000
                "750" -> p1.pointsOnTheLine = 750
                "600" -> p1.pointsOnTheLine = 600
                "200" -> p1.pointsOnTheLine = 200
            }
            if (p1.pointsOnTheLine > 0) {
                button_guess.isEnabled = true
                button_guess.isEnabled = true
            } else {
                button_guess.isEnabled = false
                button_guess.isEnabled = false
            }


        }

        binding!!.buttonGuess.setOnClickListener {
            if (!TextUtils.isEmpty(editTextEnterLetter.text.toString())){
                Log.d("testfek",editTextEnterLetter.text.toString())
                var guessedChar = guessLetter(p1)
                var charIsMatch = checkChar(p1,guessedChar, guessWordCurrent, spannable)


                if (charIsMatch) {
                    textview_GuessWord.text = spannable

                } else {
                    p1.lives = p1.lives - 1
                    updateLivesText(p1)
                }
                if (p1.lives < 1) {
                    loseGame()

                }


                // Updates text on button press
                val listOfLettersText = "Guessed letters: " + p1.guessedLetters.joinToString()
                textview_List_of_letters.text = listOfLettersText
                button_spin.isEnabled = true
                button_spin.isClickable = true
                p1.pointsOnTheLine = 0
                button_guess.isEnabled = false
                button_guess.isEnabled = false
                if (p1.currectlyGuessedLetters.size==guessWordCurrentNoSpace.length){
                    //Game won
                    var totalPointsMsg = "You got a total of: " + p1.points + "!"
                    textView_points_won.text = totalPointsMsg

                    NavHostFragment.findNavController(this@WordGuessing)
                        .navigate(R.id.action_FirstFragment_to_winGame)


                }
            }

        }


    }

    /**
     * This function takes the input from editText and adds it to the list of guessedLetters
     *
     * @param Player the player currently playing the game
     *
     */
    fun guessLetter(player: Player): Char {

        val textInput = editTextEnterLetter.text.toString().toCharArray()[0]

        if (!player.guessedLetters.contains(textInput)) {

            player.guessedLetters.add(textInput)
        }
        return textInput
    }

    val guessWords: ArrayList<String> = ArrayList()
    fun addGuessWords() {
        guessWords.add("cheesecake")
        guessWords.add("croissant")
        guessWords.add("cupcake")
        guessWords.add("muffin")
        guessWords.add("pancake")
    }

    val spinWords: ArrayList<String> = ArrayList()
    fun addSpinWords() {
        spinWords.add("extra turn")
        spinWords.add("bankrupt")
        spinWords.add("miss turn")
        spinWords.add("1000")
        spinWords.add("200")
        spinWords.add("600")
        spinWords.add("750")
    }

    /**
     * This function adds space between letters
     * @param guessWord the current word being guessed at
     */
    fun guessWordsAddSpace(guessWord: String): String {
        var returnString = ""

        guessWord.forEach { letter ->
            returnString = returnString.plus("$letter ")
            Log.d("Letter", letter.toString())
            Log.d("Letter2", returnString)
        }
        return returnString

        /*
        for (letter in guessWord){

            returnString.plus(returnString).plus("$letter ")
        }
        return returnString

         */
    }


    fun checkChar(currentPlayer: Player,charGuess: Char, guessWord: String, spannableWord: Spannable): Boolean {
        var checkCharBool = false
        guessWord.forEachIndexed { index, letter ->
            if (letter == charGuess) {
                currentPlayer.currectlyGuessedLetters.add(letter)
                currentPlayer.points=currentPlayer.points+currentPlayer.pointsOnTheLine
                updatePointsText(currentPlayer)
                checkCharBool = true
                spannableWord.setSpan(
                    BackgroundColorSpan(Color.WHITE),
                    index,
                    index + 1,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )

            }
        }
        return checkCharBool
    }

    fun loseGame() {
        NavHostFragment.findNavController(this@WordGuessing)
            .navigate(R.id.action_FirstFragment_to_lose_game)
    }

    fun updateLivesText(currentPlayer: Player) {
        var livesText = "Current lives: " + currentPlayer.lives.toString()
        textview_lives.text = livesText
    }
    fun updatePointsText(currentPlayer: Player){
        var pointsText = "Current points: " + currentPlayer.points
        textView_total_points.text = pointsText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}