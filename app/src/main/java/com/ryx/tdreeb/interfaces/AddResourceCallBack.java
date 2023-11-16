package com.ryx.tdreeb.interfaces;

import com.ryx.tdreeb.data.model.api.resourcemodel.ResourceModel;

import java.io.File;

public interface AddResourceCallBack {
    void onClickFileRemoveItem(int pos, File file);

    void onClickEdit(ResourceModel mResourceModel);

    void onClickDelete(ResourceModel mResourceModel);
}
