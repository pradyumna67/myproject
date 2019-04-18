package com.example.dualproject.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dualproject.Model.User;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public  class UserAdapter extends BaseAdapter{
    private Context context;
    private Layout layout;
    private ArrayList<User> list;
    public UserAdapter(Context context, Layout layout, ArrayList<User> list){
        this.context=context;
        this.layout=layout;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        CircleImageView imageView;
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=new ViewHolder();
        if(row == null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService((context.LAYOUT_INFLATER_SERVICE));
            row=  inflater.inflate((XmlPullParser) layout,null);
            row.setTag(holder);
        }
        else{
            holder=(ViewHolder)row.getTag();
        }
        User user=list.get(position);
        byte[] userimage=user.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(userimage,0,userimage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;
    }
}
