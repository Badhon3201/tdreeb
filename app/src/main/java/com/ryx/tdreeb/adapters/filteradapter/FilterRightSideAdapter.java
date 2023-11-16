package com.ryx.tdreeb.adapters.filteradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.ui.dialogs.filterbottomsheet.FilterBottomSheetFragment;

import java.util.List;

public class FilterRightSideAdapter extends RecyclerView.Adapter<FilterRightSideAdapter.ViewHolder> {

    Context context;
    List<String> list;
    private FilterBottomSheetFragment.OnClickBottomItem mListener;

    public FilterRightSideAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(FilterBottomSheetFragment.OnClickBottomItem listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter_right, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.tvName.setText(vehicalBrandModels.get(position).getModelName());
//        holder.itemView.setOnClickListener(v -> {
//            if (mListener != null) {
//                mListener.onClickItem(vehicalBrandModels.get(position));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_search_item);
        }
    }
}
