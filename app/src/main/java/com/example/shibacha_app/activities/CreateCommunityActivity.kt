package com.example.shibacha_app.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityCreateCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class CreateCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var dbrefSpinner: DatabaseReference
    private lateinit var categoryList: ArrayList<String>
    private lateinit var imageField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        //get categories
        dbrefSpinner = firedb.getReference("Categories")

        dbrefSpinner.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                categoryList = ArrayList()
                for (categorySnapshot in dataSnapshot.children) {
                    val categoryName = categorySnapshot.child("categoryName").getValue(String::class.java)
                    if (categoryName != null) {
                        categoryList.add(categoryName)
                    }
                }
                val categorySpinner = binding.categoryField
                val categoriesAdapter = ArrayAdapter(this@CreateCommunityActivity, R.layout.simple_spinner_item, categoryList)
                categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = categoriesAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //Button Logic
        binding.buttonCreate.setOnClickListener{ createCommunity() }

        //Exit TextBox
        binding.imgField.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.commNameFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.descFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }

        //Display Image
        binding.imgField.addTextChangedListener( object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("Test", "Image")
                if (s != null) {
                    displayImg(s.toString())
                }
            }

        })

    }

    private fun displayImg(s: String) {
        if(s != null) {
            Picasso.get().load(s).into(binding.communityImg)
        }
    }

    private fun createCommunity() {
        //get details
        val name = binding.commNameFieldText.text.toString()
        val imagelink = binding.imgField.text.toString()
        val desc = binding.descFieldText.text.toString()
        val category = binding.categoryField.selectedItem.toString()

        //validate
        if (name.isBlank() || imagelink.isBlank() || desc.isBlank()) {
            Toast.makeText(this, "Please fill in all the details!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        //initialize object
        val communityID = name
        val community:CommunityModel = CommunityModel(communityID, name, desc, imagelink, category)

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

    //remove keyboard
    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //check if edittext in focus
    private fun OnFocusChangeListener (v : View, hasfocus: Boolean) {
        if (!hasfocus) {
            hideKeyboard(v)
        }
    }
}