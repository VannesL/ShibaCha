package com.example.shibacha_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.shibacha_app.R
import com.example.shibacha_app.activities.HomeActivity
import com.example.shibacha_app.models.UserModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nameField : TextInputEditText
    private lateinit var emailField : TextInputEditText
    private lateinit var passwordField : TextInputEditText
    private lateinit var ageField : TextInputEditText
    private lateinit var rbGenderMale : RadioButton
    private lateinit var rbGenderFemale : RadioButton
    private lateinit var rbGenderOther : RadioButton
    private lateinit var btnEditProfile : Button

    private lateinit var fAuth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore
    private lateinit var currUser : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        fillInitialData()

    }

    private fun fillInitialData(){
        val documentRef = fStore.collection("Users").document(currUser.uid)

        val documentListener =  documentRef.addSnapshotListener { documentSnapshot, _ ->


            if (documentSnapshot != null && documentSnapshot!!.exists()) {

                nameField.setText(documentSnapshot.getString("username"))
                emailField.setText(documentSnapshot.getString("email"))
                passwordField.setText(documentSnapshot.getString("password"))
                ageField.setText(documentSnapshot.get("age").toString())


//                documentSnapshot.getString("gender")?.let { Log.d("test", it) }

                if(documentSnapshot.getString("gender").equals("Male")){
                    rbGenderMale.isChecked = true

                }
                else if(documentSnapshot.getString("gender").equals("Female")){
                    rbGenderFemale.isChecked = true
                }
                else if(documentSnapshot.getString("gender").equals("Others")){
                    rbGenderOther.isChecked = true
                }

            } else {
                Log.d("tag", "onEvent: Document do not exists")
            }
        }
    }

    private fun init(){

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        currUser = fAuth.currentUser!!

        nameField = requireView().findViewById(R.id.edit_name_field_text)
        emailField = requireView().findViewById(R.id.edit_email_field_text)
        passwordField = requireView().findViewById(R.id.edit_pass_field_text)
        ageField = requireView().findViewById(R.id.edit_age_field_text)
        rbGenderFemale = requireView().findViewById(R.id.female_option)
        rbGenderMale = requireView().findViewById(R.id.male_option)
        rbGenderOther = requireView().findViewById(R.id.other_option)
        btnEditProfile = requireView().findViewById(R.id.edit_profile_button)

        btnEditProfile.setOnClickListener { editProfile() }

    }

    private fun editProfile(){

        var username = nameField.text.toString()
        var email = emailField.text.toString()
        var pass = passwordField.text.toString()
        var gender = ""
        if (rbGenderMale.isChecked){
            gender = "Male"
        }
        else if (rbGenderFemale.isChecked){
            gender = "Female"
        }
        else if (rbGenderOther.isChecked){
            gender = "Others"
        }



        var age = ageField.text.toString()

        //
        if (username.isBlank() || email.isBlank() || pass.isBlank() || age.isBlank()) {
            Toast.makeText(activity, "Please fill in all the details!" , Toast.LENGTH_SHORT).show()
//            binding.progressCircular.visibility = View.GONE
            return
        }

        if(!email.contains("@") || !email.contains(".com", ignoreCase = true)) {
            Toast.makeText(activity, "Email is invalid!" , Toast.LENGTH_SHORT).show()
//            binding.progressCircular.visibility = View.GONE
            return
        }

        if (pass.length < 6) {
            Toast.makeText(activity, "Password must be 6 or more characters!" , Toast.LENGTH_SHORT).show()
//            binding.progressCircular.visibility = View.GONE
            return
        }

        if (age.toInt() >= 150) {
            Toast.makeText(activity, "Age must be viable" , Toast.LENGTH_SHORT).show()
            return
        }

//        val gender: String
//        //check if gender selected
//        if (genderId != -1) {
//            gender = findViewById<RadioButton>(genderId).text as String
//        }
//        else {
//            Toast.makeText(this, "Please select a gender!" , Toast.LENGTH_SHORT).show()
//            binding.progressCircular.visibility = View.GONE
//            return
//        }
        //

        val uid = fAuth.currentUser?.uid
        val documentReference = uid?.let { it1 -> fStore.collection("Users").document(it1) };

        val user: UserModel = UserModel(username, email, pass, gender, age.toInt())

        if (documentReference != null) {
            documentReference.set(user).addOnSuccessListener {
                //                    Log.d("TAG", "user profile is created for $userID")
                Toast.makeText(activity, "Successfully join the community", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}