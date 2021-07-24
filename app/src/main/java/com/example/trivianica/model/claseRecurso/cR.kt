package com.example.trivianica.model.claseRecurso

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.trivianica.R
import com.example.trivianica.model.objectoPregunta
import com.example.trivianica.ui.activities.MenuPrincipal_activity

class cR
{
    companion object Valores
    {
        var validador: Boolean = true
        var opcionCorrecta: Boolean = true
        lateinit var respaldoPregunta: objectoPregunta

        var Contexto = MenuPrincipal_activity.ContextoApp

        var CERO  : Int = 0 /*Id de Arte*/
        var UNO   : Int = 1 /*Id de Deporte*/
        var DOS   : Int = 2 /*Id de Gastronomia*/
        var TRES  : Int = 3 /*Id de Geografia*/
        var CUATRO: Int = 4 /*Id de Historia*/
        var CINCO : Int = 5 /*Id de Tradicion*/

        var categoriaId: Int = 0 /*Id de categoria en la pregunta actual*/
        var acumulador:  Int = 0 /*Guarda la cantidad de preguntas hechas*/
        var puntaje:     Int = 0 /*Puntaje del usuario*/

        /*Guarda el orden en el que aparecen las categorias en la tombola*/
        var listaAleatoriaCategoria = IntArray(5){i -> -1}

        /*Guardan el id de las preguntas que ya aparecieron*/
        var listaArte        = RegistrosPreguntas()
        var listaDeporte     = RegistrosPreguntas()
        var listaGastronomia = RegistrosPreguntas()
        var listaGeografia   = RegistrosPreguntas()
        var listaHistoria    = RegistrosPreguntas()
        var listaTradicion   = RegistrosPreguntas()

        val nombreArte        = "listaArte"
        val nombreDeporte     = "listaDeporte"
        val nombreGastronomia = "listaGastronomia"
        val nombreGeografia   = "listaGeografia"
        val nombreHistoria    = "listaHistoria"
        val nombreTradicion   = "listaTradicion"


        /*Clase anidada*/
        class RegistrosPreguntas
        {
            var tamañoLista: Int = -1
            lateinit var registroPreguntas: MutableList<Int>
            fun inicarLista()
            {
                registroPreguntas = MutableList(tamañoLista){-1}
            }
        }

        fun tiempoEspera(): Long
        {
            var tiempo: Long = 0
            if(acumulador != 0 && opcionCorrecta == true)
                tiempo = 3000
            else if(opcionCorrecta == false)
                tiempo = obtenerTiempoEspera()
            return tiempo
        }
        fun obtenerTiempoEspera(): Long
        {
            var tiempo: Long = 0
            var lR = respaldoPregunta.Respuesta.length
            if(lR <= 60)              tiempo = 3500
            if(lR >  60 && lR <= 110) tiempo = 6500
            if(lR > 110 && lR <= 170) tiempo = 9000
            if(lR > 170 && lR <= 220) tiempo = 14500
            if(lR > 220 && lR <= 300) tiempo = 16500
            if(lR > 300)              tiempo = 19500
            return tiempo
        }

        fun obtenerNombreCategoria(categoriaId: Int): String
        {
            var nombre: String

                 if(categoriaId == CERO)   nombre = "Arte"
            else if(categoriaId == UNO)    nombre = "Deporte"
            else if(categoriaId == DOS)    nombre = "Gastronomia"
            else if(categoriaId == TRES)   nombre = "Geografia"
            else if(categoriaId == CUATRO) nombre = "Historia"
            else                           nombre = "Tradicion"

            return nombre;
        }

        /*Crea una arreglo de 5 número aleatorios entre 0 y 5*/
        fun generarlistaAleatoria()
        {
            var listaCompleta = IntArray(6){i -> i}

            /*Desordena sus elemento de forma aleatoria*/
            listaCompleta.shuffle()/*Propiedad de los arrays*/

            /*Tomamos los primeros 5 números de listaCompleta*/
            for (i in (0..4))
                listaAleatoriaCategoria[i] = listaCompleta[i]
        }

        fun obtenerPreguntaAleatoria(): Int
        {
            var listaRegistro = obtenerListaRegistroCategoria(-1)
            var tamaño = listaRegistro.tamañoLista - 1
            var indice = (0..tamaño).random()

            if(listaRegistro.registroPreguntas.contains(indice))
                indice = obtenerPreguntaAleatoria()
            else
                insertarDatoLista(indice, listaRegistro)

            actualizarListaRegistroCategoria(listaRegistro)

            return indice
        }

        fun insertarDatoLista(Dato: Int, Lista: RegistrosPreguntas)
        {
            for( (i, valor) in Lista.registroPreguntas.withIndex())
            {
                if(valor == -1)
                {
                    Lista.registroPreguntas[i] = Dato
                    break
                }
            }

            if(Lista.registroPreguntas.find{it == -1} == null)
                for(i in (0..(Lista.registroPreguntas.size-1)))
                    Lista.registroPreguntas[i] = -1
        }

        fun actualizarListaRegistroCategoria(listaRegistro: RegistrosPreguntas)
        {
            if(categoriaId == CERO)   listaArte        = listaRegistro
            if(categoriaId == UNO)    listaDeporte     = listaRegistro
            if(categoriaId == DOS)    listaGastronomia = listaRegistro
            if(categoriaId == TRES)   listaGeografia   = listaRegistro
            if(categoriaId == CUATRO) listaHistoria    = listaRegistro
            if(categoriaId == CINCO)  listaTradicion   = listaRegistro
        }
        fun obtenerListaRegistroCategoria(Valor: Int): RegistrosPreguntas
        {
            var listaRegistro = RegistrosPreguntas()
            var Id: Int

            if(Valor == -1)
                Id = categoriaId
            else
                Id = Valor

            if(Id == CERO)   listaRegistro = listaArte
            if(Id == UNO)    listaRegistro = listaDeporte
            if(Id == DOS)    listaRegistro = listaGastronomia
            if(Id == TRES)   listaRegistro = listaGeografia
            if(Id == CUATRO) listaRegistro = listaHistoria
            if(Id == CINCO)  listaRegistro = listaTradicion

            return listaRegistro
        }
        fun obtenerNombreRegistroCategoria(Id: Int): String
        {
            var nombre = ""
            if(Id == CERO)   nombre = nombreArte
            if(Id == UNO)    nombre = nombreDeporte
            if(Id == DOS)    nombre = nombreGastronomia
            if(Id == TRES)   nombre = nombreGeografia
            if(Id == CUATRO) nombre = nombreHistoria
            if(Id == CINCO)  nombre = nombreTradicion
            return nombre
        }
        fun obtenerValoresRegistrados(Nombre: String)
        {
            var PreferenciasCompartidas = Contexto.getSharedPreferences(Nombre, Context.MODE_PRIVATE)
            var Cadena = PreferenciasCompartidas.getString(Nombre,"-1")

            if (Cadena != null && Cadena != "")
                obtenerNumerosCadena(Cadena)

        }
        fun obtenerNumerosCadena(Cadena: String)
        {
            var lista = obtenerListaRegistroCategoria(-1)
            var indice = 0
            while(indice < Cadena.length)
            {
                var endIndice = obtenerEndIndice(Cadena, indice, Cadena.length)
                var numero = Cadena.substring(indice, endIndice)
                insertarDatoLista(numero.toInt(), lista)
                indice = endIndice
                indice++
            }
            actualizarListaRegistroCategoria(lista)
        }
        fun obtenerEndIndice(Cadena: String, indice: Int, tamaño: Int): Int
        {
            if(indice != tamaño)
                if(Cadena[indice].toString() != " ")
                    return obtenerEndIndice(Cadena, indice+1, tamaño)
                else
                    return indice
            else
                return indice
        }

        fun insertarTamañoListaRegistro(tamaño: Int)
        {
            if(categoriaId == CERO && listaArte.tamañoLista == -1)
            {
                listaArte.tamañoLista = tamaño;
                listaArte.inicarLista()
                obtenerValoresRegistrados(nombreArte)
            }

            if(categoriaId == UNO && listaDeporte.tamañoLista == -1)
            {
                listaDeporte.tamañoLista = tamaño;
                listaDeporte.inicarLista()
                obtenerValoresRegistrados(nombreDeporte)
            }

            if(categoriaId == DOS && listaGastronomia.tamañoLista == -1)
            {
                listaGastronomia.tamañoLista = tamaño;
                listaGastronomia.inicarLista()
                obtenerValoresRegistrados(nombreGastronomia)
            }

            if(categoriaId == TRES && listaGeografia.tamañoLista == -1)
            {
                listaGeografia.tamañoLista = tamaño;
                listaGeografia.inicarLista()
                obtenerValoresRegistrados(nombreGeografia)
            }

            if(categoriaId == CUATRO && listaHistoria.tamañoLista == -1)
            {
                listaHistoria.tamañoLista = tamaño;
                listaHistoria.inicarLista()
                obtenerValoresRegistrados(nombreHistoria)
            }

            if(categoriaId == CINCO && listaTradicion.tamañoLista == -1)
            {
                listaTradicion.tamañoLista = tamaño;
                listaTradicion.inicarLista()
                obtenerValoresRegistrados(nombreTradicion)
            }
        }

        fun guardarListasRegistro( Indice: Int, Nombre: String)
        {
            var PreferenciasCompartidas = Contexto.getSharedPreferences(Nombre, Context.MODE_PRIVATE)
            var listaRegistro = obtenerListaRegistroCategoria(Indice)
            if(listaRegistro.tamañoLista != -1)
                with(PreferenciasCompartidas.edit())
                {
                    var nuevaCadena = obtenerNuevaCadena(listaRegistro.registroPreguntas)
                    putString(Nombre, nuevaCadena)
                    commit()
                }
        }
        fun obtenerNuevaCadena(Lista: MutableList<Int>): String?
        {
            var nuevaCadena = ""

            for(valor in Lista)
                if(valor != -1)
                    nuevaCadena += "${valor} "

            return nuevaCadena
        }
    }
}