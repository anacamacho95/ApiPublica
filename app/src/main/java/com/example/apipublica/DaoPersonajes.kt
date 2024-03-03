package com.example.apipublica

import android.util.Log
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DaoPersonajes : InterfaceDaoPersonajes {
    var url = "https://rickandmortyapi.com/api/character/"
    override suspend fun getPersonajes(): MutableList<Personaje> {
        var  personajes:MutableList<Personaje> = mutableListOf()
        return suspendCoroutine { tarea_asincrona ->
            url.collection("alimentos")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val personaje = document.toObject(Personaje::class.java)
                            personaje.idAlimentoFB = document.id
                            personajes.add(personaje)
                        }
                        tarea_asincrona.resume(personajes)
                    } else {
                        Log.d("firebase", "Error al obtener documentos.", task.exception)
                    }
                }
        }    }
}