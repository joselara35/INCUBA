package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityAdministrarCiclosBinding
import com.incuba.app.databinding.ActivityParametrosBinding
import kotlinx.coroutines.Job

class Administrar_ciclos : AppCompatActivity() {

    private lateinit var binding:ActivityAdministrarCiclosBinding
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdministrarCiclosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtraas.setOnClickListener {
            onBackPressed()
            job?.cancel()
        }
    }

}