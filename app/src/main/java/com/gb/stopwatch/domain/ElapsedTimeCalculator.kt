package com.gb.stopwatch.domain

import com.gb.stopwatch.domain.model.StopwatchState

/**
 *   Project: Stopwatch
 *
 *   Package: com.gb.stopwatch.domain
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
class ElapsedTimeCalculator(
    private val timestampProvider: ITimestampProvider,
) {

    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}