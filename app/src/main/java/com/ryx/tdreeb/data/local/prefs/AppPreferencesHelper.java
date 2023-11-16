package com.ryx.tdreeb.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.UserModel;
import com.ryx.tdreeb.di.PreferenceInfo;
import com.ryx.tdreeb.utils.AppConstants;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_ACCESS_LANGUAGE = "PREF_KEY_ACCESS_LANGUAGE";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_USER_PHONE = "PREF_KEY_USER_PHONE";

    private static final String PREF_KEY_USER_PASSWORD = "PREF_KEY_USER_PASSWORD";

    private static final String PREF_KEY_USER_DOB = "PREF_KEY_USER_DOB";

    private static final String PREF_KEY_USER_GENDER = "PREF_KEY_USER_GENDER";

    private static final String PREF_KEY_USER_BLOOD = "PREF_KEY_USER_BLOOD";

    private static final String PREF_KEY_USER_QUALIFICATION = "PREF_KEY_USER_QUALIFICATION";

    private static final String PREF_KEY_USER_IMAGE = "PREF_KEY_USER_IMAGE";

    private static final String PREF_KEY_USER_TYPE = "PREF_KEY_USER_TYPE";

    private static final String PREF_KEY_USER_PROFILE = "PREF_KEY_USER_PROFILE";

    private static final String PREF_KEY_UNDER_EIGHTEEN = "PREF_KEY_UNDER_EIGHTEEN";

    private final SharedPreferences mPrefs;

    Gson gson = new Gson();

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public int getCurrentUserId() {
        return mPrefs.getInt(PREF_KEY_CURRENT_USER_ID, 0);
    }

    @Override
    public void setCurrentUserId(int userId) {
//        long id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putInt(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public String getPhone() {
        return mPrefs.getString(PREF_KEY_USER_PHONE, null);
    }

    @Override
    public void setPhone(String phone) {
        mPrefs.edit().putString(PREF_KEY_USER_PHONE, phone).apply();
    }

    @Override
    public String getPassword() {
        return mPrefs.getString(PREF_KEY_USER_PASSWORD, null);
    }

    @Override
    public void setPassword(String password) {
        mPrefs.edit().putString(PREF_KEY_USER_PASSWORD, password).apply();
    }

    @Override
    public String getDOB() {
        return mPrefs.getString(PREF_KEY_USER_DOB, null);
    }

    @Override
    public void setDOB(String DOB) {
        mPrefs.edit().putString(PREF_KEY_USER_DOB, DOB).apply();
    }

    @Override
    public String getGender() {
        return mPrefs.getString(PREF_KEY_USER_GENDER, null);
    }

    @Override
    public void setGender(String Gender) {
        mPrefs.edit().putString(PREF_KEY_USER_GENDER, Gender).apply();
    }

    @Override
    public String getBlood() {
        return mPrefs.getString(PREF_KEY_USER_BLOOD, null);
    }

    @Override
    public void setBlood(String Blood) {
        mPrefs.edit().putString(PREF_KEY_USER_BLOOD, Blood).apply();
    }

    @Override
    public String getImage() {
        return mPrefs.getString(PREF_KEY_USER_IMAGE, null);
    }

    @Override
    public void setImage(String img) {
        mPrefs.edit().putString(PREF_KEY_USER_IMAGE, img).apply();
    }

    @Override
    public boolean getQualification() {
        return mPrefs.getBoolean(PREF_KEY_USER_QUALIFICATION, false);
    }

    @Override
    public void setQualification(boolean qualification) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_QUALIFICATION, qualification).apply();
    }

    @Override
    public int getUserType() {
        return mPrefs.getInt(PREF_KEY_USER_TYPE, DataManager.UserInMode.PARENT_MODE.getType());
    }

    @Override
    public void setUserType(DataManager.UserInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_TYPE, mode.getType()).apply();
    }

    @Override
    public UserModel getUserProfile() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_USER_PROFILE, "");
        return gson.fromJson(json, UserModel.class);
    }

    @Override
    public void setUserProfile(UserModel mUserModel) {
        String json = gson.toJson(mUserModel);
        mPrefs.edit().putString(PREF_KEY_USER_PROFILE, json).apply();
    }

    @Override
    public String getLanguage() {
        return mPrefs.getString(PREF_KEY_ACCESS_LANGUAGE, "en");
    }

    @Override
    public void setPrefLanguage(String language) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_LANGUAGE, language).apply();
    }

    @Override
    public boolean getUnderEighteen() {
        return mPrefs.getBoolean(PREF_KEY_UNDER_EIGHTEEN, false);
    }

    @Override
    public void setUnderEighteen(boolean eighteen) {
        mPrefs.edit().putBoolean(PREF_KEY_UNDER_EIGHTEEN, eighteen).apply();
    }
}
