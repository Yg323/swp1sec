package com.example.swp1sec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class D_Reminder_RecylerViewAdapter extends RecyclerView.Adapter<D_Reminder_RecylerViewAdapter.ViewHolder> {
    private ArrayList<Data> mData = null ;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    D_Reminder_RecylerViewAdapter(ArrayList<Data> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public D_Reminder_RecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.day_item, parent, false) ;
        D_Reminder_RecylerViewAdapter.ViewHolder vh = new D_Reminder_RecylerViewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(D_Reminder_RecylerViewAdapter.ViewHolder holder, int position) {

        Data item = mData.get(position);

        holder.title.setText(item.getTitle());
        holder.importance_txt.setText(item.getContent());
        holder.logo.setImageDrawable(item.get_l_ResId());
        holder.importance_img.setImageDrawable(item.get_s_ResId());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        ImageView importance_img;
        TextView title;
        TextView importance_txt;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            logo = itemView.findViewById(R.id.day_logo);
            importance_img = itemView.findViewById(R.id.day_im_img);
            title = itemView.findViewById(R.id.title);
            importance_txt = itemView.findViewById(R.id.day_im_txt);
        }
    }
}