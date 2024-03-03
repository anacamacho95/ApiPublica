package com.example.apipublica

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ServiceModelView(application: Application):   AndroidViewModel(application), InterfaceDaoPersonajes {

    private lateinit var daoPersonajes: InterfaceDaoPersonajes

    var personajes: MutableList<Personaje> = mutableListOf<Personaje>()

    //prueba métodos de carga aíncrona
    //Usa suspends con corrutinas
    override suspend fun getPersonajes(): MutableList<Personaje> {
        this.personajes = daoPersonajes.getPersonajes()
        return personajes
    }
}