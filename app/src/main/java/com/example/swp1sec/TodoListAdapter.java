package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.CustomViewHolder> {

    private ArrayList<TodoList> mList = null;
    private Activity context = null;

    // switch 버튼
    public interface OnCheckedChangeListener {
        void onCheckedChanged (CompoundButton compoundButton, boolean isChecked, int position);
    }

    private OnCheckedChangeListener mListener = null;

    public void setOnCheckedChangeListener (OnCheckedChangeListener listener) {
        this.mListener = listener;
    }


    public TodoListAdapter(Activity context, ArrayList<TodoList> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_callisttitle,txt_date;
        private CheckBox cb_todo;
        private RatingBar star_cal;
        private Switch swit_Dmin, swit_Dplus;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_callisttitle = (TextView) view.findViewById(R.id.txt_week);
            this.txt_date = (TextView) view.findViewById(R.id.txt_date);
            this.cb_todo = (CheckBox) view.findViewById(R.id.cb_todo);
            this.star_cal = (RatingBar) view.findViewById(R.id.star_cal);
            this.swit_Dmin = (Switch) view.findViewById(R.id.swit_Dmin);
            this.swit_Dplus = (Switch) view.findViewById(R.id.swit_Dplus);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.todolist_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.txt_callisttitle.setText(mList.get(position).getCate_title()+"_"+mList.get(position).getTitle());
        viewholder.txt_date.setText(mList.get(position).getDate());
        if(mList.get(position).getPerformance()==1){
            viewholder.cb_todo.setChecked(true);
        }
        else viewholder.cb_todo.setChecked(false);

        viewholder.star_cal.setRating(mList.get(position).getStar());
        final int dday = mList.get(position).getDday();
        switch (dday) {
            case 0: //d-만 켜져있음
                viewholder.swit_Dmin.setChecked(true);
                viewholder.swit_Dplus.setChecked(false);
                break;
            case 1: //d+
                viewholder.swit_Dplus.setChecked(true);
                viewholder.swit_Dmin.setChecked(false);

                break;
            case 2: //설정 X
                viewholder.swit_Dmin.setChecked(false);
                viewholder.swit_Dplus.setChecked(false);

                break;
            default:
                break;
        }
        viewholder.cb_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = viewholder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (isChecked) {
                        mList.get(position).setPerformance(1);
                    }
                    else {
                        mList.get(position).setPerformance(0);
                    }
                }
                if (mListener != null) mListener.onCheckedChanged(viewholder.cb_todo, isChecked, pos);

            }

        });
        viewholder.swit_Dmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = viewholder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (isChecked) {
                        viewholder.swit_Dplus.setChecked(false);
                        mList.get(position).setDday(0);
                    }
                    else {
                        if (viewholder.swit_Dplus.isChecked()) {
                            mList.get(position).setDday(1);
                        }
                        else {
                            mList.get(position).setDday(2);
                        }
                    }
                }
                if (mListener != null) mListener.onCheckedChanged(viewholder.swit_Dmin, isChecked, pos);

            }

        });
        viewholder.swit_Dplus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = viewholder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (isChecked) {
                        viewholder.swit_Dmin.setChecked(false);
                        mList.get(position).setDday(1);
                    }
                    else {
                        if (viewholder.swit_Dmin.isChecked()) {
                            mList.get(position).setDday(0);
                        }
                        else {
                            mList.get(position).setDday(2);
                        }
                    }
                    if (mListener != null) mListener.onCheckedChanged(viewholder.swit_Dplus, isChecked, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }



}