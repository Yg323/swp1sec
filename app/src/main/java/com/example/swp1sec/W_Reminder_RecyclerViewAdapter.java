package com.example.swp1sec;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class W_Reminder_RecyclerViewAdapter extends RecyclerView.Adapter<W_Reminder_RecyclerViewAdapter.ViewHolder> {
    String TAG = "W_Reminder_RecyclerViewAdapter";
    private ArrayList<Data> mData = null ;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    W_Reminder_RecyclerViewAdapter(ArrayList<Data> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public W_Reminder_RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.week_item, parent, false) ;
        W_Reminder_RecyclerViewAdapter.ViewHolder vh = new W_Reminder_RecyclerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(W_Reminder_RecyclerViewAdapter.ViewHolder holder, int position) {
        Data item = mData.get(position);
        Log.d(TAG, "holder.title= " + holder.title);
        holder.title.setText(item.getTitle());
        Log.d(TAG, "item.content= " + item.getContent());
        holder.subtitle1.setText(item.getContent());
        holder.subtitle2.setText(item.getContent1());
        Log.d(TAG, "item.get_l_ResId= " + item.get_l_ResId());
        Log.d(TAG, "holder.logo= " + holder.logo);
        holder.logo.setImageDrawable(item.get_l_ResId());
        holder.importance_img1.setImageDrawable(item.get_s_ResId());
        holder.importance_img2.setImageDrawable(item.get_s_ResId());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        ImageView importance_img1;
        ImageView importance_img2;
        TextView title;
        TextView subtitle1;
        TextView subtitle2;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            logo = itemView.findViewById(R.id.week_logo);
            importance_img1 = itemView.findViewById(R.id.r_wek_star1);
            importance_img2 = itemView.findViewById(R.id.r_wek_star2);
            title = itemView.findViewById(R.id.week_title);
            subtitle1 = itemView.findViewById(R.id.r_wek_list1);
            subtitle2 = itemView.findViewById(R.id.r_wek_list2);
        }
    }
}