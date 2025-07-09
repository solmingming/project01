package com.example.myapplication_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myapplication_2.databinding.ActivityMainBinding
import com.example.myapplication_2.utils.UserGenerator
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ 유저 없으면 IntroActivity로 이동
        val existingUser = UserGenerator.loadUserFromPrefs(this)
        if (existingUser == null) {
            Log.d("MainActivity", "🚫 유저 없음 → IntroActivity로 이동")
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        } else {
            Log.d("MainActivity", "✅ 유저 존재: ${existingUser.name}")
            UserGenerator.setCachedUser(existingUser)
        }

        // ✅ 뷰 바인딩
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 네비게이션 설정
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // ✅ 하단 탭바 설정
        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener { item ->
            // 이미 해당 화면에 있으면 재네비게이션 하지 않음 (Bundle 유지 목적)
            if (navController.currentDestination?.id == item.itemId) return@setOnItemSelectedListener true

            navController.popBackStack(item.itemId, false)
            navController.navigate(item.itemId)
            true
        }
    }

    // 바깥 터치 시 키보드 내리기
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 외부에서 하단 탭을 강제로 선택하는 함수
     */
    fun setSelectedTab(menuId: Int) {
        binding.navView.selectedItemId = menuId
    }
}
