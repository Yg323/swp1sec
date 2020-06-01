package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cal_title_adapter extends RecyclerView.Adapter<cal_title_adapter.CustomViewHolder> {

    private ArrayList<cal_title_data> mList = null;
    private Activity context = null;


    public cal_title_adapter(Activity context, ArrayList<cal_title_data> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView cal_title;


        public CustomViewHolder(View view) {
            super(view);
            this.cal_title = (TextView) view.findViewById(R.id.cal_title);

        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cal_title_list_data, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.cal_title.setText(mList.get(position).gettitle());

    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}