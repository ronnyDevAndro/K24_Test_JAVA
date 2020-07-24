package com.ronny.k24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.ronny.k24.R;
import com.ronny.k24.database.DatabaseSqlite;
import com.ronny.k24.helper.SessionManager;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final int PERIOD = 2000;
    Button signin;
    SessionManager sessionManager;
    LinearLayout lLForm, lLHome;
    RelativeLayout lLProgressbar;
    ProgressBar pDialog;
    DatabaseSqlite databaseSqlite;
    private long lastPressedTime;
    private EditText logtxtUser, logtxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logtxtUser = findViewById(R.id.user_id);
        logtxtPassword = findViewById(R.id.password);
        signin = findViewById(R.id.login);
        lLForm = findViewById(R.id.form);
        lLHome = findViewById(R.id.home);
        lLProgressbar = findViewById(R.id.wraper_progressbar);
        databaseSqlite = new DatabaseSqlite(this);
        pDialog = findViewById(R.id.progress_workshop);
        sessionManager = new SessionManager(getApplicationContext());
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            confrimempty();
        }
    }

    public void confrimempty() {
        final String user = logtxtUser.getText().toString().toLowerCase().trim();
        final String password = logtxtPassword.getText().toString();
        boolean userEmpty = TextUtils.isEmpty(user);
        boolean passEmpty = TextUtils.isEmpty(password);
        if (userEmpty) {
            Snackbar.make(lLHome, "User ID Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
        } else if (passEmpty) {
            Snackbar.make(lLHome, "Password Tidak Boleh Kosong", Snackbar.LENGTH_LONG).show();
        } else {
            loginUser(user, password);
        }
    }

    private void loginUser(final String User, String Pass) {
        pDialog.setVisibility(View.VISIBLE);
        lLForm.setVisibility(View.GONE);
        lLProgressbar.setVisibility(View.VISIBLE);
        if (User.equals("admin")) {
            pDialog.setVisibility(View.GONE);
            lLForm.setVisibility(View.VISIBLE);
            lLProgressbar.setVisibility(View.GONE);
            if (Pass.equals("admin")) {
                sessionManager.createSessionUserEmployee(User);
                Intent it = new Intent(Login.this, MainActivity.class);
                startActivity(it);
                finish();
                Toast.makeText(Login.this, "Anda Berhasil Login", Toast.LENGTH_LONG).show();
            } else {
                Snackbar.make(lLHome, "Password Anda Salah", Snackbar.LENGTH_LONG).show();

            }
        } else {
            pDialog.setVisibility(View.GONE);
            lLForm.setVisibility(View.VISIBLE);
            lLProgressbar.setVisibility(View.GONE);
            boolean cekUser = databaseSqlite.cekUser(User);
            boolean res = databaseSqlite.loginUser(User, Pass);
            if (cekUser) {
                if (res) {
                    sessionManager.createSessionUserEmployee(User);
                    Toast.makeText(Login.this, "Anda Berhasil Login", Toast.LENGTH_SHORT).show();
                    Intent intentMoveToMain = new Intent(Login.this, MainActivity.class);
                    startActivity(intentMoveToMain);
                } else {
                    Snackbar.make(lLHome, "Password Anda Salah", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(lLHome, "User Anda Tidak Ada", Snackbar.LENGTH_LONG).show();
            }


        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Anda Keluar Aplikasi",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                        moveTaskToBack(true);
                        finish();
                    }
                    return true;
            }
        }
        return false;
    }
}