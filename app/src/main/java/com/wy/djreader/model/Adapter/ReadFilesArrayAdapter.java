package com.wy.djreader.model.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wy.djreader.model.entity.HaveReadFilesSerializable;
import com.wy.djreader.R;

import java.util.List;

public class ReadFilesArrayAdapter extends ArrayAdapter<HaveReadFilesSerializable> {
    private Context context;
    private List<HaveReadFilesSerializable> haveReadFilesList = null;
    private int resource;

    public ReadFilesArrayAdapter(@NonNull Context context, int resource,@NonNull List<HaveReadFilesSerializable> haveReadFilesList) {
        super(context, resource, haveReadFilesList);
        this.context = context;
        this.resource = resource;
        this.haveReadFilesList = haveReadFilesList;
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        //获取已读文件对象
        HaveReadFilesSerializable havereadFiles = haveReadFilesList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
            holder.file_name = convertView.findViewById(R.id.file_name);
            holder.time_size = convertView.findViewById(R.id.time_size);
            holder.file_path = convertView.findViewById(R.id.file_path);
            holder.file_thum = convertView.findViewById(R.id.file_thum);
            //为convertView设置标签
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
            //加载从数据库读取的文件信息
            holder.file_name.setText(havereadFiles.getFile_name());
            holder.time_size.setText(havereadFiles.getDisplay_time() +"  " + havereadFiles.getFile_size());
            holder.file_path.setText(havereadFiles.getFile_path());
            //将缩略图的byte数据转为bitmap对象
            byte[] thumbnails = havereadFiles.getFile_thum();
            if (thumbnails != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(thumbnails,0,thumbnails.length);
                holder.file_thum.setImageBitmap(bitmap);
            }
            //
            holder.file_name.setTextSize(20);
            holder.time_size.setTextColor(Color.parseColor("#CCCCCC"));
            holder.file_path.setTextColor(Color.parseColor("#CCCCCC"));
        return convertView;
    }

    class ViewHolder{
        TextView file_name;
        TextView time_size;
        TextView file_path;
        ImageView file_thum;
    }
}
