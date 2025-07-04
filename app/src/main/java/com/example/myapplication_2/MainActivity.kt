package com.example.myapplication_2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication_2.databinding.ActivityMainBinding
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.data.RecipeRepository




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        RecipeRepository.recipeList.add(
            Recipe(
                imageFileName = "morning_fresh_sandwich.jpg",
                title = "모닝 프레시 샌드위치",
                description = "1. 식빵을 토스터나 팬에 살짝 노릇하게 구워줍니다.\n" +
                        "2. 계란을 완숙으로 프라이 해줍니다.\n" +
                        "3. 토마토는 얇게 잘라주고, 상추는 씻어서 물기를 제거해줍니다.\n" +
                        "4. 빵 한 쪽에 마요네즈를 바르고 상추 -> 토마토 -> 계란 순으로 쌓아줍니다.\n" +
                        "5. 다른 빵으로 덮고 반으로 자르면 완성!",
                rating = 1,
                author = "김제형",
                ingredients = listOf("빵","계란","토마토","상추","마요네즈")
            )
        )


    }
}