package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shibacha_app.databinding.ActivityPickHobbiesBinding

class PickHobbiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickHobbiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickHobbiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { gotoHome() }
    }

    private fun gotoHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}