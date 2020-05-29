package com.example.swp1sec;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CategoryAdapterS extends RecyclerView.Adapter<CategoryAdapterS.CustomViewHolder> {

    private ArrayList<CategoryItem> mList = null;
    private Activity context = null;

    private OnItemClickListener mListener = null;
    ///////
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;

    }


    ////////
    public CategoryAdapterS(Activity context, ArrayList<CategoryItem> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_catetitle;
        private ImageView iv_circle;
        private TextView mdivision;

        public CustomViewHolder(View view) {
            super(view);
            this.tv_catetitle = (TextView) view.findViewById(R.id.tv_catetitle);
            this.iv_circle = (ImageView) view.findViewById(R.id.iv_circle);
            //mdivision = (TextView) view.findViewById(R.id.tv_division);

            //추가
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //CategoryItem categoryItem = new CategoryItem();
                    //int division = categoryItem.getDivision();

                    if(position != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v, position);
                        }
                    }

                    /*switch (division){
                        case 0 :
                            Intent intent = new Intent(v.getContext(), CreateSubject.class);
                            v.getContext().startActivity(intent);
                            break;

                        case 1 :
                            Intent intent2 = new Intent(v.getContext(), CreateExercise.class);
                            v.getContext().startActivity(intent2);
                            break;

                        case 2 :
                            Intent intent3 = new Intent(v.getContext(), CreateNomal.class);
                            v.getContext().startActivity(intent3);
                            break;*/
                    /*}*/


                    /*Toast toast = Toast.makeText(v.getContext(), "division = " + division, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                    toast.show();*/


                }
            });
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.tv_catetitle.setText(mList.get(position).getTitle());
        viewholder.iv_circle.setColorFilter(Color.parseColor(mList.get(position).getColor()), PorterDuff.Mode.SRC_IN);

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
