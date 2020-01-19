package com.example.servicesexample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var varbtnObtenerNumero: Button
    private lateinit var boundService: MyBoundService
    private var mBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        varbtnObtenerNumero = findViewById(R.id.btnGetNumber)
        varbtnObtenerNumero.setOnClickListener {
            if (mBound) {
                Toast.makeText(this, boundService.getRandomNumber().toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

//        startMyIntentService()    //Descomentar para iniciar servicio
        startMyService()          //Descomentar para iniciar servicio
    }

    private fun startMyIntentService() {
        val intent = Intent(this@MainActivity, MyIntentService::class.java)
        startService(intent)
    }

    private fun startMyService() {
        val intent = Intent(this@MainActivity, MyService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("MainActivity", "onServiceConnected")
            val binder = service as MyBoundService.MyBinder
            boundService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("MainActivity", "onServiceDisconnected")
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        startMyBoundService()
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    private fun startMyBoundService() {
        val intent = Intent(this@MainActivity, MyBoundService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}
