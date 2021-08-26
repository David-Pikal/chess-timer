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

        // TODO - maybe set in View
        btnBlack.visibility = View.INVISIBLE
        btnWhite.visibility = View.INVISIBLE
        // start button
        button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                // TODO - validation of inserted times
                // time white
                val timeWhite  = timeEditWhite.text.toString()
                timeInMilliSecondsWhite = timeWhite.toLong() *60000L
                // time black
                val timeBlack  = timeEditBlack.text.toString()
                timeInMilliSecondsBlack = timeBlack.toLong() *60000L
                // start white timer
                startWhite(timeInMilliSecondsWhite)
                // todo set values in view - for black is still 0
            }
        }

        btnPause.setOnClickListener {
            pauseTimer()
        }

        reset.setOnClickListener {
            resetTimer()
        }

        // todo - disable buttons onCreate
        btnWhite.setOnClickListener{
            startBlack(timeInMilliSecondsBlack)
        }

        btnBlack.setOnClickListener {
            startWhite(timeInMilliSecondsWhite)
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
        // todo - cancle black timer
        if(blackPlay) {
            blackCounter.cancel()
            blackPlay = false
        }

        whiteCounter.start()
        whitePlay = true
        blackPlay = false
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
        // todo - cancle white timer
        whiteCounter.cancel()

        blackCounter.start()
        blackPlay = true
        whitePlay = false
        btnBlack.visibility = View.VISIBLE
        btnWhite.visibility = View.INVISIBLE
    }

    private fun updateBlackTimer() {
        // TODO("Not yet implemented")
        val minute = (timeInMilliSecondsBlack / 1000) / 60
        val seconds = (timeInMilliSecondsBlack / 1000) % 60

        timerBlack.text = "${minute}:${seconds}"
    }

    private fun updateWhiteTimer() {
        //TODO("Not yet implemented")
        val minute = (timeInMilliSecondsWhite / 1000) / 60
        val seconds = (timeInMilliSecondsWhite / 1000) % 60

        timerWhite.text = "${minute}:${seconds}"
    }

    private fun pauseTimer() {

        button.text = getString(R.string.start)
        whiteCounter.cancel()
        blackCounter.cancel()
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