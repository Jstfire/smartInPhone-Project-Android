package com.example.smartinphoneprojectandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartinphoneprojectandroid.MainActivity;
import com.example.smartinphoneprojectandroid.adminAct.MainActivityAdmin;
import com.example.smartinphoneprojectandroid.userAct.MainActivityUser;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String KEY_ID = "id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_ROLE = "role";
    public static final String is_login = "loginstatus";
    public static final String is_admin = "userstatus";
    private final String SHARE_NAME = "loginsession";
    private final int MODE_PRIVATE = 0;
    private Context _context;

    public SessionManager (Context context) {
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sp.edit();
    }

    public void storeLoginAdmin(String token, Integer id, String email, String name, String username, String avatar, String role) {
        editor.putBoolean(is_login, true);
        editor.putBoolean(is_admin, true);
        editor.putString(KEY_TOKEN, token);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    public void storeLoginUser(String token, Integer id, String email, String name, String username, String avatar, String role) {
        editor.putBoolean(is_login, true);
        editor.putBoolean(is_admin, false);
        editor.putString(KEY_TOKEN, token);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_AVATAR, avatar);
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    public HashMap getDetailLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_TOKEN, sp.getString(KEY_TOKEN, null));
        map.put(KEY_ID, String.valueOf(sp.getInt(KEY_ID, 0)));
        map.put(KEY_EMAIL, sp.getString(KEY_EMAIL, null));
        map.put(KEY_NAME, sp.getString(KEY_NAME, null));
        map.put(KEY_USERNAME, sp.getString(KEY_USERNAME, null));
        map.put(KEY_AVATAR, sp.getString(KEY_AVATAR, null));
        map.put(KEY_ROLE, sp.getString(KEY_ROLE, null));
        return map;
    }

    public void checkLogin() {
        if (!this.Loggin()) {
            Intent i = new Intent(_context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void checkUser() {
        if (this.isAdmin()) {
            Intent i = new Intent(_context, MainActivityAdmin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void checkAdmin() {
        if (!this.isAdmin()) {
            Intent i = new Intent(_context, MainActivityUser.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public Boolean Loggin() {
        return sp.getBoolean(is_login,false);
    }

    public Boolean isAdmin() {
        return sp.getBoolean(is_admin,false);
    }

    public void Logout() {
        editor.clear();
        editor.commit();
    }

}
