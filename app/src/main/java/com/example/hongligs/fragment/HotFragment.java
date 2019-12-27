package com.example.hongligs.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.hongligs.R;
import com.example.hongligs.activity.SecondchoiceActivity;
import com.example.hongligs.adapter.SecondListAdapter;
import com.example.hongligs.bean.SecondChoice;
import com.example.hongligs.http.NetCallBack;
import com.example.hongligs.http.OkHttpUtils;
import com.example.hongligs.http.URL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 热门群聊
 */
public class HotFragment extends Fragment {


    private View inflate;
    private ListView mlistview;
    private List<SecondChoice.TaglistBean> taglist;
    private SecondListAdapter secondListAdapter;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_hot, container, false);
        taglist = new ArrayList<>();
        iniview();
        inidata();
        return inflate;
    }

    private void inidata() {

        Map<String, String> map = new HashMap<>();

        OkHttpUtils.getInstance(getActivity()).postMap(URL.SENDCHIOCED, map, new NetCallBack() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String response, String type) {


                SecondChoice secondChoice = JSON.parseObject(response, SecondChoice.class);
                taglist.addAll(secondChoice.getTaglist());

                if (secondListAdapter==null){
                    secondListAdapter = new SecondListAdapter(taglist, getActivity());
                    mlistview.setAdapter(secondListAdapter);
                }else {
                    secondListAdapter.notifyDataSetChanged();
                }



            }
        }, "");


    }

    private void iniview() {
        mlistview= inflate.findViewById(R.id.listview);

    }

}
