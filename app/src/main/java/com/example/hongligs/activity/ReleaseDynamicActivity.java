package com.example.hongligs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hongligs.R;
import com.example.hongligs.chatdemo.MainConstant;
import com.example.hongligs.chatdemo.PictureSelectorConfig;
import com.example.hongligs.chatdemo.PictureViewer;
import com.example.hongligs.chatdemo.TitleBar;
import com.example.hongligs.ninegridimage.NineGridImageView;
import com.example.hongligs.ninegridimage.WeiboNineGridImageAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

//  发布动态
public class ReleaseDynamicActivity extends AppCompatActivity implements View.OnClickListener {


    private TitleBar title_bar;

    private NineGridImageView mNineGridImageView;
    private List<String> mList = new ArrayList<>();
    private WeiboNineGridImageAdapter adapter;
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_dynamic);
        initView();
        inidata();
    }
    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    private void inidata() {

    }

    private void initView() {

        title_bar = (TitleBar) findViewById(R.id.title_bar);
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(this);
        mNineGridImageView = (NineGridImageView) findViewById(R.id.gridView);
        mNineGridImageView.setOnItemClickListener(mOnItemClickListener );
        adapter = new WeiboNineGridImageAdapter(this, mList);
        if (mList.size() == 0) {
            mNineGridImageView.setVisibility(View.GONE);
        } else if (mList.size() == 9) {
            mImage.setVisibility(View.GONE);
        } else {
            mNineGridImageView.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.VISIBLE);
        }
        mNineGridImageView.setAdapter(adapter);
    }


    NineGridImageView.OnItemClickListener mOnItemClickListener = new NineGridImageView.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            PictureViewer.getInstance().open(ReleaseDynamicActivity.this,mList,(ImageView) view,mNineGridImageView.getImageViewGroup());
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image:
                Toast.makeText(this, "打开相机", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mList.clear();
            mList.addAll(toDeletePicList);
            adapter.notify();
        }
    }
    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mList.add(compressPath); //把图片添加到将要上传的图片数组中
                adapter.notify();
            }
        }
    }

}
