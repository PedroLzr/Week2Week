package com.manzano.week2week.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.dbconfig.AdminSQLiteOpenHelper
import com.manzano.week2week.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        val idUsuario = prefs.getLong("idUsuario", -1)

        //Si existe un idUsuario es que hay un usuario que se quiere recordar
        if(idUsuario > 0){
            Log.i("MainActivity", "Entra al if de idUsuario > 0")
            val intent = Intent(this, SemanasActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }else{
            //Sino existe idUsuario se muestra la pantalla de acceso
            Log.i("MainActivity", "Entra al else")
            val btnNuevoUsuario = findViewById<Button>(R.id.btnCrearCuenta)
            val btnIniciarSesion = findViewById<Button>(R.id.btnEntrar)
            val etUsuario = findViewById<EditText>(R.id.etUsuario)
            val etPassword = findViewById<EditText>(R.id.etPassword)
            val cbRecordarme = findViewById<CheckBox>(R.id.cbRecordarme)

            btnNuevoUsuario.setOnClickListener {
                val intent = Intent(this, RegistroUsuarioActivity::class.java)
                startActivity(intent)
            }
            btnIniciarSesion.setOnClickListener{
                val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
                val bd = admin.writableDatabase
                val fila = bd.rawQuery("select id, password from usuario where nombreusuario='${etUsuario.text}'", null)

                if (fila.moveToFirst()) {
                    if(fila.getString(1) == etPassword.text.toString()){

                        if(cbRecordarme.isChecked){
                            prefs.edit().putLong("idUsuario", fila.getLong(0)).apply()
                        }

                        val intent = Intent(this, SemanasActivity::class.java)
                        intent.putExtra("idUsuario", fila.getLong(0))
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "No existe el usuario indicado", Toast.LENGTH_LONG).show()
                }
                fila.close()
                bd.close()
            }
        }
    }
}