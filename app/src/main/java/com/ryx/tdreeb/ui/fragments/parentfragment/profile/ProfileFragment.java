package com.ryx.tdreeb.ui.fragments.parentfragment.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.libraries.maps.model.LatLng;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;
import com.ryx.tdreeb.data.model.api.registration.SendRegistrationData;
import com.ryx.tdreeb.data.model.api.registration.UserModel;
import com.ryx.tdreeb.databinding.FragmentProfileBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.activites.map.MapActivity;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.languagedialog.LanguageDialogFragment;
import com.ryx.tdreeb.utils.CommonUtils;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

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


public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator {

    FragmentProfileBinding mFragmentProfileBinding;
    TextView tvToolBarTitle;
    ImageView backImg, profileImg;
    private NavController navController;

    LanguageDialogFragment mLanguageDialogFragment;
    List<LanguageModel> languageModelList;
    List<NationalityModel> nationalityModelList;
    LanguageModel mLanguageModel;
    NationalityModel mNationalityModel;

    String gender = "Male";

    @Inject
    DataManager dataManager;

    //TODO: Date
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);
    String mSelectedDate = "", mSelectedDateserver = "";

    private LatLng myAddressLatLng;
    private String addressStr;

    String mCurrentPhotoPath;
    private String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    String myChildrenImage = "";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        return R.layout.fragment_profile;
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
        mFragmentProfileBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {

        if(dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()){
            mFragmentProfileBinding.tvPersonalInfo.setText(getString(R.string.parent_details));
        }else{
            mFragmentProfileBinding.tvPersonalInfo.setText(getString(R.string.student_details));
        }

        navController = Navigation.findNavController(mFragmentProfileBinding.getRoot());
        tvToolBarTitle = mFragmentProfileBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentProfileBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentProfileBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.profile_title));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        UserModel model = dataManager.getUserProfile();
        if (model != null) {
            if (model.getGender() != null && model.getGender().toLowerCase().equals("female")) {
                mFragmentProfileBinding.radioFemale.setChecked(true);
                mFragmentProfileBinding.radioMale.setChecked(false);
            } else {
                mFragmentProfileBinding.radioFemale.setChecked(false);
                mFragmentProfileBinding.radioMale.setChecked(true);
            }

            mFragmentProfileBinding.edtFirstName.setText(model.getFirstName());
            mFragmentProfileBinding.edtLastName.setText(model.getLastName());

            mLanguageModel = new LanguageModel();
            mLanguageModel.setLanguageId(model.getSpokenLanguageId());
            mNationalityModel = new NationalityModel();
            mNationalityModel.setCountryId(model.getCountryId());

            File file = new File(model.getImage());
            Glide.with(this).asBitmap().load(model.getImage()).apply(options).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    mFragmentProfileBinding.myImage.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadCleared(Drawable placeholder) {
                }
            });
            //Glide.with(this).load(childenModel.getImage()).apply(options).into(mFragmentAddChildrenBinding.studentImage);
            mFragmentProfileBinding.btnChangeImage.setText(file.getName());
            mFragmentProfileBinding.edtFirstName.setText(model.getFirstName());
            mFragmentProfileBinding.edtLastName.setText(model.getLastName());
            mFragmentProfileBinding.edtPass.setText(model.getPhoneNo());
            mSelectedDate = CommonUtils.dateFormater(model.getDob(), "dd/mm/yyyy", "mm/dd/yyyy");
            mFragmentProfileBinding.edtdob.setText(mSelectedDate);
            mFragmentProfileBinding.edtNationality.setText(model.getNationality());
            if (model.getGetLanguage() != null) {
                mFragmentProfileBinding.edtLanguageSpeak.setText(model.getGetLanguage().getLanguageName());
            }
            addressStr = model.getLocation();
            mFragmentProfileBinding.edtLocation.setText(addressStr);
        }
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void showPass() {
        if (mFragmentProfileBinding.editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            mFragmentProfileBinding.showPassBtn.setImageResource(R.drawable.ic_eyes_hide);
            //Show Password
            mFragmentProfileBinding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mFragmentProfileBinding.showPassBtn.setImageResource(R.drawable.ic_eyes);
            //Hide Password
            mFragmentProfileBinding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void openImage() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 500);
        } else {
            selectImage(getContext(), 100, getString(R.string.upload_your_children_picture));
        }
    }

    @Override
    public void openDOB() {
        new SpinnerDatePickerDialogBuilder()
                .context(getContext())
                .callback((view, year, monthOfYear, dayOfMonth) -> {
                    if ((monthOfYear + 1) < 10) {
                        if (dayOfMonth < 10) {
                            mSelectedDate = "0" + dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year;
                            mSelectedDateserver = year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth;
                        } else {
                            mSelectedDate = dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year;
                            mSelectedDateserver = year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }
                    } else {
                        mSelectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        mSelectedDateserver = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                    mFragmentProfileBinding.edtdob.setText(mSelectedDate);
                })
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(year, month, day)
                .showDaySpinner(true)
                .maxDate(year, month, day)
                .minDate(year - 50, 0, 1)
                .build()
                .show();
    }

    @Override
    public void openLanguage() {
        if (languageModelList == null) {
            getBaseActivity().showLoading();
            mViewModel.getLanguages();
        } else {
            mLanguageDialogFragment = new LanguageDialogFragment();
            mLanguageDialogFragment.setData(languageModelList, getString(R.string.language));
            mLanguageDialogFragment.setCallBack(new LanguagClickBack() {
                @Override
                public void onClickItem(LanguageModel mLanguageModel) {
                    ProfileFragment.this.mLanguageModel = mLanguageModel;
                    mFragmentProfileBinding.edtLanguageSpeak.setText(mLanguageModel.getLanguageName());
                }

                @Override
                public void onClickItemNationality(NationalityModel mNationalityModel) {
                }
            });
            mLanguageDialogFragment.show(getParentFragmentManager());
        }

    }

    @Override
    public void openNationality() {
        if (nationalityModelList == null) {
            getBaseActivity().showLoading();
            mViewModel.getNationalities();
        } else {
            mLanguageDialogFragment = new LanguageDialogFragment();
            mLanguageDialogFragment.setDataNationality(nationalityModelList, getString(R.string.nationality));
            mLanguageDialogFragment.setCallBack(new LanguagClickBack() {
                @Override
                public void onClickItem(LanguageModel mLanguageModel) {
                }

                @Override
                public void onClickItemNationality(NationalityModel mNationalityModel) {
                    ProfileFragment.this.mNationalityModel = mNationalityModel;
                    mFragmentProfileBinding.edtNationality.setText(mNationalityModel.getNationalityTitle());
                }
            });
            mLanguageDialogFragment.show(getParentFragmentManager());
        }
    }

    @Override
    public void openMap() {
        startActivityForResult(MapActivity.newIntent(getBaseActivity()), 1000);
    }

    @Override
    public void updateProfile() {
        hideKeyboard();

        int selectedId = mFragmentProfileBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMale) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        if (checkData()) {
            getBaseActivity().showLoading();
            SendRegistrationData mSendRegistrationData = new SendRegistrationData();
            UserModel model = dataManager.getUserProfile();
            mSendRegistrationData.setFirstName(mFragmentProfileBinding.edtFirstName.getText().toString());
            mSendRegistrationData.setLastName(mFragmentProfileBinding.edtLastName.getText().toString());
            mSendRegistrationData.setImage(myChildrenImage);
            mSendRegistrationData.setSpokenLanguageId(mLanguageModel.getLanguageId());
            List<Integer> SpokenLanguageIds = new ArrayList<>();
            SpokenLanguageIds.add(mLanguageModel.getLanguageId());
            mSendRegistrationData.setSpokenLanguageIds(SpokenLanguageIds);
            mSendRegistrationData.setLocation(addressStr);
            mSendRegistrationData.setEmail("pritom@royex.net");
            if (myAddressLatLng != null) {
                mSendRegistrationData.setLatitude(myAddressLatLng.latitude + "");
                mSendRegistrationData.setLongitude(myAddressLatLng.longitude + "");
            } else {
                mSendRegistrationData.setLatitude(model.getLatitude());
                mSendRegistrationData.setLongitude(model.getLongitude());
            }
            mSendRegistrationData.setPhoneNo(mFragmentProfileBinding.edtPass.getText().toString());
            mSendRegistrationData.setGender(gender);
            mSendRegistrationData.setCountryId(mNationalityModel.getCountryId());
            if (!mFragmentProfileBinding.editPassword.getText().toString().isEmpty()) {
                mSendRegistrationData.setPassword(mFragmentProfileBinding.editPassword.getText().toString());
            }
            mSendRegistrationData.setDob(CommonUtils.dateFormater(mSelectedDate, "mm/dd/yyyy hh:mm:ss a", "dd/mm/yyyy"));

            if (dataManager.getUserType() == DataManager.UserInMode.PARENT_MODE.getType()) {
                mSendRegistrationData.setParentId(model.getParentId());
                mViewModel.updateProfile(mSendRegistrationData);
            } else {
                mSendRegistrationData.setStudentId(model.getStudentId());
                mViewModel.updateStudentProfile(mSendRegistrationData);
            }
            Log.e("DATAPASS", "updateProfile: " + mSendRegistrationData.toString());
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
    public void onSuccessLanguageResponse(LanguagesResponse mLanguagesResponse) {
        getBaseActivity().hideLoading();
        if (mLanguagesResponse.getData().getLanguages() != null) {
            languageModelList = mLanguagesResponse.getData().getLanguages();
        } else {
            languageModelList = new ArrayList<>();
        }
        openLanguage();
    }

    @Override
    public void onSuccessNationalitiesResponse(NationalitiesResponse mNationalitiesResponse) {
        getBaseActivity().hideLoading();
        if (mNationalitiesResponse.getData().getNationalities() != null) {
            nationalityModelList = mNationalitiesResponse.getData().getNationalities();
        } else {
            nationalityModelList = new ArrayList<>();
        }
        openNationality();
    }

    @Override
    public void onSuccessProfileUpdate(RegistrationResponse mRegistrationResponse) {
        getBaseActivity().hideLoading();
        if (mRegistrationResponse.getCode() == 200) {
            if(mRegistrationResponse.getData().getParent()!=null){
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getParent().getFirstName() + " " + mRegistrationResponse.getData().getParent().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getParent().getImage());
                dataManager.setUserProfile(mRegistrationResponse.getData().getParent());
                getBaseActivity().openDialog("SuccessFully updated", () -> {
                    setUp();
                });
            }else{
                dataManager.setCurrentUserName(mRegistrationResponse.getData().getStudent().getFirstName() + " " + mRegistrationResponse.getData().getStudent().getLastName());
                dataManager.setImage(mRegistrationResponse.getData().getStudent().getImage());
                dataManager.setUserProfile(mRegistrationResponse.getData().getStudent());
                getBaseActivity().openDialog("SuccessFully updated", () -> {
                    setUp();
                });
            }

        } else {
            Toast.makeText(getContext(), "" + mRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkData() {
        UserModel model = dataManager.getUserProfile();
        if (model == null) {
            if (myChildrenImage.isEmpty()) {
                mFragmentProfileBinding.btnChangeImage.setError(getBaseActivity().getResources().getString(R.string.upload_image));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.upload_image), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (mFragmentProfileBinding.edtFirstName.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtFirstName.setError(getBaseActivity().getResources().getString(R.string.enter_first_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentProfileBinding.edtdob.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtdob.setError(getBaseActivity().getResources().getString(R.string.enter_your_dob));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_your_dob), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentProfileBinding.edtLocation.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentProfileBinding.edtLocation.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if(mFragmentAddChildrenBinding.edtLanguageSpeak.getText().toString().isEmpty()){
//            mFragmentAddChildrenBinding.edtLanguageSpeak.setError(getBaseActivity().getResources().getString(R.string.profile_language_hint_title));
//            Toast.makeText(getContext(), ""+getBaseActivity().getResources().getString(R.string.profile_language_hint_title), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (mFragmentProfileBinding.edtNationality.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtNationality.setError(getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentProfileBinding.edtPass.getText().toString().isEmpty()) {
            mFragmentProfileBinding.edtPass.setError(getBaseActivity().getResources().getString(R.string.type_user_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.type_user_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (model == null) {
            if (mFragmentProfileBinding.editPassword.getText().toString().isEmpty()) {
                mFragmentProfileBinding.editPassword.setError(getBaseActivity().getResources().getString(R.string.enter_password));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!mFragmentProfileBinding.editPassword.getText().toString().isEmpty()) {
            if (!mFragmentProfileBinding.editPassword.getText().toString().equals(mFragmentProfileBinding.edtConfirmPassword.getText().toString())) {
                mFragmentProfileBinding.edtConfirmPassword.setError(getBaseActivity().getResources().getString(R.string.not_match_password));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.not_match_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
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
                selectImage(getContext(), 100, getString(R.string.upload_your_children_picture));
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
                mFragmentProfileBinding.myImage.setImageBitmap(bm);
                mFragmentProfileBinding.btnChangeImage.setText(imgFile.getName());
                myChildrenImage = base64Image;
                Log.e("Image", "onActivityResult:100 " + base64Image);
            }
        } else if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mFragmentProfileBinding.btnChangeImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                mFragmentProfileBinding.myImage.setImageBitmap(bitmap);
                String base64Image = getImgBase64(bitmap);
                Log.e("Image", "onActivityResult:100 " + base64Image);
                myChildrenImage = base64Image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 1000) {
            if (data != null) {
                addressStr = data.getStringExtra("address");
                myAddressLatLng = data.getParcelableExtra("myAddressLatLng");
                mFragmentProfileBinding.edtLocation.setText(addressStr);
            }
        } else {
            if (data != null) {
//                dateStr = data.getStringExtra("dateStr");
//                timeStr = data.getStringExtra("timeStr");
//                mActivityCarServiceBinding.etBookingAnAppointmentDate.setText(CommonUtils.dateFormater(dateStr, "dd MMM, yyyy", "yyyy-MM-dd"));
//                mActivityCarServiceBinding.etBookingAnAppointmentTime.setText(timeStr);
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