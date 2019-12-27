package com.example.hongligs.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.hongligs.R;
import com.example.hongligs.bean.GroupBannerBean;
import com.example.hongligs.fragment.HomepageFragment;
import com.example.hongligs.http.NetCallBack;
import com.example.hongligs.http.OkHttpUtils;
import com.example.hongligs.http.URL;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GropCardActivity extends AppCompatActivity implements OnBannerListener {
    private Banner mbanner;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> list_title = new ArrayList<>();
   // private List<GroupBannerBean.CircleBean> groupBannerBeans = new ArrayList<GroupBannerBean.CircleBean>();
   private List<String> bglist = new ArrayList<>();
    private TextView text_yanzhi;
    private TextView text_qiamming;
    private TextView text_number;
    private TextView text_hyd;
    private TextView text_party;
    private TextView text_jiaoy;
    private TextView text_gongago;
    private ImageView Image_icon;
    private Bitmap bitmap;
    private String name;
    private String clist;

    //群资料卡
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grop_card);
        clist = getIntent().getStringExtra("clist");


        initView();
        inidata();
    }

    private void inidata( ) {
        Map<String, String> map = new HashMap<>();
        map.put("cid",clist);

        OkHttpUtils.getInstance(this).postMap(URL.GroupBanner, map, new NetCallBack() {


            @Override
            public void onFailure(IOException e) {
            }

            @Override
            public void onResponse(String response, String type) {

                Log.e("response",response);
                GroupBannerBean groupBannerBean = JSON.parseObject(response, GroupBannerBean.class);
                GroupBannerBean.CircleBean circle = groupBannerBean.getCircle();
                String name = circle.getName();
                String title = circle.getTitle();
                String icon = circle.getIcon();
                String tag = circle.getTag();
                int personnum = circle.getPersonnum();//人数
                int hot = circle.getHot();//热度
                int party = circle.getParty();
                String bulletin = circle.getBulletin();//公告


                Log.e("icon",icon);
                //画圆形图片
                Glide.with(getApplicationContext()).load(icon).into(Image_icon);

                Uri parse = Uri.parse(circle.getIcon());
                text_yanzhi.setText(name);
                text_qiamming.setText(title);
                text_number.setText(personnum+"");
                text_hyd.setText(hot+"");
                text_party.setText(party+"");
                text_gongago.setText(bulletin);
                text_jiaoy.setText(tag);


                for (int i = 0; i < circle.getBglist().size(); i++) {
                    list_path.add(circle.getBglist().get(i));
                    Log.e("response",response);
                    list_title.add(circle.getName());
                }
                initBanner();
            }
        },"");
    }

    private void initBanner() {

            //设置内置样式，共有六种可以点入方法内逐一体验使用。
        mbanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //设置图片加载器，图片加载器在下方
        mbanner.setImageLoader(new MyLoader());
            //设置图片网址或地址的集合
        mbanner.setImages(list_path);
            //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mbanner.setBannerAnimation(Transformer.Default);
            //设置轮播图的标题集合
       mbanner.setBannerTitles(list_title);
            //设置轮播间隔时间
        mbanner.setDelayTime(2000);
            //设置是否为自动轮播，默认是“是”。
        mbanner.isAutoPlay(true);
            //设置指示器的位置，小点点，左中右。
        mbanner.setIndicatorGravity(BannerConfig.CENTER)
                    //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                    .setOnBannerListener(this)
                    //必须最后调用的方法，启动轮播图。
                    .start();
        }



    private void initView() {
        mbanner = (Banner) findViewById(R.id.mbanner);
        text_yanzhi = (TextView) findViewById(R.id.text_yanzhi);
        text_qiamming = (TextView) findViewById(R.id.text_qiamming);
        text_number = (TextView) findViewById(R.id.text_number);
        text_hyd = (TextView) findViewById(R.id.text_hyd);
        text_jiaoy = (TextView) findViewById(R.id.text_jiaoy);
        text_gongago = (TextView) findViewById(R.id.text_gongago);
        text_party = (TextView) findViewById(R.id.text_party);
        Image_icon = (ImageView) findViewById(R.id.Image_icon);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
    //轮播图的监听方法

    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第" + position + "张轮播图");
    }





    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mbanner.stopAutoPlay();
    }

    @Override
    public void onStart() {
        super.onStart();
        mbanner.startAutoPlay();
    }


}
