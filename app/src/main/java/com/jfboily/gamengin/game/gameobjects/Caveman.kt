package com.jfboily.gamengin.game.gameobjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.gamengin.R
import com.jfboily.gamengin.engine.GameNginObject
import com.jfboily.gamengin.engine.GameNginScreen
import com.jfboily.gamengin.engine.RefPixel

class Caveman : GameNginObject() {

    var target: Destination? = null;
    var screen: GameNginScreen? = null
    val paint = Paint()

    override fun init(screen: GameNginScreen) {
        x = 320.0f
        y = 200.0f

        this.screen = screen

        sprite = screen.createSprite("caveman.png", 32, 32, RefPixel.CENTER)

        val refSprite = sprite!!

        ANIM_IDLE = refSprite.createAnim(listOf(14))
        refSprite.playAnim(ANIM_IDLE)
    }

    override fun logicUpdate(deltaTime: Long) {
        when(state) {
            STATE_IDLE -> {
                if(enterState) {
                    sprite?.playAnim(ANIM_IDLE)
                    speedY = 0f
                    speedX = 0f
                }
            }
            STATE_RUNNING -> {
                if(enterState) {
                    sprite?.playAnim(ANIM_RUNNING, true)
                    return
                }

                if(target != null) {
                    // arrived
                    val refTarget: Destination = target!!

                    if(distance2(refTarget as GameNginObject) < 20) {
                        state = STATE_IDLE
                        refTarget.active = false

                        // play sfx
                        screen?.audio?.playSound(R.raw.towerup)
                        return
                    }

                    // not arrived yet, move in the correct direction
                    if (x > refTarget.x) {
                        speedX = -SPEED
                        // sprite faces left
                        sprite?.flipHoriz = false
                    } else if (x < refTarget.x) {
                        speedX = SPEED
                        // sprite faces right
                        sprite?.flipHoriz = true
                    }

                    if(y > refTarget.y) {
                        speedY = -SPEED
                    } else if (y < refTarget.y) {
                        speedY = SPEED
                    }
                }
            }
        }
    }

    fun setDestination(target: Destination) {
        this.target = target
        state = STATE_RUNNING
    }

    override fun draw(canvas: Canvas) {
        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        canvas.drawRect(sprite?.dstRect, paint)
    }


    companion object {
        var ANIM_IDLE: Int = 0
        var ANIM_RUNNING = 0

        const val SPEED = 40.0f

        const val STATE_IDLE = 0
        const val STATE_RUNNING = 1
    }
}