package com.example.fitnes.fitnes.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnes.fitnes.ListProgram.ProgramItem;
import com.example.fitnes.fitnes.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
 
    private List<ProgramItem> listItems;
    private Context mContext;
 
    public RecyclerAdapter(List<ProgramItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }
 
    @Override //Создаём элемент
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }
 
    @Override //Заполняем данными
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
 
        final ProgramItem itemList = listItems.get(i);
        viewHolder.txtTitle.setText(itemList.getProgramName());
        viewHolder.txtDescription.setText(itemList.getDescription());

    }
 
    @Override //определяем количество элементов
    public int getItemCount() {
        return listItems.size();
    }
    //
    public class ViewHolder extends RecyclerView.ViewHolder{
 
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtOptionDigit;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtOptionDigit = itemView.findViewById(R.id.txtOptionDigit);
        }
    }
}