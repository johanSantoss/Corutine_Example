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
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val MAX_BAR = 100
        const val BAR1 = 1
        const val BAR2 = 2
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
        // generar viewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // set estado inicial de las bar
        setBarStatus(binding.proBar1, binding.proBar2)

        // Bar - 1
        binding.btnStartPB1.setOnClickListener {
            /**
             *             viewModel.progressBar1(viewModel.progress, it, binding.proBar1)
             *           GlobalScope.launch(Dispatchers.Main) {
             *               it.isEnabled = FALSE
             *               val success = withContext(Dispatchers.IO){
             *                   prueba1(binding.proBar1)
             *                   progresBarLive(binding.proBar1)
             *                   viewModel.progressBar1(binding.proBar1)
             *               }
             *               it.isEnabled = TRUE
             *           }
             */
            viewModel.startBar(binding.proBar1, it, BAR1, binding.btnPausePB1)
            binding.btnPausePB1.isEnabled = TRUE
            binding.btnCancelPB1.isEnabled = TRUE
        }
        binding.btnPausePB1.setOnClickListener {
            viewModel.pauseBar(binding.proBar1, binding.btnStartPB1, BAR1)
        }
        binding.btnCancelPB1.setOnClickListener {
            binding.btnPausePB1.isEnabled = FALSE
            it.isEnabled = FALSE
            viewModel.cancelBar(binding.proBar1, binding.btnStartPB1, BAR1)
        }

        // Bar - 2
        binding.btnStartPB2.setOnClickListener {
            binding.btnPausePB2.isEnabled = TRUE
            binding.btnCancelPB2.isEnabled = TRUE
            viewModel.startBar(binding.proBar2, it, BAR2, binding.btnPausePB2)

        }
        binding.btnPausePB2.setOnClickListener {
            viewModel.pauseBar(binding.proBar2, binding.btnStartPB2, BAR2)
        }
        binding.btnCancelPB2.setOnClickListener {
            binding.btnPausePB2.isEnabled = FALSE
            it.isEnabled = FALSE
            viewModel.cancelBar(binding.proBar2, binding.btnStartPB2, BAR2)
        }



        return binding.root
    }

    private fun setBarStatus(bar1: ProgressBar, bar2: ProgressBar) {
        bar1.progress = viewModel.progress1.value!!
        bar1.max = MAX_BAR

        bar2.progress = viewModel.progress2.value!!
        bar2.max = MAX_BAR

    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveStatusBars()
    }

    private fun saveStatusBars() {
        viewModel.setProgress1(binding.proBar1.progress)
        viewModel.setProgress2(binding.proBar2.progress)
    }


}