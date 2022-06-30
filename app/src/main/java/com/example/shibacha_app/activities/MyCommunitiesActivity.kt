package com.example.shibacha_app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.adapters.CommunityRVAdapter.CommunityClickInterface
import com.example.shibacha_app.models.CommunityModel
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MyCommunitiesActivity : AppCompatActivity(), CommunityClickInterface {
    private var communityRV: RecyclerView? = null
    private var addCommunity: FloatingActionButton? = null
    private var fireDB: FirebaseDatabase? = null
    private var dbRef: DatabaseReference? = null
    private var communityList: ArrayList<CommunityModel?>? = null
    private var communityRVAdapter: CommunityRVAdapter? = null
    val db = Firebase.firestore
    val ref = db.collection("Communities")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_communities)

        // initialize values
        communityRV = findViewById(R.id.recycler_view)
        addCommunity = findViewById(R.id.add_community_button)
        val back: FloatingActionButton = findViewById(R.id.back_button)
        fireDB = FirebaseDatabase.getInstance()
        dbRef = fireDB!!.getReference("Communities")
        communityList = ArrayList()
        communityRVAdapter = CommunityRVAdapter(communityList, this, this)

        // set how to display recycler view
        communityRV?.setLayoutManager(GridLayoutManager(this, 2))
        //set adapter for recycler view
        communityRV?.setAdapter(communityRVAdapter)

        // logic for when add button is clicked
        addCommunity?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MyCommunitiesActivity,
                    CreateCommunityActivity::class.java
                )
            )
        })
        back.setOnClickListener{
            startActivity(
                Intent(
                    this@MyCommunitiesActivity,
                    HomeActivity::class.java
                )
            )
        }

        allCommunities
    }// add the value from the model

    // Show all the communities
    private val allCommunities: Unit
        private get() {
            val query = db.collection("Communities").orderBy("communityName", Query.Direction.ASCENDING)
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
                    val com = document.toObject<CommunityModel>()
                    communityList?.add(com)
                    communityRVAdapter?.notifyDataSetChanged()
                }
            }
        }

    // When the community is clicked on
    override fun onCommunityClick(position: Int) {
        //TODO: Redirect to chat page
    }
}