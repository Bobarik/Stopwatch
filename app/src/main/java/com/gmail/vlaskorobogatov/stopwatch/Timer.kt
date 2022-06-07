package com.gmail.vlaskorobogatov.stopwatch

interface Timer {
    fun pause()
    fun resume()
    fun reset()
    fun getTime(): Long
}