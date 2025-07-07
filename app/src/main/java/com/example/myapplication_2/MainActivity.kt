package com.example.myapplication_2

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication_2.databinding.ActivityMainBinding
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.data.sampleRecipes
import com.example.myapplication_2.R.id.navigation_notifications
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager

class MainActivity : AppCompatActivity() {

    private lateinit var autoCompleteSearch: AutoCompleteTextView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.popBackStack(R.id.navigation_home, false)
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_dashboard -> {
                    navController.popBackStack(R.id.navigation_dashboard, false)
                    navController.navigate(R.id.navigation_dashboard)
                    true
                }
                R.id.navigation_notifications -> {
                    navController.popBackStack(R.id.navigation_notifications, false)
                    navController.navigate(R.id.navigation_notifications)
                    true
                }
                else -> false
            }
        }


    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
