package com.example.dualproject.Activities;

import android.content.ContentValues;
import android.content.Intent;
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
import com.example.dualproject.Validation.InputValidation;

public class AddingNoteActivity extends AppCompatActivity {
    EditText editTextTitle,editTextDescription;
    Button btnSave;
    Notes noteList;
    DatabaseHelper databaseHelpers;
    InputValidation inputValidation;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_note);
        editTextTitle=findViewById(R.id.edit_title);
        editTextDescription=findViewById(R.id.edit_description);
        btnSave=findViewById(R.id.btn_savenote);
        noteList=new Notes();
        databaseHelpers=new DatabaseHelper(this);
        listener();
    }
    public void listener()
    {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db=databaseHelpers.getWritableDatabase();
                String title=editTextTitle.getText().toString();
                String description=editTextDescription.getText().toString();
                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseHelper.COL_TITLE,title);
                contentValues.put(DatabaseHelper.COL_DESCRIPTION,description);
                long isinserted = db.insert(DatabaseHelper.TABLE_NOTE, null, contentValues);
                if (isinserted != -1) {
                    Toast.makeText(AddingNoteActivity.this, "Note Added Successfully", Toast.LENGTH_SHORT).show();

                    startActivities(new Intent[]{new Intent(getApplicationContext(), NoteHomeActivity.class)});
                }
                else
                    Toast.makeText(AddingNoteActivity.this, "Failed To Adding Note", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
