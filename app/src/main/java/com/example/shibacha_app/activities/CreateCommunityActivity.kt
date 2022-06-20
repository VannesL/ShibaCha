package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shibacha_app.R
import com.example.shibacha_app.databinding.ActivityCreateCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*

class CreateCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        binding.buttonCreate.setOnClickListener{ createCommunity() }

    }

    private fun createCommunity() {
        //get details
        val name = binding.commNameFieldText.text.toString()
        val imagelink = binding.imgFieldText.text.toString()
        val desc = binding.descFieldText.text.toString()

        //initialize object
        val communityID = name
        val community:CommunityModel = CommunityModel(communityID, name, desc, imagelink)

        //add to database
        dbref.child(communityID).setValue(community)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added to Database", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyCommunitiesActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add to Database", Toast.LENGTH_SHORT).show()
            }

        return
    }
}