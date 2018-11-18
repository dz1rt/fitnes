package com.example.fitnes.fitnes.service;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnes.fitnes.ListProgram.RecyclerItem;
import com.example.fitnes.fitnes.R;

import java.util.List;
 
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
 
    private List<RecyclerItem> listItems;
    private Context mContext;
 
    public RecyclerAdapter(List<RecyclerItem> listItems, Context mContext) {
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
 
        final RecyclerItem itemList = listItems.get(i);
        viewHolder.txtTitle.setText(itemList.getTitle());
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