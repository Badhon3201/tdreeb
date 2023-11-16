package com.ryx.tdreeb.ui.dialogs.videoplayer;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.potyvideo.library.globalEnums.EnumResizeMode;
import com.potyvideo.library.globalInterfaces.AndExoPlayerListener;
import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.databinding.FragmentVideoPlayerBinding;
import com.ryx.tdreeb.di.component.DaggerDialogComponent;
import com.ryx.tdreeb.di.component.DialogComponent;
import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.ui.activites.main.MainActivity;
import com.ryx.tdreeb.ui.base.BaseDialog;
import com.ryx.tdreeb.ui.dialogs.mysubject.MySubjectAddViewModel;

import javax.inject.Inject;

public class VideoPlayerFragment extends BaseDialog implements VideoPlayerNavigator, AndExoPlayerListener {

    FragmentVideoPlayerBinding mFragmentVideoPlayerBinding;
    private static final String TAG = "VideoPlayerFragment";
    private String videoUrl = "";

    private AppCompatImageButton btnFull;
    boolean flag = false;

    @Inject
    VideoPlayerViewModel mVideoPlayerViewModel;

    public VideoPlayerFragment() {
    }

    public static VideoPlayerFragment newInstance() {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
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
        mFragmentVideoPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false);
        View view = mFragmentVideoPlayerBinding.getRoot();
        performDependencyInjection(getBuildComponent());
        mFragmentVideoPlayerBinding.setViewModel(mVideoPlayerViewModel);
        mVideoPlayerViewModel.setNavigator(this);
        setUp();
        setSizeFullWrap();
        return view;
    }



    private void setUp() {

        mFragmentVideoPlayerBinding.andExoPlayerView.setResizeMode(EnumResizeMode.FIT);
        mFragmentVideoPlayerBinding.andExoPlayerView.setAndExoPlayerListener(this);
        btnFull =  mFragmentVideoPlayerBinding.andExoPlayerView.findViewById(R.id.exo_fullscreen);
        btnFull.setImageDrawable(getBaseActivity().getDrawable(R.drawable.ic_fullscreen_black_24dp));
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        params.width = params.MATCH_PARENT;
        params.height = (int) ( 220 * getBaseActivity().getResources().getDisplayMetrics().density);
        mFragmentVideoPlayerBinding.andExoPlayerView.setLayoutParams(params);

        btnFull.setOnClickListener(v -> {
           // Toast.makeText(getBaseActivity(), "Click", Toast.LENGTH_SHORT).show();
            if (flag) {
                btnFull.setImageDrawable(getBaseActivity().getDrawable(R.drawable.ic_fullscreen_black_24dp));
                getBaseActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                params.width = params.MATCH_PARENT;
                params.height = (int) ( 220 * getBaseActivity().getResources().getDisplayMetrics().density);
                mFragmentVideoPlayerBinding.andExoPlayerView.setLayoutParams(params);
                setSizeFullWrap();
            } else {
                btnFull.setImageDrawable(getBaseActivity().getDrawable(R.drawable.ic_fullscreen_exit_black_24dp));
                getBaseActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                mFragmentVideoPlayerBinding.andExoPlayerView.setLayoutParams(params);
                setSizeFull();
            }
            flag = !flag;
        });

        mFragmentVideoPlayerBinding.andExoPlayerView.setSource(videoUrl);

    }

    public void setData(String videoUrl){
        this.videoUrl = videoUrl;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    private DialogComponent getBuildComponent() {
        return DaggerDialogComponent.builder()
                .appComponent(((MvvmApp) (getContext().getApplicationContext())).appComponent)
                .dialogModule(new DialogModule(this))
                .build();
    }

    private void performDependencyInjection(DialogComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentVideoPlayerBinding.andExoPlayerView.releasePlayer();
    }

    @Override
    public void onExoPlayerStart() {

    }

    @Override
    public void onExoPlayerFinished() {

    }

    @Override
    public void onExoPlayerLoading() {

    }

    @Override
    public void onExoPlayerError(@Nullable String errorMessage) {

    }

    @Override
    public void onExoBuffering() {

    }

    @Override
    public void onExoEnded() {

    }

    @Override
    public void onExoIdle() {

    }

    @Override
    public void onExoReady() {

    }
}