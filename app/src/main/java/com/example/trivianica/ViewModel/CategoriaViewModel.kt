package com.example.trivianica.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivianica.model.claseRecurso.cR
import com.example.trivianica.model.objectoPregunta
import com.example.trivianica.model.network.Repositorio

class CategoriaViewModel: ViewModel()
{
    private val DataBase = Repositorio()
    fun obtenerCategoria(nombreCategoria: String): MutableLiveData<MutableList<objectoPregunta>>
    {
        val DataList = MutableLiveData<MutableList<objectoPregunta>>()

        DataBase.obtenerCategoria(nombreCategoria).observeForever{Lista -> DataList.value = Lista}

        return DataList;
    }

}