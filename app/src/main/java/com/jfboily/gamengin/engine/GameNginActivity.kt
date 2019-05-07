package com.example.gamengin.engine

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jfboily.gamengin.engine.GameNginLogic
import com.jfboily.gamengin.engine.GameNginRenderer
import com.jfboily.gamengin.engine.GameNginScreen



object ActivityHolder {
    var gameNginActivity: GameNginActivity? = null

    fun getContext(): Context {
        return gameNginActivity!!
    }
}

abstract class GameNginActivity : AppCompatActivity() {

    var screen: GameNginScreen? = null
    val renderer = GameNginRenderer(this, false)
    val logic = GameNginLogic(this)

    abstract fun startScreen(): GameNginScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set as global "main" activity
        ActivityHolder.gameNginActivity = this

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
}