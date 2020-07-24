package com.ronny.k24.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ronny.k24.model.ModelBiodata;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseSqlite extends SQLiteOpenHelper {

    public static final String DB_NAME = "K24Test.db";
    public static final String TABLE_BIODATA = "tbl_biodata";
    public static final String ID_USER = "id_user";
    public static final String KODE_MEMBER = "kode_member";
    public static final String NAMA = "nama";
    public static final String TGL_LHR = "tgl_lhr";
    public static final String ALAMAT = "alamat";
    public static final String JEN_KEL = "jen_kel";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COLUMN_STATUS = "status";
    private static final int DB_VERSION = 1;

    public DatabaseSqlite(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_BIODATA
                + "(" + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KODE_MEMBER + " VARCHAR, "
                + NAMA + " VARCHAR, "
                + TGL_LHR + " DATE, "
                + ALAMAT + " VARCHAR, "
                + JEN_KEL + " VARCHAR, "
                + USERNAME + " VARCHAR, "
                + PASSWORD + " VARCHAR, "
                + COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addUser(String kodeMember, String nameUser, String tglLhr, String almtUser, String jnsKel, String userName, String passUser, int statusdb) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryValues = "INSERT INTO tbl_biodata ( kode_member,nama,tgl_lhr,alamat,jen_kel,username,password,status) VALUES ( '" + kodeMember + "','" + nameUser + "','" + tglLhr + "'" +
                ",'" + almtUser + "','" + jnsKel + "','" + userName + "','" + passUser + "','" + statusdb + "');";
        Log.d("insert sqlite ", "" + queryValues);
        db.execSQL(queryValues);
        db.close();
        return true;
    }

    public ArrayList<ModelBiodata> getAllData() {
        ArrayList<ModelBiodata> arrayBiodata = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BIODATA;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ModelBiodata modelBiodata = new ModelBiodata();

                modelBiodata.setIdUser( cursor.getString(0));
                modelBiodata.setKodeMember( cursor.getString(1));
                modelBiodata.setNamaUser( cursor.getString(2));
                modelBiodata.setTglUser( cursor.getString(3));
                modelBiodata.setAlmtUser(cursor.getString(4));
                modelBiodata.setJenKel( cursor.getString(5));
                modelBiodata.setUserName( cursor.getString(6));
                modelBiodata.setPassUser( cursor.getString(7));
                arrayBiodata.add(modelBiodata);
            } while (cursor.moveToNext());
        }
        database.close();
        return arrayBiodata;
    }

    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_BIODATA, contentValues, ID_USER + "=" + id, null);
        db.close();
        return true;
    }

    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_BIODATA + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void removeAll() {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(DatabaseSqlite.TABLE_BIODATA, COLUMN_STATUS + " =" + 1, null);
    }

    public boolean loginUser(String username, String password) {
        String[] columns = {ID_USER};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERNAME + "=?" + " and " + PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor CekLogin = db.query(TABLE_BIODATA, columns, selection, selectionArgs, null, null, null);
        int countCekLogin = CekLogin.getCount();
        CekLogin.close();

        db.close();

        if (countCekLogin > 0)
            return true;
        else
            return false;
    }

    public boolean cekUser(String username) {
        String[] columns = {ID_USER};
        SQLiteDatabase db = getReadableDatabase();
        String selectionUser = USERNAME + "=?";
        String[] selectionArgsUser = {username};
        Cursor CekUser = db.query(TABLE_BIODATA, columns, selectionUser, selectionArgsUser, null, null, null);
        int countCekUser = CekUser.getCount();
        CekUser.close();
        db.close();

        if (countCekUser > 0)
            return true;
        else
            return false;
    }
}