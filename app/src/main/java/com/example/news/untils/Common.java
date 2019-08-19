package com.example.news.untils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.news.MainActivity;
import com.example.news.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Common {

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static String findText(String start,String end,String input){
        String regex = start+"(.*?)"+end;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            return matcher.group(1);

        }
        return "";
    }



    public static void saveEmail(Context context, String email)
    {
        SharedPreferences preferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email",email);
        editor.apply();
    }

    public static  String getEmail1(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        return preferences.getString("email", "");
    }

    public static void dangXuat(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }
    public static boolean checkDangNhap(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        if(!preferences.getString("email", "").equals(""))
            return true;
        return false;
    }


    public static boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    // check data QRCODE
    public static boolean isValidQRCODE(String data){
        if(!data.contains("HỌ VÀ TÊN:"))
            return false;
        if(!data.contains("NGÀNH:"))
            return false;
        if(!data.contains("KHÓA HỌC:"))
            return false;
        if(!data.contains("MÃ SINH VIÊN:"))
            return false;
        if(!data.contains("ĐỊNH DANH:"))
            return false;
        if(!data.contains("@is-tech.vn"))
            return false;
        return true;


    }

    public static String getTenSVQRCODE(String data){
        data = VNCharacterUtils.removeAccent(data);
        return findText("HO VA TEN:","NGANH",data).trim();

    }






}
