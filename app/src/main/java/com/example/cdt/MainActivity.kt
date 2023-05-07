package com.example.cdt

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.Format
import java.util.Objects

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
                startTimer()
                t1.text = "Keep going..."
            }
        }

        t3.setOnClickListener{
            resetTimer()
        }
    }

    private fun startTimer() {
         timer = object : CountDownTimer(Start_Time_In_Milli.toLong(), 1 * 1000) {
            override fun onTick(timerLeft: Long) {
                remainingTime = timerLeft.toInt()
                updateTimerText()
                progressBar.progress = remainingTime.toDouble().div(Start_Time_In_Milli.toDouble()).times(100).toInt()
            }
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Take a rest ", Toast.LENGTH_SHORT).show()
                isTimerRun = false
            }

        }.start()
        isTimerRun = true
    }

    private fun resetTimer(){
        timer?.cancel()
        remainingTime = Start_Time_In_Milli
        updateTimerText()
        t1.text = "Take Promodoro"
        isTimerRun = false
        progressBar.progress = 100
    }

    private fun updateTimerText(){
        val minute = remainingTime.div(1000).div(60)
        val second = remainingTime.div(1000)% 60
        val formattedTime = String.format("%02d:%02d" , minute , second)
        t2.text = formattedTime
    }
}


