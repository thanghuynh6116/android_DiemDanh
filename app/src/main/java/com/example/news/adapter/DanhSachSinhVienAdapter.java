package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.DanhSachSinhVienActivity;
import com.example.news.R;
import com.example.news.model.SinhVien;
import com.example.news.model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSinhVienAdapter extends ArrayAdapter<SinhVien> {

    private Context context;
    private int resource;
    private List<SinhVien> arrDanhSachSV;
    private int stt = 0;

    public DanhSachSinhVienAdapter(Context context, int resource, ArrayList<SinhVien> arrDanhSachSV) {
        super(context, resource, arrDanhSachSV);
        this.context = context;
        this.resource = resource;
        this.arrDanhSachSV = arrDanhSachSV;
    }

    @Override
    public View getView(final  int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tvCode = (TextView) convertView.findViewById(R.id.msv);
            viewHolder.tvBirthday = (TextView) convertView.findViewById(R.id.birthday);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try{
                        if(b){
                            arrDanhSachSV.get(position).setCheck(true);
                            DanhSachSinhVienActivity.arrSinhVien.get(position).setCheck(true);
                        }
                        else{
                            arrDanhSachSV.get(position).setCheck(false);
                            DanhSachSinhVienActivity.arrSinhVien.get(position).setCheck(false);
                        }
                    }
                    catch(Exception e){

                    }


                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SinhVien sinhvien = arrDanhSachSV.get(position);

        viewHolder.tvName.setText(sinhvien.getName());
        viewHolder.tvCode.setText(sinhvien.getCode());
        viewHolder.tvBirthday.setText(sinhvien.getBirthday());
        return convertView;
    }


    public class ViewHolder {
        TextView tvName,tvCode,tvBirthday;
        CheckBox checkBox;

    }
    public void refresh(List<SinhVien> newsList){
        this.arrDanhSachSV = newsList;
        notifyDataSetChanged();
    }
}

