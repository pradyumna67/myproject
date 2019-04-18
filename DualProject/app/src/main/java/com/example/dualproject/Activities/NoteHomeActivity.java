package com.example.dualproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.Notes;
import com.example.dualproject.R;
import com.example.dualproject.Utils.Constants;
import com.example.dualproject.Utils.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteHomeActivity extends AppCompatActivity  implements  View.OnClickListener{
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelpers;
    private RecyclerView.LayoutManager layoutManager ;
    private List<Notes> notesList;
    private SQLiteDatabase db;
    TextView tvIsEmpty;
    private RecyclerView.Adapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_home);
        tvIsEmpty=findViewById(R.id.tv_empty);
        recyclerView=findViewById(R.id.recyclerView);

       /* if(==null){
            tvIsEmpty.setVisibility(View.VISIBLE);
        }
        else {
            tvIsEmpty.setVisibility(View.GONE);
        }*/

        databaseHelpers=new DatabaseHelper(this);
        db=databaseHelpers.getWritableDatabase();
        floatingActionButton=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent[]{new Intent(getApplicationContext(),AddingNoteActivity.class)});
            }
        });
        notesList = new ArrayList<>();
        Cursor c1=db.query(DatabaseHelper.TABLE_NOTE,null,null,null,null,null,null);
        if(c1 != null && c1.getCount() !=0)
        {
            while (c1.moveToNext())
            {
               Notes noteitem=new Notes();
               noteitem.setId(c1.getInt(c1.getColumnIndex(DatabaseHelper.COL_ID)));
               noteitem.setTitle(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_TITLE)));
               noteitem.setDescription(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
               notesList.add(noteitem);
            }
        }
        layoutManager=new LinearLayoutManager(this);
        noteAdapter=new NoteAdapter(notesList);
        ((NoteAdapter) noteAdapter).setListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view:
               int noteId  = (int) v.getTag();
                Intent intent=new Intent(NoteHomeActivity.this, UpdateDeleteActivity.class);
                intent.putExtra(Constants.NOTE_ID,noteId);
                startActivityForResult(intent,Constants.NOTE_DETAILS_REQ_ID);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == Constants.NOTE_DETAILS_REQ_ID){
            //update the list item and list
            notesList = new ArrayList<>();
            Cursor c1=db.query(DatabaseHelper.TABLE_NOTE,null,null,null,null,null,null);
            if(c1 != null && c1.getCount() !=0)
            {
                while (c1.moveToNext())
                {
                    Notes noteitem=new Notes();
                    noteitem.setId(c1.getInt(c1.getColumnIndex(DatabaseHelper.COL_ID)));
                    noteitem.setTitle(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_TITLE)));
                    noteitem.setDescription(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
                    notesList.add(noteitem);
                }
            }


        }
    }

}
