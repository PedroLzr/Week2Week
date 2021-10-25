package com.manzano.week2week.activities

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.AdminSQLiteOpenHelper
import com.manzano.week2week.R

class CrearEntrenoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_entreno)

        val bundle = intent.extras
        val idDia = bundle?.getLong("idDia")

        val etEntreno = findViewById<EditText>(R.id.etEntreno)
        val cbDescanso = findViewById<CheckBox>(R.id.cbDescanso)
        val btnGuardarEntreno = findViewById<Button>(R.id.btnGuardarEntreno)

        val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select * from dia where id=${idDia}", null)
        if (fila.moveToFirst()) {
            etEntreno.setText(fila.getString(4))
            if(fila.getInt(5) == 1){
                cbDescanso.isChecked = true
            }
        }

        cbDescanso.setOnClickListener {
            if(cbDescanso.isChecked){
                etEntreno.isEnabled = false
                etEntreno.setText("Descanso")
            }
            else{
                etEntreno.isEnabled = true
                etEntreno.setText("")
            }
        }

        btnGuardarEntreno.setOnClickListener {
            val values = ContentValues()
            values.put("entreno", etEntreno.text.toString())
            if(cbDescanso.isChecked){
                values.put("descanso", 1)
            }
            bd.update("dia", values, "id = ${idDia}", null)
            Toast.makeText(this, "Entreno guardado", Toast.LENGTH_LONG).show()

            onBackPressed()
        }
        fila.close()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}