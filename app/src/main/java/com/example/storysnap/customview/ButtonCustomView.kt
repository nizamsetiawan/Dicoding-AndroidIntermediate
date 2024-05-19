package com.example.storysnap.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class ButtonCustomView : AppCompatEditText {
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER

    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
    }
}