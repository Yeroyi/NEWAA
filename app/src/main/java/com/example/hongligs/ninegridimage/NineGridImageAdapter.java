package com.example.hongligs.ninegridimage;

import android.view.View;

public interface NineGridImageAdapter<T> {

    int getCount();

    T getItem(int position);

    View getView(int position, View convertView);

}
