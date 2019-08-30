package com.example.news;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.news.adapter.CustomAdapter;
import com.example.news.adapter.DanhSachSinhVienAdapter;
import com.example.news.model.MonHoc;
import com.example.news.model.SinhVien;
import com.example.news.untils.Common;
import com.example.news.untils.VNCharacterUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhSachSinhVienActivity extends AppCompatActivity {
    public ListView lvSinhVien;
    Button btnDiemDanh, btnQRCode;
    DanhSachSinhVienAdapter danhSachSinhVienAdapter;
    public static ArrayList<SinhVien> arrSinhVien = new ArrayList<>();
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    int malop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sinh_vien);
        lvSinhVien = (ListView) findViewById(R.id.lvsinhvien);
        btnDiemDanh = (Button) findViewById(R.id.btndiemdanh);
        btnQRCode = (Button) findViewById(R.id.btndiemdanhbangqrcode);
        setTitle("Danh Sách Sinh Viên");
        Intent intent = getIntent();
        malop = intent.getIntExtra("malop",0);

        getSinhVien();
        danhSachSinhVienAdapter = new DanhSachSinhVienAdapter(getApplicationContext(),R.layout.row_ds_sinhvien,arrSinhVien);
        lvSinhVien.setAdapter(danhSachSinhVienAdapter);
        danhSachSinhVienAdapter.notifyDataSetChanged();
        lvSinhVien.setItemsCanFocus(true);

        btnDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = 0;
                for(int i = 0; i< arrSinhVien.size(); i++){
                    if(arrSinhVien.get(i).isCheck())
                    {
                        diemdanh(malop, arrSinhVien.get(i).getCode());
                        dem++;

                    }

                }
                Toast.makeText(DanhSachSinhVienActivity.this, "Điểm danh thành công "+ dem + " sinh viên", Toast.LENGTH_SHORT).show();
            }
        });
//điểm danh bằng qrcode
        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachSinhVienActivity.this, QrCodeActivity.class);
                startActivityForResult( intent,101);
            }
        });

    }

    public void getSinhVien() {
        arrSinhVien = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://cntttest.vanlanguni.edu.vn:18080/Cap21T4/AttendanceManagement/JSON/GetStudents?courseID="+malop,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0 ;i<response.length();i++){
                        try {
                            JSONObject jsonArray = response.getJSONObject(i);
                            SinhVien sinhVien = new SinhVien(jsonArray.getString("FirstName") +" "+jsonArray.getString("LastName") , jsonArray.getString("Birthday"), "MSV: "+jsonArray.getString("Code"));
                            arrSinhVien.add(sinhVien);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    danhSachSinhVienAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);



        //set menu
        dl = (DrawerLayout)findViewById(R.id.activity_dssinhvien);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.dangxuat:
                        Toast.makeText(getApplicationContext(), "Đăng Xuất Thành Công", Toast.LENGTH_SHORT).show();
                        Common.dangXuat(getApplicationContext());
                        Intent intentSetting = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(intentSetting);
                        break;
                    case R.id.infor:
                        Toast.makeText(getApplicationContext(), "Phần mềm được thực hiện bởi nhóm sinh viên ",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });



    }


    public void diemdanh(int malop,String masv) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String urlAPI = "http://cntttest.vanlanguni.edu.vn:18080/Cap21T4/AttendanceManagement/JSON/AddAttendance?code=6128&courseID="+malop+"&sessionID=1&attendance=[\""+masv+"\"]";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlAPI,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    // điểm danh bằng QR code

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(DanhSachSinhVienActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;


            final String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            // check xem data có đúng hay không, có phải là đang quyest thẻ sinh viên hay không
            if(!Common.isValidQRCODE(result.toString())){
                AlertDialog alertDialog = new AlertDialog.Builder(DanhSachSinhVienActivity.this).create();
                alertDialog.setTitle("Thông Tin Không Chính Xác");
                alertDialog.setMessage("Vui lòng quét đúng mã QR in trên thẻ sinh viên của trường.!");


                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            }

            // check xem sinh vien co trong lop hoc khong
            boolean isCheckSV = false;
            for (SinhVien item : arrSinhVien) {
                if(Common.getMaSV(result).equals(item.getCode())){
                    isCheckSV = true;
                }
            }
            // nếu ischeck = false nghĩa là không trùng vs bất kì msv nào trong lớp đưa ra thông báo
            if(!isCheckSV){
                AlertDialog alertDialog = new AlertDialog.Builder(DanhSachSinhVienActivity.this).create();
                alertDialog.setTitle("Sinh Viên Không Tồn Tại");
                alertDialog.setMessage("Sinh viên này không thuộc lớp học này vui lòng kiểm tra lại.!");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            }
            //thông tin điểm danh
            AlertDialog alertDialog = new AlertDialog.Builder(DanhSachSinhVienActivity.this).create();
            alertDialog.setTitle("Thông Tin Sinh Viên");
            alertDialog.setMessage(result);
            Log.d("thang", "onActivityResult: "+ result);

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Hủy",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Điểm Danh",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            diemdanh(malop, "masv");
                            Toast.makeText(DanhSachSinhVienActivity.this, "Điểm danh thành công" , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });


            alertDialog.show();

        }
    }
    }

