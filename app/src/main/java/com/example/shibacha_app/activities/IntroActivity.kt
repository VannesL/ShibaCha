package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shibacha_app.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set listeners for login and register button
        binding.loginButton.setOnClickListener{ gotoLogin() }
        binding.regisButton.setOnClickListener{ gotoRegister() }

        auth = FirebaseAuth.getInstance()
    }

    //Register Button
    private fun gotoRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    //Login Button
    private fun gotoLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    //Check if user is already authenticated
//    override fun onStart() {
//        super.onStart()
//        val user: FirebaseUser? = auth.currentUser
//        if(user != null) {
//            Toast.makeText(this, "Already logged in!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}