package com.example.servicesexample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.util.Log

class MyService : Service() {

    private val CHANNEL_ID: String = "1"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MyService", "Servicio iniciado")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId()
            startForeground(    //Muestra la notificacion. Al detener el servicio, la notificación también lo hará
                1,
                createNotification()
            )
        }
        Counter().execute()

        return START_STICKY
    }

    inner class Counter : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg params: Unit?) {
            for (i in 0..500000) {
                Log.d("Numero", i.toString())
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            stopSelf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        return Notification.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Ejecutando MyService")
            .setContentText("Contando")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelId() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, "Servicio", importance)
        mChannel.description = "Se mostrará si hay algun servicio en ejecucion"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyService", "Servicio detenido")
    }
}