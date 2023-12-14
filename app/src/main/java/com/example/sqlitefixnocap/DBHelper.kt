package com.example.sqlitefixnocap

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.w3c.dom.Text
import kotlin.math.log

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DBNAME, null, 1) {

    companion object {
        const val DBNAME = "Login.db"
    }

    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("PRAGMA foreign_keys = ON;")
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)")
        MyDB.execSQL("create Table warung(idwarung TEXT primary key, namawarung TEXT, logo TEXT, gambar TEXT)")
        MyDB.execSQL("create Table menu(idmenu TEXT primary key, namamenu TEXT, hargamenu TEXT, gambarmenu TEXT, kategorimenu TEXT, idwarung TEXT, foreign key (idwarung) references warung(idwarung))")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
    }

    fun insertData(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)
        return result != -1L
    }

    fun insertWarung(idwarung: String, namawarung: String, logo: String, gambar: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("idwarung", idwarung)
        contentValues.put("namawarung", namawarung)
        contentValues.put("logo", logo)
        contentValues.put("gambar", gambar)
        val result = MyDB.insert("warung", null, contentValues)
        return result != -1L
    }

    fun insertMenu(idmenu: String, namamenu: String, hargamenu: String, gambarmenu: String, kategorimenu: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("idmenu", idmenu)
        contentValues.put("namamenu", namamenu)
        contentValues.put("hargamenu", hargamenu)
        contentValues.put("gambarmenu", gambarmenu)
        contentValues.put("kategorimenu", kategorimenu)
        val result = MyDB.insert("menu", null, contentValues)
        return result != -1L
    }

    fun checkUsername(username: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
        return cursor.count > 0
    }

//    fun checkWarung(idwarung: String): Boolean {
//        val MyDB = this.writableDatabase
//        val cursor = MyDB.rawQuery("Select * from warung where idwarung = ?", arrayOf(idwarung))
//        return cursor.count > 0
//    }

    fun checkUsernamePassword(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor =
            MyDB.rawQuery("Select * from users where username = ? and password = ?", arrayOf(username, password))
        return cursor.count > 0
    }

    data class Warung(
        var id: String,
        var nama: String,
        var logo: String,
        var gambar: String
    )

    fun getAllWarungs(): ArrayList<Warung> {
        val warungsList = ArrayList<Warung>()
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT * FROM warung", null)

        val idwarungIndex = cursor.getColumnIndex("idwarung")
        val namawarungIndex = cursor.getColumnIndex("namawarung")
        val logoIndex = cursor.getColumnIndex("logo")
        val gambarIndex = cursor.getColumnIndex("gambar")

        if (idwarungIndex != -1 && namawarungIndex != -1 && logoIndex != -1 && gambarIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    val idwarung = cursor.getString(idwarungIndex)
                    val namawarung = cursor.getString(namawarungIndex)
                    val logo = cursor.getString(logoIndex)
                    val gambar = cursor.getString(gambarIndex)

                    val warung = Warung(idwarung, namawarung, logo, gambar)
                    warungsList.add(warung)
                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        return warungsList
    }

    fun getFirstWarung(): Warung? {
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT * FROM warung", null)

        var warungInfo: Warung? = null

        if (cursor.moveToFirst()) {
            val idwarung = cursor.getString(cursor.getColumnIndex("idwarung"))
            val namawarung = cursor.getString(cursor.getColumnIndex("namawarung"))
            val logo = cursor.getString(cursor.getColumnIndex("logo"))
            val gambar = cursor.getString(cursor.getColumnIndex("gambar"))

            warungInfo = Warung(idwarung, namawarung, logo, gambar)
        }

        cursor.close()
        return warungInfo
    }

    fun cariWarung(id: String?): DBHelper.Warung {
        val warung = DBHelper.Warung(id = "", nama = "", logo = "", gambar = "")

        // Pastikan ID tidak kosong atau null sebelum melakukan query
        if (!id.isNullOrEmpty()) {
            try {
                val db = this.readableDatabase
                val selectQuery = "SELECT * FROM warung WHERE idwarung = ?"

                val cursor = db.rawQuery(selectQuery, arrayOf(id))

                if (cursor.moveToFirst()) {
                    warung.id = cursor.getString(cursor.getColumnIndex("idwarung")) ?: ""
                    warung.nama = cursor.getString(cursor.getColumnIndex("namawarung")) ?: ""
                    warung.logo = cursor.getString(cursor.getColumnIndex("logo")) ?: ""
                    warung.gambar = cursor.getString(cursor.getColumnIndex("gambar")) ?: ""

                    Log.d("DBHelper", "Warung Data - ID: ${warung.id}, Nama: ${warung.nama}, Logo: ${warung.logo}, Gambar: ${warung.gambar}")
                }


                cursor.close()
            } catch (e: Exception) {
                // Handle exceptions, log, or report the error as needed
                e.printStackTrace()
            }
        }

        return warung
    }

    fun updateWarung(id: String, nama: String, logo: String, gambar: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("namawarung", nama)
        contentValues.put("logo", logo)
        contentValues.put("gambar", gambar)

        // Update data based on ID
        db.update("warung", contentValues, "idwarung = ?", arrayOf(id))
    }


    fun hapusWarung(id: String) {
        val db = this.writableDatabase

        db.delete("warung", "idwarung = ?", arrayOf(id))
    }

    data class Menu(
        var idmenu: String,
        var namamenu: String,
        var hargamenu: String,
        var gambarmenu: String,
        var kategorimenu: String
    )

    fun getAllMenus(): ArrayList<Menu> {
        val menusList = ArrayList<Menu>()
        val MyDB = this.readableDatabase
        val cursor = MyDB.rawQuery("SELECT * FROM menu", null)

        val idmenuIndex = cursor.getColumnIndex("idmenu")
        val namamenuIndex = cursor.getColumnIndex("namamenu")
        val hargamenuIndex = cursor.getColumnIndex("hargamenu")
        val gambarmenuIndex = cursor.getColumnIndex("gambarmenu")
        val kategorimenuIndex = cursor.getColumnIndex("kategorimenu")

        if (idmenuIndex != -1 && namamenuIndex != -1 && hargamenuIndex != -1 && gambarmenuIndex != -1 && kategorimenuIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    val idmenu = cursor.getString(idmenuIndex)
                    val namamenu = cursor.getString(namamenuIndex)
                    val hargamenu = cursor.getString(hargamenuIndex)
                    val gambarmenu = cursor.getString(gambarmenuIndex)
                    val kategorimenu = cursor.getString(kategorimenuIndex)

                    val menu = Menu(idmenu, namamenu, hargamenu, gambarmenu, kategorimenu)
                    menusList.add(menu)
                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        return menusList
    }

    fun cariMenu(idmenu: String?): DBHelper.Menu {
        val menu = DBHelper.Menu(idmenu = "", namamenu = "", hargamenu = "", gambarmenu = "", kategorimenu = "")

        // Pastikan ID tidak kosong atau null sebelum melakukan query
        if (!idmenu.isNullOrEmpty()) {
            val db = this.readableDatabase
            val selectQuery = "SELECT * FROM menu WHERE idmenu = ?"

            val cursor = db.rawQuery(selectQuery, arrayOf(idmenu))

            if (cursor.moveToFirst()) {
                menu.idmenu = cursor.getString(cursor.getColumnIndex("idmenu")) ?: ""
                menu.namamenu = cursor.getString(cursor.getColumnIndex("namamenu")) ?: ""
                menu.hargamenu = cursor.getString(cursor.getColumnIndex("hargamenu")) ?: ""
                menu.gambarmenu = cursor.getString(cursor.getColumnIndex("gambarmenu")) ?: ""
                menu.kategorimenu = cursor.getString(cursor.getColumnIndex("kategorimenu")) ?: ""
                // Isi atribut lainnya sesuai dengan struktur tabel Anda
            }

            cursor.close()
        }

        return menu
    }

    fun updateMenu(idmenu: String, namamenu: String, hargamenu: String, gambarmenu: String, kategorimenu: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("namamenu", namamenu)
        contentValues.put("hargamenu", hargamenu)
        contentValues.put("gambarmenu", gambarmenu)
        contentValues.put("kategorimenu", kategorimenu)

        // Update data berdasarkan ID
        db.update("menu", contentValues, "idmenu = ?", arrayOf(idmenu))
    }

    fun hapusMenu(idmenu: String) {
        val db = this.writableDatabase

        db.delete("menu", "idmenu = ?", arrayOf(idmenu))
    }
}