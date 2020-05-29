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


public class HabitListAdapter extends RecyclerView.Adapter<HabitListAdapter.CustomViewHolder> {

    private ArrayList<HabitList> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public HabitListAdapter(Activity context, ArrayList<HabitList> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_habitlisttitle;
        private ImageView iv_habitlistperformance;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_habitlisttitle = (TextView) view.findViewById(R.id.txt_habitlisttitle);
            this.iv_habitlistperformance = (ImageView) view.findViewById(R.id.iv_habitlistperformance);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onItemClick(v, pos);
                    }
                }
            });
        }
}

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.habitlist_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.txt_habitlisttitle.setText(mList.get(position).getHabitTitle());
        if(mList.get(position).getPerformance()==1) viewholder.iv_habitlistperformance.setImageResource(R.drawable.icon_checkbox);
        else viewholder.iv_habitlistperformance.setImageResource(R.drawable.icon_uncheckbox);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}