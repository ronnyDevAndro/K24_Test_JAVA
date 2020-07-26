package com.ronny.k24.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ronny.k24.R;
import com.ronny.k24.adapter.AdapterBiodata;
import com.ronny.k24.database.DatabaseSqlite;
import com.ronny.k24.helper.SessionManager;
import com.ronny.k24.helper.Util;
import com.ronny.k24.model.ModelBiodata;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    LinearLayout lLWrpEmpty, lLHome, lLWrpMbr,lLBtnUpdate;
    RelativeLayout rRWrpAdm;
    RecyclerView recyclerView;
    ArrayList<ModelBiodata> arrayListUser = new ArrayList<>();
    SwipeRefreshLayout swipe;
    FloatingActionButton fbBtnAdd;
    SessionManager sessionManager;
    private DatabaseSqlite databaseSqlite;
    EditText etNama, etTgl, etAlmt, etUserId, etPass,etKodeMember,etPassNew;
    AutoCompleteTextView actvJnsKel;
    TextView tvPass,tvBtn;
    LinearLayout lLPassBru;
    ModelBiodata modelBiodata = new ModelBiodata();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseSqlite = new DatabaseSqlite(this);
        lLWrpMbr = findViewById(R.id.wraper_member);
        rRWrpAdm = findViewById(R.id.wraper_admin);


        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String usrEmp = user.get(SessionManager.kunci_email);
        assert usrEmp != null;
        if (usrEmp.equals("admin")) {
            clearLayout();
            rRWrpAdm.setVisibility(View.VISIBLE);
            viewAdmin();
        } else {
            clearLayout();
            lLWrpMbr.setVisibility(View.VISIBLE);
            viewMember(usrEmp);
        }
    }

    private void viewAdmin() {
        Util.setToolbar(this, getString(R.string.menu_list));
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        lLWrpEmpty = findViewById(R.id.wraper_empty);
        fbBtnAdd = findViewById(R.id.btn_add);
        lLHome = findViewById(R.id.home);
        fbBtnAdd.setOnClickListener(this);
        swipe = findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           getUSer();
                       }
                   }
        );
    }

    private void viewMember(String USERNAME) {
        Util.setToolbar(this, getString(R.string.menu_list));
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }


        SQLiteDatabase db = databaseSqlite.getWritableDatabase();
        String whereclause = "username=?";
        String[] whereargs = new String[]{USERNAME};
        Cursor csr = db.query(DatabaseSqlite.TABLE_BIODATA, null, whereclause, whereargs, null, null, null);
        if (csr.moveToFirst()) {
            modelBiodata.setIdUser(csr.getString(csr.getColumnIndex(DatabaseSqlite.ID_USER)));
            modelBiodata.setKodeMember(csr.getString(csr.getColumnIndex(DatabaseSqlite.KODE_MEMBER)));
            modelBiodata.setNamaUser(csr.getString(csr.getColumnIndex(DatabaseSqlite.NAMA)));
            modelBiodata.setJenKel(csr.getString(csr.getColumnIndex(DatabaseSqlite.JEN_KEL)));
            modelBiodata.setAlmtUser(csr.getString(csr.getColumnIndex(DatabaseSqlite.ALAMAT)));
            modelBiodata.setTglUser(csr.getString(csr.getColumnIndex(DatabaseSqlite.TGL_LHR)));
            modelBiodata.setUserName(csr.getString(csr.getColumnIndex(DatabaseSqlite.USERNAME)));
            modelBiodata.setPassUser(csr.getString(csr.getColumnIndex(DatabaseSqlite.PASSWORD)));
        }

        etKodeMember = findViewById(R.id.et_kode);
        etNama = findViewById(R.id.et_nama);
        etTgl = findViewById(R.id.et_tgl);
        etAlmt = findViewById(R.id.et_alamat);
        etUserId = findViewById(R.id.et_userid);
        etPass = findViewById(R.id.et_password);
        actvJnsKel = findViewById(R.id.actv_jns_kel);
        tvPass = findViewById(R.id.tv_pass);
        etPassNew = findViewById(R.id.et_password_new);
        lLPassBru = findViewById(R.id.ll_pass_bru);
        lLBtnUpdate = findViewById(R.id.btn_update);
        tvBtn = findViewById(R.id.btn);
        tvBtn.setText(getString(R.string.rubah_password));
        tvPass.setText(getString(R.string.password_lama));
        lLBtnUpdate.setVisibility(View.VISIBLE);
        etNama.setText(modelBiodata.getNamaUser());
        etTgl.setText(Util.viewFormatDate(modelBiodata.getTglUser()));
        etAlmt.setText(modelBiodata.getAlmtUser());
        etUserId.setText(modelBiodata.getUserName());
        etPass.setText(modelBiodata.getPassUser());
        actvJnsKel.setText(modelBiodata.getJenKel());
        etKodeMember.setText(modelBiodata.getKodeMember());
        lLBtnUpdate.setOnClickListener(this);
    }

    private void clearLayout() {
        if (lLWrpMbr.getVisibility() == View.VISIBLE) {
            lLWrpMbr.setVisibility(View.GONE);
        }

        if (rRWrpAdm.getVisibility() == View.VISIBLE) {
            rRWrpAdm.setVisibility(View.GONE);
        }

    }

    private void getUSer() {
        arrayListUser = databaseSqlite.getAllData();
        if (arrayListUser.size() > 0) {
            swipe.setRefreshing(false);
            AdapterBiodata adapter = new AdapterBiodata(MainActivity.this, arrayListUser);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            lLWrpEmpty.setVisibility(View.GONE);
        } else {
            swipe.setRefreshing(false);
            arrayListUser.clear();
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(lLHome, "Data User Tidak Ada!", Snackbar.LENGTH_LONG).show();
            lLWrpEmpty.setVisibility(View.VISIBLE);
        }

    }

    private void updatePass(){
        databaseSqlite.updateNameStatus(modelBiodata.getIdUser(),etPassNew.getText().toString().trim());
        Toast.makeText(MainActivity.this, "Anda Berhasil Rubah Sandi. Mohon Login Kembali", Toast.LENGTH_LONG).show();
        sessionManager.logout();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, AddUser.class);
                startActivity(intent);
                break;
            case R.id.btn_update:
                pengingat();
                break;
        }
    }

    private void pengingat() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda Ingin Update Password?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updatePass();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onRefresh() {
        getUSer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                sessionManager.logout();
                finish();
                return true;
        }

        return false;
    }
}