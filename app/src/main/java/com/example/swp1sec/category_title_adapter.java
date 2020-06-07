package com.example.swp1sec;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class category_title_adapter extends RecyclerView.Adapter<category_title_adapter.CustomViewHolder> {

    private ArrayList<category_title_data> mList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
    public interface OnItemClickListener1 {
        void onItemClick(ImageButton imageButton, int position);
    }
    private OnItemClickListener mListener = null;


    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }


    public category_title_adapter(Activity context, ArrayList<category_title_data> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView cate_title;
        private ImageView cate_color;


        public CustomViewHolder(View view) {
            super(view);
            this.cate_title = (TextView) view.findViewById(R.id.cate_title);
            this.cate_color =(ImageView)view.findViewById(R.id.cate_color);

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_title_list_data, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, int position) {
        viewholder.cate_title.setText(mList.get(position).gettitle());
        /*viewholder.cate_color.setOnItemClickListener(new  ImageButton.OnItemClickListener1(){
            @Override
            public void onItemClick(View v) {
                int position = viewholder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (mList.get(position).getperformance() == 1) {
                        viewholder.cate_color.setColorFilter(Color.parseColor(mList.get(position).getcolor()), PorterDuff.Mode.SRC_IN);
                        mList.get(position).setperformance(0);

                    }
                    else {
                        viewholder.cate_color.setColorFilter(Color.parseColor("#E9E9E9"), PorterDuff.Mode.SRC_IN);
                        mList.get(position).setperformance(1);
                    }

                }
                if (mListener != null) mListener.onItemClick(viewholder.cate_color, position);
            }
        });*/
        
        if(mList.get(position).getperformance() == 1) viewholder.cate_color.setColorFilter(Color.parseColor(mList.get(position).getcolor()), PorterDuff.Mode.SRC_IN);
        else viewholder.cate_color.setColorFilter(Color.parseColor("#E9E9E9"),PorterDuff.Mode.SRC_IN);
    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}