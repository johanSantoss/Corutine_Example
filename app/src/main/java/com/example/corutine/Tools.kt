package com.example.corutine


import android.nfc.Tag
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import kotlin.random.Random


const val TAG = "break"


suspend fun progresBarLive(bar: ProgressBar, status : Int, nBar : Int) {
    return withContext(Dispatchers.IO) {
        val subida = 1
        bar.progress = status
        while (bar.progress < bar.max){
            bar.incrementProgressBy(subida)
            delay(50)
        }
    }
}