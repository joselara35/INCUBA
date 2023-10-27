package com.incuba.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incuba.app.API.ApiService
import com.incuba.app.API.datosCiclos
import com.incuba.app.API.respuestaCiclos
import com.incuba.app.API.respuestaIncubadora
import com.incuba.app.Adaptadores.adapterCiclos
import com.incuba.app.Adaptadores.adapterIncubadora
import com.incuba.app.databinding.ActivityAdministrarCiclosBinding
import com.incuba.app.databinding.ActivityParametrosBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Administrar_ciclos : AppCompatActivity() {

    private lateinit var binding: ActivityAdministrarCiclosBinding
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdministrarCiclosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtraas.setOnClickListener {
            onBackPressed()
            job?.cancel()
        }
        //-------------------------------------------------
        comprobar_conexion()
    }

    @SuppressLint("ServiceCast")
    fun comprobar_conexion() {
        val connectivityManager =
            this.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        //--------verifica la conexion de internet para continuar-----------------------------
        if (networkInfo != null && networkInfo.isConnected) {
            obtenerListaCiclos()
        } else {
            //toastExt("Active el Internet para seguir")
            //mensajeDialog.startMenssageDialogo("Active el Internet para seguir")
        }
    }

    ////------------------API-RESET-Retrofit------------------------------------------/////
    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .addInterceptor(logging)
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://parseapi.back4app.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    //-------------------------------Hacer pedido a API en Hilo secundario------------------------------------
    private fun obtenerListaCiclos() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<respuestaCiclos> = getRetrofit().create(ApiService::class.java)
                    .getObtenerCiclos()
                val listaCiclos: respuestaCiclos? = call.body()
                if (call.isSuccessful) {
                    Looper.prepare()
                    listarCiclos(listaCiclos!!)  //-------------Pasar Respuesta obtenido a metodo
                    Looper.loop()
                } else { //--------------------Respuesta Error------------------------------
                    if (call.code() == 400 || call.code() == 404) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(call.errorBody()?.string())
                            val Codigo = jsonObject!!.getString("code")
                            val Errors = jsonObject!!.getString("error")
                            Looper.prepare()
                            ToasDeError(Codigo, Errors)
                            Looper.loop()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

            }
        } catch (e: Exception) {
            Logger.d("$e")
        }
    }

    private fun listarCiclos(listaCiclos: respuestaCiclos) {
        Logger.addLogAdapter(AndroidLogAdapter())
        var userID: String = listaCiclos!!.results[0].objectId
        Logger.d(userID)
        Logger.d(listaCiclos?.results)
        //respuestaAPI=listaCiclos
        rellenarRecycleView(listaCiclos?.results)
    }

    private fun rellenarRecycleView(results: List<datosCiclos>) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                //binding.textoError.visibility=View.GONE
                val recycle_area: RecyclerView = binding.recycleLISTACilos
                var adapter = adapterCiclos(results)
                recycle_area.layoutManager = LinearLayoutManager(this@Administrar_ciclos)
                recycle_area.adapter = adapter
                adapter!!.setOnItemClickListener(object : adapterCiclos.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        //var IdIncuabdora=results[position].objectId.toString()
                        //var rc=lista_resp[position].valor_rc.toString()
                        //startActivity(Intent(Activity, ParametrosActivity::class.java))
                        //guardadId(IdIncuabdora)
                    }
                })
            } catch (Ex: Exception) {
                Logger.d(Ex.toString())
            }
        }
        //Logger.addLogAdapter(AndroidLogAdapter())
        //Logger.d(respuestaAPI);
    }

    fun ToasDeError(code: String, error: String) {
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("$code $error")
    }

}