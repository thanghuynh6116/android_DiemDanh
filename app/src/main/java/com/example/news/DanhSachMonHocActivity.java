package com.example.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.adapter.CustomAdapter;
import com.example.news.model.HocKy;
import com.example.news.model.MonHoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMonHocActivity extends AppCompatActivity {
    private ListView lvMonHoc;
    CustomAdapter customAdaper;
    ArrayList<MonHoc> arrMonhoc = new ArrayList<>();
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
                Intent intent = new Intent(DanhSachMonHocActivity.this,DanhSachSinhVienActivity.class);
                intent.putExtra("mamon",arrMonhoc.get(i).getId());
                startActivity(intent);
            }
        });

    }

    public void getMonHoc() {
        arrMonhoc = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://cntttest.vanlanguni.edu.vn:18080/Cap21T4/AttendanceManagement/JSON/GetCourses?email=phamngocduy@vanlanguni.edu.vn&hk=182",new Response.Listener<JSONArray>() {
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
