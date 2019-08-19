package com.example.news;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.news.untils.Common;

import javax.xml.validation.Validator;

public class DangNhapActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        Xinquyen();
        email = (EditText) findViewById(R.id.etLoginUsername);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        if(Common.checkDangNhap(getApplicationContext())){
            Intent intentSetting = new Intent(DangNhapActivity.this,DanhSachMonHocActivity.class);
            startActivity(intentSetting);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.isValidEmailId(email.getText().toString())){
                    Intent intentSetting = new Intent(DangNhapActivity.this,DanhSachMonHocActivity.class);
                    startActivity(intentSetting);
                    Common.saveEmail(getApplicationContext(),email.getText().toString());
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(DangNhapActivity.this).create();
                    alertDialog.setTitle("Lỗi");
                    alertDialog.setMessage("Vui lòng nhập chính xác địa chỉ email");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hủy",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });


                    alertDialog.show();
                }
            }
        });
    }

    public void Xinquyen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        101);
            }

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        101);
            }

            if (checkSelfPermission(Manifest.permission.VIBRATE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.VIBRATE},
                        101);
            }

            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        101);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(Common.checkDangNhap(getApplicationContext())){
            Intent intentSetting = new Intent(DangNhapActivity.this,DanhSachMonHocActivity.class);
            startActivity(intentSetting);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Common.checkDangNhap(getApplicationContext())){
            Intent intentSetting = new Intent(DangNhapActivity.this,DanhSachMonHocActivity.class);
            startActivity(intentSetting);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Common.checkDangNhap(getApplicationContext())){
            Intent intentSetting = new Intent(DangNhapActivity.this,DanhSachMonHocActivity.class);
            startActivity(intentSetting);
        }
    }
}
