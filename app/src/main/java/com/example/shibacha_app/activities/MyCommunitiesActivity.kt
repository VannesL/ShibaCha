package com.example.shibacha_app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.adapters.CommunityRVAdapter.CommunityClickInterface
import com.example.shibacha_app.models.CommunityModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class MyCommunitiesActivity : AppCompatActivity(), CommunityClickInterface {
    private var communityRV: RecyclerView? = null
    private var addCommunity: FloatingActionButton? = null
    private var fireDB: FirebaseDatabase? = null
    private var dbRef: DatabaseReference? = null
    private var communityList: ArrayList<CommunityModel?>? = null
    private var communityRVAdapter: CommunityRVAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_communities)

        // initialize values
        communityRV = findViewById(R.id.recycler_view)
        addCommunity = findViewById(R.id.add_community_button)
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
        allCommunities
    }// add the value from the model

    // notify new addition
    // Show all the communities
    private val allCommunities: Unit
        private get() {
            communityList!!.clear()
            dbRef!!.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // add the value from the model
                    communityList!!.add(snapshot.getValue(CommunityModel::class.java))
                    // notify new addition
                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    private fun editCommunities() {
        //TODO: Edit button leads to edit page
    }

    // When the community is clicked on
    override fun onCommunityClick(position: Int) {
        //TODO: Redirect to chat page
        val i = Intent(this@MyCommunitiesActivity, EditCommunityActivity::class.java)
        i.putExtra("community", communityList!![position])
        startActivity(i)
    }
}