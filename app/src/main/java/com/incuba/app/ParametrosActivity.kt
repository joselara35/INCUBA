package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityLoginBinding
import com.incuba.app.databinding.ActivityParametrosBinding

class ParametrosActivity : AppCompatActivity() {

    private lateinit var binding:ActivityParametrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityParametrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //------------------------------------------------------------
        binding.btnAtraas.setOnClickListener {
            onBackPressed()
        }

    }
}