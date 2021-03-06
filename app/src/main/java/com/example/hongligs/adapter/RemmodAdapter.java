package com.example.hongligs.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hongligs.R;
import com.example.hongligs.activity.DTdetailpageActivity;
import com.example.hongligs.bean.RemmondBean;

import java.util.List;

public class RemmodAdapter extends RecyclerView.Adapter<RemmodAdapter.ViewHolder> {

    private Context context;
    private List<RemmondBean.DlistBean> dlist;
    public RemmodAdapter(Context context, List<RemmondBean.DlistBean> dlist) {
        this.context = context;
        this.dlist = dlist;

        Log.e("RemmodAdapter","dlist==="+dlist.size());
    }

    private OnItemClickListener mOnItemClick;

    public void setOnItemClick(OnItemClickListener mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    @NonNull
    @Override
    public RemmodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("RemmodAdapter","onCreateViewHolder==="+dlist.size());
        View inflate = LayoutInflater.from(context).inflate(R.layout.remmond_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RemmodAdapter.ViewHolder holder, int position) {

        Log.e("RemmodAdapter","onBindViewHolder"+dlist.size());
        holder.itemView.setTag(position);
        holder.remmond_text.setText(dlist.get(position).getUname()+"#");
        holder.remmond_content.setText(dlist.get(position).getContent());
        Glide.with(context).load(dlist.get(position).getAvatar()).into(holder.remmon_iamge);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (mOnItemClick != null) {
                 //  mOnItemClick.onClick(1);
                    Intent intent = new Intent(context, DTdetailpageActivity.class);
                   context.startActivity(intent);

                }
           // }

        });

    }

    @Override
    public int getItemCount() {
        return dlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView remmond_text;
        private TextView remmond_content;
        private  ImageView remmon_iamge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            remmon_iamge=    itemView.findViewById(R.id.remmon_iamge);
            remmond_text= itemView.findViewById(R.id.remmond_text);
            remmond_content=  itemView.findViewById(R.id.remmond_content);


        }
    }
    public interface OnItemClickListener {
        void onClick(int position);
    }
}
