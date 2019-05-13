package com.jfboily.gamengin.engine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.gamengin.engine.GameNginActivity

abstract class GameNginScreen(val gameNginActivity: GameNginActivity) {

    private val sprites = mutableListOf<Sprite>()
    private val bitmaps = mutableMapOf<String, Bitmap>()

    fun render(canvas: Canvas) {

        // black background
        canvas.drawColor(Color.BLACK)

        // draw all sprites
        sprites.map {
            it.animateAndDraw(canvas, 16)
        }

        // TODO : add mote stuff here:
        // * debug drawing : draw debugging infos, like fps
        // * UI drawing : HUB and buttons etc
        // * User drawing : additional drawing over sprites
    }


    /**
     * Sprites functions
     */
    fun loadBitmap(filename: String): Bitmap {
        // check if bitmap is already loaded
        val bitmap: Bitmap
        if(bitmaps[filename] != null) {
            bitmap = bitmaps[filename]!!
        } else {
            val inputStream = gameNginActivity.applicationContext.assets.open(filename)
            bitmap = BitmapFactory.decodeStream(inputStream)
            bitmaps[filename] = bitmap
        }

        return bitmap
    }

    fun createSprite(filename: String, frameW: Int = 0, frameH: Int = 0, refPixel: RefPixel): Sprite {
        val s = Sprite(loadBitmap(filename), frameW, frameH, refPixel)
        sprites.add(s)
        return s
    }



}