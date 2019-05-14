package com.jfboily.gamengin.engine

import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.gamengin.engine.GameNginActivity


enum class TouchState {
    TOUCH_PRESS,
    TOUCH_RELEASE,
    TOUCH_MOVE,
    TOUCH_NONE
}

class GameNginInput : View.OnTouchListener {

    var touchX = 0
    var touchY = 0
    var touchState = TouchState.TOUCH_NONE

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        touchX = event?.x?.toInt()?:0
        touchY = event?.y?.toInt()?:0

        if(event != null && v != null) {
            if(event.x != null) {
                val ratioX = event.x / v.width
                touchX = (GameNginActivity.GAME_WIDTH * ratioX).toInt()
            }
            if(event.y != null) {
                val ratioY = event.y / v.height
                touchY = (GameNginActivity.GAME_HEIGHT * ratioY).toInt()
            }
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchState = TouchState.TOUCH_PRESS
            }

            MotionEvent.ACTION_MOVE -> {
                touchState = TouchState.TOUCH_MOVE
            }

            MotionEvent.ACTION_UP -> {
                touchState = TouchState.TOUCH_NONE
            }

            else -> {}
        }

        Log.i("Input", "Touch event at $touchX,$touchY")

        return true
    }
}