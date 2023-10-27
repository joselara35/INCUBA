package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityAdministrarIncubadoraBinding
import com.incuba.app.databinding.ActivityHistorialBinding
import kotlinx.coroutines.Job

class Historial : AppCompatActivity() {

    private lateinit var binding:ActivityHistorialBinding
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtraasH.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            job?.cancel()
        }
    }
}