package com.ryx.tdreeb.data.local.prefs;


import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.registration.UserModel;

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    int getCurrentUserId();

    void setCurrentUserId(int userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserProfilePicUrl();

    void setCurrentUserProfilePicUrl(String profilePicUrl);

    String getPhone();

    void setPhone(String phone);

    String getPassword();

    void setPassword(String password);

    String getDOB();

    void setDOB(String DOB);

    String getGender();

    void setGender(String Gender);

    String getBlood();

    void setBlood(String Blood);

    String getImage();

    void setImage(String img);

    boolean getQualification();

    void setQualification(boolean qualification);

    int getUserType();

    void setUserType(DataManager.UserInMode mode);

    UserModel getUserProfile();

    void setUserProfile(UserModel mUserModel);

    String getLanguage();

    void setPrefLanguage(String language);


    boolean getUnderEighteen();

    void setUnderEighteen(boolean eighteen);
}
