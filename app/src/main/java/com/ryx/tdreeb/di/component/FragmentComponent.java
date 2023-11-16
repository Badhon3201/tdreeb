package com.ryx.tdreeb.di.component;


import com.ryx.tdreeb.di.module.FragmentModule;
import com.ryx.tdreeb.di.scope.FragmentScope;
import com.ryx.tdreeb.ui.fragments.parentfragment.addchildren.AddChildrenFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.booktrainer.TrainerBookingFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.buytrainer.BuyTrainerFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.children.ChildrenListFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.FindCourseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findcourse.subcategory.SubCategoryFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findresource.FindResourceFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.findteacher.FindTrainerFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.home.HomeParentFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.LiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.mylive.MyLiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.livecourses.upcomminglive.UpCommingLiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.map.MapFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.map.directionmap.DirectionMapFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.myfavorites.MyFavoritesFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.myresource.MyResourceFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.mysession.MySessionParentFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.profile.ProfileFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.review.ReviewFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.scheduledatetime.ScheduleDateTimeFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerdetails.TrainerDetailsFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.trainerlist.TrainerListFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.VideoCourseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.findvideo.FindVideoCourseFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.myvideo.MyVideoFragment;
import com.ryx.tdreeb.ui.fragments.parentfragment.videocourse.videolist.VideoListFragment;
import com.ryx.tdreeb.ui.fragments.paymentmethod.PaymentMethodFragment;
import com.ryx.tdreeb.ui.fragments.paymentsummary.PaymentSummaryFragment;
import com.ryx.tdreeb.ui.fragments.reportcard.ReportCardFragment;
import com.ryx.tdreeb.ui.fragments.thankyou.ThankYouFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.TrainerAllRequestFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.accept.AcceptedRequestFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.allrequest.newrequest.NewRequestFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.TrainerChatFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.chat.chatdetails.ChatDetailsFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.home.TrainerHomeFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.TrainerLiveCoursesFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.livecourses.addlivecourse.AddLiveCourseFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mycourses.TrainerMyCoursesFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.mypayment.TrainerMyPaymentFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.TrainerMyResourcesFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource.AddResourceFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.notification.TrainerNotificationFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.profile.TrainerProfileFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.TrainerScheduleFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.completesession.CompletedSessionFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.mysession.TrainerMySessionFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.sessiondetails.TrainerSessionDetailsFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.trainersession.upcommingsession.UpcomingSessionFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.TrainerVideoCoursesFragment;
import com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse.AddVideoCourseFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(HomeParentFragment fragment);
//
//    void inject(NotificationFragment fragment);
//
//    void inject(ChatFragment fragment);
//
//    void inject(MySessionFragment fragment);

    //TODO:- Trainer Fragment
    void inject(TrainerHomeFragment fragment);

    void inject(TrainerAllRequestFragment fragment);

    void inject(AcceptedRequestFragment fragment);

    void inject(NewRequestFragment fragment);

    void inject(TrainerMySessionFragment fragment);

    void inject(CompletedSessionFragment fragment);

    void inject(UpcomingSessionFragment fragment);

    void inject(TrainerSessionDetailsFragment fragment);

    void inject(TrainerLiveCoursesFragment fragment);

    void inject(TrainerMyCoursesFragment fragment);

    void inject(TrainerVideoCoursesFragment fragment);

    void inject(TrainerNotificationFragment fragment);

    void inject(TrainerChatFragment fragment);

    void inject(TrainerMyResourcesFragment fragment);

    void inject(TrainerMyPaymentFragment fragment);

    void inject(TrainerScheduleFragment fragment);

    void inject(TrainerProfileFragment fragment);

    void inject(AddLiveCourseFragment fragment);

    void inject(AddVideoCourseFragment fragment);

    void inject(AddResourceFragment fragment);

    void inject(FindCourseFragment fragment);

    void inject(FindTrainerFragment fragment);

    void inject(ChildrenListFragment fragment);

    void inject(MyResourceFragment fragment);

    void inject(FindResourceFragment fragment);

    void inject(MyFavoritesFragment fragment);

    void inject(VideoCourseFragment fragment);

    void inject(FindVideoCourseFragment fragment);

    void inject(MyVideoFragment fragment);

    void inject(LiveCoursesFragment fragment);

    void inject(UpCommingLiveCoursesFragment fragment);

    void inject(MyLiveCoursesFragment fragment);

    void inject(MapFragment fragment);

    void inject(TrainerListFragment fragment);

    void inject(TrainerDetailsFragment fragment);

    void inject(ReviewFragment fragment);

    void inject(TrainerBookingFragment fragment);

    void inject(ScheduleDateTimeFragment fragment);

    void inject(PaymentMethodFragment fragment);

    void inject(ThankYouFragment fragment);

    void inject(BuyTrainerFragment fragment);

    void inject(PaymentSummaryFragment fragment);

    void inject(VideoListFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(ReportCardFragment fragment);

    void inject(AddChildrenFragment fragment);

    void inject(ChatDetailsFragment fragment);

    void inject(MySessionParentFragment fragment);

    void inject(DirectionMapFragment fragment);

    void inject(SubCategoryFragment fragment);

}
