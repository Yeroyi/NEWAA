package com.example.hongligs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hongligs.R;
import com.example.hongligs.chatdemo.PictureViewer;
import com.example.hongligs.ninegridimage.NineGridImageView;
import com.example.hongligs.ninegridimage.WeiboNineGridImageAdapter;

import java.util.ArrayList;
import java.util.List;

//动态详情页

public class DTdetailpageActivity extends AppCompatActivity {

    private NineGridImageView mNineGridImageView;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtdetailpage);

        mList.add("http://file02.16sucai.com/d/file/2015/0128/8b0f093a8edea9f7e7458406f19098af.jpg");
        mList.add("http://dl.ppt123.net/pptbj/201603/2016030410190920.jpg");
        mList.add("http://b-ssl.duitang.com/uploads/item/201507/04/20150704212949_PSfNZ.jpeg");
        mList.add("http://cdn.duitang.com/uploads/item/201409/08/20140908130732_kVXzh.jpeg");
        mList.add("http://i2.w.yun.hjfile.cn/doc/201303/54c809bf-1eb2-400b-827f-6f024d7d599b_01.jpg");
        mList.add("http://img02.tooopen.com/images/20131212/sy_51552288528.jpg");
        mList.add("http://file02.16sucai.com/d/file/2014/1006/e94e4f70870be76a018dff428306c5a3.jpg");
        mList.add("http://file02.16sucai.com/d/file/2014/1110/cd71e43a3076f36284057528380e3dea.jpg");
        mList.add("http://a3.att.hudong.com/35/34/19300001295750130986345801104.jpg");
        initView();
        inidata();
    }

    private void inidata() {

    }

    private void initView() {
        mNineGridImageView = (NineGridImageView) findViewById(R.id.nine_grid_image);
        mNineGridImageView.setOnItemClickListener(mOnItemClickListener);
        mNineGridImageView.setAdapter(new WeiboNineGridImageAdapter(this,mList));
    }

    private NineGridImageView.OnItemClickListener mOnItemClickListener = new NineGridImageView.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            PictureViewer.getInstance().open(DTdetailpageActivity.this, mList, (ImageView) view, mNineGridImageView.getImageViewGroup());
        }
    };
}
