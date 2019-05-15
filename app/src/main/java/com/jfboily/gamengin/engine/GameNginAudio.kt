package com.jfboily.gamengin.engine

import android.content.Context.AUDIO_SERVICE
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import com.example.gamengin.engine.GameNginActivity

class GameNginAudio(val gameNginActivity: GameNginActivity) {
    private val soundPool: SoundPool
    private val audioManager: AudioManager = gameNginActivity.getSystemService(AUDIO_SERVICE) as AudioManager
    private var loaded = false
    private val sounds = mutableMapOf<Int, Int>()
    private var mediaPlayer = MediaPlayer()
    val sfxVolume = 0.8f
    val musicVolume = 0.8f

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


    fun loadSound(ressourceId: Int) {
        val poolId = soundPool.load(gameNginActivity, ressourceId, 1)
        sounds[ressourceId] = poolId
    }

    fun stopSound(poolId: Int) = soundPool.stop(poolId)

    fun playSound(ressourceId: Int, loop: Boolean = false): Int {
        var poolId = sounds[ressourceId]

        // if poolId is null, try to autoload the ressource
        if(poolId == null) {
            loadSound(ressourceId)
            poolId = sounds[ressourceId]
        }

        if(poolId != null) {
            return soundPool.play(poolId, sfxVolume, musicVolume, 1, if (loop) -1 else 0, 1.0f)
        } else {
            Log.e("Audio", "Invalid sound resource ${ressourceId}")
            return 0
        }
    }

    fun loadMusic(ressourceId: Int) {
        // destroy existing mediaplayer
        mediaPlayer.release()
        // and create a new one
        mediaPlayer = MediaPlayer.create(gameNginActivity, ressourceId)
    }

    fun startMusic() {
        mediaPlayer.setVolume(musicVolume, musicVolume)
        mediaPlayer.start()
        mediaPlayer.isLooping = true
    }

    fun pauseMusic() = mediaPlayer.pause()


    fun resumeMusic() = mediaPlayer.start()

    fun stopMusic() = mediaPlayer.stop()

    fun pauseAll() {
        // pause music
        pauseMusic()

        // pause all SFX
        sounds.map {
            soundPool.pause(it.value)
        }
    }

    fun resumeAll() {
        // resume music
        resumeMusic()

        // resume SFX
        sounds.map {
            soundPool.resume(it.value)
        }
    }

    fun musicPlaying(): Boolean = mediaPlayer.isPlaying()

    fun dispose() {
        pauseAll()
        soundPool.release()
        mediaPlayer.release()
    }
}