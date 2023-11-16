package com.ryx.tdreeb.data;

import android.content.Context;

import com.google.gson.Gson;
import com.ryx.tdreeb.data.local.db.DbHelper;
import com.ryx.tdreeb.data.local.prefs.PreferencesHelper;
import com.ryx.tdreeb.data.model.api.TrainingModel.TrainingResponseModel;
import com.ryx.tdreeb.data.model.api.addchildmodel.AddChildResponse;
import com.ryx.tdreeb.data.model.api.addchildmodel.ChildenModel;
import com.ryx.tdreeb.data.model.api.allvideocourse.AllVideoCourseResponse;
import com.ryx.tdreeb.data.model.api.bookingmodel.BookingResponse;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionStatusUpdateResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.ChatResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.ChatHistoryResponse;
import com.ryx.tdreeb.data.model.api.chatmodel.chathistory.SendChatModel;
import com.ryx.tdreeb.data.model.api.curriculamodel.CurriculaResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteResponse;
import com.ryx.tdreeb.data.model.api.favoritemodel.FavoriteSubmitResponse;
import com.ryx.tdreeb.data.model.api.getcoursemodel.AddCourseModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesDataModel;
import com.ryx.tdreeb.data.model.api.getcoursemodel.CoursesResponse;
import com.ryx.tdreeb.data.model.api.gradesmodel.GradesResponse;
import com.ryx.tdreeb.data.model.api.laguagemodel.LanguagesResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseAddModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;
import com.ryx.tdreeb.data.model.api.mapdirectionmodel.DirectionResponses;
import com.ryx.tdreeb.data.model.api.mapmodel.MapPlaceModel;
import com.ryx.tdreeb.data.model.api.mapmodel.MapPlaceResponse;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionModel;
import com.ryx.tdreeb.data.model.api.mapmodel.MapSuggestionResponse;
import com.ryx.tdreeb.data.model.api.nationalitiesmodel.NationalitiesResponse;
import com.ryx.tdreeb.data.model.api.registration.RegistrationResponse;
import com.ryx.tdreeb.data.model.api.registration.SendRegistrationData;
import com.ryx.tdreeb.data.model.api.registration.UserModel;
import com.ryx.tdreeb.data.model.api.report.ReportResponse;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceResponse;
import com.ryx.tdreeb.data.model.api.sessionmodel.AllRequestUpdate;
import com.ryx.tdreeb.data.model.api.sessionmodel.SessionResponse;
import com.ryx.tdreeb.data.model.api.subjectmodel.SubjectResponse;
import com.ryx.tdreeb.data.model.api.submitmodels.LiveCourseParentModel;
import com.ryx.tdreeb.data.model.api.teacherprofile.TrainerProfileResponse;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.ScheduleSubmitModel;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.TrainerScheduleResponse;
import com.ryx.tdreeb.data.model.api.videoslider.SliderResponse;
import com.ryx.tdreeb.data.remote.ApiHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public int getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }


    @Override
    public void setCurrentUserId(int userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public String getPhone() {
        return mPreferencesHelper.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        mPreferencesHelper.setPhone(phone);
    }

    @Override
    public String getPassword() {
        return mPreferencesHelper.getPassword();
    }

    @Override
    public void setPassword(String password) {
        mPreferencesHelper.setPassword(password);
    }

    @Override
    public String getDOB() {
        return mPreferencesHelper.getDOB();
    }

    @Override
    public void setDOB(String DOB) {
        mPreferencesHelper.setDOB(DOB);
    }

    @Override
    public String getGender() {
        return mPreferencesHelper.getGender();
    }

    @Override
    public void setGender(String Gender) {
        mPreferencesHelper.setGender(Gender);
    }

    @Override
    public String getBlood() {
        return mPreferencesHelper.getBlood();
    }

    @Override
    public void setBlood(String Blood) {
        mPreferencesHelper.setBlood(Blood);
    }

    @Override
    public String getImage() {
        return mPreferencesHelper.getImage();
    }

    @Override
    public void setImage(String img) {
        mPreferencesHelper.setImage(img);
    }

    @Override
    public boolean getQualification() {
        return mPreferencesHelper.getQualification();
    }

    @Override
    public void setQualification(boolean qualification) {
        mPreferencesHelper.setQualification(qualification);
    }

    @Override
    public int getUserType() {
        return mPreferencesHelper.getUserType();
    }

    @Override
    public void setUserType(UserInMode mode) {
        mPreferencesHelper.setUserType(mode);
    }

    @Override
    public UserModel getUserProfile() {
        return mPreferencesHelper.getUserProfile();
    }

    @Override
    public void setUserProfile(UserModel mUserModel) {
        mPreferencesHelper.setUserProfile(mUserModel);
    }

    @Override
    public String getLanguage() {
        return mPreferencesHelper.getLanguage();
    }

    @Override
    public void setPrefLanguage(String language) {
        mPreferencesHelper.setPrefLanguage(language);
    }

    @Override
    public boolean getUnderEighteen() {
        return mPreferencesHelper.getUnderEighteen();
    }

    @Override
    public void setUnderEighteen(boolean eighteen) {
        mPreferencesHelper.setUnderEighteen(eighteen);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                -1,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            int userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String phone,
            String password,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setPhone(phone);
        setPassword(password);
        setCurrentUserProfilePicUrl(profilePicPath);

        // updateApiHeader(userId, accessToken);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        //mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }


    @Override
    public void updateApiHeader(Long userId, String accessToken) {
//        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public Single<RegistrationResponse> doPostParentLogin(Map<String, String> params) {
        return mApiHelper.doPostParentLogin(params);
    }

    @Override
    public Single<RegistrationResponse> doPostParentRegistration(SendRegistrationData param) {
        return mApiHelper.doPostParentRegistration(param);
    }

    @Override
    public Single<AddChildResponse> doAddChildByParent(ChildenModel param) {
        return mApiHelper.doAddChildByParent(param);
    }

    @Override
    public Single<AddChildResponse> doUpdateChildByParent(ChildenModel param) {
        return mApiHelper.doUpdateChildByParent(param);
    }

    @Override
    public Single<AddChildResponse> doGetChild(int id, String lan) {
        return mApiHelper.doGetChild(id, lan);
    }

    @Override
    public Single<RegistrationResponse> doGetUpdateParent(SendRegistrationData param) {
        return mApiHelper.doGetUpdateParent(param);
    }

    @Override
    public Single<LiveCourseResponse> doGetLiveCoursesParent(String lan) {
        return mApiHelper.doGetLiveCoursesParent(lan);
    }

    @Override
    public Single<BookingResponse> doGetBookSession(LiveCourseParentModel params) {
        return mApiHelper.doGetBookSession(params);
    }

    @Override
    public Single<SessionResponse> doGetUpComingSession(int parentId, String courseType, int userType, String lan) {
        return mApiHelper.doGetUpComingSession(parentId, courseType, userType, lan);
    }

    @Override
    public Single<SessionResponse> doGetUpCompleteSession(int parentId, int userType, String lan) {
        return mApiHelper.doGetUpCompleteSession(parentId, userType, lan);
    }

    @Override
    public Single<CoursesResponse> getAllCoursesBySubject(String querySearch, String lan) {
        return mApiHelper.getAllCoursesBySubject(querySearch, lan);
    }

    @Override
    public Single<CoursesResponse> getAllTraining(String querySearch, String lan) {
        return mApiHelper.getAllTraining(querySearch, lan);
    }

    @Override
    public Single<CoursesResponse> postSessionDateUpdate(Map<String, String> params) {
        return mApiHelper.postSessionDateUpdate(params);
    }

    //  TODO:- Student Part
    @Override
    public Single<RegistrationResponse> doPostStudentLogin(Map<String, String> params) {
        return mApiHelper.doPostStudentLogin(params);
    }

    @Override
    public Single<RegistrationResponse> doPostStudentRegistration(SendRegistrationData param) {
        return mApiHelper.doPostStudentRegistration(param);
    }

    @Override
    public Single<RegistrationResponse> doGetUpdateStudentProfile(SendRegistrationData param) {
        return mApiHelper.doGetUpdateStudentProfile(param);
    }

    @Override
    public Single<ReportResponse> doGetReportCard(Map<String, String> param, String lan) {
        return mApiHelper.doGetReportCard(param, lan);
    }

    @Override
    public Single<RegistrationResponse> doPostTeacherLogin(Map<String, String> params) {
        return mApiHelper.doPostTeacherLogin(params);
    }

    @Override
    public Single<RegistrationResponse> doPostTeacherRegistration(SendRegistrationData param) {
        return mApiHelper.doPostTeacherRegistration(param);
    }

    @Override
    public Single<TrainerProfileResponse> doGetTeacherProfile(int id, String lan) {
        return mApiHelper.doGetTeacherProfile(id, lan);
    }

    @Override
    public Single<TrainerProfileResponse> doPostTeacherUpdate(ChildenModel param) {
        return mApiHelper.doPostTeacherUpdate(param);
    }

    @Override
    public Single<CoursesResponse> doGetCourses(int id, String lan) {
        return mApiHelper.doGetCourses(id, lan);
    }

    @Override
    public Single<CoursesResponse> doAddCourses(AddCourseModel params) {
        return mApiHelper.doAddCourses(params);
    }

    @Override
    public Single<CoursesResponse> doRemoveCourses(AddCourseModel params) {
        return mApiHelper.doRemoveCourses(params);
    }

    @Override
    public Single<CoursesResponse> doUpdateCourses(AddCourseModel params) {
        return mApiHelper.doUpdateCourses(params);
    }

    @Override
    public Single<LiveCourseResponse> doGetLiveCourses(int id, String lan) {
        return mApiHelper.doGetLiveCourses(id, lan);
    }

    @Override
    public Single<LiveCourseResponse> doAddLiveCourses(LiveCourseAddModel mLiveCourseAddModel) {
        return mApiHelper.doAddLiveCourses(mLiveCourseAddModel);
    }

    @Override
    public Single<LiveCourseResponse> doUpdateLiveCourses(LiveCourseAddModel mLiveCourseAddModel) {
        return mApiHelper.doUpdateLiveCourses(mLiveCourseAddModel);
    }

    @Override
    public Single<LiveCourseResponse> doGetVideoCourses(int id) {
        return mApiHelper.doGetVideoCourses(id);
    }

    @Override
    public Single<LiveCourseResponse> doRemoveVideoCourses(int id) {
        return mApiHelper.doRemoveVideoCourses(id);
    }

    @Override
    public Single<LiveCourseResponse> doRemoveVideoResources(int id) {
        return mApiHelper.doRemoveVideoResources(id);
    }

    @Override
    public Single<LiveCourseResponse> doAddVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams,File intro) {
        return mApiHelper.doAddVideoCourses(params, file, videoParams,intro);
    }

    @Override
    public Single<LiveCourseResponse> doUpdateVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams,File intro) {
        return mApiHelper.doUpdateVideoCourses(params, file, videoParams,intro);
    }

    @Override
    public Single<LiveCourseResponse> doAddResource(Map<String, String> params, List<File> fileList) {
        return mApiHelper.doAddResource(params, fileList);
    }

    @Override
    public Single<ResourceResponse> doGetResource(int id, String lan) {
        return mApiHelper.doGetResource(id, lan);
    }

    @Override
    public Single<ResourceResponse> doRemoveResource(int id) {
        return mApiHelper.doRemoveResource(id);
    }

    @Override
    public Single<ResourceResponse> doRemoveResourceFile(int id) {
        return mApiHelper.doRemoveResourceFile(id);
    }

    @Override
    public Single<ResourceResponse> doGetAllResource(Map<String, String> params, String lan) {
        return mApiHelper.doGetAllResource(params, lan);
    }

    @Override
    public Single<ResourceResponse> doEditResource(Map<String, String> params, List<File> fileList) {
        return mApiHelper.doEditResource(params, fileList);
    }

    @Override
    public Single<LanguagesResponse> doGetGeneralLanguage(String lan) {
        return mApiHelper.doGetGeneralLanguage(lan);
    }

    @Override
    public Single<NationalitiesResponse> doGetGeneralNationalities(String lan) {
        return mApiHelper.doGetGeneralNationalities(lan);
    }

    @Override
    public Single<SubjectResponse> doGetSubjects(String lan) {
        return mApiHelper.doGetSubjects(lan);
    }

    @Override
    public Single<TrainingResponseModel> doGetAllTraining(String lan) {
        return mApiHelper.doGetAllTraining(lan);
    }

    @Override
    public Single<GradesResponse> doGetGrades(String lan) {
        return mApiHelper.doGetGrades(lan);
    }

    @Override
    public Single<CurriculaResponse> doGetCurriculum(String lan) {
        return mApiHelper.doGetCurriculum(lan);
    }

    @Override
    public Single<ChatResponse> doGetChatList(String userType, int id, String lan) {
        return mApiHelper.doGetChatList(userType, id, lan);
    }

    @Override
    public Single<ChatHistoryResponse> doGetChatHistory(String userType, int id, String receiverType, int receiverId, String lan) {
        return mApiHelper.doGetChatHistory(userType, id, receiverType, receiverId, lan);
    }

    @Override
    public Single<ChatHistoryResponse> doSendChat(SendChatModel mSendChatModel) {
        return mApiHelper.doSendChat(mSendChatModel);
    }

    @Override
    public Single<AllVideoCourseResponse> getAllVideo() {
        return mApiHelper.getAllVideo();
    }

    @Override
    public Single<SliderResponse> getSlider() {
        return mApiHelper.getSlider();
    }

    @Override
    public Single<LiveCourseResponse> doGetAllVideoListParent(String videoNameList) {
        return mApiHelper.doGetAllVideoListParent(videoNameList);
    }

    @Override
    public Single<MapSuggestionResponse> doGoogleSuggestionApiCall(MapSuggestionModel request) {
        return mApiHelper.doGoogleSuggestionApiCall(request);
    }

    @Override
    public Single<MapPlaceResponse> doGooglePlaceApiCall(MapPlaceModel request) {
        return mApiHelper.doGooglePlaceApiCall(request);
    }

    @Override
    public Single<DirectionResponses> doMapDirectionApi(String origin, String destination, String key) {
        return mApiHelper.doMapDirectionApi(origin, destination, key);
    }

    @Override
    public Single<FavoriteResponse> doGetAllFavorite(int FavoriteById, String FavoriteBy, String lan) {
        return mApiHelper.doGetAllFavorite(FavoriteById, FavoriteBy, lan);
    }

    @Override
    public Single<FavoriteSubmitResponse> doSubmitFavorite(Map<String, String> params) {
        return mApiHelper.doSubmitFavorite(params);
    }

    @Override
    public Single<FavoriteSubmitResponse> doRemoveFavorite(Map<String, String> params, String lan) {
        return mApiHelper.doRemoveFavorite(params, lan);
    }

    @Override
    public Single<TrainerScheduleResponse> doGetTrainerSchedule(int id, String lan) {
        return mApiHelper.doGetTrainerSchedule(id, lan);
    }

    @Override
    public Single<TrainerScheduleResponse> doSubmitTrainerSchedule(ScheduleSubmitModel params) {
        return mApiHelper.doSubmitTrainerSchedule(params);
    }

    @Override
    public Single<TrainerScheduleResponse> doGetTrainerScheduleTypeWise(Map<String, String> params, String lan) {
        return mApiHelper.doGetTrainerScheduleTypeWise(params, lan);
    }

    @Override
    public Single<SessionResponse> doGetTrainerAllRequest(Map<String, String> params, String lan) {
        return mApiHelper.doGetTrainerAllRequest(params, lan);
    }

    @Override
    public Single<AllRequestUpdate> doGetTrainerAllRequestUpdate(Map<String, String> params, String lan) {
        return mApiHelper.doGetTrainerAllRequestUpdate(params, lan);
    }

    @Override
    public Single<SessionStatusUpdateResponse> doPostSessionUpdate(Map<String, String> params) {
        return mApiHelper.doPostSessionUpdate(params);
    }
}
