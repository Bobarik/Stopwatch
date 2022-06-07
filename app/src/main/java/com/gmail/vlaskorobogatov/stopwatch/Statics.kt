package com.gmail.vlaskorobogatov.stopwatch

import android.content.Context

fun formatSeconds(context: Context, deciseconds: Int): String {
    val timeM = (deciseconds / 600).toString().padStart(2, '0')
    val timeS = (deciseconds / 10 % 60).toString().padStart(2, '0')
    val timeMM = (deciseconds % 10).toString()
    return context.getString(R.string.mmss, timeM, timeS, timeMM)
}