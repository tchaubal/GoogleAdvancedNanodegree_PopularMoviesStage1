package com.example.tanushreechaubal.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tanushreechaubal.popularmovies.data.GridItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<GridItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mItems;

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mItems) {
        super(mContext,layoutResourceId, mItems);
        this.mContext = mContext;
        this.mItems =  mItems;
        this.layoutResourceId = layoutResourceId;
    }

    public void setGridData(ArrayList<GridItem> mGridData){
        this.mItems = mGridData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item_layout, parent, false);
        }
        ImageView imageView = row.findViewById(R.id.grid_item_image);
        GridItem gridItem = mItems.get(position);
        Picasso.with(mContext).load(gridItem.getImage()).into(imageView);
        return row;
    }
}
