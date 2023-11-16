package com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.sessiondetails;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionLogsModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionStatusUpdateResponse;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.databinding.TrainerSessionDetailsFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.utils.BindingUtils;
import com.ryx.tdreeb.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class TrainerSessionDetailsFragment extends BaseFragment<TrainerSessionDetailsFragmentBinding, TrainerSessionDetailsViewModel> implements TrainerSessionDetailsNavigator {

    TrainerSessionDetailsFragmentBinding mTrainerSessionDetailsFragmentBinding;

    private NavController navController;
    private String timeSlot, dateStr;
    private boolean isUpdate = false;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    @Inject
    DataManager dataManager;

    public TrainerSessionDetailsFragment() {
    }

    private SessionModel mSessionModel;

    public static TrainerSessionDetailsFragment newInstance() {
        TrainerSessionDetailsFragment fragment = new TrainerSessionDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.trainer_session_details_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mTrainerSessionDetailsFragmentBinding = getViewDataBinding();
        if (getArguments() != null) {
            TrainerSessionDetailsFragmentArgs args = TrainerSessionDetailsFragmentArgs.fromBundle(getArguments());
            mSessionModel = args.getSessionData();
        }
        setUp();
    }

    private void setUp() {

        //TODO:- Action Button
        navController = Navigation.findNavController(mTrainerSessionDetailsFragmentBinding.getRoot());

        tvToolBarTitle = mTrainerSessionDetailsFragmentBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mTrainerSessionDetailsFragmentBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mTrainerSessionDetailsFragmentBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_session_details_toolbar_title));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
            mTrainerSessionDetailsFragmentBinding.btnAddNewSubject.setVisibility(View.INVISIBLE);
            mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.VISIBLE);
        } else {
            mTrainerSessionDetailsFragmentBinding.btnAddNewSubject.setVisibility(View.VISIBLE);
            mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.INVISIBLE);
        }

        if (mSessionModel != null) {

            BindingUtils.set_image_from_url(mTrainerSessionDetailsFragmentBinding.teacherImage, mSessionModel.getTrainer().getImage());
            mTrainerSessionDetailsFragmentBinding.tvName.setText(mSessionModel.getTrainer().getName());
            BindingUtils.set_image_from_url(mTrainerSessionDetailsFragmentBinding.studentProfileImage, mSessionModel.getBookFor().getImage());
            mTrainerSessionDetailsFragmentBinding.tvStudentName.setText(mSessionModel.getBookFor().getName() + " - ");
            if (mSessionModel.getTrainerCourse() != null) {
                mTrainerSessionDetailsFragmentBinding.tvStudentCourseName.setText(mSessionModel.getTrainerCourse().getSubjectName());
            } else if (mSessionModel.getLiveorVideoCourse() != null) {
                mTrainerSessionDetailsFragmentBinding.tvStudentCourseName.setText(mSessionModel.getLiveorVideoCourse().getCourseSubject());
            } else if (mSessionModel.getTrainerTrainingsCourseResponse() != null) {
                mTrainerSessionDetailsFragmentBinding.tvStudentCourseName.setText(mSessionModel.getTrainerTrainingsCourseResponse().getCourseName());
            } else {
                mTrainerSessionDetailsFragmentBinding.tvStudentCourseName.setText("");
            }


            if (mSessionModel.getLiveorVideoCourse() != null && mSessionModel.getLiveorVideoCourse().getCourseType().equals("LiveCourse")) {

                mTrainerSessionDetailsFragmentBinding.tvSecondStep.setText(getString(R.string.online));
                mTrainerSessionDetailsFragmentBinding.tvLocation.setText(getString(R.string.zoom_meeting));
                if (mSessionModel.getLiveorVideoCourse().getRemainingTime().equals("0 d")) {
                    mTrainerSessionDetailsFragmentBinding.tvDay.setText("Today");
                } else {
                    mTrainerSessionDetailsFragmentBinding.tvDay.setText(CommonUtils.dateFormater(mSessionModel.getLiveorVideoCourse().getLiveDate(), "dd/MM/yy", "yyyy-MM-dd'T'hh:mm:ss"));
                }
                mTrainerSessionDetailsFragmentBinding.tvTime.setText(CommonUtils.dateFormater(mSessionModel.getLiveorVideoCourse().getLiveTime(), "HH a", "HH:mm a"));
                mTrainerSessionDetailsFragmentBinding.tvReachTime.setText(mSessionModel.getLiveorVideoCourse().getLiveTime());
                mTrainerSessionDetailsFragmentBinding.tvReachTitle.setText(getString(R.string.you_can_join_from));
                mTrainerSessionDetailsFragmentBinding.tvSessionTime.setText(mSessionModel.getLiveorVideoCourse().getLiveTime());
                mTrainerSessionDetailsFragmentBinding.tvHour.setText(mSessionModel.getLiveorVideoCourse().getRemainingTime());
                mTrainerSessionDetailsFragmentBinding.tvZoomLink.setText(mSessionModel.getLiveorVideoCourse().getMeetingLink());
                mTrainerSessionDetailsFragmentBinding.tvDistance.setVisibility(View.INVISIBLE);
                mTrainerSessionDetailsFragmentBinding.tvDistanceTitle.setVisibility(View.INVISIBLE);
            } else {
                mTrainerSessionDetailsFragmentBinding.tvDay.setText(mSessionModel.getDate());
                mTrainerSessionDetailsFragmentBinding.tvTime.setText(CommonUtils.dateFormater(mSessionModel.getTime(), "HH a", "HH:mm a"));
                mTrainerSessionDetailsFragmentBinding.tvLocation.setText(mSessionModel.getTrainer().getLocation().getStreetAddress());
                String[] timeArray = mSessionModel.getTime().split(" - ");
                if (timeArray.length > 1) {
                    mTrainerSessionDetailsFragmentBinding.tvReachTime.setText(timeArray[0]);
                    mTrainerSessionDetailsFragmentBinding.tvSessionTime.setText(timeArray[1]);
                } else {
                    mTrainerSessionDetailsFragmentBinding.tvReachTime.setText(timeArray[0]);
                    mTrainerSessionDetailsFragmentBinding.tvSessionTime.setText(timeArray[0]);
                }

                mTrainerSessionDetailsFragmentBinding.tvHour.setText(mSessionModel.getDuration() + "");
                mTrainerSessionDetailsFragmentBinding.layoutForFreesData.setVisibility(View.GONE);
                mTrainerSessionDetailsFragmentBinding.ivZoomLogo.setVisibility(View.GONE);
                mTrainerSessionDetailsFragmentBinding.tvButtonName.setText(getString(R.string.view_in_map));
            }

            MutableLiveData<String> liveData = navController.getCurrentBackStackEntry()
                    .getSavedStateHandle()
                    .getLiveData("time");

            liveData.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
//                    mFragmentTrainerBookingBinding.etTimeSlot.setVisibility(View.VISIBLE);
//                    mFragmentTrainerBookingBinding.etTimeSlot.setText(s);
                    timeSlot = s;
                }
            });

            MutableLiveData<String> liveDataTwo = navController.getCurrentBackStackEntry()
                    .getSavedStateHandle()
                    .getLiveData("dateStr");
            liveDataTwo.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (isUpdate) {
                        getBaseActivity().showLoading();
                        Map<String, String> map = new HashMap<>();
                        map.put("date", s);
                        map.put("time", timeSlot);
                        map.put("sessionMasterId", mSessionModel.getSessionId() + "");
                        mViewModel.sessionUpdate(map);
                    }
                }
            });
            if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                mTrainerSessionDetailsFragmentBinding.llButton.setVisibility(View.VISIBLE);
            } else {
                mTrainerSessionDetailsFragmentBinding.llButton.setVisibility(View.GONE);
            }
            setLogsSession(mSessionModel.getSessionLogs());
        }
    }

    //  TODO: Update Session Status
    private void setLogsSession(List<SessionLogsModel> sessionLogs) {
        if (sessionLogs != null && sessionLogs.size() > 0) {
            if (sessionLogs.size() == 1) {
                if (sessionLogs.get(0).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnStart.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnStart.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.etHr.setText(sessionLogs.get(0).getHours());
                    mTrainerSessionDetailsFragmentBinding.etMim.setText(sessionLogs.get(0).getMinutes());
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setText(CommonUtils.dateFormater(sessionLogs.get(0).getTime(), "HH:mm\na", "HH:mm a"));
                }
                if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.VISIBLE);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes()), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes())));
                    }
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.GONE);
                    mTrainerSessionDetailsFragmentBinding.tvFirstStep.setText(mSessionModel.getSessionLogs().get(0).getStatus());
                }

            } else if (sessionLogs.size() == 2) {
                if (sessionLogs.get(0).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnStart.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnStart.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.etHr.setText(sessionLogs.get(0).getHours());
                    mTrainerSessionDetailsFragmentBinding.etMim.setText(sessionLogs.get(0).getMinutes());
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setText(CommonUtils.dateFormater(sessionLogs.get(0).getTime(), "HH:mm\na", "HH:mm a"));
                }
                if (sessionLogs.get(1).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnYes.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnYes.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.tvTime2.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime2.setText(CommonUtils.dateFormater(sessionLogs.get(1).getTime(), "HH:mm\na", "HH:mm a"));
                }

                if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.VISIBLE);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes()), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes())));
                    }
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.GONE);
                    mTrainerSessionDetailsFragmentBinding.tvFirstStep.setText(mSessionModel.getSessionLogs().get(0).getStatus());
                    mTrainerSessionDetailsFragmentBinding.tvSecondStep.setText(mSessionModel.getSessionLogs().get(1).getStatus());
                }

            } else if (sessionLogs.size() == 3) {
                if (sessionLogs.get(0).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnStart.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnStart.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.etHr.setText(sessionLogs.get(0).getHours());
                    mTrainerSessionDetailsFragmentBinding.etMim.setText(sessionLogs.get(0).getMinutes());
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime1.setText(CommonUtils.dateFormater(sessionLogs.get(0).getTime(), "HH:mm\na", "HH:mm a"));
                }
                if (sessionLogs.get(1).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnYes.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnYes.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.tvTime2.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime2.setText(CommonUtils.dateFormater(sessionLogs.get(1).getTime(), "HH:mm\na", "HH:mm a"));
                }
                if (sessionLogs.get(2).isUpdated()) {
                    mTrainerSessionDetailsFragmentBinding.btnYes1.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.btnYes1.setTextColor(getResources().getColor(R.color.red));
                    mTrainerSessionDetailsFragmentBinding.tvTime3.setVisibility(View.VISIBLE);
                    mTrainerSessionDetailsFragmentBinding.tvTime3.setText(CommonUtils.dateFormater(sessionLogs.get(2).getTime(), "HH:mm\na", "HH:mm a"));
                }
                if (dataManager.getUserType() == DataManager.UserInMode.TRAINER.getType()) {
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.VISIBLE);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes()), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        mTrainerSessionDetailsFragmentBinding.tvEstimateTime.setText(Html.fromHtml(getContext().getString(R.string.trainer_session_details_estimate_time_title_with_value, mSessionModel.getSessionLogs().get(0).getHours(), mSessionModel.getSessionLogs().get(0).getMinutes())));
                    }
                    mTrainerSessionDetailsFragmentBinding.llTimeContain.setVisibility(View.GONE);
                    mTrainerSessionDetailsFragmentBinding.tvFirstStep.setText(mSessionModel.getSessionLogs().get(0).getStatus());
                    mTrainerSessionDetailsFragmentBinding.tvSecondStep.setText(mSessionModel.getSessionLogs().get(1).getStatus());
                }

            }
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onClickStart() {
        if (mTrainerSessionDetailsFragmentBinding.etHr.getText().toString().isEmpty() && mTrainerSessionDetailsFragmentBinding.etMim.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter Hour and Minute", Toast.LENGTH_SHORT).show();
            return;
        }
        getBaseActivity().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("sessionLogId", mSessionModel.getSessionLogs().get(0).getSessionLogId() + "");
        params.put("sessionId", mSessionModel.getSessionLogs().get(0).getSessionId() + "");
        params.put("hours", mTrainerSessionDetailsFragmentBinding.etHr.getText().toString());
        params.put("minutes", mTrainerSessionDetailsFragmentBinding.etMim.getText().toString());
        //Log.e("DATAPARAMS", "onClickStart: "+params.toString() );
        mViewModel.sessionStatusUpdate(params);
    }

    @Override
    public void onClickReached() {
        if (!mSessionModel.getSessionLogs().get(0).isUpdated()) {
            Toast.makeText(getContext(), "Update First Steps", Toast.LENGTH_SHORT).show();
            return;
        }
        getBaseActivity().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("sessionLogId", mSessionModel.getSessionLogs().get(1).getSessionLogId() + "");
        params.put("sessionId", mSessionModel.getSessionLogs().get(1).getSessionId() + "");
        //Log.e("DATAPARAMS", "onClickStart: "+params.toString() );
        mViewModel.sessionStatusUpdate(params);
    }

    @Override
    public void onClickFinished() {
        if (!mSessionModel.getSessionLogs().get(1).isUpdated()) {
            Toast.makeText(getContext(), "Update Second Steps", Toast.LENGTH_SHORT).show();
            return;
        }
        getBaseActivity().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("sessionLogId", mSessionModel.getSessionLogs().get(2).getSessionLogId() + "");
        params.put("sessionId", mSessionModel.getSessionLogs().get(2).getSessionId() + "");
        //Log.e("DATAPARAMS", "onClickStart: "+params.toString() );
        mViewModel.sessionStatusUpdate(params);
    }

    @Override
    public void openScheduleDateTime() {
        if (mSessionModel != null) {
            if (mSessionModel.getLiveorVideoCourse() != null && mSessionModel.getLiveorVideoCourse().getCourseType().equals("LiveCourse")) {
                isUpdate = true;
                TrainerSessionDetailsFragmentDirections.ActionTrainerSessionDetailsFragmentToScheduleDateTimeFragment action = TrainerSessionDetailsFragmentDirections.actionTrainerSessionDetailsFragmentToScheduleDateTimeFragment();
                action.setScheduleType("Online Schedule");
                action.setTrainerId(mSessionModel.getTrainer().getId());
                navController.navigate(action);
            } else {
                isUpdate = true;
                TrainerSessionDetailsFragmentDirections.ActionTrainerSessionDetailsFragmentToScheduleDateTimeFragment action = TrainerSessionDetailsFragmentDirections.actionTrainerSessionDetailsFragmentToScheduleDateTimeFragment();
                action.setScheduleType("Daily Schedule");
                action.setTrainerId(mSessionModel.getTrainer().getId());
                navController.navigate(action);
            }
        } else {
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openZoom() {
        if (mSessionModel.getLiveorVideoCourse() != null && mSessionModel.getLiveorVideoCourse().getCourseType().equals("LiveCourse")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTrainerSessionDetailsFragmentBinding.tvZoomLink.getText().toString()));
            if (browserIntent != null) {
                try {
                    startActivity(browserIntent);
                }catch (Exception e){
                    Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            TrainerSessionDetailsFragmentDirections.ActionTrainerSessionDetailsFragmentToDirectionMapFragment action = TrainerSessionDetailsFragmentDirections.actionTrainerSessionDetailsFragmentToDirectionMapFragment();
            action.setSessionData(mSessionModel);
            navController.navigate(action);
        }
    }

    @Override
    public void copyLinkZoom() {
        copyToClipBoard();
    }

    @Override
    public void shareLinkZoom() {
        if (mSessionModel != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            if (mSessionModel.getTrainerCourse() != null) {
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this link " + mSessionModel.getTrainer().getName() + " Sir, " + mSessionModel.getTrainerCourse().getSubjectName() + "- Online Class : " + mTrainerSessionDetailsFragmentBinding.tvZoomLink.getText().toString());
            } else {
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this link " + mSessionModel.getTrainer().getName() + " Sir, " + mSessionModel.getLiveorVideoCourse().getCourseSubject() + "- Online Class : " + mTrainerSessionDetailsFragmentBinding.tvZoomLink.getText().toString());
            }
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        getBaseActivity().hideLoading();
        if (throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            getBaseActivity().handleApiError(anError);
        }
    }

    @Override
    public void onSuccessSession(CoursesResponse mCoursesResponse) {
        getBaseActivity().hideLoading();
        isUpdate = false;
        if (mCoursesResponse.getIsSuccess()) {
            mSessionModel = mCoursesResponse.getData().getSession();
            setUp();
        }
    }

    @Override
    public void onSuccessSessionStatusUpdate(SessionStatusUpdateResponse mSessionStatusUpdateResponse) {
        getBaseActivity().hideLoading();
        if (mSessionStatusUpdateResponse.getData().getSession().size() == 1) {
            mSessionModel.getSessionLogs().set(0, mSessionStatusUpdateResponse.getData().getSession().get(0));
        } else if (mSessionStatusUpdateResponse.getData().getSession().size() == 2) {
            mSessionModel.getSessionLogs().set(0, mSessionStatusUpdateResponse.getData().getSession().get(0));
            mSessionModel.getSessionLogs().set(1, mSessionStatusUpdateResponse.getData().getSession().get(1));
        } else if (mSessionStatusUpdateResponse.getData().getSession().size() == 3) {
            mSessionModel.getSessionLogs().set(0, mSessionStatusUpdateResponse.getData().getSession().get(0));
            mSessionModel.getSessionLogs().set(1, mSessionStatusUpdateResponse.getData().getSession().get(1));
            mSessionModel.getSessionLogs().set(2, mSessionStatusUpdateResponse.getData().getSession().get(2));
        }
        setLogsSession(mSessionStatusUpdateResponse.getData().getSession());
        Toast.makeText(getContext(), mSessionStatusUpdateResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void copyToClipBoard() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", mTrainerSessionDetailsFragmentBinding.tvZoomLink.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Copy Zoom Link", Toast.LENGTH_SHORT).show();
    }
}
