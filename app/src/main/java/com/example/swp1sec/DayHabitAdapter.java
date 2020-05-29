package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class DayHabitAdapter extends RecyclerView.Adapter<DayHabitAdapter.CustomViewHolder> {

    private ArrayList<DayHabit> mList = null;
    private Activity context = null;


    public DayHabitAdapter(Activity context, ArrayList<DayHabit> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_habittitle;
        private ImageView iv_habitperformance;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_habittitle = (TextView) view.findViewById(R.id.txt_habittitle);
            this.iv_habitperformance = (ImageView) view.findViewById(R.id.iv_habitperformance);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.habit_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.txt_habittitle.setText(mList.get(position).getTitle());
        if(mList.get(position).getPerformance()==1) viewholder.iv_habitperformance.setImageResource(R.drawable.icon_checkbox);
        else viewholder.iv_habitperformance.setImageResource(R.drawable.icon_uncheckbox);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}