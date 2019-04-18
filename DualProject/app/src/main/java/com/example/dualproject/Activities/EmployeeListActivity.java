package com.example.dualproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.Student;
import com.example.dualproject.R;
import com.example.dualproject.Utils.Constants;
import com.example.dualproject.Utils.StudentAdapter;

import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity {
    StudentAdapter adapter;
    DatabaseHelper databaseHelper;
    ArrayList<Student> list;
    ListView listView;
    FloatingActionButton floatEmployee;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        listView=findViewById(R.id.data_loader_list);
        databaseHelper=new DatabaseHelper(this);
        floatEmployee=findViewById(R.id.floatingActionButtonemployee);
        list=new ArrayList<>();
        adapter=new StudentAdapter(this,R.layout.student_details,list);
        listView.setAdapter(adapter);
        db=databaseHelper.getReadableDatabase();
        Cursor c1=db.query(DatabaseHelper.TABLE_STUDENT,null,null,null,null,null,null);
        if(c1 !=null && c1.getCount()!=0){
            while (c1.moveToNext()){
                Student studentList=new Student();
                studentList.setId(c1.getInt(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_ID)));
                studentList.setFirst(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_FIRST_NAME)));
                studentList.setSecond(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_LAST_NAME)));
                studentList.setEmail(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_EMAIL)));
                studentList.setPhone(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_PHONE)));
                studentList.setBranch(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_DEPARTMENT)));
                studentList.setExperience(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_EXPERIENCE)));
                studentList.setImage(c1.getBlob(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_IMAGE)));
                list.add(studentList);
            }
        }
        adapter.notifyDataSetChanged();
      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
              CharSequence[] item={"Delete"};
              AlertDialog.Builder dialog=new AlertDialog.Builder(EmployeeListActivity.this);
             // dialog.setTitle("Choose a action");
              dialog.setItems(item, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int item) {

                          final int id=list.get(position).getId();
                          final android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(EmployeeListActivity.this);
                          builder.setMessage("Are You Sure To delete");
                          builder.setTitle("Delete!");
                          builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.dismiss();
                                  db.delete(DatabaseHelper.TABLE_STUDENT, DatabaseHelper.COL_EMPLOYEE_ID +" = "+ id,null);
                                  Toast.makeText(EmployeeListActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                                  list.remove(position);
                                  adapter.notifyDataSetChanged();
                                  dialog.dismiss();
                              }
                          });
                          builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.dismiss();
                              }
                          });
                          builder.show();


                  }
              });
              dialog.show();
              return true;
          }
      });
      floatEmployee.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(EmployeeListActivity.this, EmployeeFormActivity.class);
              startActivity(intent);

          }
      });
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent=new Intent(EmployeeListActivity.this,UpdateActivity.class);
              intent.putExtra(Constants.STUDENT_ID,list.get(position).getId());
              startActivityForResult(intent,2);
          }
      });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode ==2){
           //update the list
            if(data != null){
                Student updatedStudent = (Student) data.getSerializableExtra(Constants.studentObject);
                if(updatedStudent != null) {
                    //data.getSerializableExtra()
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId() == updatedStudent.getId()) {
                         list.set(i,updatedStudent);
                         break;
                        }
                    }
                }
                //update list
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
