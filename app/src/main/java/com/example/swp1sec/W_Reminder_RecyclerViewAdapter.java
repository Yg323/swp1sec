package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class W_Reminder_RecyclerViewAdapter extends RecyclerView.Adapter<W_Reminder_RecyclerViewAdapter.CustomViewHolder> {
    ///
    private ArrayList<CalendarList> mList = null;
    private Activity context = null;

    // switch 버튼
    public interface OnCheckedChangeListener {
        void onCheckedChanged (CompoundButton compoundButton, boolean isChecked, int position);
    }

    private OnCheckedChangeListener mListener = null;

    public void setOnCheckedChangeListener (OnCheckedChangeListener listener) {
        this.mListener = listener;
    }


    public W_Reminder_RecyclerViewAdapter(Activity context, ArrayList<CalendarList> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_week,txt_date;
        private ImageView iv_calcolor;
        private RatingBar star_cal;
        private Switch swit_Dmin, swit_Dplus;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_week = (TextView) view.findViewById(R.id.txt_week);
            this.txt_date = (TextView) view.findViewById(R.id.txt_date);
            this.iv_calcolor = (ImageView) view.findViewById(R.id.iv_calcolor);
            this.star_cal = (RatingBar) view.findViewById(R.id.star_cal);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.week_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.txt_week.setText(mList.get(position).getTitle());
        viewholder.txt_date.setText(mList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }



}