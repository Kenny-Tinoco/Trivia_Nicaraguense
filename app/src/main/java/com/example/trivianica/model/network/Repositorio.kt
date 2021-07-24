package com.example.trivianica.model.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trivianica.model.objectoPregunta
import com.google.firebase.firestore.FirebaseFirestore

class Repositorio
{
    fun obtenerCategoria(nombreCategoria: String): LiveData<MutableList<objectoPregunta>>
    {
        val Lista = MutableLiveData<MutableList<objectoPregunta>>()

        FirebaseFirestore.getInstance().collection(nombreCategoria)
                .get()
                .addOnSuccessListener { coleccionCategoria ->

                    val listData = mutableListOf<objectoPregunta>()

                    for(documento in coleccionCategoria)
                    {
                        var objecto = documento.toObject(objectoPregunta::class.java)
                        listData.add(objecto)
                    }

                    Lista.value = listData
                }

        return Lista;
    }
}