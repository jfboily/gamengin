package com.jfboily.gamengin.engine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import com.example.gamengin.engine.GameNginActivity

abstract class GameNginScreen(val gameNginActivity: GameNginActivity) {

    private val sprites = mutableListOf<Sprite>()
    private val bitmaps = mutableMapOf<String, Bitmap>()
    private val gameObjects = mutableListOf<GameNginObject>()
    private var time: Long = 0L
    private var background: Bitmap? = null
    protected val input: GameNginInput

    init {
        input = gameNginActivity.input!!
    }

    fun render(canvas: Canvas) {

        if(background != null) {
            // draw background bitmap
            canvas.drawBitmap(background, 0f, 0f,null)
        }
        else {
            // black background
            canvas.drawColor(Color.BLACK)
        }

        // draw all sprites
        sprites.map {
            it.animateAndDraw(canvas, 16)
        }

        draw(canvas)
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

    fun setBackground(filename: String) {
        background = loadBitmap(filename)
    }


    fun createSprite(filename: String, frameW: Int = 0, frameH: Int = 0, refPixel: RefPixel): Sprite {
        val s = Sprite(loadBitmap(filename), frameW, frameH, refPixel)
        sprites.add(s)
        return s
    }

    fun registerGameObject(gameNginObject: GameNginObject) {
        this.gameObjects.add(gameNginObject)
        gameNginObject.init(this)
    }

    fun logicUpdate(deltaTime: Long) {
        // update time
        time += deltaTime

        // update gameobjects
        gameObjects.map {
            it.internalUpdate(deltaTime)
        }

        // game logic
        update(deltaTime)
    }

    abstract fun update(deltaTime: Long)

    abstract fun draw(canvas: Canvas)
}