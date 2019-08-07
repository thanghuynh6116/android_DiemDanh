package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.model.MonHoc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<MonHoc> {

    private Context context;
    private int resource;
    private List<MonHoc> arrMonHocs;

    public CustomAdapter(Context context, int resource, ArrayList<MonHoc> arrMonHoc) {
        super(context, resource, arrMonHoc);
        this.context = context;
        this.resource = resource;
        this.arrMonHocs = arrMonHoc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageNews = (ImageView) convertView.findViewById(R.id.image_news);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.category);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MonHoc monhoc = arrMonHocs.get(position);
        try{
            //Picasso.with(getContext()).load(contact.getImgageNew()).error(R.drawable.ic_launcher_foreground).into(viewHolder.imageNews);
        }
        catch (Exception ex){

        }
        viewHolder.tvTitle.setText(monhoc.getTenmonhoc());
        viewHolder.tvCategory.setText(monhoc.getCahoc());
        return convertView;
    }

    public class ViewHolder {
        ImageView imageNews;
        TextView tvTitle,tvCategory;

    }
    public void refresh(List<MonHoc> newsList){
        this.arrMonHocs = newsList;
        notifyDataSetChanged();
    }
}
