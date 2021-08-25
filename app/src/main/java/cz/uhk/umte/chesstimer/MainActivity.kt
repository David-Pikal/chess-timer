package cz.uhk.umte.chesstimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var blackPlay: Boolean = false
    private var whitePlay: Boolean = false
    private val START_MILLI_SECONDS = 60000L

    private lateinit var whiteCounter: CountDownTimer
    private lateinit var blackCounter: CountDownTimer
    private var isRunning: Boolean = false
    // time of white player
    private var timeInMilliSecondsWhite = 0L
    // remaining time of black player
    private var timeInMilliSecondsBlack = 0L

    // start button
    private lateinit var button: Button
    // reset button
    private lateinit var reset: Button
    // pause button
    private lateinit var btnPause: Button
    private lateinit var btnBlack: Button
    private lateinit var btnWhite: Button

    private lateinit var timerWhite: TextView
    private lateinit var timerBlack: TextView
    private lateinit var timeEditWhite: EditText
    private lateinit var timeEditBlack: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // binding with view components
        button = findViewById(R.id.button)
        reset = findViewById(R.id.reset)
        btnPause = findViewById(R.id.btn_pause)
        btnWhite = findViewById(R.id.btn_w)
        btnBlack = findViewById(R.id.btn_b)

        timerWhite = findViewById(R.id.timer_w)
        timerBlack = findViewById(R.id.timer_b)

        timeEditWhite = findViewById(R.id.time_edit_w)
        timeEditBlack = findViewById(R.id.time_edit_b)

        // start button
        button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                // time white
                val time  = timeEditWhite.text.toString()
                timeInMilliSecondsWhite = time.toLong() *60000L
                // start white timer
                startWhite(timeInMilliSecondsWhite)
                //startTimer(timeInMilliSeconds)
            }
        }

        reset.setOnClickListener {
            resetTimer()
        }


    }

    private fun startWhite(timeInMilliSeconds: Long) {
        whiteCounter = object : CountDownTimer(timeInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSecondsWhite = millisUntilFinished
                updateWhiteTimer()
            }

            override fun onFinish() {
                //TODO("Not yet implemented")
            }
        }

        whiteCounter.start()
        whitePlay = true
        btnBlack.visibility = View.INVISIBLE
        btnWhite.visibility = View.VISIBLE
    }

    private fun startBlack(timeInMilliSeconds: Long) {
        blackCounter = object : CountDownTimer(timeInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSecondsBlack = millisUntilFinished
                updateBlackTimer()
                //TODO("Not yet implemented")
            }

            override fun onFinish() {
                //TODO("Not yet implemented")
            }

        }

        blackCounter.start()
        blackPlay = true
        whitePlay = false
    }

    private fun updateBlackTimer() {
        TODO("Not yet implemented")
        val minute = (timeInMilliSecondsWhite / 1000) / 60
        val seconds = (timeInMilliSecondsWhite / 1000) % 60

        timerBlack.text = "${minute}:${seconds}"
    }

    private fun updateWhiteTimer() {
        TODO("Not yet implemented")
        val minute = (timeInMilliSecondsWhite / 1000) / 60
        val seconds = (timeInMilliSecondsWhite / 1000) % 60

        timerWhite.text = "${minute}:${seconds}"
    }

    private fun pauseTimer() {

        button.text = getString(R.string.start)
        whiteCounter.cancel()
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        timeInMilliSecondsWhite = START_MILLI_SECONDS
        timeInMilliSecondsBlack = START_MILLI_SECONDS
        //todo updateTextUI()
        reset.visibility = View.INVISIBLE
    }
}