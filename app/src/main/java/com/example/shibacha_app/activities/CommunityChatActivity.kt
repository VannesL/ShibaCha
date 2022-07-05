package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shibacha_app.databinding.ActivityCommunityChatBinding

class CommunityChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityChatBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.buttonGchatSend.setOnClickListener { gotoMyCommunity() }
    }

    private fun gotoMyCommunity() {
        val intent = Intent(this, MyCommunitiesActivity::class.java)
        startActivity(intent)
    }
}