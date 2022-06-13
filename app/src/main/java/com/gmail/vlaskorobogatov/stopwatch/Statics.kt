package com.gmail.vlaskorobogatov.stopwatch

import android.content.Context

fun formatSeconds(context: Context, diff: Long): String {
    val timeMM = (diff / 10 % 100).toString().padStart(2, '0')
    val timeS = (diff / 1000 % 60).toString().padStart(2, '0')
    val timeM = (diff / 60000).toString().padStart(2, '0')
    return context.getString(R.string.mmss, timeM, timeS, timeMM)
}