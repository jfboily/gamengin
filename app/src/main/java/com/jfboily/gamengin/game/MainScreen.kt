package com.jfboily.gamengin.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.gamengin.engine.GameNginActivity
import com.jfboily.gamengin.engine.*
import com.jfboily.gamengin.game.gameobjects.Caveman
import com.jfboily.gamengin.game.gameobjects.Skeleton

class MainScreen(gameNginActivity: GameNginActivity) : GameNginScreen(gameNginActivity) {
    var skeleton = Skeleton()
    var caveman = Caveman()
    var target = Target()

    var debugPaint = Paint()

    init {
        registerGameObject(skeleton)
        registerGameObject(caveman)

        setBackground("cave.png")

        debugPaint.color = Color.WHITE
    }

    override fun update(deltaTime: Long) {
        if (input.touchState == TouchState.TOUCH_PRESS) {
            target.x = input.touchX.toFloat()
            target.y = input.touchY.toFloat()

            caveman.setDestination(target)

            Log.i("MainScreen", "Target set to $target.x,$target.y")
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText("FPS : ${gameNginActivity.renderer?.fps}", 500.0f, 10.0f, debugPaint)
    }
}

class Target: GameNginObject() {
    override fun logicUpdate(deltaTime: Long) {

    }

    override fun init(screen: GameNginScreen) {

    }
}