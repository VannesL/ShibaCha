package com.example.shibacha_app.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.shibacha_app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { gotoHobbies() }

        //remove keyboard on enter
        binding.nameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.emailField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.ageField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.passField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun gotoHobbies() {
        val intent = Intent(this, PickHobbiesActivity::class.java)
        startActivity(intent)
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