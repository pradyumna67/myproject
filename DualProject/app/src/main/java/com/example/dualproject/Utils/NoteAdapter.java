package com.example.dualproject.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.Notes;
import com.example.dualproject.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Notes> noteList;
    Context context;
    DatabaseHelper databaseHelpers;
    SQLiteDatabase db;
    private View.OnClickListener listener;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public NoteAdapter(List<Notes> noteList) {
        this.noteList = noteList;
    }

    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.recyclerlist,parent,false);
        NoteViewHolder viewHolder=new NoteViewHolder(itemView);
        return viewHolder;
    }
    public void onBindViewHolder(@NonNull final NoteAdapter.NoteViewHolder holder, final int position) {
        Notes note=noteList.get(position);
        holder.textTitle.setText(note.getTitle());
        holder.textDescription.setText(note.getDescription());
        holder.view.setTag(noteList.get(position).getId());
    }
    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textDescription;
        View view ;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.cardview_text_title);
            textDescription=itemView.findViewById(R.id.cardview_text_description);
            view = itemView.findViewById(R.id.card_view);
            view.setOnClickListener(listener);
        }

    }
}