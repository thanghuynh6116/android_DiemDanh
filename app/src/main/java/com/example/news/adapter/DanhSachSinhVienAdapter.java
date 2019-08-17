package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.model.SinhVien;
import com.example.news.model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class DanhSachSinhVienAdapter extends ArrayAdapter<SinhVien> {

    private Context context;
    private int resource;
    private List<SinhVien> arrDanhSachSV;

    public DanhSachSinhVienAdapter(Context context, int resource, ArrayList<SinhVien> arrDanhSachSV) {
        super(context, resource, arrDanhSachSV);
        this.context = context;
        this.resource = resource;
        this.arrDanhSachSV = arrDanhSachSV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tvCode = (TextView) convertView.findViewById(R.id.msv);
            viewHolder.tvBirthday = (TextView) convertView.findViewById(R.id.birthday);

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

    }
    public void refresh(List<SinhVien> newsList){
        this.arrDanhSachSV = newsList;
        notifyDataSetChanged();
    }
}

