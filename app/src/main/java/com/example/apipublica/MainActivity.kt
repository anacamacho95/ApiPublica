package com.example.apipublica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    val url ="https://rickandmortyapi.com/api/character/"
    // Crear una cola de solicitudes
    lateinit var cola: RequestQueue
    var personajes:MutableList<Personaje> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cola = Volley.newRequestQueue(this)

        // Probar acceso a FB como api REST
        pruebaGetPersonajesAllInfo()
        pruebaGetPersonajesNombre()

    }

    fun pruebaGetPersonajesAllInfo() {
        val solicitud = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val documents = response.getJSONArray("results")
                    for (i in 0 until documents.length()) {
                        val item = documents.getJSONObject(i)
                        Log.d("FirestoreAll", item.toString())
                    // val personajeJson = results.getJSONObject(i)
                        //val nombre = personajeJson.getString("name")
                        //Log.d("Perzonaje", nombre)

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()

            })
        cola.add(solicitud)

    }

    fun pruebaGetPersonajesNombre() {
        val solicitud = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val documents = response.getJSONArray("results")
                    for (i in 0 until documents.length()) {
                        val item = documents.getJSONObject(i)
                        val nombre = item.getString("name")
                        personajes.add(Personaje(item.getString("name")))
                        Log.d("Perzonaje", nombre)

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()

                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()

            })
        cola.add(solicitud)

    }
}