package com.example.corutine.ui.main

import android.app.usage.UsageEvents
import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corutine.Resultado
import com.example.corutine.progresBarLive
import kotlinx.coroutines.*
import java.lang.Boolean

class MainViewModel : ViewModel() {

    var progress  = 0
    var progress2 = 0

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

    // Cancell Job
    private val _cancellJob = MutableLiveData(0)
    val cancellJob: LiveData<Int> get() = _cancellJob
    private fun failJob(){
        _cancellJob.value = 0
    }
    private fun successJob(){
        _cancellJob.value = 1
    }

    fun progressBar1(bar : ProgressBar, buton: View) {
        // main thread
        job1 = viewModelScope.launch(Dispatchers.Main) {
            buton.isEnabled = Boolean.FALSE
            val success = withContext(Dispatchers.IO){
//                    prueba1(binding.proBar1)
                progresBarLive(bar)
//                    viewModel.progressBar1(binding.proBar1)
            }
            buton.isEnabled = Boolean.TRUE
        }
    }

    fun cancelBar1(buton: View){
        job1?.apply {
            if (isActive) cancel()
            buton.isEnabled = true
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