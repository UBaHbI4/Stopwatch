package com.gb.stopwatch.domain

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
interface ITimestampProvider {
    fun getMilliseconds(): Long
}