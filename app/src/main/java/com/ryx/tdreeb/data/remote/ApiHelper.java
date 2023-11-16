package com.ryx.tdreeb.data.remote;


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
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public interface ApiHelper {

    // TODO:-  Parent Api
    Single<RegistrationResponse> doPostParentLogin(Map<String, String> params);

    Single<RegistrationResponse> doPostParentRegistration(SendRegistrationData param);

    Single<AddChildResponse> doAddChildByParent(ChildenModel param);

    Single<AddChildResponse> doUpdateChildByParent(ChildenModel param);

    Single<AddChildResponse> doGetChild(int id,String lan);

    Single<RegistrationResponse> doGetUpdateParent(SendRegistrationData param);

    Single<LiveCourseResponse> doGetAllVideoListParent(String nameVide);

    Single<LiveCourseResponse> doGetLiveCoursesParent(String lan);

    Single<BookingResponse> doGetBookSession(LiveCourseParentModel params);

    Single<SessionResponse> doGetUpComingSession(int parentId, String courseType, int userType,String lan);

    Single<SessionResponse> doGetUpCompleteSession(int parentId,int userType,String lan);

    Single<CoursesResponse> getAllCoursesBySubject(String querySearch,String lan);

    Single<CoursesResponse> getAllTraining(String querySearch,String lan);

    Single<CoursesResponse> postSessionDateUpdate(Map<String, String> params);


    // TODO:-  Student Api
    Single<RegistrationResponse> doPostStudentLogin(Map<String, String> params);

    Single<RegistrationResponse> doPostStudentRegistration(SendRegistrationData param);

    Single<RegistrationResponse> doGetUpdateStudentProfile(SendRegistrationData param);

    Single<ReportResponse> doGetReportCard(Map<String, String> param,String lan);


    // TODO:-  Teacher Api
    Single<RegistrationResponse> doPostTeacherLogin(Map<String, String> params);

    Single<RegistrationResponse> doPostTeacherRegistration(SendRegistrationData param);

    Single<TrainerProfileResponse> doGetTeacherProfile(int id,String lan);

    Single<TrainerProfileResponse> doPostTeacherUpdate(ChildenModel param);

    Single<CoursesResponse> doGetCourses(int id,String lan);

    Single<CoursesResponse> doAddCourses(AddCourseModel params);

    Single<CoursesResponse> doRemoveCourses(AddCourseModel params);

    Single<CoursesResponse> doUpdateCourses(AddCourseModel params);

    Single<LiveCourseResponse> doGetLiveCourses(int id,String lan);

    Single<LiveCourseResponse> doAddLiveCourses(LiveCourseAddModel mLiveCourseAddModel);

    Single<LiveCourseResponse> doUpdateLiveCourses(LiveCourseAddModel mLiveCourseAddModel);

    Single<LiveCourseResponse> doGetVideoCourses(int id);

    Single<LiveCourseResponse> doRemoveVideoCourses(int id);

    Single<LiveCourseResponse> doRemoveVideoResources(int id);

    Single<LiveCourseResponse> doAddVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams,File intro);

    Single<LiveCourseResponse> doUpdateVideoCourses(Map<String, String> params, File file, Map<String, File> videoParams,File intro);

    Single<LiveCourseResponse> doAddResource(Map<String, String> params, List<File> fileList);

    Single<ResourceResponse> doGetResource(int id,String lan);

    Single<ResourceResponse> doRemoveResource(int id);

    Single<ResourceResponse> doRemoveResourceFile(int id);

    Single<ResourceResponse> doGetAllResource(Map<String, String> params,String lan);

    Single<ResourceResponse> doEditResource(Map<String, String> params, List<File> fileList);

    //Trainer Schedule
    Single<TrainerScheduleResponse> doGetTrainerSchedule(int id,String lan);

    Single<TrainerScheduleResponse> doSubmitTrainerSchedule(ScheduleSubmitModel params);

    Single<TrainerScheduleResponse> doGetTrainerScheduleTypeWise(Map<String, String> params,String lan);

    Single<SessionResponse> doGetTrainerAllRequest(Map<String, String> params,String lan);

    Single<AllRequestUpdate> doGetTrainerAllRequestUpdate(Map<String, String> params,String lan);

    Single<SessionStatusUpdateResponse> doPostSessionUpdate(Map<String, String> params);

    // TODO:-  General Api
    Single<LanguagesResponse> doGetGeneralLanguage(String lan);

    Single<NationalitiesResponse> doGetGeneralNationalities(String lan);

    Single<SubjectResponse> doGetSubjects(String lan);

    Single<TrainingResponseModel> doGetAllTraining(String lan);

    Single<GradesResponse> doGetGrades(String lan);

    Single<CurriculaResponse> doGetCurriculum(String lan);

    Single<ChatResponse> doGetChatList(String userType, int id,String lan);

    Single<ChatHistoryResponse> doGetChatHistory(String userType, int id, String receiverType, int receiverId,String lan);

    Single<ChatHistoryResponse> doSendChat(SendChatModel mSendChatModel);

    Single<AllVideoCourseResponse> getAllVideo();

    Single<SliderResponse> getSlider();


    // TODO:-  Map Api
    Single<MapSuggestionResponse> doGoogleSuggestionApiCall(MapSuggestionModel request);

    Single<MapPlaceResponse> doGooglePlaceApiCall(MapPlaceModel request);

    Single<DirectionResponses> doMapDirectionApi(String origin, String destination, String key);

    // TODO:-  Favorite Api
    Single<FavoriteResponse> doGetAllFavorite(int FavoriteById, String FavoriteBy,String lan);

    Single<FavoriteSubmitResponse> doSubmitFavorite(Map<String, String> params);

    Single<FavoriteSubmitResponse> doRemoveFavorite(Map<String, String> params,String lan);

}
