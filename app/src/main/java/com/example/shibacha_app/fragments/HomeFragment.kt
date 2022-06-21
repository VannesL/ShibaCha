package com.example.shibacha_app.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.shibacha_app.R
import com.example.shibacha_app.activities.MyCommunitiesActivity
import com.example.shibacha_app.models.CommunityModel
import com.google.firebase.database.*
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

    var carouselView:CarouselView? = null

    private var fireDB: FirebaseDatabase? = null
    private var dbRef: DatabaseReference? = null
    private var communityList: ArrayList<CommunityModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //
        fireDB = FirebaseDatabase.getInstance()
        dbRef = fireDB!!.getReference("Communities")

        allCommunities

        carouselView = view?.findViewById(R.id.carouselView)
        carouselView!!.pageCount = communityList?.size!!
        carouselView!!.setImageListener(imageListener)


        //
    }

    var imageListener = object :ImageListener{
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            Picasso.get().load(communityList?.get(position)?.communityImg).into(imageView)
        }

    }

    private val allCommunities: Unit
        private get() {
            communityList!!.clear()
            dbRef!!.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // add the value from the model
                    communityList!!.add(snapshot.getValue(CommunityModel::class.java))
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

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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