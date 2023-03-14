package com.incuba.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import com.incuba.app.databinding.ActivitySplashScreenBinding

class Splash_screen : AppCompatActivity() {

    private lateinit var binding:ActivitySplashScreenBinding
    private val tiempo:Long = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //--------------------------------------------------------------
        ajustesIniciales()
        var versionName : String = applicationContext.packageManager.getPackageInfo(packageName, 0).versionName
        binding.txtVersion.setText("Versión $versionName")
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        var inicio_rapido = shareprefs.getInt("inicio_valor", 0)!!
        //------------------------------------------------------------
        Handler().postDelayed({
            when(inicio_rapido){
                0->startActivity(Intent(this,Login::class.java))
                1->startActivity(Intent(this,MainActivity::class.java))
            }
            //startActivity(Intent(this,Seguridad_bio::class.java))
            //startActivity(Intent(this,Login::class.java))
            finish()
        },tiempo)
    }

    private fun ajustesIniciales() {

    }
}