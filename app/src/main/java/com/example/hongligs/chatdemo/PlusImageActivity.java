package com.example.hongligs.chatdemo;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.hongligs.R;
import java.util.ArrayList;
public class PlusImageActivity extends AppCompatActivity {
    private ArrayList<String> imgList;
    private int mPosition;
    private TitleBar mTitle;
    private ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_image);
        initView();
        imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
        mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
        mTitle.setShareIconResId(R.mipmap.shanchu);
        mTitle.setOnBackClickListener(new TitleBar.OnBackClickListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
        mTitle.setOnShareClickListener(new TitleBar.OnShareClickListener() {
            @Override
            public void onShareClick() {
                //                执行删除的逻辑
            }
        });
        mAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
//        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    private void initView() {
        mTitle = (TitleBar) findViewById(R.id.title);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    //删除图片
    private void deletePic() {
        CancelOrOkDialog dialog = new CancelOrOkDialog(this, "要删除这张图片吗?") {
            @Override
            public void ok() {
                super.ok();
                imgList.remove(mPosition); //从数据源移除删除的图片
                setPosition();
                dismiss();
            }
        };
        dialog.show();
    }
    //设置当前位置
    private void setPosition() {
//        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }
    //返回上一个页面
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
//
