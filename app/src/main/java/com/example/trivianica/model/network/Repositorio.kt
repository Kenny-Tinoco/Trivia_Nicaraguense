package com.example.trivianica.model.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trivianica.model.objetoPregunta
import com.google.firebase.firestore.FirebaseFirestore

class Repositorio
{
    fun obtenerCategoria(nombreCategoria: String): LiveData<MutableList<objetoPregunta>>
    {
        val lista = MutableLiveData<MutableList<objetoPregunta>>()

        FirebaseFirestore.getInstance().collection(nombreCategoria)
                .get()
                .addOnSuccessListener { coleccionCategoria ->

                    val listData = mutableListOf<objetoPregunta>()

                    for(documento in coleccionCategoria)
                    {
                        val objeto = documento.toObject(objetoPregunta::class.java)
                        listData.add(objeto)
                    }

                    lista.value = listData
                }

        return lista
    }
}