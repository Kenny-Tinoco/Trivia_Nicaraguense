package com.example.trivianica.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivianica.model.objetoPregunta
import com.example.trivianica.model.network.Repositorio

class CategoriaViewModel: ViewModel()
{
    private val dataBase = Repositorio()
    fun obtenerCategoria(nombreCategoria: String): MutableLiveData<MutableList<objetoPregunta>>
    {
        val dataList = MutableLiveData<MutableList<objetoPregunta>>()

        dataBase.obtenerCategoria(nombreCategoria)
            .observeForever{Lista -> dataList.value = Lista}

        return dataList;
    }
}