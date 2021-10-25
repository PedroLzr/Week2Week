package com.manzano.week2week.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.R

class CrearSemanaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_semana)

        val btnComidaLunes = findViewById<Button>(R.id.btnComidaLunes)
        val btnComidaMartes = findViewById<Button>(R.id.btnComidaMartes)
        val btnComidaMiercoles = findViewById<Button>(R.id.btnComidaMiercoles)
        val btnComidaJueves = findViewById<Button>(R.id.btnComidaJueves)
        val btnComidaViernes = findViewById<Button>(R.id.btnComidaViernes)
        val btnComidaSabado = findViewById<Button>(R.id.btnComidaSabado)
        val btnComidaDomingo = findViewById<Button>(R.id.btnComidaDomingo)

        val btnEntrenoLunes = findViewById<Button>(R.id.btnEntrenoLunes)
        val btnEntrenoMartes = findViewById<Button>(R.id.btnEntrenoMartes)
        val btnEntrenoMiercoles = findViewById<Button>(R.id.btnEntrenoMiercoles)
        val btnEntrenoJueves = findViewById<Button>(R.id.btnEntrenoJueves)
        val btnEntrenoViernes = findViewById<Button>(R.id.btnEntrenoViernes)
        val btnEntrenoSabado = findViewById<Button>(R.id.btnEntrenoSabado)
        val btnEntrenoDomingo = findViewById<Button>(R.id.btnEntrenoDomingo)

        val btnGuardarSemana = findViewById<Button>(R.id.btnGuardarSemana)

        val bundle = intent.extras
        val idLunes = bundle?.getLong("idLunes")
        val idMartes = bundle?.getLong("idMartes")
        val idMiercoles = bundle?.getLong("idMiercoles")
        val idJueves = bundle?.getLong("idJueves")
        val idViernes = bundle?.getLong("idViernes")
        val idSabado = bundle?.getLong("idSabado")
        val idDomingo = bundle?.getLong("idDomingo")
        val idUsuario = bundle?.getLong("idUsuario")

        btnComidaLunes.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idLunes)
            startActivity(intent)
        }

        btnEntrenoLunes.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idLunes)
            startActivity(intent)
        }

        btnComidaMartes.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idMartes)
            startActivity(intent)
        }

        btnEntrenoMartes.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idMartes)
            startActivity(intent)
        }

        btnComidaMiercoles.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idMiercoles)
            startActivity(intent)
        }

        btnEntrenoMiercoles.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idMiercoles)
            startActivity(intent)
        }

        btnComidaJueves.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idJueves)
            startActivity(intent)
        }

        btnEntrenoJueves.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idJueves)
            startActivity(intent)
        }

        btnComidaViernes.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idViernes)
            startActivity(intent)
        }

        btnEntrenoViernes.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idViernes)
            startActivity(intent)
        }

        btnComidaSabado.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idSabado)
            startActivity(intent)
        }

        btnEntrenoSabado.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idSabado)
            startActivity(intent)
        }

        btnComidaDomingo.setOnClickListener {
            val intent = Intent(this, CrearComidaActivity::class.java)
            intent.putExtra("idDia", idDomingo)
            startActivity(intent)
        }

        btnEntrenoDomingo.setOnClickListener{
            val intent = Intent(this, CrearEntrenoActivity::class.java)
            intent.putExtra("idDia", idDomingo)
            startActivity(intent)
        }

        btnGuardarSemana.setOnClickListener{
            val intent = Intent(this, SemanasActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
    }
}