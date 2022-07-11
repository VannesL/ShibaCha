package com.example.shibacha_app.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.shibacha_app.R
import com.example.shibacha_app.activities.MyCommunitiesActivity
import com.example.shibacha_app.models.CommunityMemberModel
import com.example.shibacha_app.models.CommunityModel
import com.example.shibacha_app.models.UserHobbyModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


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
    private var communityMemberList: ArrayList<String>? = ArrayList()
    private var userHobbyList: ArrayList<String>? = ArrayList()
    private var dbRef: DatabaseReference? = null
    private var carouselIdx = 0
    val db = Firebase.firestore
    private lateinit var fStore: FirebaseFirestore
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

//        refreshHomeFrag(context)
    }

    override fun onStart() {
        super.onStart()
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false)
        }
        ft.detach(this).attach(this).commit()
    }
//    override fun onResume() {
//        super.onResume()
//        init()
//
//        // Write a message to the database
////        val fireDB = Firebase.database
////        dbRef = fireDB.getReference("Communities")
//        communityList = java.util.ArrayList()
////        communityList!!.clear()
//        fStore = FirebaseFirestore.getInstance()
//
////        <<<<<<< Updated upstream
////        var query = fStore.collection("TestCollection")
////        query
////            .get()
////            .addOnSuccessListener { result ->
////                for (document in result) {
////
////                    val id = document.id
////                    var imgLink = document.getString("CommunityImageLink")!!
////                    Log.d("Debugging", imgLink)
////
////                    //
////                    var imageView: ImageView = ImageView(activity)
////                    Picasso.get().load(imgLink).into(imageView)
////                    if (imageView != null) {
////                        slideImages(imageView)
////                    }
////                    //
////
////                    Log.d("Debugging", "hello")
////                }
////            }
////            .addOnFailureListener { exception ->
////                Log.w("Warning", "Error getting documents.", exception)
////            }
//
////        allCommunities
////        =======
//
//        // test query
//        fillArrayList()
//        //
//
//
//        fillCommunityList()
////        btnJoin.isVisible = true
//
//
//        checkEmpty()
//    }

    private fun slideImages(img: ImageView){

        viewFlipper.addView(img)
        viewFlipper.isAutoStart = false

        viewFlipper.setInAnimation(activity, android.R.anim.fade_in)
        viewFlipper.setOutAnimation(activity, android.R.anim.fade_out)

//        btnJoin.isVisible = true

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
//        communityList!!.clear()
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

        // test query
        fillArrayList()
        //


        fillCommunityList()
//        btnJoin.isVisible = true


        checkEmpty()
    }

    private fun checkEmpty() {
        if (communityList!!.isEmpty()) {
            lblCommCate.text = ""
            lblCommName.text = ""
            lblCommDesc.text = "Looks like there's currently no recommended community for you"

    //            var imageView: ImageView = ImageView(activity)
    //            Picasso.get().load("https://cdn.shopify.com/s/files/1/1061/1924/products/Emoji_Icon_-_Sad_Emoji_large.png?v=1571606093").into(imageView)
    //            if (imageView != null) {
    //                slideImages(imageView)
    //            }
//            btnJoin.isVisible = false
        }
    }

    private fun fillCommunityList() {
        var query = fStore.collection("Communities")
        query
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {


//                    Log.d(
//                        "Testing",
//                        communityMemberList?.contains(document.toObject(CommunityModel::class.java).communityId)
//                            .toString()
//                    )
                    if (communityMemberList?.isEmpty() == true  || communityMemberList?.contains(document.toObject(CommunityModel::class.java).communityId) == false) {
                        Log.d("Testing", "c")
                        if (userHobbyList?.isEmpty() == true) {
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
                        } else {
                            Log.d("Testing",
                                userHobbyList?.contains(document.toObject(CommunityModel::class.java).communityCategory)
                                    .toString()
                            )
                            if (userHobbyList?.contains(document.toObject(CommunityModel::class.java).communityCategory) == true) {

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


                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w("Warning", "Error getting documents.", exception)
            }
    }

    private fun fillArrayList() {
        var q = fStore.collection("CommunityMembers")
        q.get().addOnSuccessListener { result ->
            for (doc in result) {
                if (doc.toObject(CommunityMemberModel::class.java).userId.equals(fAuth.currentUser?.uid)) {
                    Log.d("Testing", "a")
                    doc.toObject(CommunityMemberModel::class.java).communityId?.let {
                        communityMemberList?.add(
                            it
                        )
                    }
                }
            }
        }

        q = fStore.collection("UserHobbies")
        q.get().addOnSuccessListener { res ->
            for (doc in res) {
                if (doc.toObject(UserHobbyModel::class.java).userId.equals(fAuth.currentUser?.uid)) {
                    Log.d("Testing", "b")
                    doc.toObject(UserHobbyModel::class.java).categoryName?.let {
                        userHobbyList?.add(
                            it
                        )
                    }
                }
            }
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
        fAuth = Firebase.auth

        btnNext.setOnClickListener{ goToNext() }
        btnPrev.setOnClickListener { goToPrev() }
        btnJoin.setOnClickListener { joinCommunity() }
    }

    private fun refreshHomeFrag(context: Context?){
        context?.let{
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager

            fragmentManager?.let {
                val currFrag = fragmentManager.findFragmentById(R.id.frame_container)
                currFrag?.let {
                    val fragTrans = fragmentManager.beginTransaction()
                    fragTrans.detach(it)
                    fragTrans.attach(it)
                    fragTrans.commit()
                }
            }
        }
    }

    private fun joinCommunity(){
        if(communityList.isNullOrEmpty()){
            return
        }
        val commId = communityList!!.get(carouselIdx)?.communityId.toString()
        val user = Firebase.auth.currentUser
        val id = fAuth.currentUser?.uid
        val role = "Member"
        val currComm = communityList!!.get(carouselIdx)
        var commSize = currComm?.communityMembers

        val fireDB = Firebase.database
        val dbRefJoin = fireDB.getReference("CommunityMembers")

        val commMember = CommunityMemberModel(commId, id, role)

        val uniqueId = commId + commSize.toString()

        if (commSize != null) {
            commSize = commSize + 1
        }

        var que = fStore.collection("CommunityMembers")

        que.add(commMember).addOnSuccessListener {
            val docRef = fStore.collection("Communities").document(commId)
            val comm = CommunityModel(commId, currComm?.communityName,
                currComm?.communityDesc, currComm?.communityImg,
                currComm?.communityCategory, commSize)
            docRef.set(comm).addOnSuccessListener {
                //
                val intent = Intent(context, MyCommunitiesActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }

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
        if(communityList?.isNotEmpty() == true){
            viewFlipper.showNext()
            carouselIdx = ( carouselIdx + 1 ) % (communityList?.size!!)
            updateText()
        }


    }
    private fun goToPrev(){
        if(communityList?.isNotEmpty() == true){
            viewFlipper.showPrevious()
            carouselIdx = ( carouselIdx - 1 )
            if (carouselIdx < 0){
                carouselIdx = 2
            }
            carouselIdx %= (communityList?.size!!)
            updateText()
        }

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