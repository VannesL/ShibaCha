package com.example.shibacha_app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.IntroBinding

class IntroScreenActivity: AppCompatActivity() {

    private lateinit var binding: IntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.regisButton.setOnClickListener{ regisCall() }
        binding.loginButton.setOnClickListener{ loginCall() }
    }

    private fun loginCall() {
        TODO("Not yet implemented")
    }

    private fun regisCall() {
        TODO("Not yet implemented")
    }
}