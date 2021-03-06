package com.example.gamengin.game

import com.example.gamengin.engine.GameNginActivity
import com.jfboily.gamengin.game.MainScreen

class GameActivity : GameNginActivity() {

    // must specify a start screen
    override fun startScreen() = MainScreen(this)

    init {
        GAME_HEIGHT = 480
        GAME_WIDTH = 640
    }
}