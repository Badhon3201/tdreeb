package com.ryx.tdreeb.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ryx.tdreeb.ViewModelProviderFactory;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.activites.login.LoginViewModel;
import com.ryx.tdreeb.ui.activites.main.MainViewModel;
import com.ryx.tdreeb.ui.activites.map.MapViewModel;
import com.ryx.tdreeb.ui.activites.map.addresssearch.MapSuggestionViewModel;
import com.ryx.tdreeb.ui.activites.otp.OtpViewModel;
import com.ryx.tdreeb.ui.activites.registration.RegistrationViewModel;
import com.ryx.tdreeb.ui.activites.splash.SplashViewModel;
import com.ryx.tdreeb.ui.activites.trainer.TrainerMainViewModel;
import com.ryx.tdreeb.ui.base.BaseActivity;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }


    //  TODO:- LayoutManager
    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(activity);
    }

    @Provides
    GridLayoutManager provideGridLayoutManager() {
        return new GridLayoutManager(activity, 4);
    }

    //  TODO:- ViewModel
    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<SplashViewModel> supplier = () -> new SplashViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<SplashViewModel> factory = new ViewModelProviderFactory<>(SplashViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SplashViewModel.class);
    }

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }

    @Provides
    RegistrationViewModel provideRegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<RegistrationViewModel> supplier = () -> new RegistrationViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<RegistrationViewModel> factory = new ViewModelProviderFactory<>(RegistrationViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RegistrationViewModel.class);
    }

    @Provides
    OtpViewModel provideOtpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<OtpViewModel> supplier = () -> new OtpViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<OtpViewModel> factory = new ViewModelProviderFactory<>(OtpViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(OtpViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    TrainerMainViewModel provideTrainerMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<TrainerMainViewModel> supplier = () -> new TrainerMainViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<TrainerMainViewModel> factory = new ViewModelProviderFactory<>(TrainerMainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TrainerMainViewModel.class);
    }

    @Provides
    MapViewModel provideMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MapViewModel> supplier = () -> new MapViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MapViewModel> factory = new ViewModelProviderFactory<>(MapViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MapViewModel.class);
    }

    @Provides
    MapSuggestionViewModel provideMapSuggestionViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MapSuggestionViewModel> supplier = () -> new MapSuggestionViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MapSuggestionViewModel> factory = new ViewModelProviderFactory<>(MapSuggestionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MapSuggestionViewModel.class);
    }


    //  TODO:- Adapter
//    @Provides
//    NotificationAdapter provideNotificationAdapter() {
//        return new NotificationAdapter(new ArrayList<>());
//    }


}
