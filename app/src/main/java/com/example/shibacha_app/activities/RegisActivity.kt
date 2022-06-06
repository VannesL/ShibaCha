package com.example.shibacha_app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import com.example.shibacha_app.databinding.ActivityRegisBinding

class RegisActivity : AppCompatActivity() {

    private val NAME = "Name"
    private val EMAIL = "Email"
    private val PASS = "Password"
    private val AGE = "Age"
    private val GENDER = "Gender"
    private var regisState = NAME
//    private var textHint = "NAME"

    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Removing Title and Action Bar
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
////        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        supportActionBar?.hide();
        //
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ok button
        binding.okButton.setOnClickListener { nextField() }

        //remove keyboard on enter
        binding.nameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.emailField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.ageField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    //on click functions
    private fun nextField() {
        if (regisState == NAME) {
            binding.nameField.visibility = View.INVISIBLE
            binding.emailField.visibility = View.VISIBLE
            regisState = EMAIL
        }
        else if (regisState == EMAIL) {
            binding.emailField.visibility = View.INVISIBLE
            binding.passField.visibility = View.VISIBLE
            regisState = PASS
        }
        else if(regisState == PASS) {
            binding.passField.visibility = View.INVISIBLE
            binding.ageField.visibility = View.VISIBLE
            regisState = AGE
        }
        else if (regisState == AGE) {
            binding.ageField.visibility = View.INVISIBLE
            binding.genderOptions.visibility = View.VISIBLE
            regisState = GENDER
        }
        else {
            val intent = Intent(this, ConfirmDetailsActivity::class.java)
            startActivity(intent)
        }
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