package com.example.servicesexample

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyIntentService", "Servicio iniciado")
        for (i in 0..500000) {
            Log.d("MyIntentService", "Numero: $i")
        }

        stopSelf()
    }

    override fun onDestroy() {
        Log.d("MyIntentService", "Servicio detenido")
        super.onDestroy()
    }
}