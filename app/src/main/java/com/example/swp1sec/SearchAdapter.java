package com.example.swp1sec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {

    private ArrayList<SearchItem> mList = null;
    private Activity context = null;


    public SearchAdapter(Activity context, ArrayList<SearchItem> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_searchresult;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_searchresult = (TextView) view.findViewById(R.id.txt_searchresult);

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.search_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        if(mList.get(position).getCate_title()=="null"){
            viewholder.txt_searchresult.setText("습관 _ "+mList.get(position).getTitle());
        }
        else{
            viewholder.txt_searchresult.setText(mList.get(position).getCate_title()+"_"+mList.get(position).getTitle()+"    날짜 : "+mList.get(position).getDate());
        }


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}