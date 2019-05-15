package com.example.gamengin.engine

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jfboily.gamengin.engine.*


abstract class GameNginActivity : AppCompatActivity() {

    lateinit var screen: GameNginScreen
    lateinit var renderer: GameNginRenderer
    lateinit var logic: GameNginLogic
    lateinit var input: GameNginInput
    lateinit var audio: GameNginAudio

    abstract fun startScreen(): GameNginScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderer = GameNginRenderer(this, GAME_WIDTH, GAME_HEIGHT, true)
        logic = GameNginLogic(this)
        input = GameNginInput()
        audio = GameNginAudio(this)

        // Fullscreen and no sleep
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.KEEP_SCREEN_ON
        // hide action bar, of course
        actionBar?.hide()

        // set starting screen
        screen = startScreen()

        // set Input callback
        renderer.setOnTouchListener(input)

        setContentView(renderer)
    }

    override fun onResume() {
        super.onResume()
        renderer.resume()
        logic.resume()
    }

    override fun onPause() {
        super.onPause()
        renderer.pause()
        logic.pause()
    }

    companion object {
        var GAME_WIDTH = 800
        var GAME_HEIGHT = 600
    }
}