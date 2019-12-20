package com.example.hongligs.ninegridimage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hongligs.R;


import java.util.List;

public class WeiboNineGridImageAdapter implements NineGridImageAdapter<String> {

    private List<String> mImageUrls;
    private Context mContext;

    public WeiboNineGridImageAdapter(Context context, List<String> imageUrls) {
        mImageUrls = imageUrls;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageUrls == null? 0 : mImageUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mImageUrls == null ? null: mImageUrls.get(position);
    }

    @Override
    public View getView(int position, View convertView) {
        if (convertView == null) {
            convertView = new ImageView(mContext);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        String url = mImageUrls.get(position);
        Glide.with(mContext).load(url).placeholder(R.drawable.ic_weibo_error).centerCrop().into((ImageView)convertView);
        return convertView;
    }
}
