package com.ryx.tdreeb.ui.fragments.parentfragment.booktrainer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CourseModel;
import com.ryx.tdreeb.data.model.api.submitmodels.LiveCourseParentModel;
import com.ryx.tdreeb.databinding.FragmentTrainerBookingBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsFragment;
import com.ryx.tdreeb.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TrainerBookingFragment extends BaseFragment<FragmentTrainerBookingBinding, TrainerBookingViewModel> implements TrainerBookingNavigator {

    FragmentTrainerBookingBinding mFragmentTrainerBookingBinding;
    private NavController navController;
    private CourseModel mCourseModel;
    private String mScheduleType = "";

    private ChooseKidsFragment dialog;
    private List<ChildenModel> listChildrenModel;
    private ChildenModel childenModel;
    private boolean isGroupSession = false;
    private boolean isCourseType = true;

    private TextView tvToolBarTitle;
    private ImageView backImg, profileImg;

    private LiveCourseParentModel mLiveCourseParentModel;

    @Inject
    DataManager dataManager;

    public TrainerBookingFragment() {
        // Required empty public constructor
    }

    public static TrainerBookingFragment newInstance() {
        TrainerBookingFragment fragment = new TrainerBookingFragment();
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
        return R.layout.fragment_trainer_booking;
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
        mFragmentTrainerBookingBinding = getViewDataBinding();
        if (getArguments() != null) {
            TrainerBookingFragmentArgs args = TrainerBookingFragmentArgs.fromBundle(getArguments());
            mCourseModel = args.getCourseModel();
            isCourseType = args.getIsCourse();
        }
        setUp();
    }

    private void setUp() {
        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentTrainerBookingBinding.getRoot());

        tvToolBarTitle = mFragmentTrainerBookingBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentTrainerBookingBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentTrainerBookingBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.schedule));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);
        mFragmentTrainerBookingBinding.tvTitleTeacher.setText("Book " + mCourseModel.getTrainer().getName());

        mLiveCourseParentModel = new LiveCourseParentModel();
        List<String> dataList = new ArrayList<>();
        dataList.add("Daily Schedule");
        dataList.add("Online Schedule");
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        mFragmentTrainerBookingBinding.etSelectMeetingType.setAdapter(arrayAdapter);
        mFragmentTrainerBookingBinding.etSelectMeetingType.setInputType(0);

        mFragmentTrainerBookingBinding.etSelectMeetingType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                mFragmentTrainerBookingBinding.etSelectMeetingType.showDropDown();
        });

        dialog = ChooseKidsFragment.newInstance();
        dialog.setCallBack((childenModel) -> {
            TrainerBookingFragment.this.childenModel = childenModel;
            mFragmentTrainerBookingBinding.etSelectKids.setText(childenModel.getFirstName() + " " + childenModel.getLastName());
        });

        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
            mFragmentTrainerBookingBinding.tvSelectKids.setVisibility(View.VISIBLE);
            mFragmentTrainerBookingBinding.etSelectKids.setVisibility(View.VISIBLE);
        } else {
            mFragmentTrainerBookingBinding.tvSelectKids.setVisibility(View.GONE);
            mFragmentTrainerBookingBinding.etSelectKids.setVisibility(View.GONE);
        }

        MutableLiveData<String> liveData = navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("time");
        liveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mFragmentTrainerBookingBinding.etTimeSlot.setVisibility(View.VISIBLE);
                mFragmentTrainerBookingBinding.etTimeSlot.setText(s);
            }
        });

        MutableLiveData<String> liveDataTwo = navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("dateStr");
        liveDataTwo.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mFragmentTrainerBookingBinding.etSelectDateTime.setText(s);
            }
        });


    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openScheduleType() {
        mFragmentTrainerBookingBinding.etSelectMeetingType.showDropDown();
    }

    @Override
    public void openScheduleDateTime() {
        mScheduleType = mFragmentTrainerBookingBinding.etSelectMeetingType.getText().toString();
        if (mCourseModel.getTrainerId() != null && !mScheduleType.isEmpty()) {
            TrainerBookingFragmentDirections.ActionTrainerBookingFragmentToScheduleDateTimeFragment action = TrainerBookingFragmentDirections.actionTrainerBookingFragmentToScheduleDateTimeFragment();
            action.setScheduleType(mScheduleType);
            action.setTrainerId(mCourseModel.getTrainerId());
            navController.navigate(action);
        } else {
            Toast.makeText(getContext(), "Select Meeting Type", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openPaymentMethod() {
        if (checkData()) {

            if (isCourseType) {
                mLiveCourseParentModel.setCourseType("Course");
                mLiveCourseParentModel.setSessionType("Course");
                mLiveCourseParentModel.setTrainerCourseId(mCourseModel.getTrainerCourseId());
            } else {
                mLiveCourseParentModel.setSessionType("TrainingCourse");
                mLiveCourseParentModel.setCourseType("TrainingCourse");
                mLiveCourseParentModel.setTrainerCourseId(mCourseModel.getCourseId());
            }
            mLiveCourseParentModel.setDate(CommonUtils.dateFormater(mFragmentTrainerBookingBinding.etSelectDateTime.getText().toString(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"));
            mLiveCourseParentModel.setTime(mFragmentTrainerBookingBinding.etTimeSlot.getText().toString());
            mLiveCourseParentModel.setTrainerId(mCourseModel.getTrainerId());
            mLiveCourseParentModel.setRate(mCourseModel.getPrice());
            mLiveCourseParentModel.setSubTotal(mCourseModel.getPrice());
            mLiveCourseParentModel.setGrandTotal(mCourseModel.getPrice());
            mLiveCourseParentModel.setIsGroupSession(isGroupSession);
            mLiveCourseParentModel.setNumberOfStudent(mFragmentTrainerBookingBinding.etGroupSession.getText().toString());
            mLiveCourseParentModel.setNote(mFragmentTrainerBookingBinding.etDescription.getText().toString());
            if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                mLiveCourseParentModel.setBookByType("parent");
                mLiveCourseParentModel.setBookForType("student");
                mLiveCourseParentModel.setBookById(dataManager.getCurrentUserId());
                mLiveCourseParentModel.setBookForId(childenModel.getStudentId());
            } else {
                mLiveCourseParentModel.setBookByType("student");
                mLiveCourseParentModel.setBookForType("student");
                mLiveCourseParentModel.setBookById(dataManager.getCurrentUserId());
                mLiveCourseParentModel.setBookForId(dataManager.getCurrentUserId());
            }
            Log.e("PASSDATA", "openPaymentMethod: " + mLiveCourseParentModel.toString());
            TrainerBookingFragmentDirections.ActionTrainerBookingFragmentToPaymentMethodFragment action = TrainerBookingFragmentDirections.actionTrainerBookingFragmentToPaymentMethodFragment("Course");
            action.setCourseDataPass(mLiveCourseParentModel);
            navController.navigate(action);
        }
    }

    @Override
    public void isGroupSessionClick() {
        if (isGroupSession) {
            mFragmentTrainerBookingBinding.imgSelectSession.setImageResource(R.drawable.ic_untick);
        } else {
            mFragmentTrainerBookingBinding.imgSelectSession.setImageResource(R.drawable.ic_tick);
        }
        isGroupSession = !isGroupSession;
    }

    private boolean checkData() {
        mScheduleType = mFragmentTrainerBookingBinding.etSelectMeetingType.getText().toString();
        if (mScheduleType.isEmpty()) {
            Toast.makeText(getContext(), "Select Meeting Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mFragmentTrainerBookingBinding.etSelectDateTime.getText().toString().isEmpty() && mFragmentTrainerBookingBinding.etTimeSlot.getText().toString().equals("00:00 XX - 00:00 XX")) {
            Toast.makeText(getContext(), "Select Meeting Date and Time", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType() && childenModel == null) {
            Toast.makeText(getContext(), "Select Your Kids", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (isGroupSession && mFragmentTrainerBookingBinding.etGroupSession.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Enter number of Student Attend this Session", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void openChooseKid() {
        if (listChildrenModel != null) {
            dialog.setData(listChildrenModel);
            dialog.show(getBaseActivity().getSupportFragmentManager());
        } else {
            getBaseActivity().showLoading();
            mViewModel.getChildren(dataManager.getCurrentUserId());
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
    public void onSuccessAddChildResponse(AddChildResponse mAddChildResponse) {
        getBaseActivity().hideLoading();
        if (mAddChildResponse.getCode() == 200) {
            listChildrenModel = mAddChildResponse.getData().getChilds();
            openChooseKid();
        }
    }
}