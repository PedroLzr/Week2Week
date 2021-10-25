package com.manzano.week2week.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.R

class VerDiaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_dia)

        val bundle = intent.extras
        val comida = bundle?.getString("comida")
        val comidatrampa = bundle?.getInt("comidatrampa")
        val entreno = bundle?.getString("entreno")
        val descanso  = bundle?.getInt("descanso")

        val tvComidaContenido = findViewById<TextView>(R.id.tvComidaContenido)
        val tvEntrenoContenido = findViewById<TextView>(R.id.tvEntrenoContenido)
        val btnVolverDia = findViewById<Button>(R.id.btnVolverDia)

        if(comidatrampa == 0){
            tvComidaContenido.text = comida
        }else{
            tvComidaContenido.text = "Día de comida trampa"
        }

        if(descanso == 0){
            tvEntrenoContenido.text = entreno
        }else{
            tvEntrenoContenido.text = "Día de descanso"
        }

        btnVolverDia.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}