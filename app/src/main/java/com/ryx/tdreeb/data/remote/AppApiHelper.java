package com.ryx.tdreeb.data.remote;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.ryx.tdreeb.data.DataManager;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Singleton
public class AppApiHelper implements ApiHelper {

    // private Object TrainerScheduleResponse;

    @Inject
    public AppApiHelper() {
    }

    @Override
    public Single<RegistrationResponse> doPostParentLogin(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(params)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doPostParentRegistration(SendRegistrationData param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_REGISTRATION)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<AddChildResponse> doAddChildByParent(ChildenModel param) {

        int REQUEST_TIMEOUT = 500;
        OkHttpClient okHttpClient;
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        okHttpClient = httpClient.build();
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_ADD_CHILD)
                .setOkHttpClient(okHttpClient)
                .addBodyParameter(param)
                .build()
                .getObjectSingle(AddChildResponse.class);
    }

    @Override
    public Single<AddChildResponse> doUpdateChildByParent(ChildenModel param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_CHILD_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(AddChildResponse.class);
    }

    @Override
    public Single<AddChildResponse> doGetChild(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PARENT_GET_CHILD + id + "&lan=" + lan)
                .build()
                .getObjectSingle(AddChildResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doGetUpdateParent(SendRegistrationData param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_PROFILE_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doGetLiveCoursesParent(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PARENT_GET_LIVE_COURSE)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doGetAllVideoListParent(String videoNameList) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_VIDEO_LIST)
                .addQueryParameter("course", videoNameList)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<BookingResponse> doGetBookSession(LiveCourseParentModel params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_BOOK_SESSION)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(BookingResponse.class);
    }

    @Override
    public Single<SessionResponse> doGetUpComingSession(int parentId, String courseType, int userType, String lan) {
        if (userType == DataManager.UserInMode.PARENT_MODE.getType()) {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PARENT_MY_SESSION)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("parentId", parentId + "")
                    .addQueryParameter("lan", lan)
                    .addQueryParameter("courseType", courseType)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        } else if (userType == DataManager.UserInMode.STUDENT.getType()) {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STUDENT_MY_SESSION)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("studentId", parentId + "")
                    .addQueryParameter("lan", lan)
                    .addQueryParameter("courseType", courseType)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        } else {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_MY_SESSION)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("trainerId", parentId + "")
                    .addQueryParameter("courseType", courseType)
                    .addQueryParameter("lan", lan)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        }
    }

    @Override
    public Single<SessionResponse> doGetUpCompleteSession(int parentId, int userType, String lan) {
        if (userType == DataManager.UserInMode.PARENT_MODE.getType()) {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PARENT_MY_SESSION_COMPLETE)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("parentId", parentId + "")
                    .addQueryParameter("lan", lan)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        } else if (userType == DataManager.UserInMode.STUDENT.getType()) {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STUDENT_MY_SESSION_COMPLETE)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("studentId", parentId + "")
                    .addQueryParameter("lan", lan)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        } else {
            return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_MY_SESSION_COMPLETE)
                    .addHeaders("Content-Type", "application/json")
                    .addQueryParameter("trainerId", parentId + "")
                    .addQueryParameter("lan", lan)
                    .build()
                    .getObjectSingle(SessionResponse.class);
        }
    }

    @Override
    public Single<CoursesResponse> getAllCoursesBySubject(String querySearch, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_PARENT_GET_COURSE_BY_SUBJECT)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("querySearch", querySearch)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<CoursesResponse> getAllTraining(String querySearch, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_TRAINING)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("querySearch", querySearch)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<CoursesResponse> postSessionDateUpdate(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PARENT_SESSION_DATE_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(params)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    //  TODO:- Student Part
    @Override
    public Single<RegistrationResponse> doPostStudentLogin(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_STUDENT_LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(params)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doPostStudentRegistration(SendRegistrationData param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_STUDENT_REGISTRATION)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doGetUpdateStudentProfile(SendRegistrationData param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_STUDENT_PROFILE_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(param)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<ReportResponse> doGetReportCard(Map<String, String> param, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STUDENT_GET_REPORT_CARD)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(param)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(ReportResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doPostTeacherLogin(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_LOGIN)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(params)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<RegistrationResponse> doPostTeacherRegistration(SendRegistrationData param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REGISTRATION)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(RegistrationResponse.class);
    }

    @Override
    public Single<TrainerProfileResponse> doGetTeacherProfile(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_GET_PROFILE + id + "&lan=" + lan)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(TrainerProfileResponse.class);
    }

    @Override
    public Single<TrainerProfileResponse> doPostTeacherUpdate(ChildenModel param) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_UPDATE_PROFILE)
                .addHeaders("Content-Type", "application/json")
                .addBodyParameter(param)
                .build()
                .getObjectSingle(TrainerProfileResponse.class);
    }

    @Override
    public Single<CoursesResponse> doGetCourses(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_GET_COURSES + id + "&lan=" + lan)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<CoursesResponse> doAddCourses(AddCourseModel params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_ADD_COURSES)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<CoursesResponse> doRemoveCourses(AddCourseModel params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REMOVE_COURSE)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<CoursesResponse> doUpdateCourses(AddCourseModel params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_UPDATE_COURSE)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(CoursesResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doGetLiveCourses(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_GET_LIVE_COURSES + id + "&lan=" + lan)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doAddLiveCourses(LiveCourseAddModel mLiveCourseAddModel) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_ADD_LIVE_COURSES)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(mLiveCourseAddModel)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doUpdateLiveCourses(LiveCourseAddModel mLiveCourseAddModel) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_UPDATE_LIVE_COURSES)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(mLiveCourseAddModel)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doGetVideoCourses(int id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_GET_VIDEO_COURSES + id)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doRemoveVideoCourses(int id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REMOVE_VIDEO_COURSES)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("videoCourseId", id + "")
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doRemoveVideoResources(int id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REMOVE_VIDEO_RESOURCE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("vedioCourseFileId", id + "")
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doAddVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams, File intro) {

        int REQUEST_TIMEOUT = 120;
        OkHttpClient okHttpClient;

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "multipart/form-data");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();

        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_TEACHER_ADD_VIDEO_COURSES)
                .setOkHttpClient(okHttpClient)
                .addMultipartFile(videoParams)
                .addMultipartFile("", file)
                .addMultipartFile("OverViewFile", intro)
                .addMultipartParameter(params)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<LiveCourseResponse> doUpdateVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams, File intro) {

        int REQUEST_TIMEOUT = 120;
        OkHttpClient okHttpClient;

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "multipart/form-data");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();

        if (file == null) {
            return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_TEACHER_UPDATE_VIDEO_COURSES)
                    .setOkHttpClient(okHttpClient)
                    .addMultipartFile(videoParams)
                    .addMultipartFile("OverViewFile", intro)
                    .addMultipartParameter(params)
                    .build()
                    .getObjectSingle(LiveCourseResponse.class);
        } else {
            return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_TEACHER_UPDATE_VIDEO_COURSES)
                    .setOkHttpClient(okHttpClient)
                    .addMultipartFile(videoParams)
                    .addMultipartFile("", file)
                    .addMultipartFile("OverViewFile", intro)
                    .addMultipartParameter(params)
                    .build()
                    .getObjectSingle(LiveCourseResponse.class);
        }

    }

    @Override
    public Single<LiveCourseResponse> doAddResource(Map<String, String> params, List<File> fileList) {

        int REQUEST_TIMEOUT = 120;
        OkHttpClient okHttpClient;

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "multipart/form-data");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();

        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_TEACHER_ADD_RESOURCE)
                .setOkHttpClient(okHttpClient)
                .addMultipartFileList("", fileList)
                .addMultipartParameter(params)
                .build()
                .getObjectSingle(LiveCourseResponse.class);
    }

    @Override
    public Single<ResourceResponse> doGetResource(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_GET_RESOURCE + id + "&lan=" + lan)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(ResourceResponse.class);
    }

    @Override
    public Single<ResourceResponse> doRemoveResource(int id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REMOVE_RESOURCE + id)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(ResourceResponse.class);
    }

    @Override
    public Single<ResourceResponse> doRemoveResourceFile(int id) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_REMOVE_RESOURCE_FILE + id)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(ResourceResponse.class);
    }

    @Override
    public Single<ResourceResponse> doGetAllResource(Map<String, String> params, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_ALL_RESOURCE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(params)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(ResourceResponse.class);
    }

    @Override
    public Single<ResourceResponse> doEditResource(Map<String, String> params, List<File> fileList) {
        int REQUEST_TIMEOUT = 120;
        OkHttpClient okHttpClient;

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "multipart/form-data");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        okHttpClient = httpClient.build();
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_TEACHER_EDIT_RESOURCE)
                .setOkHttpClient(okHttpClient)
                .addMultipartFileList("", fileList)
                .addMultipartParameter(params)
                .build()
                .getObjectSingle(ResourceResponse.class);
    }

    @Override
    public Single<LanguagesResponse> doGetGeneralLanguage(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_LANGUAGE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(LanguagesResponse.class);
    }

    @Override
    public Single<NationalitiesResponse> doGetGeneralNationalities(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_NATIONALITIES)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(NationalitiesResponse.class);
    }

    @Override
    public Single<SubjectResponse> doGetSubjects(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_SUBJECTS)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(SubjectResponse.class);
    }

    @Override
    public Single<TrainingResponseModel> doGetAllTraining(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_TRAINING)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(TrainingResponseModel.class);
    }

    @Override
    public Single<GradesResponse> doGetGrades(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_GRADES)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(GradesResponse.class);
    }

    @Override
    public Single<CurriculaResponse> doGetCurriculum(String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_CURRICULA)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(CurriculaResponse.class);
    }

    @Override
    public Single<ChatResponse> doGetChatList(String userType, int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_CHAT_LIST)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("userType", userType)
                .addQueryParameter("userId", id + "")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(ChatResponse.class);
    }

    @Override
    public Single<ChatHistoryResponse> doGetChatHistory(String userType, int id, String receiverType, int receiverId, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_CHAT_HISTORY)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("userType", userType)
                .addQueryParameter("userId", id + "")
                .addQueryParameter("lan", lan)
                .addQueryParameter("receiverType", receiverType)
                .addQueryParameter("receiverId", receiverId + "")
                .build()
                .getObjectSingle(ChatHistoryResponse.class);
    }

    @Override
    public Single<ChatHistoryResponse> doSendChat(SendChatModel mSendChatModel) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GENERAL_SEND_CHAT)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(mSendChatModel)
                .build()
                .getObjectSingle(ChatHistoryResponse.class);
    }

    @Override
    public Single<AllVideoCourseResponse> getAllVideo() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_ALL_VIDEO)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(AllVideoCourseResponse.class);
    }

    @Override
    public Single<SliderResponse> getSlider() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GENERAL_GET_VIDEO_SLIDER)
                .addHeaders("Content-Type", "application/json")
                .build()
                .getObjectSingle(SliderResponse.class);
    }

    @Override
    public Single<MapSuggestionResponse> doGoogleSuggestionApiCall(MapSuggestionModel request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MAP_SUGGESTION)
                .addQueryParameter(request)
                .build()
                .getObjectSingle(MapSuggestionResponse.class);
    }

    @Override
    public Single<MapPlaceResponse> doGooglePlaceApiCall(MapPlaceModel request) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MAP_PLACE)
                .addQueryParameter(request)
                .build()
                .getObjectSingle(MapPlaceResponse.class);
    }

    @Override
    public Single<DirectionResponses> doMapDirectionApi(String origin, String destination, String key) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_MAP_DIRECTION)
                .addQueryParameter("origin", origin)
                .addQueryParameter("destination", destination)
                .addQueryParameter("key", key)
                .build()
                .getObjectSingle(DirectionResponses.class);
    }

    @Override
    public Single<FavoriteResponse> doGetAllFavorite(int FavoriteById, String FavoriteBy, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_GET_FAVORITE)
                .addQueryParameter("FavoriteBy", FavoriteBy)
                .addQueryParameter("FavoriteById", FavoriteById + "")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(FavoriteResponse.class);
    }

    @Override
    public Single<FavoriteSubmitResponse> doSubmitFavorite(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SUBMIT_FAVORITE)
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(FavoriteSubmitResponse.class);
    }

    @Override
    public Single<FavoriteSubmitResponse> doRemoveFavorite(Map<String, String> params, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_REMOVE_FAVORITE)
                .addQueryParameter(params)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(FavoriteSubmitResponse.class);
    }

    @Override
    public Single<TrainerScheduleResponse> doGetTrainerSchedule(int id, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_SCHEDULE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter("trainerId", id + "")
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(TrainerScheduleResponse.class);
    }

    @Override
    public Single<TrainerScheduleResponse> doSubmitTrainerSchedule(ScheduleSubmitModel params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_SCHEDULE_SUBMIT)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(TrainerScheduleResponse.class);
    }

    @Override
    public Single<TrainerScheduleResponse> doGetTrainerScheduleTypeWise(Map<String, String> params, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_GET_SCHEDULE_TYPE_WISE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(params)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(TrainerScheduleResponse.class);
    }

    @Override
    public Single<SessionResponse> doGetTrainerAllRequest(Map<String, String> params, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_ALL_REQUEST)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(params)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(SessionResponse.class);
    }

    @Override
    public Single<AllRequestUpdate> doGetTrainerAllRequestUpdate(Map<String, String> params, String lan) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TEACHER_ALL_REQUEST_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addQueryParameter(params)
                .addQueryParameter("lan", lan)
                .build()
                .getObjectSingle(AllRequestUpdate.class);
    }

    @Override
    public Single<SessionStatusUpdateResponse> doPostSessionUpdate(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_TEACHER_SESSION_UPDATE)
                .addHeaders("Content-Type", "application/json")
                .addApplicationJsonBody(params)
                .build()
                .getObjectSingle(SessionStatusUpdateResponse.class);
    }


}
