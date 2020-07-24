package com.ronny.k24.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
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
    LinearLayout lLWrpEmpty, lLHome, lLWrpMbr;
    RelativeLayout rRWrpAdm;
    RecyclerView recyclerView;
    ArrayList<ModelBiodata> arrayListUser = new ArrayList<>();
    SwipeRefreshLayout swipe;
    FloatingActionButton fbBtnAdd;
    SessionManager sessionManager;
    private DatabaseSqlite databaseSqlite;

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

    private void viewMember(String USERNAME){
        Util.setToolbar(this, getString(R.string.menu_list));
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        SQLiteDatabase db = databaseSqlite.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT username FROM tbl_biodata WHERE username='" + USERNAME + "'", null);
        ArrayList<ModelBiodata> arrayBiodata = new ArrayList<>();
        ModelBiodata modelBiodata = new ModelBiodata();
        if (cursor.moveToFirst()) {
            do {
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
        Log.d("tes","jalanin "+arrayBiodata.get(0));

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent intent = new Intent(this, AddUser.class);
                startActivity(intent);
                break;
        }
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