package cz.uhk.umte.chesstimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var blackPlay: Boolean = false
    private var whitePlay: Boolean = false

    private var whiteCounter: CountDownTimer? = null
    private var blackCounter: CountDownTimer? = null
    private var isRunning: Boolean = false

    // time of white player
    private var timeInMilliSecondsWhite = 0L

    // remaining time of black player
    private var timeInMilliSecondsBlack = 0L

    // start button
    private lateinit var btnStart: Button

    // pause button
    private lateinit var btnPause: Button
    private lateinit var btnBlack: Button
    private lateinit var btnWhite: Button

    // show times of players
    private lateinit var timerWhite: TextView
    private lateinit var timerBlack: TextView

    // edit times
    private lateinit var timeEditWhite: EditText
    private lateinit var timeEditBlack: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // binding with view components
        btnStart = findViewById(R.id.button)
        btnPause = findViewById(R.id.btn_pause)
        btnWhite = findViewById(R.id.btn_w)
        btnBlack = findViewById(R.id.btn_b)

        timerWhite = findViewById(R.id.timer_w)
        timerBlack = findViewById(R.id.timer_b)

        timeEditWhite = findViewById(R.id.time_edit_w)
        timeEditBlack = findViewById(R.id.time_edit_b)

        // start button
        btnStart.setOnClickListener {
            /*
            if (isRunning) {
                pauseTimer()
            } else {

             */
            if (timeEditBlack.text.isNotEmpty() && timeEditWhite.text.isNotEmpty()) {
                // time white
                val timeWhite = timeEditWhite.text.toString()
                timeInMilliSecondsWhite = timeWhite.toLong() * 60000L
                // time black
                val timeBlack = timeEditBlack.text.toString()
                timeInMilliSecondsBlack = timeBlack.toLong() * 60000L
                // start white timer
                startWhite(timeInMilliSecondsWhite)
                updateBlackTimer()
                btnStart.visibility = View.INVISIBLE
                btnPause.visibility = View.VISIBLE
                isRunning = true
            } else {
                Toast.makeText(this@MainActivity, "Enter time!!!", Toast.LENGTH_SHORT).show()
            }

        }

        btnPause.setOnClickListener {
            if(isRunning) {
                pauseTimer()
            } else {
                continueTimer()
            }

        }

        btnWhite.setOnClickListener {
            startBlack(timeInMilliSecondsBlack)
        }

        btnBlack.setOnClickListener {
            startWhite(timeInMilliSecondsWhite)
        }

    }

    private fun continueTimer() {
        if(whitePlay) {
            startWhite(timeInMilliSecondsWhite)
        } else if(blackPlay) {
            startBlack(timeInMilliSecondsBlack)
        }
        isRunning = true
        btnPause.text = getString(R.string.pause)
    }

    private fun startWhite(timeInMilliSeconds: Long) {
        whiteCounter = object : CountDownTimer(timeInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSecondsWhite = millisUntilFinished
                updateWhiteTimer()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Looser!!!", Toast.LENGTH_SHORT).show()
                blackCounter?.cancel()
                updateWhiteTimer()
            }
        }

        if (blackPlay) {
            blackCounter?.cancel()
        }

        (whiteCounter as CountDownTimer).start()
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
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Looser!!!", Toast.LENGTH_SHORT).show()
                whiteCounter?.cancel()
                updateBlackTimer()
            }

        }

        whiteCounter?.cancel()
        (blackCounter as CountDownTimer).start()
        blackPlay = true
        whitePlay = false
        btnBlack.visibility = View.VISIBLE
        btnWhite.visibility = View.INVISIBLE
    }

    private fun updateBlackTimer() {
        val minute = (timeInMilliSecondsBlack / 1000) / 60
        val seconds = (timeInMilliSecondsBlack / 1000) % 60
        if (seconds < 10) {
            timerBlack.text = getString(R.string.show_time0, minute, seconds)
        } else {
            timerBlack.text = getString(R.string.show_time, minute, seconds)
        }
    }

    private fun updateWhiteTimer() {
        val minute = (timeInMilliSecondsWhite / 1000) / 60
        val seconds = (timeInMilliSecondsWhite / 1000) % 60
        if (seconds < 10) {
            timerWhite.text = getString(R.string.show_time0, minute, seconds)
        } else {
            timerWhite.text = getString(R.string.show_time, minute, seconds)
        }
    }

    private fun pauseTimer() {
        btnPause.text = getString(R.string.go_on)
        whiteCounter?.cancel()
        blackCounter?.cancel()

        isRunning = false
        btnStart.visibility = View.VISIBLE
        btnBlack.visibility = View.INVISIBLE
        btnWhite.visibility = View.INVISIBLE
    }
    
    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        updateWhiteTimer()
        updateBlackTimer()
    }
}