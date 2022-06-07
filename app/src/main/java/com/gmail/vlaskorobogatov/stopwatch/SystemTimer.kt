package com.gmail.vlaskorobogatov.stopwatch

class SystemTimer() : Timer {
    private var startMillis: Long = 0
    var diff: Long = 0
        private set

    override fun pause() {
        diff = System.currentTimeMillis() - startMillis
    }

    override fun resume() {
        startMillis = System.currentTimeMillis() - diff
    }

    override fun reset() {
        diff = 0
    }

    override fun getTime(): Long {
        diff = System.currentTimeMillis() - startMillis
        return diff
    }

}