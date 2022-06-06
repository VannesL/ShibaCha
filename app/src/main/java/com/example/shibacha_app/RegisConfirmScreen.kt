package com.example.shibacha_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.RegisConfirmBinding

class RegisConfirmScreen : AppCompatActivity() {

    private lateinit var binding: RegisConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}