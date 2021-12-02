package com.example.s205428lykkehjuletv2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.s205428lykkehjuletv2.Model.Player
import com.example.s205428lykkehjuletv2.databinding.FragmentWordGuessingBinding
import kotlinx.android.synthetic.main.fragment_word_guessing.*

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
        binding!!.buttonFirst.setOnClickListener {
            NavHostFragment.findNavController(this@WordGuessing)
                .navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        val p1 = Player("player")
        var livesText = "Current lives: " +p1.lives.toString()
        textview_lives.text = livesText

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}