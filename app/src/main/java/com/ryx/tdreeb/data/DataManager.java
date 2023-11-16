package com.ryx.tdreeb.data;


import com.ryx.tdreeb.data.local.db.DbHelper;
import com.ryx.tdreeb.data.local.prefs.PreferencesHelper;
import com.ryx.tdreeb.data.remote.ApiHelper;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {


    void setUserAsLoggedOut();

    void updateApiHeader(Long userId, String accessToken);

    void updateUserInfo(
            String accessToken,
            int userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String phone,
            String password,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

    enum UserInMode {

        PARENT_MODE(0),
        TRAINER(1),
        STUDENT(2);

        private final int uType;

        UserInMode(int type) {
            uType = type;
        }

        public int getType() {
            return uType;
        }
    }
}
