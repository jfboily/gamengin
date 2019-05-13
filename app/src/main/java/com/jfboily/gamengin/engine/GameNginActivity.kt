package com.example.gamengin.engine

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jfboily.gamengin.engine.GameNginLogic
import com.jfboily.gamengin.engine.GameNginRenderer
import com.jfboily.gamengin.engine.GameNginScreen



abstract class GameNginActivity : AppCompatActivity() {

    var screen: GameNginScreen? = null
    var renderer: GameNginRenderer? = null
    var logic: GameNginLogic? = null

    val gameWidth = 1024
    val gameHeight = 552

    abstract fun startScreen(): GameNginScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderer = GameNginRenderer(this, gameWidth, gameHeight, true)
        logic = GameNginLogic(this)

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

        setContentView(renderer)
    }

    override fun onResume() {
        super.onResume()
        renderer?.resume()
    }
}