package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shibacha_app.R
import com.example.shibacha_app.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set listeners for login and register button
        binding.loginButton.setOnClickListener{ gotoLogin() }
        binding.regisButton.setOnClickListener{ gotoRegister() }
    }

    //Register Button
    private fun gotoRegister() {
        val intent = Intent(this, RegisActivity::class.java)
        startActivity(intent)
    }

    //Login Button
    private fun gotoLogin() {
        TODO("Not yet implemented")
    }
}