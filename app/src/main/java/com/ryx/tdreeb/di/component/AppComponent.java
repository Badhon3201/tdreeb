package com.ryx.tdreeb.di.component;

import android.app.Application;


import com.ryx.tdreeb.MvvmApp;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.di.module.AppModule;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MvvmApp app);

    DataManager getDataManager();

    SchedulerProvider getSchedulerProvider();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
