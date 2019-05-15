package com.jfboily.gamengin.engine

import com.example.gamengin.engine.GameNginActivity
import kotlin.concurrent.thread

class GameNginLogic(val gameNginActivity: GameNginActivity) {

    private var running = false;
    private lateinit var screen: GameNginScreen

    fun run() {
        var startTime: Long
        var endTime: Long
        var deltaTime = 1L

        while(running) {
            startTime = System.currentTimeMillis()

            // TODO check Inputs

            screen = gameNginActivity.screen

            // check for collisions
            checkCollisions(screen.getGameObjects())

            // update Screen objects
            screen.logicUpdate(deltaTime)

            Thread.sleep(4)

            endTime = System.currentTimeMillis()
            deltaTime = endTime - startTime
        }
    }

    fun checkCollisions(objects: List<GameNginObject>?) {
        if(objects == null) return

        for (o in objects) {
            for(o2 in objects) {
                if(o != o2) {
                    if(o.collidesWith(o2)) {
                        o.onCollision(o2)
                    }
                }
            }
        }
    }

    fun pause() {
        running = false
    }

    fun resume() {
        running = true

        thread(start = true) {
            run()
        }
    }
}