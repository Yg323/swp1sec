package com.example.swp1sec;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class P_RecyclerAdapter extends RecyclerView.Adapter<P_RecyclerAdapter.ItemViewHolder> {

    String TAG = "Adapter";
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    public ItemViewHolder mholder;
    public Intent mintent;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_item1, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
        holder.Itembtn.setTag(listData.get(position));
        Log.d(TAG, "position = " + holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        //Log.d(TAG, "data" + data);
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView1;
        private TextView textView2;
        private ImageView imageView;
        private Button Itembtn;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            Itembtn = itemView.findViewById(R.id.btn);
        }

        void onBind(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            imageView.setImageResource(data.getResId());


            Itembtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Data tag = (Data)Itembtn.getTag();
                    //Log.d(TAG, "tag= " + tag);

                    switch(tag.getId()){
                        case 0:
                            mintent = new Intent(v.getContext(), t_r_PopupActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos0= " + tag.getId());
                            break;
                        case 1:
                            mintent = new Intent(v.getContext(), t_c_PopupActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos1= " + tag.getId());
                            break;
                        case 2:
                            mintent = new Intent(v.getContext(), p_r_PopupActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos2= " + tag.getId());
                            break;
                        case 3:
                            mintent = new Intent(v.getContext(), p_c_PopUpActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos3= " + tag.getId());
                            break;
                        case 4:
                            mintent = new Intent(v.getContext(), a_r_PopUpActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos4= " + tag.getId());
                            break;
                        case 5:
                            mintent = new Intent(v.getContext(), a_c_PopUpActivity.class);
                            v.getContext().startActivity(mintent);
                            Log.d(TAG, "pos5= " + tag.getId());
                            break;
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}