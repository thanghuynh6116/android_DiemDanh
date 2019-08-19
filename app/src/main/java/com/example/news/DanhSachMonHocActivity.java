package com.example.news;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.example.news.adapter.CustomAdapter;
import com.example.news.model.HocKy;
import com.example.news.model.MonHoc;
import com.example.news.untils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMonHocActivity extends AppCompatActivity {
    private ListView lvMonHoc;
    CustomAdapter customAdaper;
    ArrayList<MonHoc> arrMonhoc = new ArrayList<>();

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mon_hoc);
        lvMonHoc = (ListView) findViewById(R.id.lvmonhoc);
        setTitle("Danh Sách Môn Học");



        getMonHoc();
        customAdaper = new CustomAdapter(getApplicationContext(),R.layout.row_listview_monhoc,arrMonhoc);
        lvMonHoc.setAdapter(customAdaper);
        customAdaper.notifyDataSetChanged();

        // set on click chuyển sang danh sách sinh viên
        lvMonHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    arrMonhoc.get(i).setId(555);
                }
                Intent intent = new Intent(DanhSachMonHocActivity.this,DanhSachSinhVienActivity.class);
                intent.putExtra("malop",arrMonhoc.get(i).getId());
                startActivity(intent);
            }
        });

        //set menu
        dl = (DrawerLayout)findViewById(R.id.activity_dsmonhoc);
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

    public void getMonHoc() {
        Log.d("", "getMonHoc: "+Common.getEmail1(getApplicationContext()));
        arrMonhoc = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://cntttest.vanlanguni.edu.vn:18080/Cap21T4/AttendanceManagement/JSON/GetCourses?email="+ Common.getEmail1(getApplicationContext()) +"&hk=182",new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0 ;i<response.length();i++){
                        try {
                            JSONObject jsonArray = response.getJSONObject(i);
                            MonHoc monHoc = new MonHoc(jsonArray.getInt("id"), jsonArray.getString("Name"), "Thời Gian: "+jsonArray.getString("Time") + " Phòng: "+ jsonArray.getString("Room"));
                            arrMonhoc.add(monHoc);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    customAdaper.notifyDataSetChanged();
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
