package com.example.shibacha_app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.RegisConfirmBinding

class RegisConfirmActivity : AppCompatActivity() {

    private lateinit var binding: RegisConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}