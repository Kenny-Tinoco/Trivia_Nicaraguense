package com.example.trivianica.model.claseRecurso

import android.content.Context
import com.example.trivianica.ui.activities.MenuPrincipal_activity

class RegistroDispositivo
{
    /*
    * Esta clase contiene los metodos utilizados para
    * obtener y guardar registros de la preguntas que
    * le van apareciendo al usuario en el dispositivos,
    * esto con el fin de que no se le repitan las preguntas.
    */

    companion object
    {
        val Contexto = MenuPrincipal_activity.ContextoApp
        val VACIO = -1

        var Arte        = PreguntasRealizadas()
        var Deporte     = PreguntasRealizadas()
        var Gastronomia = PreguntasRealizadas()
        var Geografia   = PreguntasRealizadas()
        var Historia    = PreguntasRealizadas()
        var Tradicion   = PreguntasRealizadas()

        /*
        * Clase anidada
        * Estructura que almacena en una lista los id's de las
        * preguntas ya realizadas al usuario. (Preguntas de una
        * determinada categoria)
        */
        class PreguntasRealizadas
        {
            var size: Int = VACIO
            var name: String = ""
            lateinit var preguntas: MutableList<Int>

            fun inicializarLista(nombreCategoria: String)
            {
                name = nombreCategoria

                preguntas = MutableList(size)
                {
                    VACIO
                }
            }
        }

        fun getRegistroEnDispositivo(Nombre: String, categoriaId: Int)
        {
            var PreferenciasCompartidas = Contexto.getSharedPreferences(Nombre, Context.MODE_PRIVATE)
            var cadenaGuardada = PreferenciasCompartidas.getString(Nombre,"-1")

            if (cadenaGuardada != null && cadenaGuardada != "")
                getIdsPreguntasRealizadas(cadenaGuardada, categoriaId)
        }

        fun setRegistroEnDispositivo(nombreCategoria: String, categoriaId: Int)
        {
            val sharedPreferences = Contexto.getSharedPreferences(nombreCategoria, Context.MODE_PRIVATE)
            val preguntasRealizadas = getPreguntasRealizadas(categoriaId)

            if(preguntasRealizadas.size != VACIO)
                with(sharedPreferences.edit())
                {
                    val nuevaCadena = concatenarLosElementosDe(preguntasRealizadas.preguntas)
                    putString(nombreCategoria, nuevaCadena)
                    commit()
                }
        }
        fun inicializarRegistros(size: Int, categoriaId: Int)
        {
            val preguntasHechas = getPreguntasRealizadas(categoriaId)

            if(preguntasHechas.size == cR.VACIO)
            {
                val nombreDeRegistro = cR.getNombreDeRegistro(categoriaId)
                preguntasHechas.size = size
                preguntasHechas.inicializarLista(nombreDeRegistro)
                getRegistroEnDispositivo(nombreDeRegistro, categoriaId)
                updatePreguntasRealizadas(categoriaId, preguntasHechas)
            }
        }

        fun updatePreguntasRealizadas(categoriaId: Int, Preguntas: PreguntasRealizadas)
        {
            if(categoriaId == cR.arteId)
            {
                Arte = Preguntas
            }

            if(categoriaId == cR.deporteId)
            {
                Deporte = Preguntas
            }

            if(categoriaId == cR.gastronomiaId)
            {
                Gastronomia = Preguntas
            }

            if(categoriaId == cR.geografiaId)
            {
                Geografia = Preguntas
            }

            if(categoriaId == cR.historiaId)
            {
                Historia = Preguntas
            }

            if(categoriaId == cR.tradicionId)
            {
                Tradicion = Preguntas
            }
        }

        fun getPreguntasRealizadas(categoriaId: Int): PreguntasRealizadas
        {
            var preguntasHechas = PreguntasRealizadas()

            if(categoriaId == cR.arteId)
            {
                preguntasHechas = Arte
            }

            if(categoriaId == cR.deporteId)
            {
                preguntasHechas = Deporte
            }

            if(categoriaId == cR.gastronomiaId)
            {
                preguntasHechas = Gastronomia
            }

            if(categoriaId == cR.geografiaId)
            {
                preguntasHechas = Geografia
            }

            if(categoriaId == cR.historiaId)
            {
                preguntasHechas = Historia
            }

            if(categoriaId == cR.tradicionId)
            {
                preguntasHechas = Tradicion
            }

            return preguntasHechas
        }
        private fun concatenarLosElementosDe(idPreguntas: MutableList<Int>): String
        {
            var elementosConcatenados = ""

            for(elemento in idPreguntas)
            {
                if(elemento != VACIO)
                {
                    elementosConcatenados += "$elemento "
                }
            }

            return elementosConcatenados
        }
        fun getIdPreguntaAleatoria(preguntasHechas: PreguntasRealizadas): Int
        {
            var preguntaId: Int = (0 until preguntasHechas.size).random()

            if(preguntasHechas.preguntas.contains(preguntaId))
                preguntaId = getIdPreguntaAleatoria(preguntasHechas)

            return preguntaId
        }

        private fun getIdsPreguntasRealizadas(cadenaGuardada: String, categoriaId: Int)
        {
            val Preguntas = getPreguntasRealizadas(categoriaId)
            var primerIndice = 0
            while(primerIndice < cadenaGuardada.length)
            {
                val segundoIndice = getSegundoIndice(primerIndice, cadenaGuardada, cadenaGuardada.length)
                val preguntaId    = cadenaGuardada.substring(primerIndice, segundoIndice).toInt()

                guardarDatoEnListaPreguntasRealizadas(preguntaId, Preguntas)
                comprobarListaLLena(Preguntas)

                primerIndice = segundoIndice + 1
            }
            updatePreguntasRealizadas(categoriaId, Preguntas)
        }
        private fun getSegundoIndice(indice: Int, cadenaGuardada: String, tamaño: Int): Int
        {
            var variableIndice = indice

            if( variableIndice != tamaño )
            {
                if( cadenaGuardada[variableIndice].toString() != " " )
                {
                    variableIndice = getSegundoIndice(variableIndice+1, cadenaGuardada, tamaño)
                }
            }
            return variableIndice
        }

        fun guardarDatoEnListaPreguntasRealizadas(preguntaId: Int, listaPreguntasRealizadas: PreguntasRealizadas)
        {
            var bandera = true
            for( (i, valor) in listaPreguntasRealizadas.preguntas.withIndex())
            {
                if(valor == -1 && bandera)
                {
                    listaPreguntasRealizadas.preguntas[i] = preguntaId
                    bandera = false
                }
            }
        }
        private fun comprobarListaLLena(listaPreguntasRealizadas: PreguntasRealizadas)
        {
            if(listaPreguntasRealizadas.preguntas.find{it == -1} == null)
            {
                for(i in (0 until listaPreguntasRealizadas.size))
                {
                    listaPreguntasRealizadas.preguntas[i] = -1
                }
            }
        }//Final de comprobarListaLLena
    }//Final de companion object
}