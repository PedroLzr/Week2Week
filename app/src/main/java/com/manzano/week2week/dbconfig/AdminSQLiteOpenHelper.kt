package com.manzano.week2week.dbconfig

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version){

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table usuario(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombrecompleto text," +
                    "nombreusuario text," +
                    "email text," +
                    "password)")

            db.execSQL("create table semana(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre text," +
                    "idusuario int," +
                    "foreign key(idusuario) references usuario(id))")

            db.execSQL("create table dia(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre text," +
                    "comida text," +
                    "comidatrampa int," +
                    "entreno text," +
                    "descanso int," +
                    "idsemana int," +
                    "foreign key(idsemana) references semana(id))")

            db.execSQL("create table progreso(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "peso double," +
                    "foto text," +
                    "idsemana int," +
                    "foreign key(idsemana) references semana(id))")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}