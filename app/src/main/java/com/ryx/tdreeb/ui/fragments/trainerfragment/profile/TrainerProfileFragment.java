package com.ryx.tdreeb.ui.fragments.trainerfragment.profile;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.libraries.maps.model.LatLng;
import com.ryx.tdreeb.BR;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagClickBack;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguageModel;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalityModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileResponse;
import com.ryx.tdreeb.databinding.ProfileLayoutBinding;
import com.ryx.tdreeb.databinding.TrainerMyPaymentFragmentBinding;
import com.ryx.tdreeb.di.component.FragmentComponent;
import com.ryx.tdreeb.ui.activites.map.MapActivity;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.dialogs.languagedialog.LanguageDialogFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.addchildren.AddChildrenFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.profile.ProfileFragment;
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

public class TrainerProfileFragment extends BaseFragment<ProfileLayoutBinding, TrainerProfileViewModel> implements TrainerProfileNavigator, AdapterView.OnItemSelectedListener {

    public TrainerProfileFragment() {
    }

    ProfileLayoutBinding mProfileLayoutBinding;
    @Inject
    DataManager dataManager;

    TextView tvToolBarTitle;
    ImageView backImg, profileImg;
    private NavController navController;

    LanguageDialogFragment mLanguageDialogFragment;
    List<LanguageModel> languageModelList;
    List<NationalityModel> nationalityModelList;
    LanguageModel mLanguageModel;
    NationalityModel mNationalityModel;
    boolean isUpdate = false;

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
    String myPic = "", gender = "";
    private boolean isTravel = false;

    private TrainerProfileModel trainerProfileModel;

    public static TrainerProfileFragment getInstance() {
        return new TrainerProfileFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.profile_layout;
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
        mProfileLayoutBinding = getViewDataBinding();
        getBaseActivity().showLoading();
        mViewModel.getProfile(dataManager.getCurrentUserId());

        List<String> categories = new ArrayList<String>();
        categories.add("No");
        categories.add("Yes");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        mProfileLayoutBinding.edttravel.setAdapter(dataAdapter);
        mProfileLayoutBinding.edttravel.setOnItemSelectedListener(this);
    }

    private void setUp() {

        navController = Navigation.findNavController(mProfileLayoutBinding.getRoot());
        tvToolBarTitle = mProfileLayoutBinding.getRoot().findViewById(R.id.tv_title);
        backImg = mProfileLayoutBinding.getRoot().findViewById(R.id.drawer_icon);
        profileImg = mProfileLayoutBinding.getRoot().findViewById(R.id.profile_image);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(this).load(dataManager.getImage()).apply(options).into(profileImg);

        tvToolBarTitle.setText(getString(R.string.teacher_profile));
        backImg.setImageResource(R.drawable.ic_arrow_left);
        backImg.setOnClickListener(v -> navController.popBackStack());

        isUpdate = false;
        if (trainerProfileModel != null) {
            gender = trainerProfileModel.getGender();
            if (trainerProfileModel.getGender().toLowerCase().equals("female")) {
                mProfileLayoutBinding.radioFemale.setChecked(true);
                mProfileLayoutBinding.radioMale.setChecked(false);
            } else {
                mProfileLayoutBinding.radioFemale.setChecked(false);
                mProfileLayoutBinding.radioMale.setChecked(true);
            }

            File file = new File(trainerProfileModel.getImage());

            Glide.with(this).asBitmap().load(trainerProfileModel.getImage()).apply(options).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    mProfileLayoutBinding.myImage.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadCleared(Drawable placeholder) {
                }
            });

            isTravel = trainerProfileModel.getTravelAnotherArea();
            if (isTravel) {
                mProfileLayoutBinding.edttravel.setSelection(1);
            } else {
                mProfileLayoutBinding.edttravel.setSelection(0);
            }

            mProfileLayoutBinding.btnChangeImage.setText(file.getName());
            mProfileLayoutBinding.edtFirstName.setText(trainerProfileModel.getFirstName());
            mProfileLayoutBinding.edtLastName.setText(trainerProfileModel.getLastName());
            mProfileLayoutBinding.edtLastName.setText(trainerProfileModel.getLastName());
            mProfileLayoutBinding.edtBankName.setText(trainerProfileModel.getBankName());
            mProfileLayoutBinding.edtIbanNumber.setText(trainerProfileModel.getIban());
//            mProfileLayoutBinding.edt.setText(trainerProfileModel.getPhoneNo());
            mSelectedDate = CommonUtils.dateFormater(trainerProfileModel.getDob(), "dd/mm/yyyy", "mm/dd/yyyy");
            mProfileLayoutBinding.edtdob.setText(mSelectedDate);
            mProfileLayoutBinding.edtNationality.setText(trainerProfileModel.getNationality());
            mNationalityModel = new NationalityModel();
            mLanguageModel = new LanguageModel();
            mNationalityModel.setCountryId(trainerProfileModel.getCountryId());
            if (trainerProfileModel.getLanguage() != null) {
                mLanguageModel.setLanguageId(trainerProfileModel.getLanguage().getLanguageId());
                mProfileLayoutBinding.edtLanguageSpeak.setText(trainerProfileModel.getLanguage().getLanguageName());
            }
            addressStr = trainerProfileModel.getLocation();
            mProfileLayoutBinding.edtLocation.setText(addressStr);
        }
    }


    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openImage() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionsSafely(permission, 500);
        } else {
            selectImage(getContext(), 100, getString(R.string.upload_image));
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
                    mProfileLayoutBinding.edtdob.setText(mSelectedDate);
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
                    TrainerProfileFragment.this.mLanguageModel = mLanguageModel;
                    mProfileLayoutBinding.edtLanguageSpeak.setText(mLanguageModel.getLanguageName());
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
                    TrainerProfileFragment.this.mNationalityModel = mNationalityModel;
                    mProfileLayoutBinding.edtNationality.setText(mNationalityModel.getNationalityTitle());
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
        isUpdate = true;
        int selectedId = mProfileLayoutBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioMale) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        if (checkData()) {
            getBaseActivity().showLoading();
            ChildenModel childenModel = new ChildenModel();
            childenModel.setTrainerId(dataManager.getCurrentUserId());
            childenModel.setFirstName(mProfileLayoutBinding.edtFirstName.getText().toString());
            childenModel.setLastName(mProfileLayoutBinding.edtLastName.getText().toString());
            childenModel.setIban(mProfileLayoutBinding.edtIbanNumber.getText().toString());
            childenModel.setBankName(mProfileLayoutBinding.edtBankName.getText().toString());
            childenModel.setImagePath(myPic);
            if (mLanguageModel != null) {
                childenModel.setSpokenLanguageId(mLanguageModel.getLanguageId());
            }
            childenModel.setStreetAddress(addressStr);
            if (myAddressLatLng != null) {
                childenModel.setLatitude(myAddressLatLng.latitude + "");
                childenModel.setLongitude(myAddressLatLng.longitude + "");
            }else {
                childenModel.setLatitude(trainerProfileModel.getLatitude() + "");
                childenModel.setLongitude(trainerProfileModel.getLongitude() + "");
            }
            childenModel.setStatus("STEP_ONE");
            childenModel.setParentId(dataManager.getCurrentUserId());
            childenModel.setGender(gender);
            if(mNationalityModel != null){
                childenModel.setCountryId(mNationalityModel.getCountryId());
            }
            childenModel.setTravelAnotherArea(isTravel);
            childenModel.setDob(CommonUtils.dateFormater(mSelectedDate, "mm/dd/yyyy hh:mm:ss a", "dd/mm/yyyy"));
            mViewModel.updateData(childenModel);
        }

    }

    private boolean checkData() {
        if (trainerProfileModel == null) {
            if (myPic.isEmpty()) {
                mProfileLayoutBinding.btnChangeImage.setError(getBaseActivity().getResources().getString(R.string.upload_image));
                Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.upload_image), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (mProfileLayoutBinding.edtFirstName.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtFirstName.setError(getBaseActivity().getResources().getString(R.string.enter_first_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mProfileLayoutBinding.edtBankName.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtBankName.setError(getBaseActivity().getResources().getString(R.string.enter_bank_name));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_bank_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mProfileLayoutBinding.edtIbanNumber.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtIbanNumber.setError(getBaseActivity().getResources().getString(R.string.enter_iban_number));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_iban_number), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mProfileLayoutBinding.edtdob.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtdob.setError(getBaseActivity().getResources().getString(R.string.enter_your_dob));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.enter_your_dob), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mProfileLayoutBinding.edtLocation.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mProfileLayoutBinding.edtLocation.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtLocation.setError(getBaseActivity().getResources().getString(R.string.location_select_mez));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.location_select_mez), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if(mFragmentAddChildrenBinding.edtLanguageSpeak.getText().toString().isEmpty()){
//            mFragmentAddChildrenBinding.edtLanguageSpeak.setError(getBaseActivity().getResources().getString(R.string.profile_language_hint_title));
//            Toast.makeText(getContext(), ""+getBaseActivity().getResources().getString(R.string.profile_language_hint_title), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (mProfileLayoutBinding.edtNationality.getText().toString().isEmpty()) {
            mProfileLayoutBinding.edtNationality.setError(getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title));
            Toast.makeText(getContext(), "" + getBaseActivity().getResources().getString(R.string.profile_nationality_hint_title), Toast.LENGTH_SHORT).show();
            return false;
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
    public void onSuccessData(TrainerProfileResponse mTrainerProfileResponse) {
        getBaseActivity().hideLoading();
        if (mTrainerProfileResponse.getIsSuccess()) {
            this.trainerProfileModel = mTrainerProfileResponse.getData().getTrainer();
            dataManager.setCurrentUserId(mTrainerProfileResponse.getData().getTrainer().getTrainerId());
            dataManager.setCurrentUserName(mTrainerProfileResponse.getData().getTrainer().getFirstName() + " " + mTrainerProfileResponse.getData().getTrainer().getLastName());
            dataManager.setImage(mTrainerProfileResponse.getData().getTrainer().getImage());
            if (isUpdate) {
                getBaseActivity().openDialog("SuccessFully updated", () -> {
                });
            }
            setUp();
        } else {
            Toast.makeText(getContext(), "" + mTrainerProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                selectImage(getContext(), 100, getString(R.string.upload_image));
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
                mProfileLayoutBinding.myImage.setImageBitmap(bm);
                mProfileLayoutBinding.btnChangeImage.setText(imgFile.getName());
                myPic = base64Image;
            }
        } else if (requestCode == 200 && resultCode == getBaseActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream;
                imageStream = getBaseActivity().getContentResolver().openInputStream(selectedImage);
                File imgFile = new File(getRealPathFromURI(selectedImage));
                mProfileLayoutBinding.btnChangeImage.setText(imgFile.getName());
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                mProfileLayoutBinding.myImage.setImageBitmap(bitmap);
                String base64Image = getImgBase64(bitmap);
                Log.e("Image", "onActivityResult:100 " + getRealPathFromURI(selectedImage));
                myPic = base64Image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (resultCode == 1000) {
            if (data != null) {
                addressStr = data.getStringExtra("address");
                myAddressLatLng = data.getParcelableExtra("myAddressLatLng");
                mProfileLayoutBinding.edtLocation.setText(addressStr);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("onItemSelected", "onItemSelected: " + position);
        if (position == 0) {
            isTravel = false;
        } else {
            isTravel = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
