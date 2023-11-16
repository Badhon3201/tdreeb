package com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.AddLiveDateTimeAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.AddDateTimeModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseAddModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.databinding.FragmentAddLiveCourseBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListFragment;
import com.ryx.tdreeb.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class AddLiveCourseFragment extends BaseFragment<FragmentAddLiveCourseBinding, AddLiveCourseViewModel> implements AddLiveCourseNavigator {

    FragmentAddLiveCourseBinding mFragmentAddLiveCourseBinding;

    @Inject
    DataManager dataManager;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    private NavController navController;

    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    @Inject
    AddLiveDateTimeAdapter mAddLiveDateTimeAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    String myFile = "", dateStr = "";
    List<AddDateTimeModel> dateTimeModels;
    String mCurrentPhotoPath;
    private String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    List<SubjectModel> listSubject;
    SubjectModel mSubjectModel;
    ListFragment mListFragment;
    ListFragment mSubListFragment;

    int pos = 0;

    LiveCourseModel mLiveCourseModel;

    public AddLiveCourseFragment() {
        // Required empty public constructor
    }

    public static AddLiveCourseFragment newInstance() {
        AddLiveCourseFragment fragment = new AddLiveCourseFragment();
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
        return R.layout.fragment_add_live_course;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateTimeModels = new ArrayList<>();
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setNavigator(this);
        mFragmentAddLiveCourseBinding = getViewDataBinding();
        if (getArguments() != null) {
            AddLiveCourseFragmentArgs args = AddLiveCourseFragmentArgs.fromBundle(getArguments());
            mLiveCourseModel = args.getLiveCourseData();
        }
        setUp();
    }

    private void setUp() {

        navController = Navigation.findNavController(mFragmentAddLiveCourseBinding.getRoot());
        tvToolBarTitle = mFragmentAddLiveCourseBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentAddLiveCourseBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentAddLiveCourseBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.add_live_course));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentAddLiveCourseBinding.rvDateTime.setLayoutManager(mLinearLayoutManager);
        mFragmentAddLiveCourseBinding.rvDateTime.addItemDecoration(new EqualSpacingItemDecoration(40));
        mFragmentAddLiveCourseBinding.rvDateTime.setItemAnimator(new DefaultItemAnimator());
        mFragmentAddLiveCourseBinding.rvDateTime.setAdapter(mAddLiveDateTimeAdapter);
        mAddLiveDateTimeAdapter.setListener(() -> {
            dateTimeModels.add(new AddDateTimeModel());
            mAddLiveDateTimeAdapter.addItems(dateTimeModels);
//            AddLiveCourseFragmentDirections.ActionAddLiveCourseFragmentToScheduleDateTimeFragment2 action = AddLiveCourseFragmentDirections.actionAddLiveCourseFragmentToScheduleDateTimeFragment2();
//            action.setScheduleType("Online Schedule");
//            action.setTrainerId(dataManager.getCurrentUserId());
//            navController.navigate(action);
        });
        mAddLiveDateTimeAdapter.setListenerTwo(pos -> {
            dateTimeModels.remove(pos);
            mAddLiveDateTimeAdapter.addItems(dateTimeModels);
        });

        mAddLiveDateTimeAdapter.setListenerDateTime(new AddLiveDateTimeAdapter.DateTimeClick() {
            @Override
            public void onClickDate(int pos) {
                AddLiveCourseFragment.this.pos = pos;
                openDate();
            }

            @Override
            public void onClickTime(int pos) {
                AddLiveCourseFragment.this.pos = pos;
                openTimePicker();
            }
        });


        if (mLiveCourseModel != null) {
            mFragmentAddLiveCourseBinding.etCourseTitle.setText(mLiveCourseModel.getCourseTitle());
            mSubjectModel = new SubjectModel();


//            mFragmentAddLiveCourseBinding.etCourseDate.setText(CommonUtils.dateFormater(mLiveCourseModel.getLiveDate(), "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss"));
//            mFragmentAddLiveCourseBinding.etCourseTime.setText(mLiveCourseModel.getLiveTime());
            mFragmentAddLiveCourseBinding.etCoursePrice.setText(mLiveCourseModel.getPrice() + "");
            mFragmentAddLiveCourseBinding.etZoomLink.setText(mLiveCourseModel.getMeetingLink());
            mFragmentAddLiveCourseBinding.etZoomPassword.setText(mLiveCourseModel.getMeetingPassword());
            mFragmentAddLiveCourseBinding.etDetail.setText(mLiveCourseModel.getMeetingDetails());
            mFragmentAddLiveCourseBinding.etDescription.setText(mLiveCourseModel.getMeetingDescription());

            if (mLiveCourseModel.getSubject() != null) {
                mSubjectModel.setSubjectName(mLiveCourseModel.getSubject().getSubjectName());
                mSubjectModel.setSubjectId(mLiveCourseModel.getSubject().getSubjectId());
                mFragmentAddLiveCourseBinding.etCourseSubject.setText(mLiveCourseModel.getSubject().getSubjectName());
                if (mLiveCourseModel.getSubject().getSubSubjectResponse() != null) {
                    mSubjectModel.setSubjectName(mLiveCourseModel.getSubject().getSubSubjectResponse().getSubjectName());
                    mSubjectModel.setSubjectId(mLiveCourseModel.getSubject().getSubSubjectResponse().getSubjectId());

                    mFragmentAddLiveCourseBinding.etCourseSubSubject.setText(mLiveCourseModel.getSubject().getSubSubjectResponse().getSubjectName());
                    mFragmentAddLiveCourseBinding.tvCourseSubSubject.setVisibility(View.VISIBLE);
                    mFragmentAddLiveCourseBinding.etCourseSubSubject.setVisibility(View.VISIBLE);
                } else {
                    mFragmentAddLiveCourseBinding.tvCourseSubSubject.setVisibility(View.GONE);
                    mFragmentAddLiveCourseBinding.etCourseSubSubject.setVisibility(View.GONE);
                }
            }

            File file = new File(mLiveCourseModel.getUploadLink());
            mFragmentAddLiveCourseBinding.btnChangeImage.setText(file.getName());
            mFragmentAddLiveCourseBinding.btnSave.setText(getString(R.string.update));
            tvToolBarTitle.setText(getString(R.string.update_live_course));
            if (mLiveCourseModel.getLiveCourseDateRangeRequests() != null) {
                dateTimeModels = mLiveCourseModel.getLiveCourseDateRangeRequests();
                for (AddDateTimeModel data : dateTimeModels) {
                    dateStr = CommonUtils.dateFormater(data.getLiveDate(), "yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ss");
                }
                mAddLiveDateTimeAdapter.addItems(dateTimeModels);
            }
        }

//        MutableLiveData<String> liveDataTwo = navController.getCurrentBackStackEntry()
//                .getSavedStateHandle()
//                .getLiveData("dateStr");
//        liveDataTwo.observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                dateStr = s;
//                MutableLiveData<String> liveData = navController.getCurrentBackStackEntry()
//                        .getSavedStateHandle()
//                        .getLiveData("time");
//                liveData.observe(getViewLifecycleOwner(), new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        AddDateTimeModel mAddDateTimeModel = new AddDateTimeModel();
//                        mAddDateTimeModel.setLiveDate(CommonUtils.dateFormater(dateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd"));
//                        mAddDateTimeModel.setLiveTime(s);
//                        dateTimeModels.add(mAddDateTimeModel);
//                        mAddLiveDateTimeAdapter.addItems(dateTimeModels);
//                    }
//                });
//            }
//        });

        myCalendar = Calendar.getInstance();
        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        };

        mTimeSetListener = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateTime();
        };
    }

    private void updateDate() {
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        AddDateTimeModel model = dateTimeModels.get(pos);
        dateStr = sdf.format(myCalendar.getTime());
        model.setLiveDate(sdf.format(myCalendar.getTime()));
        dateTimeModels.set(pos, model);
        mAddLiveDateTimeAdapter.addItems(dateTimeModels);
//        mFragmentAddLiveCourseBinding.etCourseDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTime() {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        AddDateTimeModel model = dateTimeModels.get(pos);
        model.setLiveTime(sdf.format(myCalendar.getTime()));
        dateTimeModels.set(pos, model);
        mAddLiveDateTimeAdapter.addItems(dateTimeModels);
//        mFragmentAddLiveCourseBinding.etCourseTime.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void submitData() {
        getBaseActivity().hideKeyboard();
        if (checkData()) {
            LiveCourseAddModel liveCourseAddModel = new LiveCourseAddModel();
            liveCourseAddModel.setTrainerId(dataManager.getCurrentUserId());
            liveCourseAddModel.setCourseTitle(mFragmentAddLiveCourseBinding.etCourseTitle.getText().toString());
            liveCourseAddModel.setCourseSubject(mSubjectModel.getSubjectName());
            liveCourseAddModel.setSubjectId(mSubjectModel.getSubjectId());
            liveCourseAddModel.setMeetingDescription(mFragmentAddLiveCourseBinding.etDescription.getText().toString());
            liveCourseAddModel.setPrice(mFragmentAddLiveCourseBinding.etCoursePrice.getText().toString());
            liveCourseAddModel.setMeetingDetails(mFragmentAddLiveCourseBinding.etDetail.getText().toString());
            liveCourseAddModel.setMeetingLink(mFragmentAddLiveCourseBinding.etZoomLink.getText().toString());
            liveCourseAddModel.setMeetingPassword(mFragmentAddLiveCourseBinding.etZoomPassword.getText().toString());
            liveCourseAddModel.setLiveCourseDateRangeRequests(dateTimeModels);
            liveCourseAddModel.setLiveTime("07:59 PM - 10:59 PM");
            liveCourseAddModel.setStramingLiveDate(CommonUtils.dateFormater(dateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd"));
            liveCourseAddModel.setLiveDate(CommonUtils.dateFormater(dateStr, "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd"));

            liveCourseAddModel.setUploadLink(myFile);
            Log.e("liveCourseDATA", "submitData: " + liveCourseAddModel.toString());
            getBaseActivity().showLoading();
            if (mLiveCourseModel == null) {
                mViewModel.addLiveCoursesByData(liveCourseAddModel);
                mViewModel.addLiveCoursesByData(liveCourseAddModel);
            } else {
                liveCourseAddModel.setLiveCourseId(mLiveCourseModel.getLiveCourseId());
                mViewModel.updateLiveCoursesByData(liveCourseAddModel);
            }

        }
    }

    @Override
    public void openImgAndVideo() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 500);
        } else {
            selectImage(getContext(), 100, getString(R.string.upload_video_or_img));
        }
    }

    @Override
    public void openSubject() {
        if (listSubject == null) {
            getBaseActivity().showLoading();
            mViewModel.getSubject();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setSubject(listSubject, getString(R.string.subject));

            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                    AddLiveCourseFragment.this.mSubjectModel = mSubjectModel;
                    mFragmentAddLiveCourseBinding.etCourseSubSubject.setText("");
                    if (!mSubjectModel.getSubSubject().isEmpty()) {
                        mFragmentAddLiveCourseBinding.tvCourseSubSubject.setVisibility(View.VISIBLE);
                        mFragmentAddLiveCourseBinding.etCourseSubSubject.setVisibility(View.VISIBLE);
                    } else {
                        mFragmentAddLiveCourseBinding.tvCourseSubSubject.setVisibility(View.GONE);
                        mFragmentAddLiveCourseBinding.etCourseSubSubject.setVisibility(View.GONE);
                    }
                    mFragmentAddLiveCourseBinding.etCourseSubject.setText(mSubjectModel.getSubjectName());
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {

                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openSub() {
        if (!mSubjectModel.getSubSubject().isEmpty()) {
            mSubListFragment = ListFragment.newInstance();
            mSubListFragment.setSubject(mSubjectModel.getSubSubject(), getString(R.string.sub_category));

            mSubListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                    AddLiveCourseFragment.this.mSubjectModel = mSubjectModel;
                    mFragmentAddLiveCourseBinding.etCourseSubSubject.setText(mSubjectModel.getSubjectName());
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
            mSubListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openDate() {
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void openTimePicker() {
        new TimePickerDialog(getContext(), mTimeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
    }

    private boolean checkData() {
        if (mFragmentAddLiveCourseBinding.etCourseTitle.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etCourseTitle.setError(getString(R.string.enter_your_title));
            Toast.makeText(getContext(), "" + getString(R.string.enter_your_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateTimeModels.size() == 0) {
            Toast.makeText(getContext(), "" + getString(R.string.pick_date), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddLiveCourseBinding.etCoursePrice.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etCoursePrice.setError(getString(R.string.enter_price));
            Toast.makeText(getContext(), "" + getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddLiveCourseBinding.etCourseSubject.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etCourseSubject.setError(getString(R.string.select_subject));
            Toast.makeText(getContext(), "" + getString(R.string.select_subject), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (mFragmentAddLiveCourseBinding.etCourseTime.getText().toString().isEmpty()) {
//            mFragmentAddLiveCourseBinding.etCourseTime.setError(getString(R.string.select_time));
//            Toast.makeText(getContext(), "" + getString(R.string.select_time), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (mFragmentAddLiveCourseBinding.etDescription.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etDescription.setError(getString(R.string.description));
            Toast.makeText(getContext(), "" + getString(R.string.description), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddLiveCourseBinding.etDetail.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etDetail.setError(getString(R.string.details));
            Toast.makeText(getContext(), "" + getString(R.string.details), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddLiveCourseBinding.etZoomLink.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etZoomLink.setError(getString(R.string.paste_zoom_link));
            Toast.makeText(getContext(), "" + getString(R.string.paste_zoom_link), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddLiveCourseBinding.etZoomPassword.getText().toString().isEmpty()) {
            mFragmentAddLiveCourseBinding.etZoomPassword.setError(getString(R.string.enter_meeting_password));
            Toast.makeText(getContext(), "" + getString(R.string.enter_meeting_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mLiveCourseModel == null) {
            if (myFile.isEmpty()) {
                Toast.makeText(getContext(), "" + getString(R.string.upload_video_or_img), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private void clearData() {
        myFile = "";
        mFragmentAddLiveCourseBinding.etCourseTitle.setText("");
        mFragmentAddLiveCourseBinding.etDescription.setText("");
        mFragmentAddLiveCourseBinding.etDetail.setText("");
        mFragmentAddLiveCourseBinding.etZoomPassword.setText("");
        mFragmentAddLiveCourseBinding.etZoomLink.setText("");
        mFragmentAddLiveCourseBinding.etCoursePrice.setText("");
//        mFragmentAddLiveCourseBinding.etCourseTime.setText("");
//        mFragmentAddLiveCourseBinding.etCourseDate.setText("");
        mFragmentAddLiveCourseBinding.etCourseSubject.setText("");
        mFragmentAddLiveCourseBinding.btnChangeImage.setText("");
        navController.popBackStack();
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
    public void onSuccessLiveCourse(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        if (mLiveCourseResponse.getIsSuccess()) {
            getBaseActivity().openDialog(mLiveCourseResponse.getMessage(), () -> {
//                if (mLiveCourseModel == null) {
                clearData();
//                }
            });
        } else {
            Toast.makeText(getContext(), "" + mLiveCourseResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessSubjectResponse(SubjectResponse mSubjectResponse) {
        getBaseActivity().hideLoading();
        if (mSubjectResponse.getIsSuccess()) {
            listSubject = mSubjectResponse.getData().getSubjects();
            openSubject();
        } else {
            Toast.makeText(getContext(), "" + mSubjectResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // TODO:- Image From Camera and Gallery
    private void selectImage(Context context, int reqstCode, String title) {
        final CharSequence[] options = {getString(R.string.video), getString(R.string.image), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.take_photo))) {
//                try {
//                    //dispatchTakePictureIntent(reqstCode);
//                } catch (IOException e) {
//                }
            } else if (options[item].equals(getString(R.string.image))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, reqstCode + 100);
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent(int reqstCode) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getBaseActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                return;
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.ryx.tdreeb.fileproviders", createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, reqstCode);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //  TODO:- Over Ride
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED && grantResults[2] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode) {
            case 500:
                //TODO: My Car
                selectImage(getContext(), 100, getString(R.string.upload_video_or_img));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: My Car
        if (requestCode == 100 && resultCode == getBaseActivity().RESULT_OK) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File imgFile = new File(imageUri.getPath());
            if (imgFile.exists() && imgFile.length() > 0) {
                Bitmap bm = BitmapFactory.decodeFile(imageUri.getPath());
                String base64Image = getImgBase64(bm);
                mFragmentAddLiveCourseBinding.btnChangeImage.setText(imgFile.getName());
                myFile = base64Image;
                Log.e("Image", "onActivityResult:100 " + base64Image);
            }
        } else if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mFragmentAddLiveCourseBinding.btnChangeImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                String base64Image = getImgBase64(bitmap);
                Log.e("Image", "onActivityResult:100 " + base64Image);
                myFile = base64Image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String getImgBase64(Bitmap bitmap) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        scaled.compress(Bitmap.CompressFormat.PNG, 80, bOut);
        String base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
        return base64Image;
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getBaseActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}