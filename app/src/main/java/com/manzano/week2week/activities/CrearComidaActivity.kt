package com.manzano.week2week.activities

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.dbconfig.AdminSQLiteOpenHelper
import com.manzano.week2week.R

class CrearComidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_comida)
        val bundle = intent.extras
        val idDia = bundle?.getLong("idDia")

        val etComida = findViewById<EditText>(R.id.etComida)
        val cbComidaTrampa = findViewById<CheckBox>(R.id.cbComidaTrampa)
        val btnGuardarComida = findViewById<Button>(R.id.btnGuardarComida)

        val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select * from dia where id=${idDia}", null)
        if (fila.moveToFirst()) {
            etComida.setText(fila.getString(2))
            Log.i("CrearComida", etComida.text.toString())
            if(fila.getInt(3) == 1){
                cbComidaTrampa.isChecked = true
            }
        }

        cbComidaTrampa.setOnClickListener {
            if(cbComidaTrampa.isChecked){
                etComida.isEnabled = false
                etComida.setText("Comida trampa")
            }
            else{
                etComida.isEnabled = true
                etComida.setText("")
            }
        }

        btnGuardarComida.setOnClickListener {
            val values = ContentValues()
            values.put("comida", etComida.text.toString())
            if(cbComidaTrampa.isChecked){
                values.put("comidatrampa", 1)
            }
            bd.update("dia", values, "id = ${idDia}", null)
            Toast.makeText(this, "Comida guardada", Toast.LENGTH_LONG).show()

            Log.i("CrearComida", "Se guarda la comida: " + etComida.text.toString())
            onBackPressed()
        }
        fila.close()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}