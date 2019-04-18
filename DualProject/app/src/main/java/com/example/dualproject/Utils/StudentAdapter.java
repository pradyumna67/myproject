package com.example.dualproject.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dualproject.Model.Student;
import com.example.dualproject.R;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Student> list;
    public StudentAdapter(Context context, int layout, ArrayList<Student> list){
        this.context=context;
        this.layout=layout;
        this.list=list;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
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

    private class ViewHolder{
        TextView firstName,lastName,department;
        ImageView studentImage;
        View view;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row=view;
        ViewHolder holder=new ViewHolder();
       if(row == null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.firstName=row.findViewById(R.id.student_first_name);
            holder.lastName=row.findViewById(R.id.student_last_name);
            holder.studentImage=row.findViewById(R.id.student_image);
            holder.department=row.findViewById(R.id.tv_show_department);
            holder.view=row.findViewById(R.id.card_view);
            //holder.view.setTag(list.get(position).getId());
            row.setTag(holder);
        }
        else{
            holder=(ViewHolder) row.getTag();
        }
        Student student=list.get(position);
        holder.firstName.setText(student.getFirst());
        holder.lastName.setText(student.getSecond());
        holder.department.setText(student.getBranch());
        byte[] studentByte=student.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(studentByte,0,studentByte.length);
        holder.studentImage.setImageBitmap(bitmap);
        return row;
    }
}
