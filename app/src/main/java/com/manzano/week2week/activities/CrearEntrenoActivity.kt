package com.manzano.week2week.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.manzano.week2week.dbconfig.AdminSQLiteOpenHelper
import com.manzano.week2week.alarmasconfig.AlarmReceiver
import com.manzano.week2week.R
import com.manzano.week2week.alarmasconfig.TimePickerFragment
import java.util.*

class CrearEntrenoActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_entreno)

        val bundle = intent.extras
        val idDia = bundle?.getLong("idDia")

        val etEntreno = findViewById<EditText>(R.id.etEntreno)
        val cbDescanso = findViewById<CheckBox>(R.id.cbDescanso)
        val btnGuardarEntreno = findViewById<Button>(R.id.btnGuardarEntreno)
        val btnPonerAlarma = findViewById<Button>(R.id.btnPonerAlarma)
        val btnQuitarAlarma = findViewById<Button>(R.id.btnQuitarAlarma)

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
                btnPonerAlarma.isEnabled = false
                btnQuitarAlarma.isEnabled = false
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

    fun showTimerPickerFragment(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "time_picker")
    }

    fun cancelAlarm(view: View) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        startAlarm(calendar)
    }

    private fun startAlarm(calendar: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarma establecida", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}