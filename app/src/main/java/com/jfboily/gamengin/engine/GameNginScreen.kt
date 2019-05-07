package com.jfboily.gamengin.engine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.gamengin.engine.ActivityHolder
import java.io.InputStream

abstract class GameNginScreen {

    private val sprites = mutableListOf<Sprite>()

    fun render(canvas: Canvas) {

        // black background
        canvas.drawColor(Color.BLACK)

        // draw all sprites
        sprites.map {
            it.animateAndDraw(canvas)
        }

        // TODO : add mote stuff here:
        // * debug drawing : draw debugging infos, like fps
        // * UI drawing : HUB and buttons etc
        // * User drawing : additional drawing over sprites
    }

    companion object {
        val bitmaps = mutableMapOf<String, Bitmap>()

        fun loadBitmap(filename: String): Bitmap {
            if(!bitmaps.containsKey(filename)) {
                return BitmapFactory.decodeStream(ActivityHolder.getContext().openFileInput(filename))
            }

            return bitmaps[filename]!!
        }
    }
}