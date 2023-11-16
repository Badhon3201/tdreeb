package com.ryx.tdreeb.ui.dialogs.filterbottomsheet;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.filteradapter.FilterLeftSideAdapter;
import com.ryx.tdreeb.adapters.filteradapter.FilterRightSideAdapter;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    List<String> list;
    private OnClickBottomItem mListener;

    private RecyclerView rv,rvTwo;
    private AppCompatImageView btnClose;
    private View rootView;
    private FilterRightSideAdapter mFilterRightSideAdapter;
    private FilterLeftSideAdapter mFilterLeftSideAdapter;

    public FilterBottomSheetFragment() {
        // Required empty public constructor
    }

    public static FilterBottomSheetFragment newInstance() {
        FilterBottomSheetFragment fragment = new FilterBottomSheetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false);
        setUpView();
        return rootView;
    }

    private void setUpView() {
        btnClose = rootView.findViewById(R.id.btn_close);
        rv = rootView.findViewById(R.id.rv_side);
        rvTwo = rootView.findViewById(R.id.rv_side_right);
        list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        mFilterLeftSideAdapter = new FilterLeftSideAdapter(getContext(), list);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new EqualSpacingItemDecoration(5));
        rv.setAdapter(mFilterLeftSideAdapter);
        mFilterLeftSideAdapter.setListener(str -> {

        });

        mFilterRightSideAdapter = new FilterRightSideAdapter(getContext(), list);
        rvTwo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvTwo.addItemDecoration(new EqualSpacingItemDecoration(5));
        rvTwo.setAdapter(mFilterRightSideAdapter);
        mFilterRightSideAdapter.setListener(str -> {

        });

        event();
    }

    private void event() {
        btnClose.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void setData(List<String> list) {
        this.list = list;
    }

    public void setListener(OnClickBottomItem listener) {
        this.mListener = listener;
    }

    public interface OnClickBottomItem {
        void onClickItem(String str);
    }
}