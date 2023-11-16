package com.ryx.tdreeb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTimeTwoAdapter extends RecyclerView.Adapter<ScheduleTimeTwoAdapter.ViewHolder> {

    Context context;
    List<String> timeList;

    private ArrayList<Integer> selectCheck;
    private ItemClick itemClick;

    public interface ItemClick {
        void clickOn(String time);
    }

    public ScheduleTimeTwoAdapter(Context context, List<String> timeList) {
        this.context = context;
        this.timeList = timeList;
    }

    public void setEvent(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setData(List<String> timeList) {
        selectCheck = new ArrayList<>();
        selectCheck.add(1);
        for (int i = 1; i < timeList.size(); i++) {
            this.selectCheck.add(0);
        }
        this.timeList = timeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule_time_two, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvTime.setText(timeList.get(position));
        if (selectCheck.get(position) == 1) {
            holder.backCard.setBackgroundColor(context.getResources().getColor(R.color.app_color));
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.white));
            itemClick.clickOn(timeList.get(position));
        } else {
            holder.backCard.setBackground(context.getResources().getDrawable(R.drawable.strock_round_orange));
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.app_color));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < selectCheck.size(); k++) {
                    if (k == position) {
                        selectCheck.set(k, 1);
                    } else {
                        selectCheck.set(k, 0);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        ConstraintLayout backCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            backCard = itemView.findViewById(R.id.cl_time);
        }
    }
}
