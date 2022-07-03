package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.adapters.CategoryRVAdapter
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.models.CategoryModel
import com.example.shibacha_app.models.UserHobbyModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PickHobbiesActivity : AppCompatActivity() {

    private lateinit var btnSubmit: Button
    private lateinit var rvHobbies: RecyclerView
    private lateinit var hobbyList: ArrayList<CategoryModel>
    private lateinit var fStore: FirebaseFirestore
    private lateinit var categoryRVAdapter: CategoryRVAdapter
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_hobbies)

        init()
        fillRecycleView()
    }



    private fun fillRecycleView(){
        hobbyList = ArrayList()
        categoryRVAdapter = CategoryRVAdapter(this, hobbyList)
        rvHobbies.layoutManager = LinearLayoutManager(this)
        rvHobbies.adapter = categoryRVAdapter
        fStore.collection("Categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    hobbyList.add(document.toObject(CategoryModel::class.java))
                }

                categoryRVAdapter.notifyDataSetChanged()


            }


    }

    private fun init(){

        fStore = Firebase.firestore
        fAuth = Firebase.auth
        btnSubmit = findViewById(R.id.btn_submit_hobbies)
        rvHobbies = findViewById(R.id.rv_hobbies)

        btnSubmit.setOnClickListener { submitHobbies() }
    }

    private fun submitHobbies(){
        val uid = fAuth.currentUser?.uid
        val arrList = categoryRVAdapter.checkedArr

         fStore.collection("UserHobbies").whereEqualTo("userId", uid).get().addOnSuccessListener { res ->
             for (doc in res){
                 fStore.collection("UserHobbies").document(doc.id).delete()
             }

             for(a in arrList){
                 val userHobby = UserHobbyModel(uid,a)
                 fStore.collection("UserHobbies")
                     .add(userHobby)
                     .addOnSuccessListener { documentReference ->
                         Log.d("Testing", "DocumentSnapshot added with ID: ${documentReference.id}")
                     }
                     .addOnFailureListener { e ->
                         Log.w("Testing", "Error adding document", e)
                     }
             }

             val intent = Intent(this, HomeActivity::class.java)
             startActivity(intent)
         }





    }

    private fun gotoHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}