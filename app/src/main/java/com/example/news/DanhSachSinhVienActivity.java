package com.example.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.adapter.CustomAdapter;
import com.example.news.adapter.DanhSachSinhVienAdapter;
import com.example.news.model.MonHoc;
import com.example.news.model.SinhVien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhSachSinhVienActivity extends AppCompatActivity {
    private ListView lvSinhVien;
    DanhSachSinhVienAdapter danhSachSinhVienAdapter;
    ArrayList<SinhVien> arrSinhVien = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sinh_vien);
        lvSinhVien = (ListView) findViewById(R.id.lvmonhoc);
        setTitle("Danh Sách Sinh Viên");
        getSinhVien();
        danhSachSinhVienAdapter = new DanhSachSinhVienAdapter(getApplicationContext(),R.layout.row_ds_sinhvien,arrSinhVien);
        lvSinhVien.setAdapter(danhSachSinhVienAdapter);
        danhSachSinhVienAdapter.notifyDataSetChanged();
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
    }

