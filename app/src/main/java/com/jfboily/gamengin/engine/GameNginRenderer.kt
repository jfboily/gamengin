package com.jfboily.gamengin.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import com.example.gamengin.engine.GameNginActivity
import kotlin.concurrent.thread

class GameNginRenderer(val gameNginActivity: GameNginActivity, val gameWidth: Int, val gameHeight: Int, val autoscale: Boolean) : SurfaceView(gameNginActivity) {

    // timing variables
    private var startTime = 0L
    private var endTime = 0L
    private var deltaTime = 0L
    private var fpsTime = 0L

    // thread management
    private var isRunning = false
    private var renderThread: Thread? = null

    // drawing stuff
    private val surfaceHolder = getHolder()
    private val backBuffer = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.ARGB_8888)
    private val backCanvas = Canvas(backBuffer)
    private val backPaint = Paint()
    private val backBufferX = 0.0f
    private val backBufferY = 0.0f
    private var scaleH: Float = 0.0f
    private var scaleV: Float = 0.0f

    // game stuff
    private var screen: GameNginScreen? = null

    init {
    }

    fun initScale() {
        var metrics = DisplayMetrics()
        val windowManager =  gameNginActivity.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)

        val deviceWidth = metrics.widthPixels
        val deviceHeight = metrics.heightPixels

        scaleH = deviceWidth.toFloat() / gameWidth.toFloat()
        scaleV = deviceHeight.toFloat() / gameHeight.toFloat()
    }

    fun run() {
        var canvas: Canvas
        var sleepTime: Long


        startTime = System.currentTimeMillis()
        endTime = startTime
        deltaTime = 1
        fpsTime = startTime

        initScale()

        // "infinite" loop
        while(isRunning) {
            startTime = System.currentTimeMillis()
            screen = gameNginActivity.screen

            // must check for validity of Surface
            if(surfaceHolder.surface.isValid) {
                // render GameNginScreen (Sprites, etc) to the Back Buffer
                screen?.render(backCanvas)

                // render Back Buffer to main canvas

                // first, lock the canvas
                canvas = surfaceHolder.lockCanvas()

                if(autoscale) {
                    canvas.scale(scaleH, scaleV)
                }

                // fill with magenta (helps with debugging)
                canvas.drawColor(Color.MAGENTA)

                // draw the back buffer bitmap
                canvas?.drawBitmap(backBuffer, backBufferX, backBufferY, backPaint)

                // unlock & post!
                surfaceHolder.unlockCanvasAndPost(canvas)
            }

            endTime = System.currentTimeMillis()
            deltaTime = endTime - startTime

            // compute how long with must sleep to keep a steady framerate
            sleepTime =  if (deltaTime < 16) 16 - deltaTime else 0

            // adjust deltaTime to account for sleep
            deltaTime += sleepTime

            // ZzzzZzzzz
            Thread.sleep(sleepTime)
        }
    }

    fun resume() {
        isRunning = true
        thread(true) {
            this.run()
        }
    }
}