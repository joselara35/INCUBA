package com.incuba.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incuba.app.databinding.ActivityLoginBinding
import com.incuba.app.databinding.ActivitySplashScreenBinding

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //--------------------------------------------------------------
        binding.btnEntrar.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}