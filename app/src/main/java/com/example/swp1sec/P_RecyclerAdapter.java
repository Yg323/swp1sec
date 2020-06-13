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
import androidx.appcompat.app.AppCompatActivity;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class P_RecyclerAdapter extends RecyclerView.Adapter<P_RecyclerAdapter.ItemViewHolder> {

    String TAG = "Adapter";
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    public ItemViewHolder mholder;
    public Intent mintent;
    String outPut;
    int res;
    private String jsonString;
    private String email;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_item1, parent, false);

        String url = "http://159.89.193.200/get_money.php";

        email = PreferenceManager.getString(parent.getContext(), "email");
        Log.d(TAG, "email= " + email);

        NetworkTask networkTask = new NetworkTask();
        /*try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        //money_doJSONParser(outPut);
        //Log.d(TAG, "res_adap = " + res);
        networkTask.execute(url,email);

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

    private class NetworkTask extends AsyncTask<String, Void, String> {

        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                jsonString = result; //크롬으로 확인했던 문자열 받아오고
                ShowResult(); //밑에 dayHabitShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email


            String postParameters = "email=" + email ; //php 파일에 $_POST 변수가 받기 위한 코드

            try { //여기부턴 php코드 한줄씩 읽는거니까 그냥 읽기만 해봐

                java.net.URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void ShowResult() { //이부분 잘봐
        String result1 = new String();
        int rem_money = 0;
        int h_money = 0;
        int m_money = 0;

        try {
            JSONObject jsonObject = new JSONObject(jsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray("money"); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와
            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject tt = jsonArray.getJSONObject(i);
                String money = tt.getString("money");
                /*rem_money = Integer.parseInt(money);
                Log.d(TAG, "rem= " + rem_money);
                while(rem_money > 60){
                    if(rem_money >= 3600){
                        h_money = rem_money/3600;
                        rem_money = rem_money%3600;
                    }else{
                        m_money = rem_money/60;
                        rem_money = rem_money%60;
                    }
                }*/

                result1 = money;

                //Log.d(TAG, "result = " + result);

            }
            res = Integer.parseInt(result1);
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }
}