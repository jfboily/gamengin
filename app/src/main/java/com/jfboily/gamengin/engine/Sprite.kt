package com.jfboily.gamengin.engine

import android.graphics.Canvas
import android.graphics.Bitmap
import android.graphics.Paint
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

class Sprite(val bitmap: Bitmap, val width: Int = 0, val height: Int = 0, refPixel: RefPixel = RefPixel.TOP_LEFT) {

    var x: Int = 0
    var y: Int = 0
    val frames = mutableListOf<Rect>()
    var currentFrame: Rect
    var currentAnimFrame = 0
    var isAnimRunning = true
    var isAnimLoop = true
    val anims = mutableListOf<List<Int>>()
    val animDelay = 16
    var animTime = 0L
    val angle = 0.0f
    val alpha = 255
    var currentAnim = 0
    val paint = Paint()
    var dstRect = Rect()
    val refX: Int
    val refY: Int

    init {
        // animation frames

        val nbFramesX = bitmap.width / width
        val nbFramesY  = bitmap.height / height
        val nbFrames = nbFramesX * nbFramesY

        for (j in 0 until nbFramesY) {
            for (i in 0 until nbFramesX) {
                val left = i * width
                val top = j * height
                frames.add(Rect(left, top, left + width, top + height))
            }
        }

        // default animation (index 0) ==> all frames
        anims.add(0, (0 until nbFrames).toList())

        currentFrame = frames[anims[0][0]]

        // destination rectangle (on canvas)
        refX = when (refPixel) {
            RefPixel.TOP_LEFT,
            RefPixel.CENTER_LEFT,
            RefPixel.BOTTOM_LEFT -> 0

            RefPixel.TOP_CENTER,
            RefPixel.CENTER,
            RefPixel.BOTTOM_CENTER -> width / 2

            RefPixel.TOP_RIGHT,
            RefPixel.CENTER_RIGHT,
            RefPixel.BOTTOM_RIGHT -> width - 1
        }

        refY = when (refPixel) {
            RefPixel.TOP_LEFT,
            RefPixel.TOP_CENTER,
            RefPixel.TOP_RIGHT -> 0

            RefPixel.CENTER_LEFT,
            RefPixel.CENTER,
            RefPixel.CENTER_RIGHT -> height / 2

            RefPixel.BOTTOM_LEFT,
            RefPixel.BOTTOM_CENTER,
            RefPixel.BOTTOM_RIGHT -> height - 1

        }
    }


    fun animateAndDraw(canvas: Canvas, deltaTime: Long) {

        // update animation?

        if (isAnimRunning) {
            animTime += deltaTime

            val nbFramesElapsed = (animTime) / animDelay

            for (i in 0 until nbFramesElapsed) {
                currentAnimFrame++
                if (currentAnimFrame >= anims[currentAnim].size) {
                    if (isAnimLoop) {
                        currentAnimFrame = 0
                    } else {
                        currentAnimFrame = anims[currentAnim].last()
                        isAnimRunning = false
                    }
                }
            }

            animTime %= animDelay
            currentFrame = frames[anims[currentAnim][currentAnimFrame]]
        }


        // draw on canvas
        canvas.save()
        // ... with rotation!
        canvas.rotate(angle, x.toFloat(), y.toFloat())
        // ... and alpha!
        paint.alpha = alpha
        dstRect.left = x - refX
        dstRect.top = y - refY
        dstRect.right = dstRect.left + width
        dstRect.bottom = dstRect.top + height
        canvas.drawBitmap(bitmap, currentFrame, dstRect, paint)
        canvas.restore()
    }

}
