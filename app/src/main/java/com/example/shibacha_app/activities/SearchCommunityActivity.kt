package com.example.shibacha_app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shibacha_app.R
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.adapters.CommunitySearchRVAdapter
import com.example.shibacha_app.databinding.ActivitySearchCommunityBinding
import com.example.shibacha_app.models.CommunityModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchCommunityActivity : AppCompatActivity(), CommunitySearchRVAdapter.CommunityClickInterface {
    private lateinit var binding: ActivitySearchCommunityBinding
//    lateinit var firedb: FirebaseDatabase
//    lateinit var dbref: DatabaseReference
    private lateinit var communityList: ArrayList<CommunityModel>
    private lateinit var filteredList: ArrayList<CommunityModel>
    private lateinit var mCommunityRVAdapter: CommunitySearchRVAdapter
    private lateinit var communityRV: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCommunityBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_search_community)

        //reference database
//        firedb = FirebaseDatabase.getInstance()
//        dbref = firedb.getReference("Communities")

        fStore = Firebase.firestore
        // initialize values
        communityRV = findViewById(R.id.recycler_view)
        communityList = arrayListOf<CommunityModel>()
        mCommunityRVAdapter = CommunitySearchRVAdapter(communityList, this, this)

        // set how to display recycler view
        communityRV.layoutManager = GridLayoutManager(this, 2)
        //set adapter for recycler view
        communityRV.adapter = mCommunityRVAdapter
        Log.d("Test", "Working")

        // bottomSheet view
        val bottomsheet = findViewById<RelativeLayout>(R.id.bottomSheet)

        //remove keyboard on enter
        searchBar = findViewById(R.id.search_name_field)
        searchBar.setOnFocusChangeListener { v, hasFocus -> OnFocusChangeListener(v, hasFocus) }
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
//                println("Listener")
//                Log.d("Test", "Listener")
                filter(s.toString())
            }
        })
//        searchCommunity()
        val back: FloatingActionButton = findViewById(R.id.back_button)
        back.setOnClickListener{
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }

        getAllCommunities()
    }

    private fun filter(prompt: String) {
//        Toast.makeText(this@SearchCommunityActivity, prompt , Toast.LENGTH_SHORT).show()
//        Log.d("Test", "Function")
//        println("Filter Function")
        filteredList = arrayListOf<CommunityModel>()

        for ( community in communityList) {
            if (community.communityName.lowercase().contains(prompt.lowercase())) {
                filteredList.add(community)
            }

            mCommunityRVAdapter.filterList(filteredList)
        }
    }

    private fun getAllCommunities() {
        communityList.clear()
        fStore.collection("Communities")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                // add the value from the model
                communityList.add(document.toObject(CommunityModel::class.java))
                // notify new addition
                mCommunityRVAdapter.notifyDataSetChanged()
                }
            }

//        dbref.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                // add the value from the model
//                communityList.add(snapshot.getValue(CommunityModel::class.java)!!)
//                // notify new addition
//                mCommunityRVAdapter.notifyDataSetChanged()
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                mCommunityRVAdapter.notifyDataSetChanged()
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                mCommunityRVAdapter.notifyDataSetChanged()
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                mCommunityRVAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
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

    override fun onCommunityClick (position: Int) {
        displayBottomSheet(communityList.get(position))
    }

    private fun displayBottomSheet (communityModel: CommunityModel) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.search_bottom_sheet, null)
        dialog.setContentView(view)
        dialog.show()

        val comName: TextView = view.findViewById(R.id.community_name_sheet)
        val comDesc: TextView = view.findViewById(R.id.community_desc)
        val comCategory: TextView = view.findViewById(R.id.community_category)
        val comImg: ImageView = view.findViewById(R.id.community_img_sheet)
        val joinBtn: Button = view.findViewById(R.id.join_btn_sheet)

        comName.setText(communityModel.communityName)
        comDesc.setText(communityModel.communityDesc)
        comCategory.setText(communityModel.communityCategory)
        Picasso.get().load(communityModel.communityImg).into(comImg)

        joinBtn.setOnClickListener{
            Toast.makeText(this, "Joined Community", Toast.LENGTH_SHORT).show()
        }
    }
}