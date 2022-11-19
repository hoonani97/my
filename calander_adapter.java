package com.example.mcheck;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class calander_adapter extends RecyclerView.Adapter<calander_adapter.CalanderviewHolder>{

    ArrayList<String> daylist;

    OnItemListener onItemListener;

    public calander_adapter(ArrayList<String> daylist, schedule_prof schedule_prof) {
        this.daylist = daylist;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalanderviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calander_cell, parent, false);

        return new CalanderviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalanderviewHolder holder, int position) {
        //날짜 변수에 담기
        String day = daylist.get(position);

        holder.daytext.setText(day);

        //토일 색상 지정
        if((position + 1) % 7 == 0){//토욜
            holder.daytext.setTextColor(Color.BLUE);
        }else if( position == 0 || position % 7 == 0){//일욜
            holder.daytext.setTextColor(Color.RED);
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemListener.onItemClick(day);//인터페이스를 통해 날짜 넘김
            }
        });
    }

    @Override
    public int getItemCount() {
        return daylist.size();
    }

    class CalanderviewHolder extends RecyclerView.ViewHolder{

        TextView daytext;

        public CalanderviewHolder(@NonNull View itemView) {
            super(itemView);

            daytext = itemView.findViewById(R.id.daytext);
        }
    }
}
