package com.jfboily.gamengin.engine

import android.content.Context.AUDIO_SERVICE
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import com.example.gamengin.engine.GameNginActivity

class GameNginAudio(gameNginActivity: GameNginActivity) {
    private val soundPool: SoundPool
    private val audioManager: AudioManager = gameNginActivity.getSystemService(AUDIO_SERVICE) as AudioManager
    private var loaded = false
    private val sounds

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        this.soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes)
            .setMaxStreams(10)
            .build()

        gameNginActivity.volumeControlStream = AudioManager.STREAM_MUSIC

        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            loaded = true
        }
    }


}