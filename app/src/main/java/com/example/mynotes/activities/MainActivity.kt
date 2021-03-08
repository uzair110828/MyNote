package com.example.mynotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
    }

    fun replaceFragment(fragment: Fragment ){
        var fragmentTrasition = supportFragmentManager.beginTransaction()

        fragmentTrasition.replace(R.id.frame_layout,fragment).commit()

    }
}