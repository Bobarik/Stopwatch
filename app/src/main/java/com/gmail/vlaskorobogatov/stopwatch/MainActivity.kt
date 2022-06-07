package com.gmail.vlaskorobogatov.stopwatch

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val timer: Timer = SystemTimer()
    private var paused: Boolean = true
    private var upperLimit: Int? = null
    private var laps: ArrayList<Long> = ArrayList()
    private lateinit var adapter: LapAdapter
    private lateinit var future: ScheduledFuture<*>
    private val backgroundExecutor: ScheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTextView(0)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        adapter = LapAdapter(this, laps)
        recycler.adapter = adapter

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "org.hyperskill", "Timer", NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Timer notification"
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateTextView(diff: Long) {
        val timeMM = (diff / 10 % 100).toString().padStart(2, '0')
        val timeS = (diff / 1000 % 60).toString().padStart(2, '0')
        val timeM = (diff / 60000).toString().padStart(2, '0')
        textView.text = getString(R.string.mmss, timeM, timeS, timeMM)
    }

    private fun changeColor() {
        val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        progressBar.indeterminateTintList = ColorStateList.valueOf(color)
    }

    private val runnable: Runnable = Runnable {
        val diff = timer.getTime()
        if (upperLimit != null && diff >= upperLimit!!) {
            textView.setTextColor(Color.RED)
            val notificationBuilder = NotificationCompat
                .Builder(applicationContext, "org.hyperskill")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Timer has expired")
                .setContentText("Click here to return to the app.")
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(393939, notificationBuilder.build())
        }
        updateTextView(diff)
        if (diff % 1000 == 0L) {
            changeColor()
        }
    }

    private fun flagTimer(view: View) {
        laps.add(timer.getTime())
        adapter.notifyItemInserted(laps.size - 1)
    }

    fun startTimer(view: View) {
        if (paused) {
            paused = false
            settingsButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            startButton.text = "Lap"
            timer.resume()
            future =
                backgroundExecutor.scheduleWithFixedDelay(runnable, 0, 10, TimeUnit.MILLISECONDS)
        } else {
            flagTimer(view)
        }
    }

    fun resetTimer(view: View) {
        startButton.text = getString(R.string.startText)
        if (!future.isCancelled)
            future.cancel(false)
        progressBar.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE
        if (paused) {
            timer.reset()
            updateTextView(0L)
            settingsButton.isEnabled = true
            adapter.notifyItemRangeRemoved(0, laps.size)
            laps.clear()
        } else {
            paused = true
        }
    }

    fun settingsDialog(view: View) {
        val contentView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null, false)
        AlertDialog.Builder(this)
            .setTitle("Set upper limit in seconds")
            .setView(contentView)
            .setPositiveButton("OK") { _, _ ->
                val editText = contentView.findViewById<EditText>(R.id.upperLimitEditText)
                upperLimit = editText.text.toString().toInt()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}