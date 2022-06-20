package com.example.shibacha_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shibacha_app.R
import com.example.shibacha_app.databinding.ActivityHomeBinding
import com.example.shibacha_app.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityHomeBinding

    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val homeFragment = HomeFragment()
    private val settingFragment = SettingFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var  top_navigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
//        setContentView(binding.root)
        replaceFragment(homeFragment)
        setNavigationListener()
    }

    private fun init(){
        top_navigation = findViewById(R.id.top_navigation)
    }

    private fun setNavigationListener(){
        top_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_favorite -> replaceFragment(favoriteFragment)
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_setting -> replaceFragment(settingFragment)
                R.id.ic_profile -> replaceFragment(profileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.commit()
        }
    }
}