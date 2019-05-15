package com.jfboily.gamengin.engine

import android.graphics.Canvas
import android.graphics.Rect

abstract class GameNginObject {
    var state = 0
        set(value) {
            field = value
            this.enterState = true
        }
    var x = 0.0f
    var y = 0.0f
    var speedX = 0.0f
    var speedY = 0.0f
    var sprite: Sprite? = null
    var enterState = false
    private var prevState = 0

    fun internalUpdate(deltaTime: Long) {
        this.prevState = state
        // move object & sprite
        x += (speedX * deltaTime / 1000L)
        y += (speedY * deltaTime / 1000L)

        var spriteRef = this.sprite

        if (spriteRef != null) {
            spriteRef.x = x.toInt()
            spriteRef.y = y.toInt()
        }

        // call user specified update
        logicUpdate(deltaTime)

        // set enterState
        enterState = prevState != state
    }

    fun distance2(other: GameNginObject): Float {
        return  ((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y))
    }

    fun collidesWith(other: GameNginObject): Boolean {
        if (sprite != null && other.sprite != null) {
            return Rect.intersects(sprite?.dstRect, other?.sprite?.dstRect)
        } else {
            return distance2(other) < 256
        }
    }

    open fun onCollision(other: GameNginObject) {

    }

    open fun draw(canvas: Canvas) {

    }

    abstract fun logicUpdate(deltaTime: Long)

    abstract fun init(screen: GameNginScreen)
}