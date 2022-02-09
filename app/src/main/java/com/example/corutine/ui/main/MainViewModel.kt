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
import com.example.corutine.MainActivity
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

    // bar1 - funtion
    private val _runBar1 = MutableLiveData<Int>(0)
    val runBar1: LiveData<Int> get() = _runBar1
    fun enableBar1 (){
        _runBar1.value = 1
    }
    fun disenableBar1(){
        _runBar1.value = 0
    }
    // bar2 - funtion
    private val _runBar2 = MutableLiveData<Int>(0)
    val runBar2: LiveData<Int> get() = _runBar2
    fun enableBar2 (){
        _runBar2.value = 1
    }
    fun disenableBar2(){
        _runBar2.value = 0
    }


    // flujo de trabajo
    var job1: Job? = null
    var job2: Job? = null


    fun startBar(bar : ProgressBar, btnStart : View, nBar : Int, btnPause : View, btnCancel : View) {
        // main thread
        if (nBar == 1) {
            job1 = viewModelScope.launch(Dispatchers.Main) {
                btnStart.isEnabled = Boolean.FALSE
                enableBar1()
                val success = withContext(Dispatchers.IO) { progresBarLive(bar, progress1.value!!, nBar) }
                btnPause.isEnabled = Boolean.FALSE
                btnCancel.isEnabled = Boolean.FALSE
                btnStart.isEnabled = Boolean.TRUE
                resetProgress1()
            }
        } else {
            job2 = viewModelScope.launch(Dispatchers.Main) {
                btnStart.isEnabled = Boolean.FALSE
                enableBar2()
                val success = withContext(Dispatchers.IO) { progresBarLive(bar, progress2.value!!, nBar) }
                btnPause.isEnabled = Boolean.FALSE
                btnCancel.isEnabled = Boolean.FALSE
                btnStart.isEnabled = Boolean.TRUE
                resetProgress2()
            }
        }
    }
    fun restartBar(bar: ProgressBar, nBar: Int){
        if (nBar == 1){
            job1!!.cancel()
            resetProgress1()
            disenableBar1()
            bar.progress = progress1.value!!
        } else {
            job2!!.cancel()
            resetProgress2()
            disenableBar2()
            bar.progress = progress2.value!!
        }
    }
    fun pauseBar(bar: ProgressBar, nBar: Int, btnStart: View){
        if (nBar == 1){
            if (job1 != null){
                job1!!.cancel()
                setProgress1(bar.progress)
                btnStart.isEnabled = Boolean.TRUE
            }
        } else {
            if (job2 != null){
                job2!!.cancel()
                setProgress2(bar.progress)
                btnStart.isEnabled = Boolean.TRUE
            }
        }
    }

//    // limpieza de recursos
//    override fun onCleared() {
//        super.onCleared()
//        // comprobamos si está activo cada unos de las coroutine
//        job1?.apply {
//            if (isActive)
//                cancel()
//        }
//        job2?.apply {
//            if (isActive)
//                cancel()
//        }
//    }


}