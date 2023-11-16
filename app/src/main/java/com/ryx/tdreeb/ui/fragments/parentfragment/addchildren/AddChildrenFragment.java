package com.ryx.tdreeb.ui.fragments.parentfragment.addchildren;

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
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.databinding.FragmentAddChildrenBinding;
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


public class AddChildrenFragment extends BaseFragment<FragmentAddChildrenBinding, AddChildrenViewModel> implements AddChildrenNavigation {

    private final String TAG = "AddChildrenFragment";

    FragmentAddChildrenBinding mFragmentAddChildrenBinding;
    TextView tvToolBarTitle;
    ImageView backImg, profileImg;
    private NavController navController;

    LanguageDialogFragment mLanguageDialogFragment;
    List<LanguageModel> languageModelList;
    List<NationalityModel> nationalityModelList;
    LanguageModel mLanguageModel;
    NationalityModel mNationalityModel;

    String childrenGender = "Male";

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
    ChildenModel childenModel;

    public AddChildrenFragment() {
        // Required empty public constructor
    }

    public static AddChildrenFragment newInstance() {
        AddChildrenFragment fragment = new AddChildrenFragment();
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
        return R.layout.fragment_add_children;
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
        mFragmentAddChildrenBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        if (getArguments() != null) {
            AddChildrenFragmentArgs args = AddChildrenFragmentArgs.fromBundle(getArguments());
            childenModel = args.getChildrenData();
        }
        setUp();
    }

    private void setUp() {
        if (childenModel != null) {
            if (childenModel.getGender().toLowerCase().equals("female")) {
                mFragmentAddChildrenBinding.radioFemale.setChecked(true);
                mFragmentAddChildrenBinding.radioMale.setChecked(false);
            } else {
                mFragmentAddChildrenBinding.radioFemale.setChecked(false);
                mFragmentAddChildrenBinding.radioMale.setChecked(true);
            }
            mLanguageModel = new LanguageModel();
            mLanguageModel.setLanguageId(childenModel.getSpokenLanguageId());
            mNationalityModel = new NationalityModel();
            mNationalityModel.setCountryId(childenModel.getCountryId());

            File file = new File(childenModel.getImage());
            RequestOptions options = new RequestOptions()
                    .error(R.drawable.ic_user);
            Glide.with(this).asBitmap().load(childenModel.getImage()).apply(options).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    mFragmentAddChildrenBinding.studentImage.setImageBitmap(bitmap);
                }
                @Override
                public void onLoadCleared(Drawable placeholder) {
                }
            });
            //Glide.with(this).load(childenModel.getImage()).apply(options).into(mFragmentAddChildrenBinding.studentImage);
            mFragmentAddChildrenBinding.btnChangeImage.setText(file.getName());
            mFragmentAddChildrenBinding.edtFirstName.setText(childenModel.getFirstName());
            mFragmentAddChildrenBinding.edtLastName.setText(childenModel.getLastName());
            mFragmentAddChildrenBinding.edtPhone.setText(childenModel.getUserName());
            mSelectedDate = CommonUtils.dateFormater(childenModel.getDob(), "dd/mm/yyyy", "yyyy-mm-dd'T'HH:mm:ss");
            mFragmentAddChildrenBinding.edtdob.setText(mSelectedDate);
            mFragmentAddChildrenBinding.edtNationality.setText(childenModel.getNationality());
            mFragmentAddChildrenBinding.edtLanguageSpeak.setText(childenModel.getSpokenLanguage());
            addressStr = childenModel.getLocation();
            mFragmentAddChildrenBinding.edtLocation.setText(addressStr);
            mFragmentAddChildrenBinding.btnSave.setText(getString(R.string.profile_save_btn_title));
        }
        navController = Navigation.findNavController(mFragmentAddChildrenBinding.getRoot());

        tvToolBarTitle = mFragmentAddChildrenBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mFragmentAddChildrenBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mFragmentAddChildrenBinding.getRoot().findViewById(R.id.profile_image);

        tvToolBarTitle.setText(getString(R.string.student_profile));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);
    }

    @Override
    public void showPass() {
        if (mFragmentAddChildrenBinding.editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            mFragmentAddChildrenBinding.showPassBtn.setImageResource(R.drawable.ic_eyes_hide);
            //Show Password
            mFragmentAddChildrenBinding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mFragmentAddChildrenBinding.showPassBtn.setImageResource(R.drawable.ic_eyes);
            //Hide Password
            mFragmentAddChildrenBinding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
                    mFragmentAddChildrenBinding.edtdob.setText(mSelectedDate);
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
                    AddChildrenFragment.this.mLanguageModel = mLanguageModel;
                    mFragmentAddChildrenBinding.edtLanguageSpeak.setText(mLanguageModel.getLanguageName());
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
                    AddChildrenFragment.this.mNationalityModel = mNationalityModel;
                    mFragmentAddChildrenBinding.edtNationality.setText(mNationalityModel.getNationalityTitle());
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
    public void submitData() {

        int selectedId = mFragmentAddChildrenBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMale) {
            childrenGender = "Male";
        } else {
            childrenGender = "Female";
        }
        if (childenModel == null) {
            if (checkData()) {
                getBaseActivity().showLoading();
                ChildenModel childenModel = new ChildenModel();
                childenModel.setFirstName(mFragmentAddChildrenBinding.edtFirstName.getText().toString());
                childenModel.setLastName(mFragmentAddChildrenBinding.edtLastName.getText().toString());
                childenModel.setImage(myChildrenImage);
                childenModel.setSpokenLanguageId(mLanguageModel.getLanguageId());
                childenModel.setLocation(addressStr);
                childenModel.setLatitude(myAddressLatLng.latitude + "");
                childenModel.setLongitude(myAddressLatLng.longitude + "");
                childenModel.setUserName(mFragmentAddChildrenBinding.edtPhone.getText().toString());
                childenModel.setEmail(mFragmentAddChildrenBinding.edtPhone.getText().toString());
                childenModel.setStatus("STEP_ONE");
                childenModel.setParentId(dataManager.getCurrentUserId());
                childenModel.setGender(childrenGender);
                childenModel.setCountryId(mNationalityModel.getCountryId());
                childenModel.setPassword(mFragmentAddChildrenBinding.editPassword.getText().toString());
                childenModel.setDob(CommonUtils.dateFormater(mSelectedDate, "mm/dd/yyyy hh:mm:ss a", "dd/mm/yyyy"));
                // Log.e("DATALINK", "submitData: " + childenModel.toString());
                mViewModel.submitData(childenModel);
            }
        } else {
            if (checkData()) {

                getBaseActivity().showLoading();
                childenModel.setFirstName(mFragmentAddChildrenBinding.edtFirstName.getText().toString());
                childenModel.setLastName(mFragmentAddChildrenBinding.edtLastName.getText().toString());
                childenModel.setImage(myChildrenImage);
                childenModel.setSpokenLanguageId(mLanguageModel.getLanguageId());
                childenModel.setLocation(addressStr);
                if (myAddressLatLng!=null){
                    childenModel.setLatitude(myAddressLatLng.latitude + "");
                    childenModel.setLongitude(myAddressLatLng.longitude + "");
                }
                childenModel.setUserName(mFragmentAddChildrenBinding.edtPhone.getText().toString());
                childenModel.setEmail(mFragmentAddChildrenBinding.edtPhone.getText().toString());
                childenModel.setGender(childrenGender);
                childenModel.setCountryId(mNationalityModel.getCountryId());
                if (!mFragmentAddChildrenBinding.editPassword.getText().toString().isEmpty()) {
                    childenModel.setPassword(mFragmentAddChildrenBinding.editPassword.getText().toString());
                }
                childenModel.setDob(CommonUtils.dateFormater(mSelectedDate, "mm/dd/yyyy hh:mm:ss a", "dd/mm/yyyy"));
                Log.e(TAG, "submitData: "+childenModel.toString() );
                mViewModel.updateData(childenModel);
            }
        }

    }

    private boolean checkData() {
        if(childenModel == null){
            if (myChildrenImage.isEmpty()) {
                mFragmentAddChildrenBinding.btnChangeImage.setError(getBaseActivity().getResources().getString(R.string.upload_image));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.upload_image), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (mFragmentAddChildrenBinding.edtFirstName.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtFirstName.setError(getBaseActivity().getResources().getString(R.string.enter_first_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddChildrenBinding.edtdob.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtdob.setError(getBaseActivity().getResources().getString(R.string.enter_your_dob));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_your_dob), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddChildrenBinding.edtLocation.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddChildrenBinding.edtLocation.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if(mFragmentAddChildrenBinding.edtLanguageSpeak.getText().toString().isEmpty()){
//            mFragmentAddChildrenBinding.edtLanguageSpeak.setError(getBaseActivity().getResources().getString(R.string.profile_language_hint_title));
//            Toast.makeText(getContext(), ""+getBaseActivity().getResources().getString(R.string.profile_language_hint_title), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (mFragmentAddChildrenBinding.edtNationality.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtNationality.setError(getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFragmentAddChildrenBinding.edtPhone.getText().toString().isEmpty()) {
            mFragmentAddChildrenBinding.edtPhone.setError(getBaseActivity().getResources().getString(R.string.type_user_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.type_user_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (childenModel == null) {
            if (mFragmentAddChildrenBinding.editPassword.getText().toString().isEmpty()) {
                mFragmentAddChildrenBinding.editPassword.setError(getBaseActivity().getResources().getString(R.string.enter_password));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!mFragmentAddChildrenBinding.editPassword.getText().toString().isEmpty()) {
            if (!mFragmentAddChildrenBinding.editPassword.getText().toString().equals(mFragmentAddChildrenBinding.edtConfirmPassword.getText().toString())) {
                mFragmentAddChildrenBinding.edtConfirmPassword.setError(getBaseActivity().getResources().getString(R.string.not_match_password));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.not_match_password), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
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
    public void onSuccessAddChildResponse(AddChildResponse mAddChildResponse) {
        getBaseActivity().hideLoading();
        if (mAddChildResponse.getCode() == 200) {
            if (childenModel == null) {
                getBaseActivity().openDialog("SuccessFully Added", () -> {
                    ClearData();
                });
            } else {
                getBaseActivity().openDialog("SuccessFully updated", () -> {
                    //ClearData();
                });
            }
        } else {
            Toast.makeText(getContext(), "" + mAddChildResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ClearData() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load("").apply(options).into(mFragmentAddChildrenBinding.studentImage);
        mFragmentAddChildrenBinding.btnChangeImage.setText("");
        mFragmentAddChildrenBinding.edtLastName.setText("");
        mFragmentAddChildrenBinding.edtPhone.setText("");
        mFragmentAddChildrenBinding.editPassword.setText("");
        mFragmentAddChildrenBinding.edtConfirmPassword.setText("");
        mFragmentAddChildrenBinding.edtFirstName.setText("");
        mFragmentAddChildrenBinding.edtdob.setText("");
        mFragmentAddChildrenBinding.edtLocation.setText("");
        mFragmentAddChildrenBinding.edtLanguageSpeak.setText("");
        mFragmentAddChildrenBinding.edtNationality.setText("");
        navController.popBackStack();
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
                mFragmentAddChildrenBinding.studentImage.setImageBitmap(bm);
                mFragmentAddChildrenBinding.btnChangeImage.setText(imgFile.getName());
                myChildrenImage = base64Image;
                Log.e("Image", "onActivityResult:100 " + base64Image);
            }
        } else if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mFragmentAddChildrenBinding.btnChangeImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                mFragmentAddChildrenBinding.studentImage.setImageBitmap(bitmap);
                String base64Image = getImgBase64(bitmap);
                Log.e("Image", "onActivityResult:100 " + getRealPathFromURI(selectedImage));
                myChildrenImage = base64Image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 1000) {
            if (data != null) {
                addressStr = data.getStringExtra("address");
                myAddressLatLng = data.getParcelableExtra("myAddressLatLng");
                mFragmentAddChildrenBinding.edtLocation.setText(addressStr);
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