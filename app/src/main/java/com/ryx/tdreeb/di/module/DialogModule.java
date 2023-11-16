package com.ryx.tdreeb.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ryx.tdreeb.ViewModelProviderFactory;
import com.ryx.tdreeb.adapters.ChooseKidAdapter;
import com.ryx.tdreeb.adapters.LanguageAdapter;
import com.ryx.tdreeb.adapters.SessionAdapter;
import com.ryx.tdreeb.data.DataManager;
import com.ryx.tdreeb.ui.base.BaseDialog;
import com.ryx.tdreeb.ui.dialogs.choosekid.ChooseKidsViewModel;
import com.ryx.tdreeb.ui.dialogs.languagedialog.LanguageDialogViewModel;
import com.ryx.tdreeb.ui.dialogs.listdialog.ListViewModel;
import com.ryx.tdreeb.ui.dialogs.mysubject.MySubjectAddViewModel;
import com.ryx.tdreeb.ui.dialogs.videoplayer.VideoPlayerViewModel;
import com.ryx.tdreeb.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogModule {

    private BaseDialog dialog;

    public DialogModule(BaseDialog dialog) {
        this.dialog = dialog;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(dialog.getActivity());
    }

    @Provides
    ChooseKidsViewModel provideChooseKidsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ChooseKidsViewModel> supplier = () -> new ChooseKidsViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ChooseKidsViewModel> factory = new ViewModelProviderFactory<>(ChooseKidsViewModel.class, supplier);
        return new ViewModelProvider(dialog.getActivity(), factory).get(ChooseKidsViewModel.class);
    }

    @Provides
    LanguageDialogViewModel provideLanguageDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<LanguageDialogViewModel> supplier = () -> new LanguageDialogViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<LanguageDialogViewModel> factory = new ViewModelProviderFactory<>(LanguageDialogViewModel.class, supplier);
        return new ViewModelProvider(dialog.getActivity(), factory).get(LanguageDialogViewModel.class);
    }

    @Provides
    MySubjectAddViewModel provideMySubjectAddViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MySubjectAddViewModel> supplier = () -> new MySubjectAddViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MySubjectAddViewModel> factory = new ViewModelProviderFactory<>(MySubjectAddViewModel.class, supplier);
        return new ViewModelProvider(dialog.getActivity(), factory).get(MySubjectAddViewModel.class);
    }

    @Provides
    ListViewModel provideListViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ListViewModel> supplier = () -> new ListViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ListViewModel> factory = new ViewModelProviderFactory<>(ListViewModel.class, supplier);
        return new ViewModelProvider(dialog.getActivity(), factory).get(ListViewModel.class);
    }

    @Provides
    VideoPlayerViewModel provideVideoPlayerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<VideoPlayerViewModel> supplier = () -> new VideoPlayerViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<VideoPlayerViewModel> factory = new ViewModelProviderFactory<>(VideoPlayerViewModel.class, supplier);
        return new ViewModelProvider(dialog.getActivity(), factory).get(VideoPlayerViewModel.class);
    }

    //TODO:- Adapter
    @Provides
    ChooseKidAdapter provideChooseKidAdapter() {
        return new ChooseKidAdapter(new ArrayList<>());
    }

    @Provides
    LanguageAdapter provideLanguageAdapter() {
        return new LanguageAdapter(new ArrayList<>());
    }

}
