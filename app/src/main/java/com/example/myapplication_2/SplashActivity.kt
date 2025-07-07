package com.example.myapplication_2  // ← 패키지명에 맞게 수정해줘요

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 일정 시간 뒤 MainActivity로 이동 (1.5초 후)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()  // SplashActivity는 종료시켜서 뒤로가기 방지
        }, 1500)
    }
}
