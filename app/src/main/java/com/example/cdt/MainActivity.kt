package com.example.cdt

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //                        min * s * millS
    val Start_Time_In_Milli = 25 * 60 * 1000
    var remainingTime = Start_Time_In_Milli
    var timer :CountDownTimer?= null
    var isTimerRun = false
    lateinit var t1 : TextView
    lateinit var t2 : TextView
    lateinit var B1 : Button
    lateinit var t3 : TextView
    var player: MediaPlayer?=null
    lateinit var progressBar : ProgressBar
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t1 = findViewById(R.id.t1)
        t2 = findViewById(R.id.T2)
        t3 = findViewById(R.id.T3)
        B1 = findViewById(R.id.B1)
        progressBar = findViewById(R.id.progress_circular)

        B1.setOnClickListener {
            if (!isTimerRun) {
                startTimer(Start_Time_In_Milli.toLong())
                t1.text = "Keep going..."
            }
        }

        t3.setOnClickListener{
            resetTimer()
        }
    }

    private fun startTimer(startTime : Long) {
         timer = object : CountDownTimer(startTime, 1 * 1000) {
            override fun onTick(timerLeft: Long) {
                remainingTime = timerLeft.toInt()
                updateTimerText()
                progressBar.progress = remainingTime.toDouble().div(Start_Time_In_Milli.toDouble()).times(100).toInt()
                player?.stop()
            }
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Take a rest ", Toast.LENGTH_SHORT).show()
                player = MediaPlayer.create(this@MainActivity,R.raw.alarm_clock)
                player?.start()
                isTimerRun = false
            }

        }.start()
        isTimerRun = true
    }

    override fun onBackPressed() {
        player?.stop()
        super.onBackPressed()
    }
    @SuppressLint("SetTextI18n")
    private fun resetTimer(){
        timer?.cancel()
        remainingTime = Start_Time_In_Milli
        updateTimerText()
        t1.text = "Take Pomodoro"
        isTimerRun = false
        progressBar.progress = 100
    }

    private fun updateTimerText(){
        val minute = remainingTime.div(1000).div(60)
        val second = remainingTime.div(1000)% 60
        val formattedTime = String.format("%02d:%02d" , minute , second)
        t2.text = formattedTime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putLong("remaining Time" , remainingTime.toLong())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedTime = savedInstanceState.getLong("remaining Time")

        if (savedTime  != Start_Time_In_Milli.toLong())
            startTimer(savedTime)
    }
}


