package com.jfboily.gamengin.engine

import com.example.gamengin.engine.GameNginActivity
import kotlin.concurrent.thread

class GameNginLogic(val gameNginActivity: GameNginActivity) {

    private var running = false;
    private var screen: GameNginScreen? = null

    fun run() {
        var startTime = System.currentTimeMillis()
        var endTime: Long
        var deltaTime = 1L

        while(running) {
            startTime = System.currentTimeMillis()

            // TODO check Inputs

            screen = gameNginActivity.screen

            // update Screen objects
            screen?.logicUpdate(deltaTime)

            Thread.sleep(4)

            endTime = System.currentTimeMillis()
            deltaTime = endTime - startTime
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