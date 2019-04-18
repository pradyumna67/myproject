package com.example.dualproject.Activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.Notes;
import com.example.dualproject.R;
import com.example.dualproject.Utils.Constants;
import com.example.dualproject.Utils.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpdateDeleteActivity extends AppCompatActivity {
     DatabaseHelper databaseHelpers;
     EditText upTitle,upDescription;
     Button btnUpdate,btnDelete;
     SQLiteDatabase db;
     List<Notes> notesList;
     String title,description;
     NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        databaseHelpers=new DatabaseHelper(this);
        db=databaseHelpers.getWritableDatabase();
        upTitle=findViewById(R.id.up_edit_title);
        upDescription=findViewById(R.id.up_edit_description);
        btnUpdate=findViewById(R.id.btn_update);
        btnDelete=findViewById(R.id.btn_delete);
        final int rowid=getIntent().getIntExtra(Constants.NOTE_ID,-1);
        Cursor c1=db.query(DatabaseHelper.TABLE_NOTE,null,DatabaseHelper.COL_ID+" = "+ rowid,null,null,null,null);
        notesList=new ArrayList<Notes>();
        if(c1 !=null && c1.getCount() !=0)
        {
            while(c1.moveToNext())
            {
                upTitle.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_TITLE)));
                upDescription.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
            }
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             title=upTitle.getText().toString();
             description=upDescription.getText().toString();
             ContentValues values=new ContentValues();
             values.put(DatabaseHelper.COL_TITLE,title);
             values.put(DatabaseHelper.COL_DESCRIPTION,description);
             int updated=  db.update(DatabaseHelper.TABLE_NOTE,values,DatabaseHelper.COL_ID+" = "+ rowid,null);
             if(updated != -1)
             {
                Intent intent = new Intent(UpdateDeleteActivity.this,NoteHomeActivity.class);
                startActivity(intent);
                 Toast.makeText(UpdateDeleteActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

             }
             else
             {
                 Toast.makeText(UpdateDeleteActivity.this,"Failed To Update",Toast.LENGTH_SHORT).show();
             }
            }
        });
         btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final AlertDialog.Builder builder=new AlertDialog.Builder(UpdateDeleteActivity.this);
                 builder.setMessage("Are You Sure To delete");
                 builder.setTitle("Delete!");
                 builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                         db.delete(DatabaseHelper.TABLE_NOTE, DatabaseHelper.COL_ID +"="+ rowid,null);
                         Intent intent=new Intent(UpdateDeleteActivity.this,NoteHomeActivity.class);
                         startActivity(intent);
                     }
                 });
                 builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
                 builder.create().show();
             }
         });
    }
}
