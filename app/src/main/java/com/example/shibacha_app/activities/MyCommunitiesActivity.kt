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
import com.google.firebase.auth.FirebaseAuth
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
    private var communityList: ArrayList<CommunityModel?>? = null
    private var communityRVAdapter: CommunityRVAdapter? = null
    val db = Firebase.firestore
    val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val uid : String? = fAuth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_communities)

        // initialize values
        communityRV = findViewById(R.id.recycler_view)
        addCommunity = findViewById(R.id.add_community_button)
        val back: FloatingActionButton = findViewById(R.id.back_button)
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
            val query = db.collection("CommunityMembers").whereEqualTo("userId", uid)
            query.get().addOnSuccessListener { documents ->
                for (document in documents) {
//                    if (uid != null) {
//                        Log.d("CommunityMember", document.id)
//                        document.getString("communityId")?.let { Log.d("CommunityId", it) }
//                    };
                    val q = db.collection("Communities").whereEqualTo("communityId", document.get("communityId"))
                    q.get().addOnSuccessListener { docs->
                        for (doc in docs) {
//                            Log.d("Community", doc.id)
                            val com = doc.toObject<CommunityModel>()
                            communityList?.add(com)
                            communityRVAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    // When the community is clicked on
    override fun onCommunityClick(position: Int) {
        //TODO: Redirect to chat page
    }
}