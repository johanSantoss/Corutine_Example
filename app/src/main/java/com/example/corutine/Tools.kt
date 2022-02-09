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

sealed class  Resultado {
    data class Exitoso (val data : String) : Resultado()
    data class Error (val ex : Exception) : Resultado()
}

suspend fun progresBarLive(bar: ProgressBar) {
    return withContext(Dispatchers.IO) {
        Log.d(TAG, "doLogin ${Thread.currentThread().name}")
        val subida = 1
        bar.progress = 0
        println("------------------------------------------------")
        while (bar.progress < bar.max){
            bar.incrementProgressBy(subida)
            Thread.sleep(100)
        }
        println("...............................................")

    }

}