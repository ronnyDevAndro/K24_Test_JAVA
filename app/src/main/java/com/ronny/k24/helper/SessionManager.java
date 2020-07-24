package com.ronny.k24.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ronny.k24.activity.Login;
import com.ronny.k24.activity.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String kunci_id = "keyid";
    public static final String kunci_email = "keyemail";
    public static final String token_firebase = "token_firebase";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSessionidemployee(String idemployee){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_id, idemployee);
        editor.commit();
    }

    public void createSessionUserEmployee(String email){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_email, email);
        editor.commit();
    }

    public void createSessionToken(String token){
        editor.putString(token_firebase, token);
        editor.commit();
    }

    public void checkLogin(){
        if (!this.is_login()){
            Intent i = new Intent(context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        else {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_id, pref.getString(kunci_id, null));
        user.put(kunci_email, pref.getString(kunci_email, null));
        user.put(token_firebase, pref.getString(token_firebase, null));
        return user;
    }


}
