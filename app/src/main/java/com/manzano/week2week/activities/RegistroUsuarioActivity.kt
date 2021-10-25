package com.manzano.week2week.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.AdminSQLiteOpenHelper
import com.manzano.week2week.R

class RegistroUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuario)

        val etNombreCompleto = findViewById<EditText>(R.id.etNombreCompletoNuevoUsuario)
        val etUsuario = findViewById<EditText>(R.id.etNuevoUsuario)
        val etEmail = findViewById<EditText>(R.id.etNuevoEmail)
        val etPass = findViewById<EditText>(R.id.etNuevaPass)
        val etRepetirPass = findViewById<EditText>(R.id.etRepetirNuevaPass)
        val btnCrearUsuario = findViewById<Button>(R.id.btnCrearUsuario)
        val btnIrIniciarSesion = findViewById<Button>(R.id.btnIrIniciarSesion)

        btnIrIniciarSesion.setOnClickListener{
            onBackPressed()
        }

        btnCrearUsuario.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
            val bd = admin.writableDatabase

            val nombreCompleto = etNombreCompleto.text.toString().trim()
            val nombreUsuario = etUsuario.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()
            val repetirPass = etRepetirPass.text.toString().trim()

            if(pass.equals(repetirPass)){

                if(!nombreCompleto.equals("")){
                    if(!nombreUsuario.equals("")){
                        if(!pass.equals("")){
                            val values = ContentValues().apply{
                                put("nombrecompleto", nombreCompleto)
                                put("nombreusuario", nombreUsuario)
                                put("email", email)
                                put("password", pass)
                            }

                            bd.insert("usuario", null, values)
                            Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "La contraseña está vacía", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "El usuario está vacío", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(this, "El nombre está vacío", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}