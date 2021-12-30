package com.demo.mapdemologinext

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.demo.mapdemologinext.databinding.ActivitySplashBinding

/**
 * A full-screen activity that shows a splash screen and open the main screen after some delay.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val activityHandler = Handler(Looper.getMainLooper())

    private val activityRunnable = Runnable {
        val i = Intent(this, MapsActivity::class.java)
        // set the new task and clear flags
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        delayedOpen()
    }

    /**
     * Schedules a call to open new activity in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedOpen() {
        activityHandler.removeCallbacks(activityRunnable)
        activityHandler.postDelayed(activityRunnable, 1000)
    }

}