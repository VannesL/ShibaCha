package com.example.shibacha_app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.shibacha_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val NAME = "Name"
    private val EMAIL = "Email"
    private val AGE = "Age"
    private val GENDER = "Gender"
    private var regisState = NAME
//    private var textHint = "NAME"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { nextField() }
        binding.nameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.emailField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.ageField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun nextField() {
        if (regisState == NAME) {
            binding.nameField.visibility = View.INVISIBLE
            binding.emailField.visibility = View.VISIBLE
            regisState = EMAIL
        }
        else if (regisState == EMAIL) {
            binding.emailField.visibility = View.INVISIBLE
            binding.ageField.visibility = View.VISIBLE
            regisState = AGE
        }
        else if (regisState == AGE) {
            binding.ageField.visibility = View.INVISIBLE
            binding.genderOptions.visibility = View.VISIBLE
            regisState = GENDER
        }
//        else {
//
//        }
    }

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