package com.example.shibacha_app.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.shibacha_app.activities.HomeActivity
import com.example.shibacha_app.activities.LoginActivity
import com.example.shibacha_app.databinding.ActivityRegisterBinding
import com.example.shibacha_app.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firedb: FirebaseDatabase
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.okButton.setOnClickListener { registerUser() }

        //Ref Databases
        auth = Firebase.auth
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference()

        //remove keyboard on enter
        binding.nameField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.emailField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.ageField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode) }
        binding.passField.setOnKeyListener {view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun registerUser() {
        //make progress bar visible
        binding.progressCircular.visibility = View.VISIBLE

        //get details
        val username = binding.nameFieldText.text.toString()
        val email = binding.emailFieldText.text.toString()
        val pass = binding.passFieldText.text.toString()
        val age = binding.ageFieldText.text.toString()
        val genderId = binding.genderOptions.checkedRadioButtonId

//        Toast.makeText(this, "$username + $email + $pass + $age + ${gender.text}" , Toast.LENGTH_SHORT).show()

        //check values
        if (username.isBlank() || email.isBlank() || pass.isBlank() || age.isBlank()) {
            Toast.makeText(this, "Please fill in all the details!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        if(!email.contains("@") || !email.contains(".com", ignoreCase = true)) {
            Toast.makeText(this, "Email is invalid!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        if (pass.length < 6) {
            Toast.makeText(this, "Password must be 6 or more characters!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        if (age.toInt() >= 150) {
            Toast.makeText(this, "Age must be viable" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        val gender: String
        //check if gender selected
        if (genderId != -1) {
            gender = findViewById<RadioButton>(genderId).text as String
        }
        else {
            Toast.makeText(this, "Please select a gender!" , Toast.LENGTH_SHORT).show()
            binding.progressCircular.visibility = View.GONE
            return
        }

        //register user
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                binding.progressCircular.visibility = View.GONE

                //create user in database
                //make user model


                //add to database
//                dbref.child("Users").child(userID).setValue(user)
//                    .addOnSuccessListener {
//                        Toast.makeText(this, "Successfully Signed Up!", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, HomeActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(this, "Error during creation", Toast.LENGTH_SHORT).show()
//                    }


                val db = Firebase.firestore
//                db.collection("Users")
//                    .add(user)
//                    .addOnSuccessListener { documentReference ->
//                        Log.d("Debugging", "Successfully added to database")
//                        Toast.makeText(this, "Successfully Signed Up!", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, HomeActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                    .addOnSuccessListener {
//                        Log.w("Warning", "Error adding to database")
//                        Toast.makeText(this, "Error during creation", Toast.LENGTH_SHORT).show()
//                    }

                val uid = auth.currentUser?.uid
                val documentReference = uid?.let { it1 -> db.collection("Users").document(it1) };

                val user:UserModel = UserModel(username, email, pass, gender, age.toInt())

                if (documentReference != null) {
                    documentReference.set(user).addOnSuccessListener {
                        //                    Log.d("TAG", "user profile is created for $userID")
                    }
                }

                Log.d("Test","Testt")

                val intent = Intent(this, PickHobbiesActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
            }
        }


//        if (success == 1) {
//
//        } else {
////            Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
//        }


        binding.progressCircular.visibility = View.GONE
        return
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