package com.example.shibacha_app.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.shibacha_app.databinding.ActivityEditCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var dbrefSpinner: DatabaseReference
    private lateinit var categoryList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        val communityModel = intent.getParcelableExtra<CommunityModel>("community")
        var communityID: String = ""

        dbref = firedb.getReference("Communities").child(communityID)

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
                val categoriesAdapter = ArrayAdapter(this@EditCommunityActivity, R.layout.simple_spinner_item, categoryList)
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
//                val categoriesAdapter = ArrayAdapter(this@EditCommunityActivity, R.layout.simple_spinner_item, categoryList)
//                categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
//                categorySpinner.adapter = categoriesAdapter
//
//                //Select the category
//                val value = communityModel?.communityCategory
//                val pos  = categoryList.indexOf(value)
//                binding.categoryField.setSelection(pos)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })

        //set values
        if (communityModel != null) {
            binding.commNameFieldText.setText(communityModel.communityName)
            binding.imgFieldText.setText(communityModel.communityImg)
            binding.descFieldText.setText(communityModel.communityDesc)
            communityID = communityModel.communityName
        }

        //Set button logic
        binding.buttonUpdate.setOnClickListener{
            if (communityModel != null) {
                editCommunity(communityModel)
            }
        }
        binding.buttonDelete.setOnClickListener{
            if (communityModel != null) {
                deleteCommunity(communityModel)
            }
        }

        //Exit TextBox
        binding.imgFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.commNameFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        binding.descFieldText.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }

    }

    private fun editCommunity(communityModel: CommunityModel) {
        //get details
        val name = binding.commNameFieldText.text.toString()
        val imagelink = binding.imgFieldText.text.toString()
        val desc = binding.descFieldText.text.toString()
        val category = binding.categoryField.selectedItem.toString()

        //initialize object
        val communityID = communityModel.communityId
        val community:CommunityModel = CommunityModel(communityID, name, desc, imagelink, category)

        //add to database
        dbref = firedb.getReference("Communities")
        dbref.child(communityID).setValue(community)
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

    private fun deleteCommunity( communityModel: CommunityModel ) {
        dbref = firedb.getReference("Communities").child(communityModel.communityId)
        dbref.removeValue()
        Toast.makeText(this, "Successfully deleted Community", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MyCommunitiesActivity::class.java)
        startActivity(intent)
        finish()
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