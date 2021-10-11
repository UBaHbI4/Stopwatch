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
class StopwatchStateCalculator(
    private val timestampProvider: ITimestampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopwatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopwatchState.Paused -> oldState
        }
}