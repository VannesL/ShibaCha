package com.example.shibacha_app.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityCreateCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CreateCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var dbrefSpinner: DatabaseReference
    private lateinit var categoryList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        //get categories
//        dbrefSpinner = firedb.getReference("Categories")

        val dbSpin = Firebase.firestore
        categoryList = ArrayList()
        dbSpin.collection("Categories")
            .get()
            .addOnSuccessListener { res ->
                for (doc in res){
                    categoryList.add(doc.getString("CategoryName").toString())
//                    Log.d("Debugging", categoryList[0])
                }
                val categorySpinner = binding.categoryField
                val categoriesAdapter = ArrayAdapter(this@CreateCommunityActivity, R.layout.simple_spinner_item, categoryList)
                categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = categoriesAdapter
            }


//        dbrefSpinner.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                categoryList = ArrayList()
//                for (categorySnapshot in dataSnapshot.children) {
//                    val categoryName = categorySnapshot.child("categoryName").getValue(String::class.java)
//                    if (categoryName != null) {
//                        categoryList.add(categoryName)
//                    }
//                }
//                val categorySpinner = binding.categoryField
//                val categoriesAdapter = ArrayAdapter(this@CreateCommunityActivity, R.layout.simple_spinner_item, categoryList)
//                categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
//                categorySpinner.adapter = categoriesAdapter
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })

        //Button Logic
        binding.buttonCreate.setOnClickListener{ createCommunity() }

        //Exit TextBox
        binding.imgFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.commNameFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.descFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }

    }

    private fun createCommunity() {
        //get details
        val name = binding.commNameFieldText.text.toString()
        val imagelink = binding.imgFieldText.text.toString()
        val desc = binding.descFieldText.text.toString()
        val category = binding.categoryField.selectedItem.toString()

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