package com.example.shibacha_app.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.shibacha_app.R
import com.example.shibacha_app.activities.HomeActivity
import com.example.shibacha_app.activities.MyCommunitiesActivity
import com.example.shibacha_app.adapters.CommunityRVAdapter
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
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
    private var communityList: java.util.ArrayList<CommunityModel?>? = null
    private var communityRVAdapter: CommunityRVAdapter? = null
    private var dbRef: DatabaseReference? = null
    val db = Firebase.firestore
    private lateinit var fStore: FirebaseFirestore

//    var carouselView:CarouselView? = null
//
//    private var fireDB: FirebaseDatabase? = null
//    private var dbRef: DatabaseReference? = null
//    private var communityList: ArrayList<CommunityModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //
//        fireDB = FirebaseDatabase.getInstance()
//        dbRef = fireDB!!.getReference("Communities")

//        allCommunities
//
//        carouselView = view?.findViewById(R.id.carouselView)
//        carouselView!!.pageCount = communityList?.size!!
//        carouselView!!.setImageListener(imageListener)



        //
    }

    private fun slideImages(img: ImageView){

        viewFlipper.addView(img)
        viewFlipper.flipInterval = 3000
        viewFlipper.isAutoStart = true

        viewFlipper.setInAnimation(activity, android.R.anim.slide_in_left)
        viewFlipper.setOutAnimation(activity, android.R.anim.slide_out_right)


    }

//    var imageListener = object :ImageListener{
//        override fun setImageForPosition(position: Int, imageView: ImageView?) {
//            Picasso.get().load(communityList?.get(position)?.communityImg).into(imageView)
//        }
//
//    }

//    private val allCommunities: Unit
//        private get() {
//            communityList!!.clear()
//            dbRef!!.addChildEventListener(object : ChildEventListener {
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    // add the value from the model
//                    communityList!!.add(snapshot.getValue(CommunityModel::class.java))
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
//                override fun onCancelled(error: DatabaseError) {}
//            })
//        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Write a message to the database
//        val fireDB = Firebase.database
//        dbRef = fireDB!!.getReference("Communities")
        communityList = java.util.ArrayList()
        fStore = FirebaseFirestore.getInstance()

        viewFlipper = requireView().findViewById(R.id.carouselFlipper)

        var query = fStore.collection("TestCollection")
        query
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val id = document.id
                    var imgLink = document.getString("CommunityImageLink")!!
                    Log.d("Debugging", imgLink)

                    //
                    var imageView: ImageView = ImageView(activity)
                    Picasso.get().load(imgLink).into(imageView)
                    if (imageView != null) {
                        slideImages(imageView)
                    }
                    //

                    Log.d("Debugging", "hello")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Warning", "Error getting documents.", exception)
            }

        Log.d("Debugging",db.collection("TestCollection").get().toString())

//        allCommunities

//        val images = IntArray(2)
//        images[0] = R.drawable.bijutsu_animal_neko
//        images[1] = R.drawable.basic_tea




//        var i: Int = 0;
//        for (comm in communityList!!){
//
//            var imageView: ImageView? = null
//            Picasso.get().load(communityList?.get(i)?.communityImg).into(imageView)
//            if (imageView != null) {
//                slideImages(imageView)
//            }
//            i = i+1
//        }

//        var imageView: ImageView = ImageView(activity)
//        Picasso.get().load("https://images.emojiterra.com/google/noto-emoji/v2.034/512px/26bd.png").into(imageView)
//        slideImages(imageView)

    }

    private val allCommunities: Unit
        private get() {
            communityList!!.clear()
            dbRef!!.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // add the value from the model
                    communityList!!.add(snapshot.getValue(CommunityModel::class.java))
                    Log.d("debugging", "test")
                    // notify new addition
//                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
//                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                    communityRVAdapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("debugging", "test")
                }
            })
        }

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