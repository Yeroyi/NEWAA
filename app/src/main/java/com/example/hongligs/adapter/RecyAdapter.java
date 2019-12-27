package com.example.hongligs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hongligs.R;
import com.example.hongligs.bean.TreeBean;
import com.example.hongligs.fragment.wetherbean;

import java.util.ArrayList;
import java.util.List;


public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {


    private Context context;

    public RecyAdapter(Context context, List<TreeBean.HollowlistBean> hollowlist) {
        this.context = context;
        this.hollowlist = hollowlist;
    }

    //List<wetherbean.DataBean.ForecastBean> forecast;
    private List<TreeBean.HollowlistBean> hollowlist;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.one_item, parent, false);
        ViewHolder holder=new ViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.itemView.setTag(position);

        holder.Tv_name.setText(hollowlist.get(position).getUname());
        holder.Tv_content.setText(hollowlist.get(position).getContent());
        holder.Tv_nuber.setText(hollowlist.get(position).getPlnum()+"");
        holder.Tv_time.setText(hollowlist.get(position).getTime()+"");

    }

    @Override
    public int getItemCount() {
        return hollowlist.size();
    }


    class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView Tv_name;
        public TextView Tv_content;
        public TextView Tv_time;
        public TextView Tv_nuber;

        public ViewHolder(@NonNull View itemView) {
           super(itemView);

            Tv_name = itemView.findViewById(R.id.Tv_name);
            Tv_time = itemView.findViewById(R.id.Tv_time);
            Tv_nuber = itemView.findViewById(R.id.Tv_nuber);
            Tv_content =  itemView.findViewById(R.id.Tv_content);

       }
   }
}
