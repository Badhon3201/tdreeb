package com.ryx.tdreeb.data.remote;


import com.ryx.tdreeb.BuildConfig;

public final class ApiEndPoint {

    //  TODO:- Parent End Point
    public static final String ENDPOINT_PARENT_LOGIN = BuildConfig.BASE_URL + "/parent/login";
    public static final String ENDPOINT_PARENT_REGISTRATION = BuildConfig.BASE_URL + "/parent/registration";
    public static final String ENDPOINT_PARENT_ADD_CHILD = BuildConfig.BASE_URL + "/child/add";
    public static final String ENDPOINT_PARENT_GET_CHILD = BuildConfig.BASE_URL + "/parent/get_all_Childs?ParentId=";
    public static final String ENDPOINT_PARENT_CHILD_UPDATE = BuildConfig.BASE_URL + "/child/update";
    public static final String ENDPOINT_PARENT_PROFILE_UPDATE = BuildConfig.BASE_URL + "/parent/profile-update";
    public static final String ENDPOINT_PARENT_GET_LIVE_COURSE = BuildConfig.BASE_URL + "/trainer/GetLivecourses";
    public static final String ENDPOINT_PARENT_BOOK_SESSION = BuildConfig.BASE_URL + "/parent/book_session";
    public static final String ENDPOINT_PARENT_MY_SESSION_COMPLETE = BuildConfig.BASE_URL + "/parent/completed_session";
    public static final String ENDPOINT_PARENT_MY_SESSION = BuildConfig.BASE_URL + "/parent/upcoming_session";
    public static final String ENDPOINT_PARENT_VIDEO_LIST = BuildConfig.BASE_URL + "/trainer/video/allcourses-get";
    public static final String ENDPOINT_PARENT_GET_COURSE_BY_SUBJECT = BuildConfig.BASE_URL + "/trainer/allcourses";
    public static final String ENDPOINT_PARENT_SESSION_DATE_UPDATE = BuildConfig.BASE_URL + "/parent/update/session-time-slot";

    //  TODO:- Student End Point
    public static final String ENDPOINT_STUDENT_LOGIN = BuildConfig.BASE_URL + "/student/login";
    public static final String ENDPOINT_STUDENT_REGISTRATION = BuildConfig.BASE_URL + "/student/registration";
    public static final String ENDPOINT_STUDENT_PROFILE_UPDATE = BuildConfig.BASE_URL + "/student/profile-update";
    public static final String ENDPOINT_STUDENT_MY_SESSION = BuildConfig.BASE_URL + "/student/upcoming_session";
    public static final String ENDPOINT_STUDENT_MY_SESSION_COMPLETE = BuildConfig.BASE_URL + "/student/completed_session";
    public static final String ENDPOINT_STUDENT_GET_REPORT_CARD = BuildConfig.BASE_URL + "/reportcard/all";


    //  TODO:- Teacher End Point
    public static final String ENDPOINT_TEACHER_LOGIN = BuildConfig.BASE_URL + "/trainer/login";
    public static final String ENDPOINT_TEACHER_REGISTRATION = BuildConfig.BASE_URL + "/trainer/registration";
    public static final String ENDPOINT_TEACHER_GET_PROFILE = BuildConfig.BASE_URL + "/trainer/profile-infoV2?id=";
    public static final String ENDPOINT_TEACHER_UPDATE_PROFILE = BuildConfig.BASE_URL + "/trainer/profile-update";
    public static final String ENDPOINT_TEACHER_GET_COURSES = BuildConfig.BASE_URL + "/trainer/courses?trainerId=";
    public static final String ENDPOINT_TEACHER_ADD_COURSES = BuildConfig.BASE_URL + "/trainer/courses-submit";
    public static final String ENDPOINT_TEACHER_REMOVE_COURSE = BuildConfig.BASE_URL + "/trainer/courses/remove";
    public static final String ENDPOINT_TEACHER_UPDATE_COURSE = BuildConfig.BASE_URL + "/trainer/courses/update";
    public static final String ENDPOINT_TEACHER_GET_LIVE_COURSES = BuildConfig.BASE_URL + "/trainer/GetLivecourses?trainerId=";
    public static final String ENDPOINT_TEACHER_ADD_LIVE_COURSES = BuildConfig.BASE_URL + "/trainer/Livecourses-submit";
    public static final String ENDPOINT_TEACHER_UPDATE_LIVE_COURSES = BuildConfig.BASE_URL + "/trainer/Livecourses-edit";
    public static final String ENDPOINT_TEACHER_GET_VIDEO_COURSES = BuildConfig.BASE_URL + "/trainer/video/courses-get?trainerId=";
    public static final String ENDPOINT_TEACHER_REMOVE_VIDEO_COURSES = BuildConfig.BASE_URL + "/trainer/video-courses/remove";
    public static final String ENDPOINT_TEACHER_REMOVE_VIDEO_RESOURCE = BuildConfig.BASE_URL + "/trainer/video-courses-video/remove";
    public static final String ENDPOINT_TEACHER_ADD_VIDEO_COURSES = BuildConfig.BASE_URL + "/trainer/video/courses-submit";
    public static final String ENDPOINT_TEACHER_UPDATE_VIDEO_COURSES = BuildConfig.BASE_URL + "/trainer/video/courses-edit";
    public static final String ENDPOINT_TEACHER_ADD_RESOURCE = BuildConfig.BASE_URL + "/trainer/myresource/submit";
    public static final String ENDPOINT_TEACHER_GET_RESOURCE = BuildConfig.BASE_URL + "/trainer/myresource/getbyTrainerId?TrainerId=";
    public static final String ENDPOINT_TEACHER_ALL_RESOURCE = BuildConfig.BASE_URL + "/trainer/myresource/getall";
    public static final String ENDPOINT_TEACHER_REMOVE_RESOURCE = BuildConfig.BASE_URL + "/trainer/myresource/remove?TrainerResourecId=";
    public static final String ENDPOINT_TEACHER_REMOVE_RESOURCE_FILE = BuildConfig.BASE_URL + "/trainer/myresource/remove/id?resourceId=";
    public static final String ENDPOINT_TEACHER_EDIT_RESOURCE = BuildConfig.BASE_URL + "/trainer/myresource/edit";
    public static final String ENDPOINT_TEACHER_SCHEDULE = BuildConfig.BASE_URL + "/trainer/schedule";
    public static final String ENDPOINT_TEACHER_GET_SCHEDULE_TYPE_WISE = BuildConfig.BASE_URL + "/trainer/schedulebydatewise";
    public static final String ENDPOINT_TEACHER_SCHEDULE_SUBMIT = BuildConfig.BASE_URL + "/trainer/schedule-submit";
    public static final String ENDPOINT_TEACHER_TRAINING = BuildConfig.BASE_URL + "/trainer/alltrainingcourses";
    public static final String ENDPOINT_TEACHER_MY_SESSION = BuildConfig.BASE_URL + "/trainer/upcoming_session";
    public static final String ENDPOINT_TEACHER_MY_SESSION_COMPLETE = BuildConfig.BASE_URL + "/trainer/completed_session";
    public static final String ENDPOINT_TEACHER_ALL_REQUEST = BuildConfig.BASE_URL + "/trainer/all-request";
    public static final String ENDPOINT_TEACHER_ALL_REQUEST_UPDATE = BuildConfig.BASE_URL + "/trainer/all-request_status_update";
    public static final String ENDPOINT_TEACHER_SESSION_UPDATE = BuildConfig.BASE_URL + "/trainer/session/log-status-update";


    //  TODO:- General End Point
    public static final String ENDPOINT_GENERAL_GET_LANGUAGE = BuildConfig.BASE_URL + "/general/languages";
    public static final String ENDPOINT_GENERAL_GET_NATIONALITIES = BuildConfig.BASE_URL + "/general/nationalities";
    public static final String ENDPOINT_GENERAL_GET_SUBJECTS = BuildConfig.BASE_URL + "/general/subjects";
    public static final String ENDPOINT_GENERAL_GET_TRAINING = BuildConfig.BASE_URL + "/general/allTraining";
    public static final String ENDPOINT_GENERAL_GET_GRADES = BuildConfig.BASE_URL + "/general/grades";
    public static final String ENDPOINT_GENERAL_GET_CURRICULA = BuildConfig.BASE_URL + "/general/curricula";
    public static final String ENDPOINT_GENERAL_GET_CHAT_LIST = BuildConfig.BASE_URL + "/general/chat/history";
    public static final String ENDPOINT_GENERAL_GET_CHAT_HISTORY = BuildConfig.BASE_URL + "/general/chat/messages";
    public static final String ENDPOINT_GENERAL_SEND_CHAT = BuildConfig.BASE_URL + "/general/chat/post";
    public static final String ENDPOINT_GENERAL_GET_ALL_VIDEO = BuildConfig.BASE_URL + "/category/top/Course";
    public static final String ENDPOINT_GENERAL_GET_VIDEO_SLIDER = BuildConfig.BASE_URL + "/general/subject-wise-slider/VideoCourse";

    //TODO: Map Api
    public static final String ENDPOINT_MAP_SUGGESTION = BuildConfig.GOOGLE_AUTOCOMPLETE_URL;
    public static final String ENDPOINT_MAP_PLACE = BuildConfig.GOOGLE_PLACE_URL + "place/details/json?";
    public static final String ENDPOINT_MAP_DIRECTION = BuildConfig.GOOGLE_PLACE_URL + "directions/json";

    //TODO: Favorite Api
    public static final String ENDPOINT_GET_FAVORITE = BuildConfig.BASE_URL + "/favorite/all";
    public static final String ENDPOINT_SUBMIT_FAVORITE = BuildConfig.BASE_URL + "/favorite/submit";
    public static final String ENDPOINT_REMOVE_FAVORITE = BuildConfig.BASE_URL + "/favorite/remove";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }


    // Staging
//    public static String paymentMood = "Test";
//    public static String DEFAULT_TOKEN = "9cc7c70eaa1ac79a7f65ce8d49aae50b85780cbab373a4bd10563b83192f76a5722ce7b3c3c8662c51df329b203cb0c941c5";
//    public static String STORE_ID = "testbox";
//    public static String STORE_PASSWORD = "qwerty";
//    public static String SSL_SDK_TYPE = SSLCSdkType.TESTBOX;

}
