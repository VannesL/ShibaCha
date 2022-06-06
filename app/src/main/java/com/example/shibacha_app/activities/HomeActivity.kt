package com.example.shibacha_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shibacha_app.R
import com.example.shibacha_app.databinding.ActivityHomeBinding
import com.example.shibacha_app.fragments.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val homeFragment = HomeFragment()
    private val settingFragment = SettingFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(homeFragment)

        binding.topNavigation.setOnNavigationItemSelectedListener {
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
            transaction.replace(binding.frameContainer, fragment)
            transaction.commit()
        }
    }
}