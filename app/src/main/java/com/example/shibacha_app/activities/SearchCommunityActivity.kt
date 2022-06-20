package com.example.shibacha_app.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivitySearchCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.android.gms.tasks.OnCompleteListener
import java.lang.String
import kotlin.Boolean
import kotlin.Int
import android.widget.Toast
import android.widget.ListView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class SearchCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchCommunityBinding
    lateinit var firedb: FirebaseDatabase
    lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        //remove keyboard on enter
        binding.searchNameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun searchCommunity() {
        // val searchText = binding.searchNameFieldText.text.toString()
        val listView: ListView

        val communityListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val communities = dataSnapshot.getValue<List<CommunityModel>>()
                listView = findViewById<ListView>()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this, "Failed to connect to Database", Toast.LENGTH_SHORT).show()
            }
        }
        dbref.addValueEventListener(communityListener)

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