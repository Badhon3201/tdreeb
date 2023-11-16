package com.ryx.tdreeb.di.component;


import com.ryx.tdreeb.di.module.ActivityModule;
import com.ryx.tdreeb.di.scope.ActivityScope;
import com.ryx.tdreeb.ui.activites.login.LoginActivity;
import com.ryx.tdreeb.ui.activites.main.MainActivity;
import com.ryx.tdreeb.ui.activites.map.MapActivity;
import com.ryx.tdreeb.ui.activites.map.addresssearch.MapSuggestionActivity;
import com.ryx.tdreeb.ui.activites.otp.OtpActivity;
import com.ryx.tdreeb.ui.activites.registration.RegistrationActivity;
import com.ryx.tdreeb.ui.activites.splash.SplashActivity;
import com.ryx.tdreeb.ui.activites.trainer.TrainerMainActivity;
import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(RegistrationActivity activity);

    void inject(OtpActivity activity);

    void inject(MainActivity activity);

    void inject(TrainerMainActivity activity);

    void inject(MapActivity activity);

    void inject(MapSuggestionActivity activity);
}
