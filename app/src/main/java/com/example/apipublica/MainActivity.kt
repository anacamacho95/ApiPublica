package com.example.apipublica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var url = "https://rickandmortyapi.com/api/character/"

    // Crear una cola de solicitudes
    lateinit var cola: RequestQueue
    var personajes = mutableListOf<Personaje>()
    val svm:ServiceModelView = TODO()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cola = Volley.newRequestQueue(this)

        // Probar acceso a FB como api REST

        pruebaGetPersonajesAllInfo1()
        pruebaGetPersonajesNombre()
        pruebaGetPersonajesNomId()


    }

    private fun pruebaGetPersonajesAllInfo1() {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Llamar a getPersonajes de forma asíncrona y esperar el resultado
                val personajes =svm.getPersonajes()
                Log.d("coroutines", "hola")
                // Mostrar los resultados con Log.d
                personajes.forEach { personaje ->
                    Log.d("coroutines", "Personaje: ${personaje.nombre}")
                }
            } catch (e: Exception) {
                // Manejar posibles excepciones
                Log.d("coroutines", "Error al obtener personajes", e)
            }
        }    }


    fun pruebaGetPersonajesAllInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Realizar la solicitud de red
                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response -> // Manejar la respuesta exitosa
                        try {
                            val documents = response.getJSONArray("results")
                            for (i in 0 until documents.length()) {
                                val item = documents.getJSONObject(i)
                                Log.d("FirestoreAll", item.toString())
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    { error -> // Manejar errores de la solicitud
                        error.printStackTrace()
                    }
                )
                // Agregar la solicitud a la cola
                cola.add(request)

            } catch (e: Exception) {
                // Manejar excepciones
                e.printStackTrace()
            }
        }
    }


    fun pruebaGetPersonajesNombre() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val solicitud = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        try {
                            val documents = response.getJSONArray("results")
                            for (i in 0 until documents.length()) {
                                val item = documents.getJSONObject(i)
                                val nombre = item.getString("name")
                                val especie = item.getString("species")
                                personajes.add(Personaje(nombre, especie))
                                Log.d("Perzonaje", "Nombre: $nombre -- Especie: $especie")

                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()

                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()

                    })
                cola.add(solicitud)

            } catch (e: Exception) {
                // Manejar excepciones
                e.printStackTrace()
            }

        }
    }

    fun pruebaGetPersonajesNomId() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val id = "2"
                val url = "https://rickandmortyapi.com/api/character/$id"
                val solicitud = JsonObjectRequest(Request.Method.GET, url, null,
                    Response.Listener { response ->
                        try {
                            val nombre = response.getString("name")
                            val iden = response.getString("id")

                            if (iden == id) {
                                personajes.add(Personaje(nombre))
                            }

                            Log.d("IdNombre", "Nombre: $nombre")
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.e("IdNombre", "Error: $error")
                    })

                cola.add(solicitud)

            } catch (e: Exception) {
                // Manejar excepciones
                e.printStackTrace()
            }
        }
    }
}
