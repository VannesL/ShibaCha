package com.example.shibacha_app.activities

import android.R
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shibacha_app.databinding.ActivityCreateCommunityBinding
import com.example.shibacha_app.models.CommunityMemberModel
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class CreateCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCommunityBinding
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var categoryList: ArrayList<String>
    val db = Firebase.firestore
    private lateinit var fAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        val dbSpin = Firebase.firestore
        categoryList = ArrayList()
        dbSpin.collection("Categories")
            .get()
            .addOnSuccessListener { res ->
                for (doc in res){
                    categoryList.add(doc.getString("categoryName").toString())
//                    Log.d("Debugging", categoryList[0])
                }
                val categorySpinner = binding.categoryField
                val categoriesAdapter = ArrayAdapter(this@CreateCommunityActivity, R.layout.simple_spinner_item, categoryList)
                categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = categoriesAdapter
            }

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
                if (s.toString() != "") {
                    Picasso.get().load(s.toString()).into(binding.communityImg)
                }
            }

        })

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
        val document = db.collection("Communities").document()
        val communityID = document.id
        val community = CommunityModel(communityID, name, desc, imagelink, category)
        Log.d("Test", "here")

        //add to database
        document.set(community)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added Community to Database", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyCommunitiesActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add Community to Database", Toast.LENGTH_SHORT).show()
            }

        fAuth = FirebaseAuth.getInstance()
        val uid = fAuth.currentUser?.uid
        val communityMember = CommunityMemberModel(communityID, uid, "Owner")
        db.collection("CommunityMembers").add(communityMember)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added CommunityMember to Database", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MyCommunitiesActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add CommunityMember to Database", Toast.LENGTH_SHORT).show()
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