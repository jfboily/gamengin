package com.jfboily.gamengin.engine

import android.graphics.Canvas
import android.graphics.Bitmap
import android.graphics.Rect

enum class RefPixel {
    TOP_LEFT,
    TOP_RIGHT,
    TOP_CENTER,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT
}

class Sprite(val filename: String, val width: Int = 0, val height: Int = 0, refPixel: RefPixel = RefPixel.TOP_LEFT) {

    val bitmap: Bitmap
    val x: Float = 0.0f
    val y: Float = 0.0f
    val frameW: Int = 0
    val frameH: Int = 0
    val nbFrames: Int = 0
    val frames: Array<Rect>? = null
    val curFrame = 0
    val animRunning = false


    init {
        bitmap = GameNginScreen.loadBitmap("TEMPO")
    }

    fun animateAndDraw(canvas: Canvas) {
//        canvas.drawBitmap(bitmap)
    }

}
