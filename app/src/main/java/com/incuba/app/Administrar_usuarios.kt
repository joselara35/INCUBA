package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityAdministrarCiclosBinding
import com.incuba.app.databinding.ActivityAdministrarUsuariosBinding
import kotlinx.coroutines.Job

class Administrar_usuarios : AppCompatActivity() {

    private lateinit var binding:ActivityAdministrarUsuariosBinding
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdministrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtraasUsuario.setOnClickListener {
            onBackPressed()
            job?.cancel()
        }
    }
}