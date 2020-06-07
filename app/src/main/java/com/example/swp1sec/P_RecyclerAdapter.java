package com.example.swp1sec;


import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swp1sec.R;
import com.example.swp1sec.a_c_PopUpActivity;
import com.example.swp1sec.a_r_PopUpActivity;
import com.example.swp1sec.p_c_PopUpActivity;
import com.example.swp1sec.p_r_PopupActivity;
import com.example.swp1sec.t_c_PopupActivity;
import com.example.swp1sec.t_r_PopupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class P_RecyclerAdapter extends RecyclerView.Adapter<P_RecyclerAdapter.ItemViewHolder> {

    String TAG = "Adapter";
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    public ItemViewHolder mholder;
    public Intent mintent;
    String outPut;
    int res;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_item1, parent, false);

        String url = "http://159.89.193.200/get_money.php";

        NetworkTask networkTask = new NetworkTask(url, null);
        try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        money_doJSONParser(outPut);
        //Log.d(TAG, "res_adap = " + res);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
        holder.Itembtn.setTag(listData.get(position));
        //Log.d(TAG, "position = " + holder.getAdapterPosition());
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
        private TextView textView3;
        private ImageView imageView1;
        private ImageView imageView2;
        private Button Itembtn;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView1 = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView1);
            Itembtn = itemView.findViewById(R.id.btn);
        }

        void onBind(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            textView3.setText(data.getCoin());
            imageView1.setImageResource(data.getm_ResId());
            imageView2.setImageResource(data.getc_ResId());

            Itembtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Data tag = (Data)Itembtn.getTag();
                    //Log.d(TAG, "tag= " + tag);

                    switch(tag.getId()){
                        case 0:
                            if(res >= 1800) {
                                mintent = new Intent(v.getContext(), t_r_PopupActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos0= " + tag.getId());
                                break;
                            }else
                                break;
                        case 1:
                            if(res >=3000) {
                                mintent = new Intent(v.getContext(), t_c_PopupActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos1= " + tag.getId());
                                break;
                            }else
                                break;
                        case 2:
                            if(res >= 1800) {
                                mintent = new Intent(v.getContext(), p_r_PopupActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos2= " + tag.getId());
                                break;
                            }else
                                break;
                        case 3:
                            if(res >=3000) {
                                mintent = new Intent(v.getContext(), p_c_PopUpActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos3= " + tag.getId());
                                break;
                            }else
                                break;
                        case 4:
                            if(res >= 1800) {
                                mintent = new Intent(v.getContext(), a_r_PopUpActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos4= " + tag.getId());
                                break;
                            }else
                                break;
                        case 5:
                            if(res >=3000) {
                                mintent = new Intent(v.getContext(), a_c_PopUpActivity.class);
                                v.getContext().startActivity(mintent);
                                //Log.d(TAG, "pos5= " + tag.getId());
                                break;
                            }else
                                break;
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        private String tv_outPut;
        private static final String TAG = "networktask";

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            //Log.d(TAG, "url = " + url);
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            //Log.d(TAG, "result = " + result);

            return result;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv_outPut = new String();
            //Log.d(TAG, "response = " + s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_outPut.setText(s);
            //doJSONParser(s);
            tv_outPut = s;
            //Log.d(TAG, "tv_output = " + tv_outPut);
        }
    }

    public void money_doJSONParser(String str){
        try{
            int result = 0;
            int rem_money = 0;
            int h_money = 0;
            int m_money = 0;
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("money");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String money = tt.getString("money");

                result = Integer.parseInt(money);
                //Log.d(TAG, "result = " + result);
            }

            res = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }
}