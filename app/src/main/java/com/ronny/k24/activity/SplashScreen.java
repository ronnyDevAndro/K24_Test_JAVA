package com.ronny.k24.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ronny.k24.R;
import com.ronny.k24.helper.SessionManager;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
    private static String TAG = SplashScreen.class.getSimpleName();
    SessionManager sessionManager;
    String answer, UserEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        UserEmp = user.get(SessionManager.kunci_email);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean empty = UserEmp == null;
                if (empty) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    sessionManager.checkLogin();
                }
                finish();
            }
            /*durasi 5000ms*/
        }, 3000);

    }
}