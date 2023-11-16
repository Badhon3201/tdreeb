package com.ryx.tdreeb.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.ryx.tdreeb.ViewModelProviderFactory;
import com.ryx.tdreeb.adapters.AddLiveDateTimeAdapter;
import com.ryx.tdreeb.adapters.AddVideoCourseAdapter;
import com.ryx.tdreeb.adapters.ChatDetailsAdapter;
import com.ryx.tdreeb.adapters.ChatListAdapter;
import com.ryx.tdreeb.adapters.ChildrenListAdapter;
import com.ryx.tdreeb.adapters.CourseListAdapter;
import com.ryx.tdreeb.adapters.FindCourseAdapter;
import com.ryx.tdreeb.adapters.FindTrainerAdapter;
import com.ryx.tdreeb.adapters.FindVideoCourseAdapter;
import com.ryx.tdreeb.adapters.LiveAdapter;
import com.ryx.tdreeb.adapters.MyChildHomeAdapter;
import com.ryx.tdreeb.adapters.MyLiveCoursesAdapter;
import com.ryx.tdreeb.adapters.MyResourceAdapter;
import com.ryx.tdreeb.adapters.MyVideoCourseAdapter;
import com.ryx.tdreeb.adapters.PackagePlanAdapter;
import com.ryx.tdreeb.adapters.ReportCardAdapter;
import com.ryx.tdreeb.adapters.RequestAdapter;
import com.ryx.tdreeb.adapters.ResourceFileUploadAdapter;
import com.ryx.tdreeb.adapters.ReviewAdapter;
import com.ryx.tdreeb.adapters.ScheduleTimeAdapter;
import com.ryx.tdreeb.adapters.ScheduleTimeTwoAdapter;
import com.ryx.tdreeb.adapters.ScheduleWeekAdapter;
import com.ryx.tdreeb.adapters.SessionAdapter;
import com.ryx.tdreeb.adapters.SubCategoryAdapter;
import com.ryx.tdreeb.adapters.TrainerChatAdapter;
import com.ryx.tdreeb.adapters.TrainerHomeSessionAdapter;
import com.ryx.tdreeb.adapters.TrainerListAdapter;
import com.ryx.tdreeb.adapters.TrainerNotificationAdapter;
import com.ryx.tdreeb.adapters.TrainerResourceAdapter;
import com.ryx.tdreeb.adapters.UpcomingLiveCourseAdapter;
import com.ryx.tdreeb.adapters.VideoCourseTrainerAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoCourseAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoHomeAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoHomeTwoAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule;
import com.ryx.tdreeb.ui.base.BaseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.addchildren.AddChildrenViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.booktrainer.TrainerBookingViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer.BuyTrainerViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.subcategory.SubCategoryViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.findresource.FindResourceViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.findteacher.FindTrainerViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.AddChildItemViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.HomeParentViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.LiveCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive.MyLiveCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive.UpCommingLiveCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.map.MapViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.map.directionmap.DirectionMapViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.myfavorites.MyFavoritesViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.myresource.MyResourceViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.mysession.MySessionParentViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.profile.ProfileViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.review.ReviewViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.scheduledatetime.ScheduleDateTimeViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerdetails.TrainerDetailsViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist.TrainerListViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.VideoCourseViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo.MyVideoViewModel;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.videolist.VideoListViewModel;
import com.ryx.tdreeb.ui.fragments.paymentmethod.PaymentMethodViewModel;
import com.ryx.tdreeb.ui.fragments.paymentsummary.PaymentSummaryViewModel;
import com.ryx.tdreeb.ui.fragments.reportcard.ReportCardViewModel;
import com.ryx.tdreeb.ui.fragments.thankyou.ThankYouViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.TrainerAllRequestViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept.AcceptedRequestViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest.NewRequestViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.TrainerChatViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails.ChatDetailsViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.home.TrainerHomeViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.TrainerLiveCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse.AddLiveCourseViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.TrainerMyCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mypayment.TrainerMyPaymentViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.TrainerMyResourcesViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource.AddResourceViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.notification.TrainerNotificationViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.profile.TrainerProfileViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.TrainerScheduleViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession.CompletedSessionViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.mysession.TrainerMySessionViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.sessiondetails.TrainerSessionDetailsViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession.UpcomingSessionViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.TrainerVideoCoursesViewModel;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.AddVideoCourseViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    GridLayoutManager provideGridLayoutManager() {
        return new GridLayoutManager(fragment.getActivity(), 4);
    }

    @Provides
    FlexboxLayoutManager provideFlexboxLayoutManager() {
        return new FlexboxLayoutManager(fragment.getActivity());
    }

    //  TODO:- VIEW MODEL
    @Provides
    HomeParentViewModel provideHomeParentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<HomeParentViewModel> supplier = () -> new HomeParentViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<HomeParentViewModel> factory = new ViewModelProviderFactory<>(HomeParentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeParentViewModel.class);
    }

    //  TODO:- Trainer Fragments VIEW MODEL
    @Provides
    TrainerHomeViewModel provideTrainerHomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerHomeViewModel> supplier = () -> new TrainerHomeViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerHomeViewModel> factory = new ViewModelProviderFactory<>(TrainerHomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerHomeViewModel.class);
    }

    @Provides
    TrainerAllRequestViewModel provideTrainerAllRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerAllRequestViewModel> supplier = () -> new TrainerAllRequestViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerAllRequestViewModel> factory = new ViewModelProviderFactory<>(TrainerAllRequestViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerAllRequestViewModel.class);
    }

    @Provides
    AcceptedRequestViewModel provideAcceptedRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AcceptedRequestViewModel> supplier = () -> new AcceptedRequestViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AcceptedRequestViewModel> factory = new ViewModelProviderFactory<>(AcceptedRequestViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AcceptedRequestViewModel.class);
    }

    @Provides
    NewRequestViewModel provideNewRequestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<NewRequestViewModel> supplier = () -> new NewRequestViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<NewRequestViewModel> factory = new ViewModelProviderFactory<>(NewRequestViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(NewRequestViewModel.class);
    }

    @Provides
    TrainerMySessionViewModel provideTrainerMySessionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerMySessionViewModel> supplier = () -> new TrainerMySessionViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerMySessionViewModel> factory = new ViewModelProviderFactory<>(TrainerMySessionViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerMySessionViewModel.class);
    }

    @Provides
    CompletedSessionViewModel provideCompletedSessionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<CompletedSessionViewModel> supplier = () -> new CompletedSessionViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<CompletedSessionViewModel> factory = new ViewModelProviderFactory<>(CompletedSessionViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CompletedSessionViewModel.class);
    }

    @Provides
    UpcomingSessionViewModel provideUpcomingSessionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<UpcomingSessionViewModel> supplier = () -> new UpcomingSessionViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<UpcomingSessionViewModel> factory = new ViewModelProviderFactory<>(UpcomingSessionViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(UpcomingSessionViewModel.class);
    }

    @Provides
    TrainerSessionDetailsViewModel provideTrainerSessionDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerSessionDetailsViewModel> supplier = () -> new TrainerSessionDetailsViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerSessionDetailsViewModel> factory = new ViewModelProviderFactory<>(TrainerSessionDetailsViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerSessionDetailsViewModel.class);
    }

    @Provides
    TrainerLiveCoursesViewModel provideTrainerLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerLiveCoursesViewModel> supplier = () -> new TrainerLiveCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerLiveCoursesViewModel> factory = new ViewModelProviderFactory<>(TrainerLiveCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerLiveCoursesViewModel.class);
    }

    @Provides
    TrainerMyCoursesViewModel provideTrainerMyCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerMyCoursesViewModel> supplier = () -> new TrainerMyCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerMyCoursesViewModel> factory = new ViewModelProviderFactory<>(TrainerMyCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerMyCoursesViewModel.class);
    }

    @Provides
    TrainerVideoCoursesViewModel provideTrainerVideoCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerVideoCoursesViewModel> supplier = () -> new TrainerVideoCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerVideoCoursesViewModel> factory = new ViewModelProviderFactory<>(TrainerVideoCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerVideoCoursesViewModel.class);
    }

    @Provides
    TrainerNotificationViewModel provideTrainerNotificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerNotificationViewModel> supplier = () -> new TrainerNotificationViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerNotificationViewModel> factory = new ViewModelProviderFactory<>(TrainerNotificationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerNotificationViewModel.class);
    }

    @Provides
    TrainerChatViewModel provideTrainerChatViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerChatViewModel> supplier = () -> new TrainerChatViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerChatViewModel> factory = new ViewModelProviderFactory<>(TrainerChatViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerChatViewModel.class);
    }

    @Provides
    TrainerMyResourcesViewModel provideTrainerMyResourcesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerMyResourcesViewModel> supplier = () -> new TrainerMyResourcesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerMyResourcesViewModel> factory = new ViewModelProviderFactory<>(TrainerMyResourcesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerMyResourcesViewModel.class);
    }

    @Provides
    TrainerMyPaymentViewModel provideTrainerMyPaymentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerMyPaymentViewModel> supplier = () -> new TrainerMyPaymentViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerMyPaymentViewModel> factory = new ViewModelProviderFactory<>(TrainerMyPaymentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerMyPaymentViewModel.class);
    }

    @Provides
    TrainerScheduleViewModel provideTrainerScheduleViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerScheduleViewModel> supplier = () -> new TrainerScheduleViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerScheduleViewModel> factory = new ViewModelProviderFactory<>(TrainerScheduleViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerScheduleViewModel.class);
    }

    @Provides
    TrainerProfileViewModel provideTrainerProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerProfileViewModel> supplier = () -> new TrainerProfileViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerProfileViewModel> factory = new ViewModelProviderFactory<>(TrainerProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerProfileViewModel.class);
    }

    @Provides
    AddLiveCourseViewModel provideAddLiveCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AddLiveCourseViewModel> supplier = () -> new AddLiveCourseViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AddLiveCourseViewModel> factory = new ViewModelProviderFactory<>(AddLiveCourseViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AddLiveCourseViewModel.class);
    }

    @Provides
    AddVideoCourseViewModel provideAddVideoCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AddVideoCourseViewModel> supplier = () -> new AddVideoCourseViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AddVideoCourseViewModel> factory = new ViewModelProviderFactory<>(AddVideoCourseViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AddVideoCourseViewModel.class);
    }

    @Provides
    AddResourceViewModel provideAddResourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AddResourceViewModel> supplier = () -> new AddResourceViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AddResourceViewModel> factory = new ViewModelProviderFactory<>(AddResourceViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AddResourceViewModel.class);
    }

    @Provides
    FindCourseViewModel provideFindCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<FindCourseViewModel> supplier = () -> new FindCourseViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<FindCourseViewModel> factory = new ViewModelProviderFactory<>(FindCourseViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FindCourseViewModel.class);
    }

    @Provides
    FindTrainerViewModel provideFindTrainerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<FindTrainerViewModel> supplier = () -> new FindTrainerViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<FindTrainerViewModel> factory = new ViewModelProviderFactory<>(FindTrainerViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FindTrainerViewModel.class);
    }

    @Provides
    MyResourceViewModel provideMyResourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MyResourceViewModel> supplier = () -> new MyResourceViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MyResourceViewModel> factory = new ViewModelProviderFactory<>(MyResourceViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyResourceViewModel.class);
    }

    @Provides
    FindResourceViewModel provideFindResourceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<FindResourceViewModel> supplier = () -> new FindResourceViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<FindResourceViewModel> factory = new ViewModelProviderFactory<>(FindResourceViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FindResourceViewModel.class);
    }

    @Provides
    MyFavoritesViewModel provideMyFavoritesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MyFavoritesViewModel> supplier = () -> new MyFavoritesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MyFavoritesViewModel> factory = new ViewModelProviderFactory<>(MyFavoritesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyFavoritesViewModel.class);
    }

    @Provides
    VideoCourseViewModel provideVideoCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<VideoCourseViewModel> supplier = () -> new VideoCourseViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<VideoCourseViewModel> factory = new ViewModelProviderFactory<>(VideoCourseViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(VideoCourseViewModel.class);
    }

    @Provides
    FindVideoCourseViewModel provideFindVideoCourseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<FindVideoCourseViewModel> supplier = () -> new FindVideoCourseViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<FindVideoCourseViewModel> factory = new ViewModelProviderFactory<>(FindVideoCourseViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FindVideoCourseViewModel.class);
    }

    @Provides
    MyVideoViewModel provideMyVideoViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MyVideoViewModel> supplier = () -> new MyVideoViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MyVideoViewModel> factory = new ViewModelProviderFactory<>(MyVideoViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyVideoViewModel.class);
    }

    @Provides
    LiveCoursesViewModel provideLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<LiveCoursesViewModel> supplier = () -> new LiveCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<LiveCoursesViewModel> factory = new ViewModelProviderFactory<>(LiveCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(LiveCoursesViewModel.class);
    }

    @Provides
    UpCommingLiveCoursesViewModel provideUpCommingLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<UpCommingLiveCoursesViewModel> supplier = () -> new UpCommingLiveCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<UpCommingLiveCoursesViewModel> factory = new ViewModelProviderFactory<>(UpCommingLiveCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(UpCommingLiveCoursesViewModel.class);
    }

    @Provides
    MyLiveCoursesViewModel provideMyLiveCoursesViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MyLiveCoursesViewModel> supplier = () -> new MyLiveCoursesViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MyLiveCoursesViewModel> factory = new ViewModelProviderFactory<>(MyLiveCoursesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyLiveCoursesViewModel.class);
    }

    @Provides
    MapViewModel provideMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MapViewModel> supplier = () -> new MapViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MapViewModel> factory = new ViewModelProviderFactory<>(MapViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MapViewModel.class);
    }

    @Provides
    TrainerListViewModel provideTrainerListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerListViewModel> supplier = () -> new TrainerListViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerListViewModel> factory = new ViewModelProviderFactory<>(TrainerListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerListViewModel.class);
    }

    @Provides
    TrainerDetailsViewModel provideTrainerDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerDetailsViewModel> supplier = () -> new TrainerDetailsViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerDetailsViewModel> factory = new ViewModelProviderFactory<>(TrainerDetailsViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerDetailsViewModel.class);
    }

    @Provides
    ReviewViewModel provideReviewViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ReviewViewModel> supplier = () -> new ReviewViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ReviewViewModel> factory = new ViewModelProviderFactory<>(ReviewViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ReviewViewModel.class);
    }

    @Provides
    TrainerBookingViewModel provideTrainerBookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerBookingViewModel> supplier = () -> new TrainerBookingViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerBookingViewModel> factory = new ViewModelProviderFactory<>(TrainerBookingViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TrainerBookingViewModel.class);
    }

    @Provides
    ScheduleDateTimeViewModel provideScheduleDateTimeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ScheduleDateTimeViewModel> supplier = () -> new ScheduleDateTimeViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ScheduleDateTimeViewModel> factory = new ViewModelProviderFactory<>(ScheduleDateTimeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ScheduleDateTimeViewModel.class);
    }

    @Provides
    PaymentMethodViewModel providePaymentMethodViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<PaymentMethodViewModel> supplier = () -> new PaymentMethodViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<PaymentMethodViewModel> factory = new ViewModelProviderFactory<>(PaymentMethodViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(PaymentMethodViewModel.class);
    }

    @Provides
    ThankYouViewModel provideThankYouViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ThankYouViewModel> supplier = () -> new ThankYouViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ThankYouViewModel> factory = new ViewModelProviderFactory<>(ThankYouViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ThankYouViewModel.class);
    }

    @Provides
    BuyTrainerViewModel provideBuyTrainerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<BuyTrainerViewModel> supplier = () -> new BuyTrainerViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<BuyTrainerViewModel> factory = new ViewModelProviderFactory<>(BuyTrainerViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BuyTrainerViewModel.class);
    }

    @Provides
    PaymentSummaryViewModel providePaymentSummaryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<PaymentSummaryViewModel> supplier = () -> new PaymentSummaryViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<PaymentSummaryViewModel> factory = new ViewModelProviderFactory<>(PaymentSummaryViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(PaymentSummaryViewModel.class);
    }

    @Provides
    VideoListViewModel provideVideoListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<VideoListViewModel> supplier = () -> new VideoListViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<VideoListViewModel> factory = new ViewModelProviderFactory<>(VideoListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(VideoListViewModel.class);
    }

    @Provides
    ProfileViewModel provideProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ProfileViewModel> supplier = () -> new ProfileViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ProfileViewModel> factory = new ViewModelProviderFactory<>(ProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProfileViewModel.class);
    }

    @Provides
    ReportCardViewModel provideReportCardViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ReportCardViewModel> supplier = () -> new ReportCardViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ReportCardViewModel> factory = new ViewModelProviderFactory<>(ReportCardViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ReportCardViewModel.class);
    }

    @Provides
    AddChildrenViewModel provideAddChildrenViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<AddChildrenViewModel> supplier = () -> new AddChildrenViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<AddChildrenViewModel> factory = new ViewModelProviderFactory<>(AddChildrenViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AddChildrenViewModel.class);
    }

    @Provides
    ChatDetailsViewModel provideChatDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ChatDetailsViewModel> supplier = () -> new ChatDetailsViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ChatDetailsViewModel> factory = new ViewModelProviderFactory<>(ChatDetailsViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ChatDetailsViewModel.class);
    }

    @Provides
    MySessionParentViewModel provideMySessionParentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MySessionParentViewModel> supplier = () -> new MySessionParentViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MySessionParentViewModel> factory = new ViewModelProviderFactory<>(MySessionParentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MySessionParentViewModel.class);
    }

    @Provides
    DirectionMapViewModel provideDirectionMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<DirectionMapViewModel> supplier = () -> new DirectionMapViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<DirectionMapViewModel> factory = new ViewModelProviderFactory<>(DirectionMapViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(DirectionMapViewModel.class);
    }

    @Provides
    SubCategoryViewModel provideSubCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<SubCategoryViewModel> supplier = () -> new SubCategoryViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<SubCategoryViewModel> factory = new ViewModelProviderFactory<>(SubCategoryViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SubCategoryViewModel.class);
    }


    //TODO:- Adapter
    //TODO:- Trainer Adapter
    @Provides
    SessionAdapter provideSessionAdapter() {
        return new SessionAdapter(new ArrayList<>());
    }

    @Provides
    TrainerHomeSessionAdapter provideTrainerHomeSession() {
        return new TrainerHomeSessionAdapter(new ArrayList<>());
    }

    @Provides
    RequestAdapter provideRequestAdapter() {
        return new RequestAdapter(new ArrayList<>());
    }

    @Provides
    LiveAdapter provideLiveAdapter() {
        return new LiveAdapter(new ArrayList<>());
    }

    @Provides
    CourseListAdapter provideCourseListAdapter() {
        return new CourseListAdapter(new ArrayList<>());
    }

    @Provides
    VideoCourseTrainerAdapter provideVideoCourseTrainerAdapter() {
        return new VideoCourseTrainerAdapter(new ArrayList<>());
    }

    @Provides
    TrainerNotificationAdapter provideTrainerNotificationAdapter() {
        return new TrainerNotificationAdapter(new ArrayList<>());
    }

    @Provides
    TrainerChatAdapter provideTrainerChatAdapter() {
        return new TrainerChatAdapter(new ArrayList<>());
    }

    @Provides
    TrainerResourceAdapter provideTrainerResourceAdapter() {
        return new TrainerResourceAdapter(new ArrayList<>());
    }

    @Provides
    PackagePlanAdapter providePackagePlanAdapter() {
        return new PackagePlanAdapter(new ArrayList<>());
    }

    @Provides
    ScheduleWeekAdapter provideScheduleWeekAdapter() {
        return new ScheduleWeekAdapter(new ArrayList<>());
    }

    @Provides
    ScheduleTimeAdapter provideScheduleTimeAdapter() {
        return new ScheduleTimeAdapter(new ArrayList<>(), 0);
    }

    @Provides
    AddVideoCourseAdapter provideAddVideoCourseAdapter() {
        return new AddVideoCourseAdapter(new ArrayList<>());
    }

    @Provides
    MyChildHomeAdapter provideMyChildHomeAdapter() {
        return new MyChildHomeAdapter(new ArrayList<>());
    }

    @Provides
    FindTrainerAdapter provideFindTrainerAdapter() {
        return new FindTrainerAdapter(new ArrayList<>());
    }

    @Provides
    FindCourseAdapter provideFindCourseAdapter() {
        return new FindCourseAdapter(new ArrayList<>());
    }

    @Provides
    ChildrenListAdapter provideChildrenListAdapter() {
        return new ChildrenListAdapter(new ArrayList<>());
    }

    @Provides
    MyResourceAdapter provideMyResourceAdapter() {
        return new MyResourceAdapter(new ArrayList<>(), new ArrayList<>());
    }

    @Provides
    FindVideoCourseAdapter provideFindVideoCourseAdapter() {
        return new FindVideoCourseAdapter(new ArrayList<>());
    }

    @Provides
    MyVideoCourseAdapter provideMyVideoCourseAdapter() {
        return new MyVideoCourseAdapter(new ArrayList<>());
    }

    @Provides
    UpcomingLiveCourseAdapter provideUpcomingLiveCourseAdapter() {
        return new UpcomingLiveCourseAdapter(new ArrayList<>());
    }

    @Provides
    MyLiveCoursesAdapter provideMyLiveCoursesAdapter() {
        return new MyLiveCoursesAdapter(new ArrayList<>());
    }

    @Provides
    TrainerListAdapter provideTrainerListAdapter() {
        return new TrainerListAdapter(new ArrayList<>());
    }

    @Provides
    ReviewAdapter provideReviewAdapter() {
        return new ReviewAdapter(new ArrayList<>());
    }

    @Provides
    ScheduleTimeTwoAdapter provideScheduleTimeTwoAdapter() {
        return new ScheduleTimeTwoAdapter(fragment.getActivity(), new ArrayList<>());
    }

    @Provides
    ReportCardAdapter provideReportCardAdapter() {
        return new ReportCardAdapter(new ArrayList<>());
    }

    @Provides
    ChatListAdapter provideChatListAdapter() {
        return new ChatListAdapter(new ArrayList<>());
    }

    @Provides
    ChatDetailsAdapter provideChatDetailsAdapter() {
        return new ChatDetailsAdapter(new ArrayList<>());
    }

    @Provides
    ResourceFileUploadAdapter provideResourceFileUploadAdapter() {
        return new ResourceFileUploadAdapter(new ArrayList<>());
    }

    @Provides
    AddLiveDateTimeAdapter provideAddLiveDateTimeAdapter() {
        return new AddLiveDateTimeAdapter(new ArrayList<>(), true);
    }

    @Provides
    SubCategoryAdapter provideSubCategoryAdapter() {
        return new SubCategoryAdapter(new ArrayList<>());
    }

    @Provides
    VideoHomeAdapter provideVideoHomeAdapter() {
        return new VideoHomeAdapter(new ArrayList<>());
    }

    @Provides
    VideoHomeTwoAdapter provideVideoHomeTwoAdapter() {
        return new VideoHomeTwoAdapter(new ArrayList<>());
    }

    @Provides
    VideoCourseAdapter provideVideoCourseAdapter() {
        return new VideoCourseAdapter(new ArrayList<>());
    }
}
