package com.ronny.k24.helper;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.ronny.k24.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {

    public static void setToolbar(AppCompatActivity appCompatActivity, String title) {
        Toolbar toolbar = appCompatActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(title);
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String viewFormatDate(String strCurrentDate) {
//        strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String sentFormatDate(String strCurrentDate) {
//        strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(newDate);
    }

    public static String formatTime(String strCurrentDate) {
//        strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("hh:mm:ss");
        return format.format(newDate);
    }
}
