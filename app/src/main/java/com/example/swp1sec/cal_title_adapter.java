package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cal_title_adapter extends RecyclerView.Adapter<cal_title_adapter.CustomViewHolder> {

    private ArrayList<cal_title_data> mList = null;
    private Activity context = null;

    //checkbox listener

    public interface OnCheckedChangeListener {
        void onCheckedChanged (CompoundButton compoundButton, int position);
    }
    private cal_title_adapter.OnCheckedChangeListener mListener = null;

    public void setOnCheckedChangeListener (cal_title_adapter.OnCheckedChangeListener listener) {
        this.mListener = listener;
    }
    //

    /*public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    private cal_title_adapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener (cal_title_adapter.OnItemClickListener listener) {
        this.mListener = listener;
    }*/


    public cal_title_adapter(Activity context, ArrayList<cal_title_data> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView cal_title;
        private CheckBox cal_check;


        public CustomViewHolder(View view) {
            super(view);

            this.cal_title = (TextView) view.findViewById(R.id.cal_title);
            this.cal_check = (CheckBox)view.findViewById(R.id.cal_check);
           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onItemClick(v, pos);
                    }
                }
            });*/

        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cal_title_list_data, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.cal_title.setText(mList.get(position).gettitle());
        if(mList.get(position).getperformance()==1)viewholder.cal_check.setChecked(true);
        else if ((mList.get(position).getperformance()==0)) viewholder.cal_check.setChecked(false);

        viewholder.cal_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos =viewholder.getAdapterPosition();
                int posi = PreferenceManager.getInt(context,"cal_pos");


                if(pos != RecyclerView.NO_POSITION){
                    if(isChecked){
                        mList.get(position).setperformance(1);
                        if(pos != posi) {
                            mList.get(posi).setperformance(0);
                            try {
                                notifyItemChanged(posi);
                            } catch (Exception e) {

                            }
                        }
                        PreferenceManager.setString(context,"cal_title",mList.get(pos).gettitle());
                        PreferenceManager.setInt(context,"cal_pos",pos);
                        try {
                            notifyItemChanged(pos);
                        } catch (Exception e) {

                        }
                    }
                    else {
                        mList.get(position).setperformance(0);
                        try {
                            notifyItemChanged(position);
                        } catch (Exception e) {

                        }
                    }
                }
                if(mListener != null)mListener.onCheckedChanged(viewholder.cal_check,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}