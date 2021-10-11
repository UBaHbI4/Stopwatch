package com.gb.stopwatch.viewmodel

import androidx.lifecycle.ViewModel
import com.gb.stopwatch.domain.StopwatchStateHolder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *   Project: Stopwatch
 *
 *   Package: com.gb.stopwatch.viewmodel
 *
 *   Created by Ivan Sheynmaer
 *
 *   Description:
 *
 *
 *   2021.10.11
 *
 *   v1.0
 */
class StopWatchViewModel(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope: CoroutineScope
) : ViewModel() {

    private var job: Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker: StateFlow<String> = mutableTicker

    fun start() {
        if (job == null) startJob()
        stopwatchStateHolder.start()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringTimeRepresentation()
                delay(DELAY)
            }
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        job?.cancel()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = DEFAULT_TIMER_VALUE
    }

    companion object {
        private const val DELAY = 20L
        private const val DEFAULT_TIMER_VALUE = "00:00:000"
    }
}