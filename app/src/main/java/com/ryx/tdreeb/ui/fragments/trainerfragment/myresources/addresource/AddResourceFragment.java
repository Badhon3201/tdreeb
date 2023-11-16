package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource;

import android.Manifest;
import android.app.Activity;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;

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
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.ResourceFileUploadAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculaResponse;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculumModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradeModel;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradesResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceDetailModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectModel;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.databinding.FragmentAddResourceBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListCallBack;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListFragment;
import com.ryx.tdreeb.utils.BindingUtils;
import com.ryx.tdreeb.utils.CommonUtils;
import com.ryx.tdreeb.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class AddResourceFragment extends BaseFragment<FragmentAddResourceBinding,
        AddResourceViewModel> implements AddResourceNavigator {

    public AddResourceFragment() {
        // Required empty public constructor
    }

    FragmentAddResourceBinding mFragmentAddResourceBinding;
    private ImageView profileImage, drawerIcon;
    private TextView tvTitle;

    @Inject
    ResourceFileUploadAdapter mResourceFileUploadAdapter;
    @Inject
    FlexboxLayoutManager mFlexboxLayoutManager;
    @Inject
    DataManager dataManager;

    private NavController navController;

    List<File> fileList;
    List<File> fileListEidt;

    List<SubjectModel> listSubject;
    SubjectModel mSubjectModel;
    SubjectModel mSubjectModelTwo;

    List<CurriculumModel> listCurriculumModel;
    CurriculumModel mCurriculumModel;

    List<GradeModel> listGrade;
    GradeModel mGradeModel;
    ListFragment mListFragment;
    ListFragment mSubListFragment;

    ResourceModel mResourceModel;

    String mCurrentPhotoPath, base64File = "";
    String myPic = "";
    private String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static AddResourceFragment newInstance() {
        AddResourceFragment fragment = new AddResourceFragment();
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
        return R.layout.fragment_add_resource;
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
        mFragmentAddResourceBinding = getViewDataBinding();
        if (getArguments() != null) {
            AddResourceFragmentArgs args = AddResourceFragmentArgs.fromBundle(getArguments());
            mResourceModel = args.getMyResources();
        }
        setUp();
    }

    private void setUp() {

        //TODO:- Action Button
        navController = Navigation.findNavController(mFragmentAddResourceBinding.getRoot());
        profileImage = mFragmentAddResourceBinding.getRoot().findViewById(R.id.profile_image);
        drawerIcon = mFragmentAddResourceBinding.getRoot().findViewById(R.id.drawer_icon);
        tvTitle = mFragmentAddResourceBinding.getRoot().findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.upload_resource));
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImage);
        drawerIcon.setImageResource(R.drawable.ic_arrow_left);
        drawerIcon.setOnClickListener(v -> {
            navController.popBackStack();
        });

        fileList = new ArrayList<>();
        mFlexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        mFlexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mFragmentAddResourceBinding.rvResource.setLayoutManager(mFlexboxLayoutManager);
        mFragmentAddResourceBinding.rvResource.addItemDecoration(new EqualSpacingItemDecoration(10));
        mFragmentAddResourceBinding.rvResource.setItemAnimator(new DefaultItemAnimator());
        mFragmentAddResourceBinding.rvResource.setAdapter(mResourceFileUploadAdapter);

        mResourceFileUploadAdapter.setListener(new AddResourceCallBack() {
            @Override
            public void onClickFileRemoveItem(int pos, File file) {
                if (mResourceModel != null && mResourceModel.getResourceDetails() != null) {
                    if (mResourceModel.getResourceDetails().size() >= pos + 1) {
                        getBaseActivity().showLoading();
                        mViewModel.removeResourceFile(mResourceModel.getResourceDetails().get(pos).getTrainerResourecDetailsId());
                        mResourceModel.getResourceDetails().remove(mResourceModel.getResourceDetails().get(pos));
                    }
                }
                if (fileListEidt!= null && fileListEidt.size() > 0) {
                    fileListEidt.remove(file);
                }
                fileList.remove(file);
                mResourceFileUploadAdapter.addItems(fileList);
            }

            @Override
            public void onClickEdit(ResourceModel mResourceModel) {

            }

            @Override
            public void onClickDelete(ResourceModel mResourceModel) {

            }
        });

        if (mResourceModel != null) {
            fileListEidt = new ArrayList<>();
            mFragmentAddResourceBinding.etCourseTitle.setText(mResourceModel.getResourceName());
            mFragmentAddResourceBinding.etCurriculum.setText(mResourceModel.getCurriculum().getCurriculumName());
            if (mResourceModel.getSubject() != null) {
                mFragmentAddResourceBinding.etSubject.setText(mResourceModel.getSubject().getSubjectName());
                //Log.e("mResourceModel", "setUp: "+mResourceModel.getSubject().getSubSubjectResponse().toString() );
                if (mResourceModel.getSubject().getSubSubjectResponse() != null) {
                    mFragmentAddResourceBinding.tvSubSubject.setVisibility(View.VISIBLE);
                    mFragmentAddResourceBinding.etSubSubject.setVisibility(View.VISIBLE);
                    mFragmentAddResourceBinding.etSubSubject.setText(mResourceModel.getSubject().getSubSubjectResponse().getSubjectName());
                } else {
                    mFragmentAddResourceBinding.tvSubSubject.setVisibility(View.GONE);
                    mFragmentAddResourceBinding.etSubSubject.setVisibility(View.GONE);
                }
            }
            mFragmentAddResourceBinding.etGrade.setText(mResourceModel.getGrade().getGradeName());
            mFragmentAddResourceBinding.etPrice.setText(mResourceModel.getPrice().toString());
            mFragmentAddResourceBinding.etDetail.setText(mResourceModel.getDetails());
            mFragmentAddResourceBinding.btnSave.setText(getString(R.string.update));

            if (mResourceModel.getResourceImage() != null) {
                File file = new File(mResourceModel.getResourceImage());
                BindingUtils.setImageUrlTwo(mFragmentAddResourceBinding.resImageT, mResourceModel.getResourceImage());
                mFragmentAddResourceBinding.btnResourceImage.setText(file.getName());
            }

            AddResourceFragment.this.mCurriculumModel = mResourceModel.getCurriculum();
            AddResourceFragment.this.mGradeModel = mResourceModel.getGrade();
            AddResourceFragment.this.mSubjectModel = mResourceModel.getSubject();

            fileList.clear();
            List<ResourceDetailModel> resourseDeatails = mResourceModel.getResourceDetails();
            if (mResourceModel.getResourceDetails() != null) {
                for (int i = 0; i < mResourceModel.getResourceDetails().size(); i++) {
                    fileList.add(new File(resourseDeatails.get(i).getFilePath()));
                }
                mResourceFileUploadAdapter.addItems(fileList);
            }

        } else {
            mFragmentAddResourceBinding.btnSave.setText(getString(R.string.add));
        }
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openFile() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 500);
        } else {
            selectFile(getContext(), 100, getString(R.string.upload_resource));
        }
    }

    @Override
    public void openImage() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 600);
        } else {
            selectImage(getContext(), 100, getString(R.string.upload_image));
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
                    AddResourceFragment.this.mSubjectModel = mSubjectModel;
                    mFragmentAddResourceBinding.etSubSubject.setText("");
                    if (!mSubjectModel.getSubSubject().isEmpty()) {
                        mFragmentAddResourceBinding.tvSubSubject.setVisibility(View.VISIBLE);
                        mFragmentAddResourceBinding.etSubSubject.setVisibility(View.VISIBLE);
                    } else {
                        mFragmentAddResourceBinding.tvSubSubject.setVisibility(View.GONE);
                        mFragmentAddResourceBinding.etSubSubject.setVisibility(View.GONE);
                    }
                    mFragmentAddResourceBinding.etSubject.setText(mSubjectModel.getSubjectName());
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
        mSubListFragment.setSubject(AddResourceFragment.this.mSubjectModel.getSubSubject(), getString(R.string.sub_category));

        mSubListFragment.setCallBack(new ListCallBack() {
            @Override
            public void callBackSubject(SubjectModel mSubjectModel) {
                AddResourceFragment.this.mSubjectModel = mSubjectModel;
                mFragmentAddResourceBinding.etSubSubject.setText(mSubjectModel.getSubjectName());
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

    @Override
    public void openCurriculum() {
        if (listCurriculumModel == null) {
            getBaseActivity().showLoading();
            mViewModel.getCurriculum();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setCurriculum(listCurriculumModel, getString(R.string.curriculum));
            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {
                    AddResourceFragment.this.mCurriculumModel = mCurriculumModel;
                    mFragmentAddResourceBinding.etCurriculum.setText(mCurriculumModel.getCurriculumName());
                }

                @Override
                public void callGrade(GradeModel mGradeModel) {

                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openGrade() {
        if (listGrade == null) {
            getBaseActivity().showLoading();
            mViewModel.getGrade();
        } else {
            mListFragment = ListFragment.newInstance();
            mListFragment.setGrade(listGrade, getString(R.string.grade));
            mListFragment.setCallBack(new ListCallBack() {
                @Override
                public void callBackSubject(SubjectModel mSubjectModel) {
                }

                @Override
                public void callCurriculum(CurriculumModel mCurriculumModel) {

                }

                @Override
                public void callGrade(GradeModel mGradeModel) {
                    AddResourceFragment.this.mGradeModel = mGradeModel;
                    mFragmentAddResourceBinding.etGrade.setText(mGradeModel.getGradeName());
                }
            });
            mListFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void submit() {
        if (checkData()) {
            Map<String, String> params = new HashMap<>();
            params.put("TrainerId", dataManager.getCurrentUserId() + "");
            params.put("ResourceName", mFragmentAddResourceBinding.etCourseTitle.getText().toString());
            params.put("Details", mFragmentAddResourceBinding.etDetail.getText().toString());
            params.put("CurriculumId", mCurriculumModel.getCurriculumId() + "");
            params.put("GradeId", mGradeModel.getGradeId() + "");
            params.put("SubjectId", mSubjectModel.getSubjectId() + "");
            params.put("ResourceImage", myPic);
            params.put("Price", mFragmentAddResourceBinding.etPrice.getText().toString());
            getBaseActivity().showLoading();
            if (mResourceModel != null) {
                params.put("TrainerResourcesId", mResourceModel.getTrainerResourcesId().toString());
                mViewModel.updateResourceData(params, fileListEidt);
            } else {
                mViewModel.addResource(params, fileList);
            }

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
    public void onSuccessCurriculumResponse(CurriculaResponse mCurriculaResponse) {
        getBaseActivity().hideLoading();
        if (mCurriculaResponse.getIsSuccess()) {
            listCurriculumModel = mCurriculaResponse.getData().getCurriculums();
            openCurriculum();
        } else {
            Toast.makeText(getContext(), "" + mCurriculaResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessGradeResponse(GradesResponse mGradesResponse) {
        getBaseActivity().hideLoading();
        if (mGradesResponse.getIsSuccess()) {
            listGrade = mGradesResponse.getData().getGrades();
            openGrade();
        } else {
            Toast.makeText(getContext(), "" + mGradesResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessAddResource(LiveCourseResponse mLiveCourseResponse) {
        getBaseActivity().hideLoading();
        getBaseActivity().openDialog(mLiveCourseResponse.getMessage(), () -> {
            if (mLiveCourseResponse.getIsSuccess()) {
//                if (mResourceModel == null) {
                clearData();
//                }
            }
        });
    }

    @Override
    public void onSuccessGetResource(ResourceResponse mResourceResponse) {
        getBaseActivity().hideLoading();
        getBaseActivity().openDialog(mResourceResponse.getMessage(), () -> {
        });
    }

    private boolean checkData() {

        if (myPic == "" && mResourceModel == null) {
            mFragmentAddResourceBinding.btnResourceImage.setError(getString(R.string.upload_resource_image));
            Toast.makeText(getContext(), "" + getString(R.string.upload_resource_image), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fileList.isEmpty()) {
            mFragmentAddResourceBinding.btnChangeImage.setError(getString(R.string.upload_file));
            Toast.makeText(getContext(), "" + getString(R.string.upload_file), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etCourseTitle.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etCourseTitle.setError(getString(R.string.enter_your_title));
            Toast.makeText(getContext(), "" + getString(R.string.enter_your_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etSubject.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etSubject.setError(getString(R.string.select_subject));
            Toast.makeText(getContext(), "" + getString(R.string.select_subject), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mSubjectModel != null && mSubjectModel.getSubSubject() != null && !mSubjectModel.getSubSubject().isEmpty()) {
            mFragmentAddResourceBinding.etSubSubject.setError(getString(R.string.select_sub_subject));
            Toast.makeText(getContext(), "" + getString(R.string.select_sub_subject), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etCurriculum.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etCurriculum.setError(getString(R.string.select_curriculum));
            Toast.makeText(getContext(), "" + getString(R.string.select_subject), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etGrade.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etGrade.setError(getString(R.string.select_grade));
            Toast.makeText(getContext(), "" + getString(R.string.select_grade), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etPrice.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etPrice.setError(getString(R.string.type_selling_price_here));
            Toast.makeText(getContext(), "" + getString(R.string.type_selling_price_here), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddResourceBinding.etDetail.getText().toString().isEmpty()) {
            mFragmentAddResourceBinding.etDetail.setError(getString(R.string.details));
            Toast.makeText(getContext(), "" + getString(R.string.details), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearData() {
        fileList = new ArrayList<>();
        mResourceFileUploadAdapter.addItems(fileList);
        mFragmentAddResourceBinding.etCourseTitle.setText("");
        mFragmentAddResourceBinding.etPrice.setText("");
        mFragmentAddResourceBinding.etDetail.setText("");
        mFragmentAddResourceBinding.etCurriculum.setText("");
        mFragmentAddResourceBinding.etSubject.setText("");
        mFragmentAddResourceBinding.etGrade.setText("");
        mFragmentAddResourceBinding.etSubSubject.setText("");
        mGradeModel = null;
        mSubjectModel = null;
        mCurriculumModel = null;
        navController.popBackStack();

    }

    // TODO:- Image From Camera and Gallery
    private void selectFile(Context context, int reqstCode, String title) {
        final CharSequence[] options = {getString(R.string.upload_file), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.upload_file))) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, reqstCode + 100);
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    // TODO:- Image From Camera and Gallery
    private void selectImage(Context context, int reqstCode, String title) {
        final CharSequence[] options = {getString(R.string.take_photo), getString(R.string.from_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.take_photo))) {
                try {
                    dispatchTakePictureIntent(reqstCode);
                } catch (IOException e) {
                }
            } else if (options[item].equals(getString(R.string.from_gallery))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, reqstCode + 200);
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
                selectFile(getContext(), 100, getString(R.string.upload_resource));
                break;
            case 600:
                selectImage(getContext(), 100, getString(R.string.upload_resource));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri path = data.getData();
                    try {
                        String pathFromUri = FileUtils.getPathFromUri(getContext(), path);
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(path);
                        byte[] pdfBytes = new byte[inputStream.available()];
                        inputStream.read(pdfBytes);
                        base64File = Base64.encodeToString(pdfBytes, Base64.DEFAULT);
                        File fileName = new File(pathFromUri);
                        if (fileName.exists()) {
                            String extension = fileName.getAbsolutePath().substring(fileName.getAbsolutePath().lastIndexOf("."));
                            File file = CommonUtils.saveFile(getContext(), base64File, fileName.getName().replace(extension, ""), extension);
                            if (file.exists()) {
                                if (mResourceModel != null) {
                                    fileListEidt.add(file);
                                }
                                fileList.add(file);
                                mResourceFileUploadAdapter.addItems(fileList);
                            }
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (requestCode == 100 && resultCode == getBaseActivity().RESULT_OK) {
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            File imgFile = new File(imageUri.getPath());
            if (imgFile.exists() && imgFile.length() > 0) {
                Bitmap bm = BitmapFactory.decodeFile(imageUri.getPath());
                String base64Image = getImgBase64(bm);
                mFragmentAddResourceBinding.resImageT.setImageBitmap(bm);
                mFragmentAddResourceBinding.btnResourceImage.setText(imgFile.getName());
                myPic = base64Image;
            }
        } else if (requestCode == 300 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mFragmentAddResourceBinding.btnResourceImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                mFragmentAddResourceBinding.resImageT.setImageBitmap(bitmap);
                String base64Image = getImgBase64(bitmap);
                Log.e("Image", "onActivityResult:100 " + getRealPathFromURI(selectedImage));
                myPic = base64Image;
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