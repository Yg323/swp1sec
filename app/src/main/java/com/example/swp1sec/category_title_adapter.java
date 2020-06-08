package com.example.swp1sec;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class category_title_adapter extends RecyclerView.Adapter<category_title_adapter.CustomViewHolder> {

    private ArrayList<category_title_data> mList = null;
    private Activity context = null;

    public interface OnClickListener {
        void onClick(View v, int view, int position);
    }

    private OnClickListener mListener = null;


    public void setOnClickListener (OnClickListener listener) {
        this.mListener = listener;
    }


    public category_title_adapter(Activity context, ArrayList<category_title_data> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView cate_title;
        private ImageView cate_color;


        public CustomViewHolder(View view) {
            super(view);
            this.cate_title = (TextView) view.findViewById(R.id.cate_title);
            this.cate_color =(ImageView)view.findViewById(R.id.cate_color);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_title_list_data, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, int position) {
        viewholder.cate_title.setText(mList.get(position).gettitle());

        if(mList.get(position).getperformance() == 1) viewholder.cate_color.setColorFilter(Color.parseColor(mList.get(position).getcolor()), PorterDuff.Mode.SRC_IN);
        else viewholder.cate_color.setColorFilter(Color.parseColor("#ffffff"),PorterDuff.Mode.SRC_IN);

        viewholder.cate_color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onClick(viewholder.cate_color, 0, viewholder.getAdapterPosition());
            }
        });
        viewholder.cate_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onClick(viewholder.cate_title,1, viewholder.getAdapterPosition());
            }
        });

    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}