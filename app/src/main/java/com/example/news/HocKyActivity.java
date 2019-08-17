package com.example.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.news.adapter.CustomAdapter;
import com.example.news.model.HocKy;
import com.example.news.model.MonHoc;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HocKyActivity extends AppCompatActivity {

    private ListView lvMonHoc;
    String tenHK;
    int maHK;
    private List<HocKy> lsHocKy;
    CustomAdapter customAdaper;
    ArrayList<MonHoc> arrMonhoc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_ky);
        lvMonHoc = (ListView) findViewById(R.id.lvmonhoc);
        setTitle("Danh Sách Môn Học");
        getMonHoc();
        customAdaper = new CustomAdapter(getApplicationContext(),R.layout.row_listview_monhoc,arrMonhoc);
        lvMonHoc.setAdapter(customAdaper);
        customAdaper.notifyDataSetChanged();



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
                            //String a = jsonArray.getString("Name");
                            // a = jsonArray.getString("Time");
                            // a = jsonArray.getString("Room");
                           /// MonHoc monHoc = new MonHoc(jsonArray.getString("Name"), "Thời Gian: "+jsonArray.getString("Time") + " Phòng: "+ jsonArray.getString("Room"));
                           // arrMonhoc.add(monHoc);


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
