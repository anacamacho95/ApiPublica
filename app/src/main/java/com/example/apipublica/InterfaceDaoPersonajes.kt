package com.example.apipublica

interface InterfaceDaoPersonajes {
    suspend fun getPersonajes():MutableList<Personaje>
}