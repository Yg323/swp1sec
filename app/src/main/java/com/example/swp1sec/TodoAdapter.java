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


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.CustomViewHolder> {

    private ArrayList<Todo> mList = null;
    private Activity context = null;


    public TodoAdapter(Activity context, ArrayList<Todo> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_todotitle;
        private ImageView iv_todoperformance;

        public CustomViewHolder(View view) {
            super(view);
            this.txt_todotitle = (TextView) view.findViewById(R.id.txt_todotitle);
            this.iv_todoperformance = (ImageView) view.findViewById(R.id.iv_todoperformance);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.txt_todotitle.setText(mList.get(position).getTitle());
        if(mList.get(position).getPerformance()==1) viewholder.iv_todoperformance.setImageResource(R.drawable.icon_checkbox);
        else viewholder.iv_todoperformance.setImageResource(R.drawable.icon_uncheckbox);
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}