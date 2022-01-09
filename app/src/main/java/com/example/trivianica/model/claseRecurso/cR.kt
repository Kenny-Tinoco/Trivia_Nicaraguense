package com.example.trivianica.model.claseRecurso

import com.example.trivianica.model.objetoPregunta

class cR//claseRecursos
{
    companion object Valores
    {
        val VACIO : Int = -1

        var categoriaId : Int = 0

        var puntaje : Int = 0

        var validador : Boolean = true
        var opcionCorrecta : Boolean = true
        lateinit var respaldoPregunta : objetoPregunta

        /*Guarda el orden en el que aparecen las categorias en la tombola*/
        var listaAleatoriaCategoria = IntArray(5)
        {
                i -> -1
        }

        var preguntasHechas : Int = 0
        fun tiempoEspera(): Long
        {
            var tiempo: Long = 0

            if(preguntasHechas != 0 && opcionCorrecta)
            {
                tiempo = 3000
            }
            else if(!opcionCorrecta)
            {
                tiempo = obtenerTiempoEspera()
            }

            return tiempo
        }
        fun obtenerTiempoEspera(): Long
        {
            var tiempo : Long = 0

            var longitud = respaldoPregunta.Respuesta.length

            if(longitud <= 60)
            {
                tiempo = 3500
            }

            if(longitud > 60 && longitud <= 110)
            {
                tiempo = 6500
            }

            if(longitud > 110 && longitud <= 170)
            {
                tiempo = 9000
            }

            if(longitud > 170 && longitud <= 220)
            {
                tiempo = 14500
            }

            if(longitud > 220 && longitud <= 300)
            {
                tiempo = 16500
            }

            if(longitud > 300)
            {
                tiempo = 19500
            }

            return tiempo
        }

        val arteId : Int = 0
        val deporteId : Int = 1
        val gastronomiaId : Int = 2
        val geografiaId: Int = 3
        val historiaId : Int = 4
        val tradicionId : Int = 5

        fun getNombreCategoria(categoriaId: Int): String
        {
            var nombre = ""

            if(categoriaId == arteId)
            {
                nombre = "Arte"
            }
            if(categoriaId == deporteId)
            {
                nombre = "Deporte"
            }
            if(categoriaId == gastronomiaId)
            {
                nombre = "Gastronomia"
            }
            if(categoriaId == geografiaId)
            {
                nombre = "Geografia"
            }
            if(categoriaId == historiaId)
            {
                nombre = "Historia"
            }
            if(categoriaId == tradicionId)
            {
                nombre = "Tradicion"
            }

            return nombre;
        }

        /*Crea una arreglo de 5 número aleatorios entre 0 y 5*/
        fun generarlistaAleatoria()
        {
            var arregloDesordenado = IntArray(6){i -> i}

            /*Desordena sus elemento de forma aleatoria*/
            arregloDesordenado.shuffle()/*Propiedad de los arrays*/

            /*Tomamos los primeros 5 números de listaCompleta*/
            for (i in (0..4))
                listaAleatoriaCategoria[i] = arregloDesordenado[i]
        }

        fun getNombreDeRegistro(categoriaId: Int): String
        {
            var nombreDeRegistro = ""
            val nombreCategoria = getNombreCategoria(categoriaId)

            nombreDeRegistro = "categoria$nombreCategoria"

            return nombreDeRegistro
        }
    }
}