package com.manzano.week2week.activities

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.manzano.week2week.dbconfig.AdminSQLiteOpenHelper
import com.manzano.week2week.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory

class ProgresoActivity : AppCompatActivity()  {

    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_progreso)

        val bundle = intent.extras
        val idSemana = bundle?.getLong("idSemana")

        val btnNuevaFoto = findViewById<Button>(R.id.btnNuevaFoto)
        val btnGuardarPeso = findViewById<Button>(R.id.btnGuardarPeso)
        val btnVolver = findViewById<Button>(R.id.btnVolverProgreso)
        val etPeso = findViewById<EditText>(R.id.etPeso)
        val imgViewProgreso = findViewById<ImageView>(R.id.imgViewProgreso)

        val admin = AdminSQLiteOpenHelper(this, getString(R.string.database_name), null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select foto, peso from progreso where idsemana = ${idSemana}", null)

        if (fila.moveToFirst()) {
            val foto = fila.getString(0)
            val peso = fila.getDouble(1)
            fila.close()

            if(!foto.equals("")){
                val bitmap = BitmapFactory.decodeFile(foto.toString())
                imgViewProgreso.setImageBitmap(bitmap)
                //Toast.makeText(this, getExternalFilesDir(null).toString(), Toast.LENGTH_LONG).show()
            }
            else{
                btnNuevaFoto.isEnabled = true
            }
            if(peso != 0.0){
                etPeso.setText(peso.toString() + " Kg")
            }
        }

        btnNuevaFoto.setOnClickListener {
            dispatchTakePictureIntent(idSemana, bd)
            finish()
        }

        btnGuardarPeso.setOnClickListener {
            val values = ContentValues()
            values.put("peso", etPeso.text.toString())
            bd.update("progreso", values, "idsemana = ${idSemana}", null)
            Toast.makeText(this, "Peso guardado", Toast.LENGTH_LONG).show()
            etPeso.setText(etPeso.text.toString() + " Kg")
        }

        btnVolver.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dispatchTakePictureIntent(idSemana: Long?, bd: SQLiteDatabase) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Se crea el archivo donde se guardará la imagen
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continua solo si el archivo de imagen se ha creado correctamente
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.manzano.week2week",
                        it
                    )
                    //Toast.makeText(this, photoURI.toString(), Toast.LENGTH_LONG).show()
                    val values = ContentValues()
                    values.put("foto", photoFile.toString())
                    bd.update("progreso", values, "idsemana = ${idSemana}", null)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Se crea el nombre del archivo de la imagen
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}