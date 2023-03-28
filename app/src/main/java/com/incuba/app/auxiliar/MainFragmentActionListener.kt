package com.incuba.app.auxiliar

import androidx.fragment.app.Fragment

interface MainFragmentActionListener {
    fun pasarFragment(fragment: Fragment)    //-------Metodo compartido para pasar a otro fragmento
    //fun rellenarRecycleView():dato           //------Metodo para pasar los resultado del Menu de API
}