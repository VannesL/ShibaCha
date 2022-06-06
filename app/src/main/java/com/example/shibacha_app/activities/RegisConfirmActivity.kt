package com.example.shibacha_app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityRegisBinding

class RegisConfirmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}