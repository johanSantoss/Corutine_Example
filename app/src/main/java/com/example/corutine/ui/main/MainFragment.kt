package com.example.corutine.ui.main

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.corutine.R
import com.example.corutine.databinding.MainFragmentBinding
import com.example.corutine.progresBarLive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Boolean
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val MIN_BAR = 0
        const val MAX_BAR = 100
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )

        binding.btnStartPB1.setOnClickListener {
//            viewModel.progressBar1(viewModel.progress, it, binding.proBar1)
//            GlobalScope.launch(Dispatchers.Main) {
//                it.isEnabled = FALSE
//                val success = withContext(Dispatchers.IO){
////                    prueba1(binding.proBar1)
//                    progresBarLive(binding.proBar1)
////                    viewModel.progressBar1(binding.proBar1)
//                }
//                it.isEnabled = TRUE
//            }
            viewModel.progressBar1(binding.proBar1, it)
        }
        binding.btnCancelPB1.setOnClickListener {
            viewModel.cancelBar1(binding.btnStartPB1)
        }
        binding.btnStartPB2.setOnClickListener {
//            viewModel.progressBar1(viewModel.progress, it, binding.proBar1)
            GlobalScope.launch(Dispatchers.Main) {
                it.isEnabled = FALSE
                val success = withContext(Dispatchers.IO){
                    prueba1(binding.proBar2)
                }
                it.isEnabled = TRUE
            }
        }





        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private suspend fun startBar(bar: ProgressBar){
        var progres = 0
//        while (bar.progress )
    }

    private fun prueba1(bar: ProgressBar){
//        Thread.sleep(2_000)
        val subida = 1
        bar.progress = 0
        while (bar.progress < bar.max){
            bar.incrementProgressBy(subida)
            Thread.sleep(100)
        }
    }

}