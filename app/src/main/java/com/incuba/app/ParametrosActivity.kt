package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import android.view.ViewGroup
import androidx.room.Room
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.snackbar.Snackbar
import com.incuba.app.API.ApiService
import com.incuba.app.API.respuestaIncubadora
import com.incuba.app.API.respuestaParametros
import com.incuba.app.Roombd.baseD
import com.incuba.app.Roombd.notificaciones
import com.incuba.app.auxiliar.graficarData
import com.incuba.app.databinding.ActivityLoginBinding
import com.incuba.app.databinding.ActivityParametrosBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ParametrosActivity : AppCompatActivity() {

    private lateinit var binding:ActivityParametrosBinding
    var id_incubadora:String=""
    private var job: Job? = null

    private lateinit var lineChart: LineChart
    private var scoreList = ArrayList<graficarData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityParametrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lineChart = this.binding.lineChartTemp
        //-------------------------------------------------------------
        //obtenerListaMenu()
        // Iniciar la repetición del método obtenerListaMenu()
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                obtenerListaMenu()
                delay(5000) // Repetir cada 5 segundos
            }
        }
        //------------------------------------------------------------
        binding.btnAtraas.setOnClickListener {
            onBackPressed()
            job?.cancel()
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
    private fun obtenerListaMenu() {
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        id_incubadora = shareprefs.getString("idIncu_key", "0")!!
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<respuestaParametros> = getRetrofit().create(ApiService::class.java)
                    .getObtenerParametros("classes/parametros?where=%7B%20%22id_incubadora%22%3A%20%22${id_incubadora}%22%20%7D")
                val listaPara: respuestaParametros? = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        listaParametros(listaPara!!)  //-------------Pasar Respuesta obtenido a metodo
                    }
                } else { //--------------------Respuesta Error------------------------------
                    if (call.code() == 400 || call.code() == 404 ) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(call.errorBody()?.string())
                            val Codigo = jsonObject!!.getString("code")
                            val Errors = jsonObject!!.getString("error")
                            withContext(Dispatchers.Main) {
                                ToasDeError(Codigo,Errors)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

            }
        } catch (e: Exception) {
            //toastExt("Error de conexión con el servidor")
        }
    }

    private fun listaParametros(listaPara: respuestaParametros) {
        Logger.addLogAdapter(AndroidLogAdapter())
        var userID: String = listaPara!!.results.last().objectId
        Logger.d(userID)
        Logger.d(listaPara?.results)
        rellenar_datos(listaPara)
        binding.textId.setText("Id de incubadora: $id_incubadora")
        binding.textTemperatura.setText(listaPara!!.results.last().temperatura)
        binding.textHumedad.setText(listaPara!!.results.last().humedad)
        binding.textVolteo.setText(listaPara!!.results.last().volteos)
        binding.textReloj.setText(listaPara!!.results.last().fecha)
        binding.textRelojHu.setText(listaPara!!.results.last().fecha)
        binding.textRelojVolteo.setText(listaPara!!.results.last().fecha)
        //guardarBDCache(listaPara)
    }

    private fun rellenar_datos(results: respuestaParametros) {
       var lista=results.results.takeLast(10)
        scoreList.clear()
        for(i in lista){
            var valor:Float?=i.temperatura.toFloatOrNull()
            var fecha:String?=i.fecha
            scoreList.add(graficarData(fecha!!, valor!!))
        }
        contruirGrafico()
        setDataToLineChart()
    }

    /*private fun guardarBDCache(listaPara: respuestaParametros) {
        var dataBase: baseD = Room
            .databaseBuilder(this, baseD::class.java, baseD.DATABASE_NAME)
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            dataBase.datosMedidosDao().insertListaParametros(listaPara)
        }
    }*/


    //----------Mostrar errores en las respuestas de API------------------------
    fun ToasDeError(code: String, error: String) {
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("$code $error")
    }
    override fun onDestroy() {
        super.onDestroy()
        // Detener la repetición del método obtenerListaMenu() cuando se destruye la actividad
        job?.cancel()
    }
    ///--------------------------GRAFICAR DATOS--------------------------------------------------
    private fun contruirGrafico() {
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false


        //remove description label
        lineChart.description.isEnabled = false


        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }
    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].fecha
            } else {
                ""
            }
        }
    }
    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        //scoreList = listener!!.getListRespuestas()

        if(scoreList[0].score==0.0F){
            binding.lineChartTemp.visibility= ViewGroup.GONE
            //binding.textError.visibility=ViewGroup.VISIBLE
        }else{
            binding.lineChartTemp.visibility=ViewGroup.VISIBLE
            //binding.textError.visibility=ViewGroup.GONE
            //you can replace this data object with  your custom object
            //------------Agregar datos a la Garfica-----------------------------------
            for (i in scoreList.indices) {
                val score = scoreList[i]
                entries.add(Entry(i.toFloat(), score.score.toFloat()))
            }

            val lineDataSet = LineDataSet(entries, "")

            val data = LineData(lineDataSet)
            lineChart.data = data

            lineChart.invalidate()
        }
    }
}