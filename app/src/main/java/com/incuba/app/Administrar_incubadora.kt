package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityAdministrarCiclosBinding
import com.incuba.app.databinding.ActivityAdministrarIncubadoraBinding
import kotlinx.coroutines.Job

class Administrar_incubadora : AppCompatActivity() {

    private lateinit var binding:ActivityAdministrarIncubadoraBinding
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdministrarIncubadoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtraasAAA.setOnClickListener {

            onBackPressedDispatcher.onBackPressed()
            job?.cancel()
        }
    }
}