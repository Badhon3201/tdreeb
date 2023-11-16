package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.snackbar.Snackbar;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.AddVideoCourseAdapter;
import com.ryx.tdreeb.customui.SwipeToDeleteCallback;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.VideoFile;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseAddModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.VedioCourseFilesModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.databinding.FragmentAddVideoCourseBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.VideoResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListFragment;
import com.ryx.tdreeb.utils.BindingUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class AddVideoCourseFragment extends BaseFragment<FragmentAddVideoCourseBinding, AddVideoCourseViewModel> implements AddVideoCourseNavigator {

    FragmentAddVideoCourseBinding mFragmentAddVideoCourseBinding;

    @Inject
    AddVideoCourseAdapter mAddVideoCourseAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @Inject
    DataManager dataManager;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;

    private NavController navController;

    List<VideoFile> videoFileEdit;
    List<VideoFile> videoFile;
    File introVideo;
    int posVideoFile = 0;

    File myFile;
    String mCurrentPhotoPath;
    private String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    List<SubjectModel> listSubject;
    SubjectModel mSubjectModel;
    ListFragment mListFragment;
    ListFragment mSubListFragment;

    LiveCourseModel mLiveCourseModel;


    public AddVideoCourseFragment() {
        // Required empty public constructor
    }

    public static AddVideoCourseFragment newInstance() {
        AddVideoCourseFragment fragment = new AddVideoCourseFragment();
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
        return R.layout.fragment_add_video_course;
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
        mFragmentAddVideoCourseBinding = getViewDataBinding();
        if (getArguments() != null) {
            AddVideoCourseFragmentArgs args = AddVideoCourseFragmentArgs.fromBundle(getArguments());
            mLiveCourseModel = args.getVideoModelData();
        }
        setUp();
    }

    private void setUp() {
        videoFile = new ArrayList<>();
        videoFileEdit = new ArrayList<>();
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentAddVideoCourseBinding.rvAddVideo.setLayoutManager(mLinearLayoutManager);
        mFragmentAddVideoCourseBinding.rvAddVideo.addItemDecoration(new EqualSpacingItemDecoration(30));
        mFragmentAddVideoCourseBinding.rvAddVideo.setItemAnimator(new DefaultItemAnimator());
        mFragmentAddVideoCourseBinding.rvAddVideo.setAdapter(mAddVideoCourseAdapter);

        mAddVideoCourseAdapter.setListener(() -> {
            videoFile.add(new VideoFile());
            mAddVideoCourseAdapter.addItems(videoFile);
        });

        mAddVideoCourseAdapter.setListenerTwo(pos -> {
            posVideoFile = pos;
            if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
                requestPermissionsSafely(permission, 600);
            } else {
                selectVideo(getContext(), 400, getString(R.string.select_video));
            }

        });

        enableSwipeToDeleteAndUndo();

        navController = Navigation.findNavController(mFragmentAddVideoCourseBinding.getRoot());
        tvToolBarTitle = mFragmentAddVideoCourseBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentAddVideoCourseBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentAddVideoCourseBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.trainer_add_video_btn_title));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        if (mLiveCourseModel != null) {
            mFragmentAddVideoCourseBinding.etCourseTitle.setText(mLiveCourseModel.getCourseTitle());
            mSubjectModel = new SubjectModel();
            mFragmentAddVideoCourseBinding.etCoursePrice.setText(mLiveCourseModel.getPrice() + "");
            mFragmentAddVideoCourseBinding.etDetail.setText(mLiveCourseModel.getMeetingDetails());
            mFragmentAddVideoCourseBinding.etDescription.setText(mLiveCourseModel.getMeetingDescription());
            File file = new File(mLiveCourseModel.getUploadLink());
            mFragmentAddVideoCourseBinding.btnChangeImage.setText(file.getName());
            mFragmentAddVideoCourseBinding.btnSave.setText(getString(R.string.update));
            tvToolBarTitle.setText(getString(R.string.update_video_course));

            if (mLiveCourseModel.getOverviewVideo() != null) {
                File overView = new File(mLiveCourseModel.getOverviewVideo());
                //BindingUtils.setImageUrlTwo(mFragmentAddVideoCourseBinding.resImageT, mResourceModel.getResourceImage());
                mFragmentAddVideoCourseBinding.btnChangeIntroVideo.setText(overView.getName());
            }

            if(mLiveCourseModel.getSubject() != null){
                mSubjectModel = mLiveCourseModel.getSubject();
                mFragmentAddVideoCourseBinding.etCourseSubject.setText(mLiveCourseModel.getSubject().getSubjectName());
                if (mLiveCourseModel.getSubject().getSubSubjectResponse() != null) {
                    mSubjectModel = mLiveCourseModel.getSubject().getSubSubjectResponse();
                    mFragmentAddVideoCourseBinding.tvSubSubject.setVisibility(View.VISIBLE);
                    mFragmentAddVideoCourseBinding.etSubSubject.setVisibility(View.VISIBLE);
                    mFragmentAddVideoCourseBinding.etSubSubject.setText(mLiveCourseModel.getSubject().getSubSubjectResponse().getSubjectName());
                } else {
                    mFragmentAddVideoCourseBinding.tvSubSubject.setVisibility(View.GONE);
                    mFragmentAddVideoCourseBinding.etSubSubject.setVisibility(View.GONE);
                }
            }


            if (mLiveCourseModel.getVedioCourseFiles() != null) {
                for (VedioCourseFilesModel data : mLiveCourseModel.getVedioCourseFiles()) {
                    videoFile.add(new VideoFile(data.getTitle(), new File(data.getFilePath())));
                }
                mAddVideoCourseAdapter.addItems(videoFile);
            }

        }
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                if (mLiveCourseModel != null) {
                    videoFileEdit.remove(videoFile.get(position));
                    if (mLiveCourseModel.getVedioCourseFiles().size() >= position + 1) {
                        getBaseActivity().showLoading();
                        mViewModel.removeVideoResources(mLiveCourseModel.getVedioCourseFiles().get(position).getVedioCourseFileId());
                        mLiveCourseModel.getVedioCourseFiles().remove(mLiveCourseModel.getVedioCourseFiles().get(position));
                    }
                }
                videoFile.remove(position);
                mAddVideoCourseAdapter.addItems(videoFile);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mFragmentAddVideoCourseBinding.rvAddVideo);
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
    public void onSuccessVideoCourse(LiveCourseResponse mLiveCourseResponse) {
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

    @Override
    public void submitData() {
        getBaseActivity().hideKeyboard();
        if (checkData()) {
            Map<String, String> params = new HashMap<>();
            params.put("TrainerId", dataManager.getCurrentUserId() + "");
            params.put("CourseTitle", mFragmentAddVideoCourseBinding.etCourseTitle.getText().toString());
            params.put("CourseSubject", mSubjectModel.getSubjectName());
            params.put("SubjectId", mSubjectModel.getSubjectId() + "");
            params.put("Price", mFragmentAddVideoCourseBinding.etCoursePrice.getText().toString());
            params.put("StramingLiveDate", "06/09/2021 00:00:00 AM");
            params.put("Details", mFragmentAddVideoCourseBinding.etDetail.getText().toString());
            params.put("Description", mFragmentAddVideoCourseBinding.etDescription.getText().toString());
            params.put("CourseType", "Video");

            getBaseActivity().showLoading();
            if (mLiveCourseModel == null) {
                Map<String, File> videoParams = new HashMap<>();
                for (VideoFile data : videoFile) {
                    videoParams.put(data.getTitle(), data.getFile());
                }
                mViewModel.addVideoCoursesByData(params, myFile, videoParams,introVideo);
            } else {
                params.put("liveCourseId", mLiveCourseModel.getLiveCourseId() + "");
                Map<String, File> videoParams = new HashMap<>();
                for (VideoFile data : videoFileEdit) {
                    videoParams.put(data.getTitle(), data.getFile());
                }
                Log.e("VIDEOFILE", "submitData: "+videoParams.size() );
                mViewModel.updateVideoCoursesByData(params, myFile, videoParams,introVideo);
            }

        }
    }

    private boolean checkData() {
        if (mFragmentAddVideoCourseBinding.etCourseTitle.getText().toString().isEmpty()) {
            mFragmentAddVideoCourseBinding.etCourseTitle.setError(getString(R.string.enter_your_title));
            Toast.makeText(getContext(), "" + getString(R.string.enter_your_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddVideoCourseBinding.etCoursePrice.getText().toString().isEmpty()) {
            mFragmentAddVideoCourseBinding.etCoursePrice.setError(getString(R.string.enter_price));
            Toast.makeText(getContext(), "" + getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddVideoCourseBinding.etCourseSubject.getText().toString().isEmpty()) {
            mFragmentAddVideoCourseBinding.etCourseSubject.setError(getString(R.string.select_subject));
            Toast.makeText(getContext(), "" + getString(R.string.select_subject), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddVideoCourseBinding.etDescription.getText().toString().isEmpty()) {
            mFragmentAddVideoCourseBinding.etDescription.setError(getString(R.string.description));
            Toast.makeText(getContext(), "" + getString(R.string.description), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddVideoCourseBinding.etDetail.getText().toString().isEmpty()) {
            mFragmentAddVideoCourseBinding.etDetail.setError(getString(R.string.details));
            Toast.makeText(getContext(), "" + getString(R.string.details), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mLiveCourseModel == null) {
            if (myFile == null) {
                Toast.makeText(getContext(), "" + getString(R.string.upload_video_or_img), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (videoFile.size() == 0) {
            Toast.makeText(getContext(), "" + getString(R.string.upload_course_video), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void clearData() {
        myFile = null;
        videoFile = new ArrayList<>();
        mAddVideoCourseAdapter.addItems(videoFile);
        mFragmentAddVideoCourseBinding.etCourseTitle.setText("");
        mFragmentAddVideoCourseBinding.etDescription.setText("");
        mFragmentAddVideoCourseBinding.etDetail.setText("");
        mFragmentAddVideoCourseBinding.etCoursePrice.setText("");
        mFragmentAddVideoCourseBinding.etCourseSubject.setText("");
        mFragmentAddVideoCourseBinding.btnChangeImage.setText("");
        navController.popBackStack();
    }


    @Override
    public void openImgAndVideo() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 500);
        } else {
            selectImage(getContext(), 100, getString(R.string.upload_your_children_picture));
        }
    }

    @Override
    public void openIntroVideo() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 700);
        } else {
            selectVideo(getContext(), 600, getString(R.string.update_video_course));
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
                    AddVideoCourseFragment.this.mSubjectModel = mSubjectModel;
                    mFragmentAddVideoCourseBinding.etSubSubject.setText("");
                    if (!mSubjectModel.getSubSubject().isEmpty()) {
                        mFragmentAddVideoCourseBinding.etSubSubject.setVisibility(View.VISIBLE);
                        mFragmentAddVideoCourseBinding.tvSubSubject.setVisibility(View.VISIBLE);
                    } else {
                        mFragmentAddVideoCourseBinding.etSubSubject.setVisibility(View.GONE);
                        mFragmentAddVideoCourseBinding.tvSubSubject.setVisibility(View.GONE);
                    }
                    mFragmentAddVideoCourseBinding.etCourseSubject.setText(mSubjectModel.getSubjectName());
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
    public void openSubSubject() {
        mSubListFragment = ListFragment.newInstance();
        mSubListFragment.setSubject(mSubjectModel.getSubSubject(), getString(R.string.sub_category));

        mSubListFragment.setCallBack(new ListCallBack() {
            @Override
            public void callBackSubject(SubjectModel mSubjectModel) {
                AddVideoCourseFragment.this.mSubjectModel = mSubjectModel;
                mFragmentAddVideoCourseBinding.etSubSubject.setText(mSubjectModel.getSubjectName());
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

    // TODO:- Image From Camera and Gallery
    private void selectImage(Context context, int reqstCode, String title) {
        final CharSequence[] options = {getString(R.string.image), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.image))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, reqstCode + 100);
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // TODO:- Image From Camera and Gallery
    private void selectVideo(Context context, int reqstCode, String title) {
        final CharSequence[] options = {getString(R.string.video), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.video))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, reqstCode);
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
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
                selectImage(getContext(), 100, getString(R.string.upload_your_children_picture));
                break;
            case 600:
                selectVideo(getContext(), 400, getString(R.string.select_video));
                break;
            case 700:
                selectVideo(getContext(), 600, getString(R.string.select_video));
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
                mFragmentAddVideoCourseBinding.btnChangeImage.setText(imgFile.getName());
                getImgBase64(bm);
            }
        } else if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mFragmentAddVideoCourseBinding.btnChangeImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                getImgBase64(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 400 && resultCode == getBaseActivity().RESULT_OK) {
            String selectedVideoPath = getPath(data.getData());
            File file = new File(selectedVideoPath);
            if (file.exists()) {
                videoFile.get(posVideoFile).setFile(file);
                if (mLiveCourseModel != null) {
                    videoFileEdit.add(videoFile.get(posVideoFile));
                }
                mAddVideoCourseAdapter.addItems(videoFile);
            } else {
                Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
            }
        }

        else if (requestCode == 600 && resultCode == getBaseActivity().RESULT_OK) {
            String selectedVideoPath = getPath(data.getData());
            File file = new File(selectedVideoPath);
            if (file.exists()) {
                introVideo = file;
                mFragmentAddVideoCourseBinding.btnChangeIntroVideo.setText(introVideo.getName());
            } else {
                Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getImgBase64(Bitmap bitmap) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        scaled.compress(Bitmap.CompressFormat.PNG, 80, bOut);
        persistImage(scaled, "myFileVideo");
    }

    private void persistImage(Bitmap bitmap, String name) {
        File imageFile = new File(getContext().getCacheDir(), name + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            myFile = imageFile;
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getBaseActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
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