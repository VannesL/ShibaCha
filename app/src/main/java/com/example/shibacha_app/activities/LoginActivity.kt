package com.example.shibacha_app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityIntroBinding
import com.example.shibacha_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set listeners for login and register button
        binding.buttonLogin.setOnClickListener {gotoHome()}
//        binding.loginButton.setOnClickListener{ gotoLogin() }
//        binding.regisButton.setOnClickListener{ gotoRegister() }
    }

    private fun gotoHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}