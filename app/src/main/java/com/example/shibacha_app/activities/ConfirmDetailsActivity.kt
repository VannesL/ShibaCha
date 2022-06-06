package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shibacha_app.databinding.ActivityConfirmDetailsBinding

class ConfirmDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { gotoHobbies() }
    }

    private fun gotoHobbies() {
        val intent = Intent(this, PickHobbiesActivity::class.java)
        startActivity(intent)
    }
}