package com.example.myapplication_2.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 가로 기준으로 세로를 똑같이 설정 (정사각형)
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
