package com.example.corutine.ui.main

import android.app.usage.UsageEvents
import android.content.ContentValues
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corutine.progresBarLive
import kotlinx.coroutines.*
import java.lang.Boolean

class MainViewModel : ViewModel() {

    // progress - 1
    private val _progress1 = MutableLiveData<Int>(0)
    val progress1: LiveData<Int> get() = _progress1
    fun setProgress1 (status : Int){
        _progress1.value = status
    }
    fun resetProgress1(){
        _progress1.value = 0
    }
    // progress - 2
    private val _progress2 = MutableLiveData<Int>(0)
    val progress2: LiveData<Int> get() = _progress2
    fun setProgress2 (status : Int){
        _progress2.value = status
    }
    fun resetProgress2(){
        _progress2.value = 0
    }


    // flujo de trabajo
    var job1: Job? = null
    var job2: Job? = null
    fun resetJob(job: Int){
        if (job == 1) {
            job1 = null
        } else {
            job2 = null
        }
    }


    fun startBar(bar : ProgressBar, btnStart: View, nBar : Int, btnPause : View) {
        // main thread
        if (nBar == 1) {
            job1 = viewModelScope.launch(Dispatchers.Main) {
                // main
                bar.visibility = VISIBLE
                btnStart.isEnabled = Boolean.FALSE
                // other thread
                val success = withContext(Dispatchers.IO) {
                    progresBarLive(bar, progress1.value!!)
                }
                // main
                btnStart.isEnabled = Boolean.TRUE
                resetProgress1()

            }
        } else {
            job2 = viewModelScope.launch(Dispatchers.Main) {
                // main
                bar.visibility = VISIBLE
                btnStart.isEnabled = Boolean.FALSE
                // other thread
                val success = withContext(Dispatchers.IO) {
                    progresBarLive(bar, progress2.value!!)
                }

                // main
                btnStart.isEnabled = Boolean.TRUE
                resetProgress1()
            }
        }
    }
    private fun restartBar(bar: ProgressBar, buton: View, nBar: Int){
        if (nBar == 1){
            resetProgress1()
            bar.progress = progress1.value!!
            bar.visibility = INVISIBLE
            if (!buton.isEnabled) buton.isEnabled = Boolean.TRUE
        } else {
            resetProgress2()
            bar.progress = progress2.value!!
            bar.visibility = INVISIBLE
            if (!buton.isEnabled) buton.isEnabled = Boolean.TRUE
        }
    }
    fun pauseBar(bar: ProgressBar, buton: View, nBar: Int){
        if (nBar == 1){
            if (job1 != null){
                job1!!.cancel()
                resetJob(nBar)
                setProgress1(bar.progress)
                buton.isEnabled = Boolean.TRUE
            }
        } else {
            if (job2 != null){
                job2!!.cancel()
                resetJob(nBar)
                setProgress2(bar.progress)
                buton.isEnabled = Boolean.TRUE
            }
        }
    }
    fun cancelBar(bar: ProgressBar, buton: View, nBar: Int){
        if (nBar == 1){
            if (job1 != null){
                job1?.cancel()
            }
            resetJob(nBar)
            restartBar(bar, buton, nBar)
        } else {
            job2?.apply {
                if (isActive) cancel()
            }
            resetJob(nBar)
            restartBar(bar, buton, nBar)
        }
    }

    // limpieza de recursos
    override fun onCleared() {
        super.onCleared()
        // comprobamos si está activo cada unos de las coroutine
        job1?.apply {
            if (isActive)
                cancel()
        }
        job2?.apply {
            if (isActive)
                cancel()
        }
    }


}