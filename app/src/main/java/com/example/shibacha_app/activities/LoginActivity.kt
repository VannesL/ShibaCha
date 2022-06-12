package com.example.shibacha_app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityIntroBinding
import com.example.shibacha_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Call function for login
        binding.buttonLogin.setOnClickListener { loginUser() }

        auth = FirebaseAuth.getInstance()
    }

    //login function
    private fun loginUser(){
        //make progress bar visible
        binding.progressCircular.visibility = View.VISIBLE

        val email = binding.emailFieldText.text.toString()
        val pass = binding.passFieldText.text.toString()

        //check values
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Please fill in all the details!", Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        //sign in user
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                binding.progressCircular.visibility = View.GONE
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Log In failed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.progressCircular.visibility = View.GONE
        return
    }

}