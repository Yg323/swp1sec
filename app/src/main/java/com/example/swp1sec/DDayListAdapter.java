package com.example.swp1sec;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
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


public class DDayListAdapter extends RecyclerView.Adapter<DDayListAdapter.CustomViewHolder> {

    private ArrayList<DDayList> mList = null;
    private Activity context = null;

    // switch 버튼
    public interface OnCheckedChangeListener {
        void onCheckedChanged (CompoundButton compoundButton, boolean isChecked, int position);
    }

    private OnCheckedChangeListener mListener = null;

    public void setOnCheckedChangeListener (OnCheckedChangeListener listener) {
        this.mListener = listener;
    }


    public DDayListAdapter(Activity context, ArrayList<DDayList> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_callisttitle,txt_date;
        private ImageView iv_calcolor;
        private RatingBar star_cal;
        private Switch swit_dday;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_callisttitle = (TextView) view.findViewById(R.id.txt_callisttitle);
            this.txt_date = (TextView) view.findViewById(R.id.txt_date);
            this.iv_calcolor = (ImageView) view.findViewById(R.id.iv_calcolor);
            this.star_cal = (RatingBar) view.findViewById(R.id.star_cal);
            this.swit_dday = (Switch) view.findViewById(R.id.swit_dday);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.dday_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        if(mList.get(position).getDday() !=2){
            viewholder.txt_callisttitle.setText(mList.get(position).getCate_title()+"_"+mList.get(position).getTitle());
            viewholder.txt_date.setText(mList.get(position).getDate());

            viewholder.iv_calcolor.setColorFilter(Color.parseColor(mList.get(position).getColor()), PorterDuff.Mode.SRC_IN);
            viewholder.star_cal.setRating(mList.get(position).getStar());
            viewholder.swit_dday.setChecked(true);
            final int dday = mList.get(position).getDday();
            viewholder.swit_dday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = viewholder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (!isChecked) {
                            mList.get(position).setDday(2);
                        }
                        else{
                            mList.get(position).setDday(dday);
                        }

                    }
                    if (mListener != null)  mListener.onCheckedChanged(viewholder.swit_dday, isChecked, pos);

                }
            } );
        }

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }



}