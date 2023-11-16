package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;
import com.ryx.tdreeb.interfaces.AddResourceCallBack;

public class ResourceTrainerItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> title;

    public final ObservableField<String> resourceBY;

    public final ObservableField<String> price;

    public final ObservableField<String> location;

    public final ResourceModel mResourceModel;

    public final AddResourceCallBack mAddResourceCallBack;


    public ResourceTrainerItemViewModel(ResourceModel mResourceModel,AddResourceCallBack mAddResourceCallBack) {
        this.mAddResourceCallBack = mAddResourceCallBack;
        this.mResourceModel = mResourceModel;
        this.image = new ObservableField<>(mResourceModel.getResourceImage());
        this.title = new ObservableField<>(mResourceModel.getResourceName());
        this.resourceBY = new ObservableField<>("Source by " + mResourceModel.getTrainer().getName());
        this.price = new ObservableField<>("AED " + mResourceModel.getPrice());
        this.location = new ObservableField<>(mResourceModel.getTrainer().getLocation());
    }

    public void onClickEdit(){
        if(mAddResourceCallBack != null){
            mAddResourceCallBack.onClickEdit(mResourceModel);
        }
    }

    public void onClickDelete(){
        if(mAddResourceCallBack != null){
            mAddResourceCallBack.onClickDelete(mResourceModel);
        }
    }
}