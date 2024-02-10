package com.tutorial.quadraticequationsolver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
             when(menuItem.itemId){
                 R.id.solve_nav_button ->{
                     replaceFragment(SolveFragment())
                     true
                 }
                 R.id.generate_nav_button -> {
                     replaceFragment(GenerateFragment())
                     true
                 }
                 else -> false
             }
        }
        replaceFragment(SolveFragment())







    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }


}