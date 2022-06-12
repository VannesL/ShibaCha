package com.example.shibacha_app.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.shibacha_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { registerUser() }
        auth = Firebase.auth

        //remove keyboard on enter
        binding.nameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.emailField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.ageField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.passField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun registerUser() {
        //make progress bar visible
        binding.progressCircular.visibility = View.VISIBLE

        //get details
        val username = binding.nameFieldText.text.toString()
        val email = binding.emailFieldText.text.toString()
        val pass = binding.passFieldText.text.toString()
        val age = binding.ageFieldText.text.toString()
        val genderId = binding.genderOptions.checkedRadioButtonId

        //check if gender selected
        if (genderId != -1) {
            val gender:RadioButton = findViewById(genderId)
        }
        else {
            Toast.makeText(this, "Please select a gender!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }
//        Toast.makeText(this, "$username + $email + $pass + $age + ${gender.text}" , Toast.LENGTH_SHORT).show()

        //check values
        if (username.isBlank() || email.isBlank() || pass.isBlank() || age.isBlank()) {
            Toast.makeText(this, "Please fill in all the details!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        //register user
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                binding.progressCircular.visibility = View.GONE
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PickHobbiesActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.progressCircular.visibility = View.GONE
        return
    }

    //remove keyboard
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}