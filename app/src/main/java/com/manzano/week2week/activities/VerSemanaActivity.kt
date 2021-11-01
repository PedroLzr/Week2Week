package com.manzano.week2week.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.dbconfig.AdminSQLiteOpenHelper
import com.manzano.week2week.R

class VerSemanaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_semana)

        val bundle = intent.extras
        val idSemana = bundle?.getLong("idSemana")
        val idUsuario = bundle?.getLong("idUsuario")

        val btnProgreso = findViewById<Button>(R.id.btnProgreso)
        val btnModificarSemana = findViewById<Button>(R.id.btnModificarSemana)
        val btnBorrarSemana = findViewById<Button>(R.id.btnBorrarSemana)
        val btnVolverSemanas = findViewById<Button>(R.id.btnVolverSemanas)

        val linearLayour = findViewById<LinearLayout>(R.id.linearLayoutDias)
        linearLayour.orientation = LinearLayout.VERTICAL

        val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select * from dia where idsemana = ${idSemana}", null)
        val dias = arrayOf(0L,1L,2L,3L,4L,5L,6L)
        var contador = 0
        if (fila.moveToFirst()) {
            while (fila.isAfterLast == false) {
                val nombre = fila.getString(1)
                val comida = fila.getString(2)
                val comidatrampa = fila.getInt(3)
                val entreno = fila.getString(4)
                val descanso = fila.getInt(5)

                val btnDia = Button(this)
                btnDia.text = nombre
                if(nombre.equals("Sábado") || nombre.equals("Domingo")){
                    btnDia.setBackgroundColor(Color.argb(100, 4, 168, 166))
                }
                btnDia.setOnClickListener{
                    val intent = Intent(this, VerDiaActivity::class.java)
                    intent.putExtra("comida", comida)
                    intent.putExtra("comidatrampa", comidatrampa)
                    intent.putExtra("entreno", entreno)
                    intent.putExtra("descanso", descanso)
                    startActivity(intent)
                }

                linearLayour.addView(btnDia)
                dias.set(contador, fila.getLong(0))
                contador++
                fila.moveToNext()
            }
        }

        fila.close()

        btnProgreso.setOnClickListener {
            val intent = Intent(this, ProgresoActivity::class.java)
            intent.putExtra("idSemana", idSemana)
            startActivity(intent)
        }

        btnModificarSemana.setOnClickListener {
            val intent = Intent(this, CrearSemanaActivity::class.java)
            intent.putExtra("idLunes", dias.get(0))
            intent.putExtra("idMartes", dias.get(1))
            intent.putExtra("idMiercoles", dias.get(2))
            intent.putExtra("idJueves", dias.get(3))
            intent.putExtra("idViernes", dias.get(4))
            intent.putExtra("idSabado", dias.get(5))
            intent.putExtra("idDomingo", dias.get(6))
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }

        btnBorrarSemana.setOnClickListener {
            bd.delete("dia", "idSemana = ${idSemana}", null)
            bd.delete("semana", "id = ${idSemana}", null)

            val intent = Intent(this, SemanasActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }

        btnVolverSemanas.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}