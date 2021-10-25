package com.manzano.week2week.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manzano.week2week.AdminSQLiteOpenHelper
import com.manzano.week2week.R

class SemanasActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal_semanas)

        val bundle = intent.extras
        val idUsuario = bundle?.getLong("idUsuario")

        val btnNuevaSemana = findViewById<FloatingActionButton>(R.id.btnNuevaSemana)
        //val btnSalir = findViewById<Button>(R.id.btnSalir)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val linearLayour = findViewById<LinearLayout>(R.id.linearLayoutSemanas)
        linearLayour.orientation = LinearLayout.VERTICAL

        val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select id, nombre from semana where idusuario = ${idUsuario}", null)

        if (fila.moveToFirst()) {
            while (fila.isAfterLast == false) {
                Log.i("Semanas", "Id de la semana " + fila.getString(1) + " es el: " + fila.getLong(0))
                val idSemana = fila.getLong(0)
                //val nombreSemana = fila.getString(1)
                val btnSemana = Button(this)
                btnSemana.text = "Semana " + idSemana
                btnSemana.setOnClickListener{
                    val intent = Intent(this, VerSemanaActivity::class.java)
                    intent.putExtra("idSemana", idSemana)
                    intent.putExtra("idUsuario", idUsuario)
                    startActivity(intent)
                }

                linearLayour.addView(btnSemana)

                fila.moveToNext()
            }
            fila.close()
        }

        btnNuevaSemana.setOnClickListener {
            //Se crea la semana en la base de datos
            val values = ContentValues().apply{
                put("nombre", "Semana")
                put("idusuario", idUsuario)
            }
            val idNuevaSemana = bd.insert("semana", null, values)

            //Se crean los días y se les añade el id de la semana a la que pertenecen
            val valuesLunes = ContentValues().apply{
                put("nombre", "Lunes")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idLunes = bd.insert("dia", null, valuesLunes)

            val valuesMartes = ContentValues().apply{
                put("nombre", "Martes")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idMartes = bd.insert("dia", null, valuesMartes)

            val valueMiercoles = ContentValues().apply{
                put("nombre", "Miercoles")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idMiercoles = bd.insert("dia", null, valueMiercoles)

            val valuesJueves = ContentValues().apply{
                put("nombre", "Jueves")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idJueves = bd.insert("dia", null, valuesJueves)

            val valuesViernes = ContentValues().apply{
                put("nombre", "Viernes")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idViernes = bd.insert("dia", null, valuesViernes)

            val valuesSabado = ContentValues().apply{
                put("nombre", "Sabado")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idSabado = bd.insert("dia", null, valuesSabado)

            val valuesDomingo = ContentValues().apply{
                put("nombre", "Domingo")
                put("comida", "")
                put("entreno", "")
                put("comidatrampa", 0)
                put("descanso", 0)
                put("idSemana", idNuevaSemana)
            }
            val idDomingo = bd.insert("dia", null, valuesDomingo)

            //Se lanza el nuevo activity
            val intent = Intent(this, CrearSemanaActivity::class.java)
            intent.putExtra("idLunes", idLunes)
            intent.putExtra("idMartes", idMartes)
            intent.putExtra("idMiercoles", idMiercoles)
            intent.putExtra("idJueves", idJueves)
            intent.putExtra("idViernes", idViernes)
            intent.putExtra("idSabado", idSabado)
            intent.putExtra("idDomingo", idDomingo)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }

        /*btnSalir.setOnClickListener {
            System.exit(0);
        }*/

        btnCerrarSesion.setOnClickListener {
            val prefs = PreferenceManager.getDefaultSharedPreferences(this@SemanasActivity)
            prefs.edit().clear().apply()
            ActivityCompat.finishAffinity(this)
        }
    }
}