package com.ryx.tdreeb.ui.fragments.parentfragment.myresource;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;
import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;
import com.ryx.tdreeb.interfaces.MyResourceCallBack;

public class MyResourceItemViewModel {
    public final ObservableField<String> title;

    public final ObservableField<String> teacher_name;

    public final ObservableField<Boolean> isVisibility;

    public final ObservableField<String> price;

    public final ObservableField<String> image;

    public final ObservableField<String> rating;

    public final ObservableField<Boolean> is_fvrt;

    public final ObservableField<String> addressStr;

    public final ResourceModel mResourceModel;

    public final SessionModel mSessionModel;

    public final AddResourceCallBack mAddResourceCallBack;
    public final MyResourceCallBack mMyResourceCallBack;

    public MyResourceItemViewModel(ResourceModel mResourceModel, Context context, AddResourceCallBack mAddResourceCallBack, boolean isVisibility) {
        this.isVisibility = new ObservableField<>(isVisibility);
        this.mResourceModel = mResourceModel;
        this.mAddResourceCallBack = mAddResourceCallBack;
        this.mSessionModel = null;
        this.mMyResourceCallBack = null;
        if (mResourceModel.getSubject() != null) {
            this.title = new ObservableField<>(mResourceModel.getSubject().getSubjectName() + " " + mResourceModel.getResourceName());
        } else {
            this.title = new ObservableField<>(mResourceModel.getResourceName());
        }

        this.price = new ObservableField<>("AED: " + mResourceModel.getPrice());
        if (mResourceModel.getCurriculum() != null) {
            this.image = new ObservableField<>(mResourceModel.getCurriculum().getImage());
        } else {
            this.image = new ObservableField<>("");
        }
        this.is_fvrt = new ObservableField<>(mResourceModel.isFavorite());
        this.rating = new ObservableField<>(mResourceModel.getRating() + "");
        this.addressStr = new ObservableField<>(mResourceModel.getTrainer().getLocation());
        this.teacher_name = new ObservableField<>(context.getResources().getString(R.string.source_by_teacher, mResourceModel.getTrainer().getName()));
    }

    public MyResourceItemViewModel(SessionModel mSessionModel, Context context, MyResourceCallBack mMyResourceCallBack, boolean isVisibility) {
        this.isVisibility = new ObservableField<>(isVisibility);
        this.mMyResourceCallBack = mMyResourceCallBack;
        this.mResourceModel = null;
        this.mAddResourceCallBack = null;
        this.mSessionModel = mSessionModel;
        if (mSessionModel.getTrainerResourcesRequest() != null) {
            if(mSessionModel.getTrainerResourcesRequest().getSubject() != null){
                this.title = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().getSubject().getSubjectName() + " " + mSessionModel.getTrainerResourcesRequest().getResourceName()+" ("+mSessionModel.getBookFor().getName()+")");
            }else{
                this.title = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().getResourceName() +" ("+mSessionModel.getBookFor().getName()+")");
            }
            this.price = new ObservableField<>("AED: " + mSessionModel.getTrainerResourcesRequest().getPrice());
            if (mSessionModel.getTrainerResourcesRequest().getCurriculum() != null) {
                this.image = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().getCurriculum().getImage());
            } else {
                this.image = new ObservableField<>("");
            }
            this.is_fvrt = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().isFavorite());
            this.rating = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().getRating() + "");
            this.addressStr = new ObservableField<>(mSessionModel.getTrainerResourcesRequest().getTrainer().getLocation());
        } else {
            this.price = new ObservableField<>("AED: 0");
            this.image = new ObservableField<>("");
            this.is_fvrt = new ObservableField<>(false);
            this.rating = new ObservableField<>("0.0");
            this.addressStr = new ObservableField<>("");
            this.title = new ObservableField<>("");
        }


        this.teacher_name = new ObservableField<>(context.getResources().getString(R.string.source_by_teacher, mSessionModel.getTrainer().getName()));
    }


    public void onClickItem() {
        if (mAddResourceCallBack != null) {
            mAddResourceCallBack.onClickEdit(mResourceModel);
        }
    }

    public void onClickFvrt() {
        if (mAddResourceCallBack != null) {
            mAddResourceCallBack.onClickDelete(mResourceModel);
        } else {
            mMyResourceCallBack.myResourceFvrt(mSessionModel);
        }
    }

}