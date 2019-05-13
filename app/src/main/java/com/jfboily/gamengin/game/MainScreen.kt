package com.jfboily.gamengin.game

import com.example.gamengin.engine.GameNginActivity
import com.jfboily.gamengin.engine.GameNginScreen
import com.jfboily.gamengin.engine.RefPixel
import com.jfboily.gamengin.engine.Sprite

class MainScreen(gameNginActivity: GameNginActivity) : GameNginScreen(gameNginActivity) {

    var skeleton: Sprite

    init {
        skeleton = createSprite("skeleton_idle.png", 24, 32, RefPixel.CENTER)
        skeleton.x = 100
        skeleton.y = 100
    }
}