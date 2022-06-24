package com.example.shibacha_app.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.databinding.ActivitySearchCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class SearchCommunityActivity : AppCompatActivity(), CommunityRVAdapter.CommunityClickInterface {
    private lateinit var binding: ActivitySearchCommunityBinding
    lateinit var firedb: FirebaseDatabase
    lateinit var dbref: DatabaseReference
    private lateinit var communityList: ArrayList<CommunityModel>
    private lateinit var mCommunityRVAdapter: CommunityRVAdapter
    private lateinit var communityRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCommunityBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_search_community)

        //reference database
        firedb = FirebaseDatabase.getInstance()
        dbref = firedb.getReference("Communities")

        // initialize values
        communityRV = findViewById(R.id.recycler_view)
        communityList = arrayListOf<CommunityModel>()
        mCommunityRVAdapter = CommunityRVAdapter(communityList, this, this)

        // set how to display recycler view
        communityRV.layoutManager = GridLayoutManager(this, 2)
        //set adapter for recycler view
        communityRV.adapter = mCommunityRVAdapter

        //remove keyboard on enter
        binding.searchNameField.setOnKeyListener(View.OnKeyListener {view, keyCode, event -> handleKeyEvent(view, keyCode, event) })

        searchCommunity()
    }

    private fun searchCommunity() {
        val searchText = binding.searchNameFieldText.text.toString()

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: kotlin.String?) {
                Toast.makeText(this@SearchCommunityActivity, searchText , Toast.LENGTH_SHORT).show()
                val community = dataSnapshot.getValue<CommunityModel>()
                if (community != null) {
                    var regex = Regex(searchText)
                    if (regex.containsMatchIn(community.communityName)) {
                        communityList.add(community)
                    }
                }

                // notify new addition
                mCommunityRVAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: kotlin.String?) {
                val newCommunity = dataSnapshot.getValue<CommunityModel>()
                val communityKey = dataSnapshot.key

                mCommunityRVAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val commentKey = dataSnapshot.key

                mCommunityRVAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: kotlin.String?) {
                val movedCommunity = dataSnapshot.getValue<CommunityModel>()
                val communityKey = dataSnapshot.key

                mCommunityRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        dbref.addChildEventListener(childEventListener)
    }

    //remove keyboard
    private fun handleKeyEvent(view: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            searchCommunity()
            return true
        }
        return false
    }

    override fun onCommunityClick(position: Int) {
        //startActivity(Intent(this, CreateCommunityActivity::class.java))
    }
}