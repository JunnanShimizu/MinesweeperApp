package com.example.minesweeper

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.minesweeper.databinding.ActivityMainBinding
import com.example.minesweeper.databinding.ActivityStartBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener(){
            binding.minesweeperView.resetGame()
        }

        binding.flagMode.setOnClickListener(){
            binding.minesweeperView.setFlagMode(binding.flagMode.isChecked)
        }
    }

    fun showMessage(msg: String) {
        binding.tvStatus.setText(msg)
    }
}