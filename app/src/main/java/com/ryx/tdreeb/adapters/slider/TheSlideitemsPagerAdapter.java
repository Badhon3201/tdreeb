package com.ryx.tdreeb.adapters.slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.videoslider.SliderModel;
import com.ryx.tdreeb.data.model.db.The_Slide_Items_Model_Class;

import java.util.List;

public class TheSlideitemsPagerAdapter extends PagerAdapter {

    private Context mcontext;
    private List<SliderModel> theSlideItemsModelClassList;


    public TheSlideitemsPagerAdapter(Context Mcontext, List<SliderModel> theSlideItemsModelClassList) {
        this.mcontext = Mcontext;
        this.theSlideItemsModelClassList = theSlideItemsModelClassList;
    }


    public void addItem(List<SliderModel> list){
        this.theSlideItemsModelClassList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.video_slider_item,null);

        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(mcontext);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(mcontext).load(theSlideItemsModelClassList.get(position).getImage())
                .placeholder(circularProgressDrawable).error(R.drawable.home_logo)
                .into(featured_image);

        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return theSlideItemsModelClassList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
