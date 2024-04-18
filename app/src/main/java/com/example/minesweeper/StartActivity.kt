package com.example.minesweeper

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.minesweeper.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {
    private lateinit var binding_start: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_start = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding_start.root)

        binding_start.btnEasy.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding_start.btnMed.setOnClickListener(){

        }

        binding_start.btnHard.setOnClickListener(){

        }

        binding_start.btnImp.setOnClickListener(){

        }
    }
}