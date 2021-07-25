package com.example.trivianica.model.claseRecurso

import com.example.trivianica.model.objetoPregunta

class cR//claseRecursos
{
    companion object Valores
    {
        val VACIO: Int = -1
        val ARTE        : Int = 0 /*Id de Arte*/
        val DEPORTE     : Int = 1 /*Id de Deporte*/
        val GASTRONOMIA : Int = 2 /*Id de Gastronomia*/
        val GEOGRAFIA   : Int = 3 /*Id de Geografia*/
        val HISTORIA    : Int = 4 /*Id de Historia*/
        val TRADICION   : Int = 5 /*Id de Tradicion*/

        var categoriaId: Int = 0 /*Id de categoria en la pregunta actual*/
        var acumulador:  Int = 0 /*Guarda la cantidad de preguntas hechas*/
        var puntaje:     Int = 0 /*Puntaje del usuario*/

        var validador: Boolean = true
        var opcionCorrecta: Boolean = true
        lateinit var respaldoPregunta: objetoPregunta

        /*Guarda el orden en el que aparecen las categorias en la tombola*/
        var listaAleatoriaCategoria = IntArray(5){ _ -> -1}

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
            /*Longitud de la Respuesta*/
            var lR = respaldoPregunta.Respuesta.length
            if(lR <= 60)              tiempo = 3500
            if(lR >  60 && lR <= 110) tiempo = 6500
            if(lR > 110 && lR <= 170) tiempo = 9000
            if(lR > 170 && lR <= 220) tiempo = 14500
            if(lR > 220 && lR <= 300) tiempo = 16500
            if(lR > 300)              tiempo = 19500
            return tiempo
        }

        fun getNombreCategoria(categoriaId: Int): String
        {
            var nombre = ""

            if(categoriaId == ARTE)        nombre = "Arte"
            if(categoriaId == DEPORTE)     nombre = "Deporte"
            if(categoriaId == GASTRONOMIA) nombre = "Gastronomia"
            if(categoriaId == GEOGRAFIA)   nombre = "Geografia"
            if(categoriaId == HISTORIA)    nombre = "Historia"
            if(categoriaId == TRADICION)   nombre = "Tradicion"

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
            var nombreDeRegistro: String
            val nombreCategoria = getNombreCategoria(categoriaId)
            nombreDeRegistro = "categoria$nombreCategoria"
            return nombreDeRegistro
        }
    }
}