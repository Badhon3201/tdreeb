/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ryx.tdreeb.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.adapters.AddLiveDateTimeAdapter;
import com.ryx.tdreeb.adapters.ScheduleTimeAdapter;
import com.ryx.tdreeb.adapters.videohome.VideoHomeTwoAdapter;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.AddDateTimeModel;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseModel;
import com.ryx.tdreeb.data.model.api.trainerScheduleResponse.DailySchedule__1;
import com.ryx.tdreeb.helper.EqualSpacingItemDecoration;
import com.ryx.tdreeb.interfaces.LiveCourseModelPass;
import com.ryx.tdreeb.ui.fragments.trainerfragment.schedule.Model.SheduleModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter({"schedule_time_adapter", "setListener", "pos"})
    public static void addOption(RecyclerView recyclerView, List<DailySchedule__1> listTime, ScheduleTimeAdapter.AddTimeSchedule mAddTimeSchedule, int pos) {
        ScheduleTimeAdapter adapter = new ScheduleTimeAdapter(listTime, pos);
        adapter.setListener(mAddTimeSchedule);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new EqualSpacingItemDecoration(10));
        }
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"time_adapter"})
    public static void timeAdapter(RecyclerView recyclerView, List<AddDateTimeModel> listTime) {
        AddLiveDateTimeAdapter adapter = new AddLiveDateTimeAdapter(listTime,false);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new EqualSpacingItemDecoration(10));
        }
        Log.e("DATALINK", "timeAdapter: "+listTime.size() );
        recyclerView.setAdapter(adapter);
        adapter.addItems(listTime);
    }

    @BindingAdapter({"video_data","setListener"})
    public static void video_data(RecyclerView recyclerView, List<LiveCourseModel> list, LiveCourseModelPass mLiveCourseModelPass) {
        VideoHomeTwoAdapter adapter = new VideoHomeTwoAdapter(list);
        if (recyclerView.getItemDecorationCount() == 0) {

            recyclerView.addItemDecoration(new EqualSpacingItemDecoration(40));
        }
        //Log.e("DATALINK", "timeAdapter: "+listTime.size() );
        adapter.setListener(mLiveCourseModelPass);
        recyclerView.setAdapter(adapter);
        adapter.addItems(list);
    }

    @BindingAdapter({"relativeLayout_backgroundTint"})
    public static void relativeLayoutBackgroundTint(RelativeLayout relativeLayout, int pos) {
        relativeLayout.getBackground().setColorFilter(pos == 0 ? Color.parseColor("#a9a9a9") : Color.parseColor("#DC143C"), PorterDuff.Mode.SRC_ATOP);
    }

    @BindingAdapter({"webViewLoad"})
    public static void webViewLoadData(WebView webView, String link) {
        webView.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://tdreeb.r-y-x.com/Content/admin-assets/uploads/resource/845337_image_file_example_PPT_250kB8380978960840972584.ppt";
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
    }

    @BindingAdapter({"image_src"})
    public static void set_image_src(AppCompatImageView view, boolean isSelect) {
        if (!isSelect) {
            view.setImageResource(R.drawable.checked1);
        } else {
            view.setImageResource(R.drawable.checked_fill);
        }
    }

    @BindingAdapter({"image_src"})
    public static void set_image_close_add(AppCompatImageView view, int pos) {
        if (pos == 0) {
            view.setImageResource(R.drawable.ic_plus_white);
        } else {
            view.setImageResource(R.drawable.ic_close_dialog);
        }
    }

    @BindingAdapter({"image_chng"})
    public static void set_image_chng(AppCompatImageView view, int img) {
        view.setImageResource(img);
    }

    @BindingAdapter({"setEditTextWatcher"})
    public static void set_edit_textWatcher(EditText editText, TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    @BindingAdapter({"image_chng_two"})
    public static void set_image_chng_two(CircleImageView view, String img) {
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_user);
        Glide.with(context).load(img).apply(options).into(view);
    }

    @BindingAdapter({"imageFromUrl"})
    public static void set_image_from_url(ImageView view, String img) {
        Context context = view.getContext();
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.home_logo);
        Glide.with(context).load(img).apply(options).into(view);
    }

    @BindingAdapter({"fvrtImgChange"})
    public static void setFvrtImg(AppCompatImageView view, boolean isFvrt) {
        if(isFvrt){
            view.setImageResource(R.drawable.ic_heart_fill);
        }else {
            view.setImageResource(R.drawable.ic_heart);
        }
    }

    @BindingAdapter({"setName","setSub"})
    public static void setHtml(TextView view, String name,String subject) {
        Context context = view.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.setText(Html.fromHtml(context.getString(R.string.report_name, name,subject), Html.FROM_HTML_MODE_COMPACT));
        } else {
            view.setText(Html.fromHtml(context.getString(R.string.report_name, name,subject)));
        }
    }

    @BindingAdapter({"setGrade"})
    public static void setGrade(TextView view, String grade) {
        Context context = view.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.setText(Html.fromHtml(context.getString(R.string.grade_value, grade), Html.FROM_HTML_MODE_COMPACT));
        } else {
            view.setText(Html.fromHtml(context.getString(R.string.grade_value, grade)));
        }
    }

    @BindingAdapter("tintColorChange")
    public static void tintColorChange(AppCompatImageView imageView, Boolean isClick) {
        Log.e("tintColorChange", "tintColorChange: "+isClick );
        if(isClick){
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
        }else{

            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    @BindingAdapter("imageUrlTwo")
    public static void setImageUrlTwo(AppCompatImageView imageView, String url) {
        Context context = imageView.getContext();
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context).load(url).placeholder(circularProgressDrawable).into(imageView);
    }

}
