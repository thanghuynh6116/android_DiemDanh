package com.example.news;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sinh_vien);
        lvSinhVien = (ListView) findViewById(R.id.lvsinhvien);
        btnDiemDanh = (Button) findViewById(R.id.btndiemdanh);
        btnQRCode = (Button) findViewById(R.id.btndiemdanhbangqrcode);
        setTitle("Danh Sách Sinh Viên");
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
                        diemdanh("malop", arrSinhVien.get(i).getCode());
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
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://cntttest.vanlanguni.edu.vn:18080/Cap21T4/AttendanceManagement/JSON/GetStudents?courseID=555",new Response.Listener<JSONArray>() {
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





    }


    public void diemdanh(String malop,String masv) {

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
            //Getting the passed result
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            AlertDialog alertDialog = new AlertDialog.Builder(DanhSachSinhVienActivity.this).create();

            alertDialog.setTitle("Thông Tin Sinh Viên");
            alertDialog.setMessage(result);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Hủy",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Điểm Danh",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            diemdanh("malop", "masv");
                            Toast.makeText(DanhSachSinhVienActivity.this, "Điểm danh thành công sinh viên " , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });


            alertDialog.show();

        }
    }
    }

