package com.gb.stopwatch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gb.stopwatch.R
import com.gb.stopwatch.databinding.ActivityMainBinding
import com.gb.stopwatch.domain.*
import com.gb.stopwatch.domain.model.TimestampMillisecondsFormatter
import com.gb.stopwatch.viewmodel.StopWatchViewModel
import com.gb.stopwatch.viewmodel.StopWatchViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val timestampProvider: ITimestampProvider = TimestampProviderImpl()

    private lateinit var modelFirst: StopWatchViewModel
    private lateinit var modelSecond: StopWatchViewModel

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        scope.launch {
            modelFirst.ticker.collect {
                binding.textTimeOne.text = it
            }
        }

        scope.launch {
            modelSecond.ticker.collect {
                binding.textTimeTwo.text = it
            }
        }
    }

    private fun init() {
        modelFirst = StopWatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Main
                        + SupervisorJob()
            )
        ).create(StopWatchViewModel::class.java)

        modelSecond = StopWatchViewModelFactory(
            stopwatchStateHolder = StopwatchStateHolder(
                StopwatchStateCalculator(
                    timestampProvider,
                    ElapsedTimeCalculator(timestampProvider),
                ),
                ElapsedTimeCalculator(timestampProvider),
                TimestampMillisecondsFormatter()
            ),
            CoroutineScope(
                Dispatchers.Main
                        + SupervisorJob()
            )
        ).create(StopWatchViewModel::class.java)

        binding.buttonStartOne.setOnClickListener {
            modelFirst.start()
        }
        binding.buttonPauseOne.setOnClickListener {
            modelFirst.pause()
        }
        binding.buttonStopOne.setOnClickListener {
            modelFirst.stop()
        }
        binding.buttonStartTwo.setOnClickListener {
            modelSecond.start()
        }
        binding.buttonPauseTwo.setOnClickListener {
            modelSecond.pause()
        }
        binding.buttonStopTwo.setOnClickListener {
            modelSecond.stop()
        }
    }
}