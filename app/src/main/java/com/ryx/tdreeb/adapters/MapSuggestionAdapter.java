package com.ryx.tdreeb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionResponse;
import com.ryx.tdreeb.ui.activites.map.addresssearch.MapCallBack;

public class MapSuggestionAdapter extends RecyclerView.Adapter<MapSuggestionAdapter.MapSuggestionViewHolder> {
    private MapCallBack recyclerViewCallBack;
    Context context;
    private MapSuggestionResponse mapSuggestionResponses;

    public MapSuggestionAdapter(Context context, MapSuggestionResponse mapSuggestionResponses) {
        this.context = context;
        this.mapSuggestionResponses = mapSuggestionResponses;
        recyclerViewCallBack = (MapCallBack) context;
    }

    @NonNull
    @Override
    public MapSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_map_dropdown, parent, false);
        return new MapSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapSuggestionViewHolder holder, final int position) {
        if(getStatus().equals("OK")) {
            holder.tv_suggestion.setText(getDescription(position));
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewCallBack.onMethodCallback(getDescription(position),getPlaceID(position));
                }
            });
        }
    }

    public String getDescription(int pos){
        return mapSuggestionResponses.getPredictions().get(pos).getDescription();
    }
    public String getPlaceID(int pos){
        return mapSuggestionResponses.getPredictions().get(pos).getPlace_id();
    }
    public String getStatus(){
        return mapSuggestionResponses.getStatus();
    }

    @Override
    public int getItemCount() {
        return   mapSuggestionResponses.getPredictions() == null ? 0 :mapSuggestionResponses.getPredictions().size();
    }


    public class MapSuggestionViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_suggestion;
        private LinearLayout linearLayout;
        public MapSuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_suggestion = itemView.findViewById(R.id.tv_suggestion);
            linearLayout = itemView.findViewById(R.id.linearlayout_place);
        }
    }
}
