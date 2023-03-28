package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.incuba.app.auxiliar.MainFragmentActionListener
import com.incuba.app.databinding.ActivityMainBinding
import com.incuba.app.fragmentos.FragmentAjustes
import com.incuba.app.fragmentos.FragmentInicial
import com.incuba.app.fragmentos.FragmentNotificaciones

class MainActivity : AppCompatActivity(), MainFragmentActionListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //--------------------Definición de variable iniciales------------------------------------
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        val nombre = shareprefs.getString("nombre_key", "0")!!
        val empresa = shareprefs.getString("empresa_key", "0")!!
        val rol = shareprefs.getString("rol_key", "0")!!
        val token = shareprefs.getString("token_key", "0")!!
        pasarFragment(FragmentInicial())                     //---------------Iniciar fragmento--------
        binding.textUser.setText(nombre)
        binding.textUbicacion.setText("$rol de $empresa")
        //----------------------Menu acciones------------------------------
        binding.btnCentral.setOnClickListener {
            pasarFragment(FragmentInicial())
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.naranja))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.white))
        }
        binding.btnDerecho.setOnClickListener {
            pasarFragment(FragmentNotificaciones())
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.naranja))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.white))
        }
        binding.btnIzquierdo.setOnClickListener {
            pasarFragment(FragmentAjustes())
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.naranja))
        }

    }
    //---------------------Metodos abtractos compartidos con los fragmentos----------
    override fun pasarFragment(fragment: Fragment) {
        val fragmnetManger = supportFragmentManager
        val fragmentTransaction = fragmnetManger.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        fragmentTransaction.replace(R.id.fragmenContainer, fragment)
        fragmentTransaction.commit()
    }

    //---------------Presionar la tecla atras para salir---------------------------
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("¿Desea salir de la aplicación?")
            .setCancelable(false)
            .setPositiveButton("Si"){
                    dialog,whichButton ->
                finishAffinity()
            }
            .setNegativeButton("No"){
                    dialog,whichButton ->

            }
            .show()
    }
}