package com.example.servicesexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class MyBoundService : Service() {

    private val binder = MyBinder()
    private val numberGenerator = Random()

    fun getRandomNumber() = numberGenerator.nextInt(1000)

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

}