package com.ronny.k24.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.ronny.k24.R;
import com.ronny.k24.database.DatabaseSqlite;
import com.ronny.k24.helper.Util;
import com.ronny.k24.model.ModelBiodata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AddUser extends AppCompatActivity implements View.OnClickListener {
    EditText etNama, etTgl, etAlmt, etUserId, etPass;
    AutoCompleteTextView actvJnsKel;
    LinearLayout  lLBtn,lLHome;
    Button btnTgl;
    String[] jnsKel = {"Laki-Laki", "Perempuan"};
    TextView tvBtn;
    DatabaseSqlite databaseSqlite;
    List<ModelBiodata> modelBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Util.setToolbar(this, getString(R.string.menu_add));
        databaseSqlite = new DatabaseSqlite(this);
        modelBiodata = new ArrayList<>();

        etNama = findViewById(R.id.et_nama);
        etTgl = findViewById(R.id.et_tgl);
        etAlmt = findViewById(R.id.et_alamat);
        etUserId = findViewById(R.id.et_userid);
        etPass = findViewById(R.id.et_password);
        actvJnsKel = findViewById(R.id.actv_jns_kel);
        lLBtn = findViewById(R.id.btn_save);
        lLHome = findViewById(R.id.home);
        tvBtn = findViewById(R.id.btn);
        tvBtn.setText("Tambah Data");
        btnTgl = findViewById(R.id.btn_tgl);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, jnsKel);
        actvJnsKel.setThreshold(1);
        actvJnsKel.setAdapter(adapter);
        btnTgl.setOnClickListener(this);
        lLBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (TextUtils.isEmpty(etNama.getText().toString())) {
                    Snackbar.make(lLHome, "Nama Member Tidak Boleh kosong", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etAlmt.getText().toString())) {
                    Snackbar.make(lLHome, "Alamat Member Tidak Boleh kosong", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etTgl.getText().toString())) {
                    Snackbar.make(lLHome, "Tanggal Lahir Tidak Boleh kosong", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(actvJnsKel.getText().toString())) {
                    Snackbar.make(lLHome, "Jenis Kelamin Tidak Boleh kosong", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etUserId.getText().toString())) {
                    Snackbar.make(lLHome, "User Id Member Boleh kosong", Snackbar.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(etPass.getText().toString())) {
                    Snackbar.make(lLHome, "Passwor Member Tidak Boleh kosong", Snackbar.LENGTH_LONG).show();
                }  else {
                    saveData();
                }
                break;
            case R.id.btn_tgl:
                final Calendar c = Calendar.getInstance();
                final Calendar currentDate = Calendar.getInstance();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("Y-MM-d");
                        etTgl.setText(dateFormat.format(c.getTime()));
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
                break;
        }
    }

    private void Data(String kodeMember, String nameUser, String tglLhr, String almtUser, String jnsKel, String userName, String passUser, int statusdb) {
        databaseSqlite.addUser(kodeMember, nameUser, tglLhr, almtUser, jnsKel, userName, passUser, statusdb);
        ModelBiodata n = new ModelBiodata(kodeMember, nameUser, tglLhr, almtUser, jnsKel, userName, passUser, statusdb);
        modelBiodata.add(n);
    }

    private void saveData(){
        Random r = new Random();
        String random = String.valueOf(r.nextInt(900) + 100);
        final String NAMA = etNama.getText().toString().trim();
        final String TGL = etTgl.getText().toString().trim();
        final String ALAMAT = etAlmt.getText().toString().trim();
        final String JNSKEL = actvJnsKel.getText().toString().trim();
        final String USERNAME = etUserId.getText().toString().trim();
        final String PASSWORD = etPass.getText().toString().trim();


        SQLiteDatabase db = databaseSqlite.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT username FROM tbl_biodata WHERE username='" + USERNAME + "'", null);
        if (cursor.getCount() > 0) {
            Snackbar.make(lLHome, "User Member Sudah Pernah Diinput!", Snackbar.LENGTH_LONG).show();
        } else {
            Data(random,NAMA,TGL,ALAMAT,JNSKEL,USERNAME,PASSWORD,0);
            Intent it = new Intent(AddUser.this, MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}