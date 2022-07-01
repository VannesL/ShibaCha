package com.example.shibacha_app.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.shibacha_app.R
import com.example.shibacha_app.activities.HomeActivity
import com.example.shibacha_app.activities.MyCommunitiesActivity
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.models.CommunityMemberModel
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var btnPrev: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var lblCommName: TextView
    private lateinit var lblCommDesc: TextView
    private lateinit var lblCommCate: TextView
    private lateinit var btnJoin: Button
    private var communityList: java.util.ArrayList<CommunityModel?>? = null
    private var dbRef: DatabaseReference? = null
    private var carouselIdx = 0
    val db = Firebase.firestore
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun slideImages(img: ImageView){

        viewFlipper.addView(img)
        viewFlipper.isAutoStart = false

        viewFlipper.setInAnimation(activity, android.R.anim.fade_in)
        viewFlipper.setOutAnimation(activity, android.R.anim.fade_out)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        // Write a message to the database
//        val fireDB = Firebase.database
//        dbRef = fireDB.getReference("Communities")
        communityList = java.util.ArrayList()
        fStore = FirebaseFirestore.getInstance()

//        <<<<<<< Updated upstream
//        var query = fStore.collection("TestCollection")
//        query
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//
//                    val id = document.id
//                    var imgLink = document.getString("CommunityImageLink")!!
//                    Log.d("Debugging", imgLink)
//
//                    //
//                    var imageView: ImageView = ImageView(activity)
//                    Picasso.get().load(imgLink).into(imageView)
//                    if (imageView != null) {
//                        slideImages(imageView)
//                    }
//                    //
//
//                    Log.d("Debugging", "hello")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("Warning", "Error getting documents.", exception)
//            }

//        allCommunities
//        =======
        var query = fStore.collection("Communities")
        query
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val id = document.id
                    var imgLink = document.getString("communityImg")!!
                    Log.d("Debugging", imgLink)

                    // Show Image
                    var imageView: ImageView = ImageView(activity)
                    Picasso.get().load(imgLink).into(imageView)
                    if (imageView != null) {
                        slideImages(imageView)
                    }

                    //

                    communityList!!.add(document.toObject<CommunityModel>())
                    updateText()
                    Log.d("Debugging", "hello")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Warning", "Error getting documents.", exception)
            }

    }

    private fun init(){
        viewFlipper = requireView().findViewById(R.id.carouselFlipper)
        btnNext = requireView().findViewById(R.id.next_btn)
        btnPrev = requireView().findViewById(R.id.prev_btn)
        lblCommName = requireView().findViewById(R.id.community_lbl)
        lblCommDesc = requireView().findViewById(R.id.comm_desc_lbl)
        lblCommCate = requireView().findViewById(R.id.comm_cate_lbl)
        btnJoin = requireView().findViewById(R.id.join_btn)

        btnNext.setOnClickListener{ goToNext() }
        btnPrev.setOnClickListener { goToPrev() }
        btnJoin.setOnClickListener { joinCommunity() }
    }

    private fun joinCommunity(){
        val commId = communityList!!.get(carouselIdx)?.communityId.toString()
        val user = Firebase.auth.currentUser
        val email = user?.email
        val role = "member"
        var commSize = communityList!!.get(carouselIdx)?.communityMembers

        val fireDB = Firebase.database
        val dbRefJoin = fireDB.getReference("CommunityMembers")

        val commMember = CommunityMemberModel(commId, email, role)

        val uniqueId = commId + commSize.toString()

        if (commSize != null) {
            commSize = commSize + 1
        }

//        dbRef?.child(commId)?.child("communityMembers")?.setValue(commSize)
//
//        dbRefJoin.child(uniqueId).setValue(commMember)
//            .addOnSuccessListener {
//                Toast.makeText(context, "Successfully join the community", Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, MyCommunitiesActivity::class.java)
//                startActivity(intent)
//            }
//            .addOnFailureListener {
//                Toast.makeText(context, "Failed to join the community", Toast.LENGTH_SHORT).show()
//            }

    }

    private fun goToNext(){
        viewFlipper.showNext()
        carouselIdx = ( carouselIdx + 1 ) % (communityList?.size!!)
        updateText()

    }
    private fun goToPrev(){
        viewFlipper.showPrevious()
        carouselIdx = ( carouselIdx - 1 )
        if (carouselIdx < 0){
            carouselIdx = 2
        }
        carouselIdx %= (communityList?.size!!)
        updateText()
    }

    @SuppressLint("SetTextI18n")
    private fun updateText(){
        lblCommName.text = "Name: " + communityList!!.get(carouselIdx)?.communityName.toString()
        lblCommDesc.text = "Description: \n" + communityList!!.get(carouselIdx)?.communityDesc.toString()
        lblCommCate.text = "Category: " + communityList!!.get(carouselIdx)?.communityCategory.toString()

    }

//    private val allCommunities: Unit
//        private get() {
//            communityList!!.clear()
//            dbRef!!.addChildEventListener(object : ChildEventListener {
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    // add the value from the model
//
//                    var comm = snapshot.getValue(CommunityModel::class.java)
//                    communityList!!.add(comm)
//                    var imageView = ImageView(activity)
//                    var imgLink = comm?.communityImg.toString()
//                    Log.d("Debugging", imgLink)
//                    Picasso.get().load(imgLink).into(imageView)
//                    slideImages(imageView)
//                    updateText()
////                    Log.d("debugging", communityList!!.get(0)?.communityImg.toString())
//                    // notify new addition
////                    communityRVAdapter!!.notifyDataSetChanged()
//                }
//
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
////                    communityRVAdapter!!.notifyDataSetChanged()
//                }
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {
////                    communityRVAdapter!!.notifyDataSetChanged()
//                }
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
////                    communityRVAdapter!!.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d("debugging", "testa")
//                }
//            })
//        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}