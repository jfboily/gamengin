package com.jfboily.gamengin.game.gameobjects

import com.example.gamengin.engine.GameNginActivity
import com.jfboily.gamengin.engine.GameNginObject
import com.jfboily.gamengin.engine.GameNginScreen
import com.jfboily.gamengin.engine.RefPixel

class Skeleton : GameNginObject() {

    override fun init(screen: GameNginScreen) {
        sprite = screen.createSprite("skeleton_idle.png", 24, 32, RefPixel.CENTER)
        x = 100.0f
        y = 100.0f

        speedX = 50.0f
        speedY = 50.0f

    }

    override fun logicUpdate(deltaTime: Long) {
        if ( x > GameNginActivity.GAME_WIDTH) {
            x = GameNginActivity.GAME_WIDTH.toFloat()
            speedX = -speedX
        }

        if ( x < 0) {
            x = 0.0f
            speedX = -speedX
        }

        if (y > GameNginActivity.GAME_HEIGHT) {
            y = GameNginActivity.GAME_HEIGHT.toFloat()
            speedY = -speedY
        }

        if (y < 0) {
            y = 0.0f
            speedY = -speedY
        }
    }
}