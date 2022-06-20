package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shibacha_app.R
import com.example.shibacha_app.databinding.ActivityEditCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*

class EditCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        val communityModel = intent.getParcelableExtra<CommunityModel>("community")
        var communityID: String = ""
        if (communityModel != null) {
            binding.commNameFieldText.setText(communityModel.communityName)
            binding.imgFieldText.setText(communityModel.communityImg)
            binding.descFieldText.setText(communityModel.communityDesc)
            //TODO: set categories too

            communityID = communityModel.communityName
        }

        dbref = firedb.getReference("Communities").child(communityID)
        binding.buttonUpdate.setOnClickListener{ editCommunity() }
        binding.buttonDelete.setOnClickListener{ deleteCommunity() }

    }

    private fun editCommunity() {
        //get details
        val name = binding.commNameFieldText.text.toString()
        val imagelink = binding.imgFieldText.text.toString()
        val desc = binding.descFieldText.text.toString()

        //initialize object
        val communityID = name
        val community:CommunityModel = CommunityModel(communityID, name, desc, imagelink)

        //add to database
        dbref.setValue(community)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Updated to Database", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyCommunitiesActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update to Database", Toast.LENGTH_SHORT).show()
            }

        return
    }

    private fun deleteCommunity() {
        dbref.removeValue()
        Toast.makeText(this, "Successfully deleted Community", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MyCommunitiesActivity::class.java)
        startActivity(intent)
        finish()
    }
}