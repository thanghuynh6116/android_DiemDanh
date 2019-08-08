package com.example.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DangNhapActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        email = (EditText) findViewById(R.id.etLoginUsername);
        pass = (EditText) findViewById(R.id.etLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("admin") && pass.getText().toString().equals("123456")){
                    Intent intentSetting = new Intent(DangNhapActivity.this,MainActivity.class);
                    startActivity(intentSetting);
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(DangNhapActivity.this).create();
                    alertDialog.setTitle("Lỗi");
                    alertDialog.setMessage("Tài Khoản Hoặc Mật Khẩu Bạn Nhập Không Chính Xác");
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
}
