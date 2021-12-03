package com.example.s205428lykkehjuletv2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
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
        var guessWordCurrent: String = guessWords[(0 until (guessWords.size-1)).random()]

        Log.d("PreSpace",guessWordCurrent)
        guessWordCurrent = guessWordsAddSpace(guessWordCurrent)
        textview_GuessWord.text = guessWordCurrent

        val p1 = Player("player")
        val livesText = "Current lives: " +p1.lives.toString()
        textview_lives.text = livesText






        val spannable = SpannableStringBuilder(guessWordCurrent)

        //spannable.setSpan(BackgroundColorSpan(Color.WHITE), 8, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //spannable.setSpan(UnderlineSpan(),0,12,0)


        // https://developer.android.com/guide/topics/text/spans documentation on spans
        guessWordCurrent.forEachIndexed { index, c -> (
                if (c.compareTo(' ')!= 0){
                    spannable.setSpan(BackgroundColorSpan(Color.BLACK), index, index+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

                }
                ) }
        textview_GuessWord.text = spannable


        binding!!.buttonFirst.setOnClickListener {
            NavHostFragment.findNavController(this@WordGuessing)
                .navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding!!.buttonGuess.setOnClickListener {
            guessLetter(p1)
            // Updates text on button press
            val listOfLettersText = "Guessed letters: " + p1.guessedLetters.joinToString()
            textview_List_of_letters.text = listOfLettersText

        }


    }

    /**
     * This function takes the input from editText and adds it to the list of guessedLetters
     *
     * @param Player the player currently playing the game
     *
     */
    fun guessLetter(player: Player){

        val textInput = editTextEnterLetter.text.toString().toCharArray()[0]
        player.guessedLetters.add(textInput)

    }
    val guessWords: ArrayList<String> = ArrayList()
    fun addGuessWords(){
        guessWords.add("Cheesecake")
        guessWords.add("Croissant")
        guessWords.add("Cupcake")
        guessWords.add("Muffin")
        guessWords.add("Pancake")
    }

    /**
     * @param guessWord
     */
    fun guessWordsAddSpace(guessWord : String): String {
        var returnString = ""

        guessWord.forEach { letter->
                returnString = returnString.plus("$letter ")
                Log.d("Letter",letter.toString())
                Log.d("Letter2",returnString)
        }
        return returnString

        /*
        for (letter in guessWord){

            returnString.plus(returnString).plus("$letter ")
        }
        return returnString

         */
    }
    fun checkChar(charGuess: Char,guessWord:String): ArrayList<Int> {

        val returnArray = ArrayList<Int>()

        guessWord.forEachIndexed {index, letter->
            if (letter == charGuess){
                returnArray.add(index)

            }
        }
    return returnArray
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}