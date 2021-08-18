package cz.uhk.umte.chesstimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var START_MILLI_SECONDS = 60000L

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var blackCounter: CountDownTimer
    private var isRunning: Boolean = false
    private var timeInMilliSeconds = 0L

    private lateinit var button: Button
    private lateinit var reset: Button

    private lateinit var timer: TextView
    private lateinit var timeEditTest: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        reset = findViewById(R.id.reset)
        timer = findViewById(R.id.timer_w)
        timeEditTest = findViewById(R.id.time_edit_w)

        button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                val time  = timeEditTest.text.toString()
                timeInMilliSeconds = time.toLong() *60000L
                startTimer(timeInMilliSeconds)
            }
        }

        reset.setOnClickListener {
            resetTimer()
        }


    }

    private fun pauseTimer() {

        button.text = getString(R.string.start)
        countdownTimer.cancel()
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun startTimer(time_in_seconds: Long) {
        countdownTimer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                // todo
            }

            override fun onTick(p0: Long) {
                timeInMilliSeconds = p0
                updateTextUI()
            }
        }
        countdownTimer.start()

        isRunning = true
        button.text = getString(R.string.pause)
        reset.visibility = View.INVISIBLE

        // TODO
        blackCounter = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onTick(p0: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }

    }

    private fun resetTimer() {
        timeInMilliSeconds = START_MILLI_SECONDS
        updateTextUI()
        reset.visibility = View.INVISIBLE
    }

    private fun updateTextUI() {
        val minute = (timeInMilliSeconds / 1000) / 60
        val seconds = (timeInMilliSeconds / 1000) % 60

        timer.text = "${minute}:${seconds}"
    }
}