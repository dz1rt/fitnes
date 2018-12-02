package com.example.fitnes.fitnes.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.fitnes.fitnes.ListProgram.ProgramItem;
import com.example.fitnes.fitnes.ListProgram.ProgramItem;
import com.example.fitnes.fitnes.R;
import com.example.fitnes.fitnes.testProgram.TestProgramItem;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<?> listItems;
    private Context mContext;
    private Boolean flag;
    private final ClickListener listener;

    public RecyclerAdapter(List<?> listItems, Context mContext, Boolean flag, ClickListener listener) {
        this.listItems = listItems;
        this.mContext = mContext;
        this.flag = flag;
        this.listener = listener;
    }

    @Override //Создаём элемент
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        if (flag){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        }else{
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.training_item, viewGroup, false);
        }
        return new ViewHolder(v,listener);
    }

    @Override //Заполняем данными
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        switch (listItems.get(i).getClass().getName()){
            case "com.example.fitnes.fitnes.testProgram.TestProgramItem":
                final TestProgramItem itemTestList = (TestProgramItem) listItems.get(i);
                viewHolder.testTitle.setText(itemTestList.getTitle());
                viewHolder.testDescription.setText(itemTestList.getDescription());
                viewHolder.testTime.setText(itemTestList.getTime());
                break;

            case "com.example.fitnes.fitnes.ListProgram.ProgramItem":
                final ProgramItem itemList = (ProgramItem) listItems.get(i);
                viewHolder.txtTitle.setText(itemList.getProgramName());
                viewHolder.txtDescription.setText(itemList.getDescription());
                break;
        }

    }

    @Override //определяем количество элементов
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private WeakReference<ClickListener> listenerRef;

        private TextView txtTitle;
        private TextView txtDescription;

        private TextView testTitle;
        private TextView testTime;
        private TextView testDescription;
        private Button testStarterTimeBtn;

        public ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);

            testTitle = itemView.findViewById(R.id.testTitle);
            testTime = itemView.findViewById(R.id.testTimer);
            testDescription = itemView.findViewById(R.id.testDescription);
            testStarterTimeBtn= itemView.findViewById(R.id.testStarterTimeBtn);

            testStarterTimeBtn.setOnClickListener(this);
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == testStarterTimeBtn.getId()) {
                listenerRef.get().onPositionClicked(getAdapterPosition(),itemView);
            }
        }
    }
}