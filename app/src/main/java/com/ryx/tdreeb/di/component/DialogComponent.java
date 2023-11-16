package com.ryx.tdreeb.di.component;


import com.ryx.tdreeb.di.module.DialogModule;
import com.ryx.tdreeb.di.scope.DialogScope;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsFragment;
import com.ryx.tdreeb.ui.dialogs.languagedialog.LanguageDialogFragment;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListFragment;
import com.ryx.tdreeb.ui.dialogs.mysubject.MySubjectAddFragment;
import com.ryx.tdreeb.ui.dialogs.videoplayer.VideoPlayerFragment;

import dagger.Component;

@DialogScope
@Component(modules = DialogModule.class, dependencies = AppComponent.class)
public interface DialogComponent {

    void inject(ChooseKidsFragment dialog);

    void inject(LanguageDialogFragment dialog);

    void inject(MySubjectAddFragment dialog);

    void inject(ListFragment dialog);

    void inject(VideoPlayerFragment dialog);

}