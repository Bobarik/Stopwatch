package com.gmail.vlaskorobogatov.stopwatch

interface Timer {
    var upperLimit: Long
    fun pause()
    fun resume()
    fun reset()
    fun getTime(): Long
    fun isOverLimit(): Boolean
}