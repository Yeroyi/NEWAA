package com.example.hongligs.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hongligs.R;
import com.example.hongligs.adapter.RecyAdapter;
import com.example.hongligs.bean.PtrClassicListFooter;
import com.example.hongligs.bean.PtrClassicListHeader;
import com.example.hongligs.bean.PtrClassicRefreshLayout;
import com.example.hongligs.bean.TreeBean;
import com.example.hongligs.http.NetCallBack;
import com.example.hongligs.http.OkHttpUtils;
import com.example.hongligs.http.URL;
import com.example.hongligs.utils.DividerItemDecoration;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TreeFragment extends Fragment {


    private RecyclerView mRecyclerView;
   // List<wetherbean.DataBean.ForecastBean> forecast;
    private View mView;
    private RecyAdapter mRecyAdapter;
    private PtrClassicRefreshLayout fragment_consultation_ptr;
    private int pagenum = 1;//
    private List<TreeBean.HollowlistBean> hollowlist;

    public TreeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_today, container, false);
      initView();
        initData();

       // initvieww();

        return mView;
    }

    private void initvieww() {


    }

    private void initView() {
        hollowlist = new ArrayList<>();
        mRecyclerView = mView.findViewById(R.id.rv_View);

        fragment_consultation_ptr = mView.findViewById(R.id.fragment_consultation_ptr);

//      分割线用
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        / /头部阻尼系数
        fragment_consultation_ptr.setResistanceHeader(1.7f);
// 底部阻尼系数
        fragment_consultation_ptr.setResistanceFooter(1.7f);
// 默认1.2f，移动达到头部高度1.2倍时触发刷新操作
        fragment_consultation_ptr.setRatioOfHeaderHeightToRefresh(1.2f);
// 头部回弹时间
        fragment_consultation_ptr.setDurationToCloseHeader(1000);
// 底部回弹时间
        fragment_consultation_ptr.setDurationToCloseFooter(1000);
// 释放刷新
        fragment_consultation_ptr.setPullToRefresh(false);
// 释放时恢复到刷新状态的时间
        fragment_consultation_ptr.setDurationToBackHeader(200);
        fragment_consultation_ptr.setDurationToBackFooter(200);


        // ------------------------------  刷新方法-----------------------------------

        PtrClassicListHeader header = new PtrClassicListHeader(getContext());
        header.setLastUpdateTimeRelateObject(this);
        PtrClassicListFooter footer = new PtrClassicListFooter(getContext());
        footer.setLastUpdateTimeRelateObject(this);
        fragment_consultation_ptr.setFooterView(footer);
        fragment_consultation_ptr.addPtrUIHandler(footer);
//
        fragment_consultation_ptr.setHeaderView(header);
        fragment_consultation_ptr.addPtrUIHandler(header);

        fragment_consultation_ptr.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //上拉加载
                pagenum++;  //++
                initData();
                fragment_consultation_ptr.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                // 下拉刷新
                pagenum = 1;
                hollowlist.clear(); //清空集合

                initData();
                fragment_consultation_ptr.refreshComplete();
            }
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, content, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });

    }

    private void initData() {


        Map<String, String> map = new HashMap<>();
        map.put("currentPage", "1");

        OkHttpUtils.getInstance(getActivity()).postMap( URL.TreeHole,map, new NetCallBack() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String response, String type) {
                TreeBean treeBean = JSON.parseObject(response, TreeBean.class);
                hollowlist = treeBean.getHollowlist();
                Log.i("onFailure", "成功了------------------------------------------------");

                mRecyAdapter = new RecyAdapter(getActivity(),hollowlist);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mRecyAdapter);

                hollowlist.addAll(treeBean.getHollowlist());

            }
        }, "");
        }

//        //第一步获取okHttpClient对象
//        OkHttpClient client = new OkHttpClient();
//        //第二步构建Request对象
//        Request request = new Request.Builder()
//                .url("http://192.168.1.107:8081/hollow/getList?currentPage=2")
//                .get()
//                .build();
//        //第三步构建Call对象
//        Call call = client.newCall(request);
//        //第四步:异步get请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("onFailure", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                Log.e("string", string + "");
//                Gson gson = new Gson();
//                TreeBean treeBean = gson.fromJson(string, TreeBean.class);
//                hollowlist = treeBean.getHollowlist();
//                Log.i("onFailure", "成功了------------------------------------------------");
//
//                mRecyAdapter = new RecyAdapter(getActivity(),hollowlist);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                mRecyclerView.setLayoutManager(linearLayoutManager);
//                mRecyclerView.setAdapter(mRecyAdapter);
//
//                hollowlist.addAll(treeBean.getHollowlist());
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mRecyAdapter == null) {
//                            mRecyAdapter = new RecyAdapter(getActivity(),hollowlist);
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                            mRecyclerView.setLayoutManager(linearLayoutManager);
//                            mRecyclerView.setAdapter(mRecyAdapter);
//                        } else {
//                            mRecyAdapter.notifyDataSetChanged();
//                        }
//
//                    }
//                });

        //    }
       // });


   // }

}
