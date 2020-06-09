package com.example.swp1sec;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class History extends Fragment {
    Store_main activity;
    H_RecyclerAdapter adapter;
    String TAG = "History";
    ViewGroup rootView;
    String outPut;
    ArrayList res_title;
    ArrayList res_date;
    ArrayList res_spend;
    int length = 0;
    int spent = 0;
    ArrayList spend = new ArrayList();

    String url = "http://159.89.193.200/history.php";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (Store_main) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//프래그먼트 메인을 인플레이트해주고 컨테이너에 붙여달라는 뜻임

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_history , container, false);

        //RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        //recyclerView.setLayoutManager(linearLayoutManager);

        //adapter = new RecyclerAdapter();
        //Log.d(TAG, "adapter = " + adapter);
        //recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        getData();

    }

    private void init() {
        Log.d(TAG, "getView = " + getView());
        RecyclerView recyclerView = getView().findViewById(R.id.h_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new H_RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        List<String> listTitle = null;
        List<String> listContent = null;
        List<Integer> listResId = null;
        NetworkTask networkTask = new NetworkTask(url, null);
        try{
            outPut = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);
        length_doJSONParser(outPut);
        title_doJSONParser(outPut);
        date_doJSONParser(outPut);
        spend_doJSONParser(outPut);
        //Log.d(TAG, "res_title= " + res_title);

        /*for(int i = length; i < 0; i--) {
            listTitle = res_title.get(i).toString();
            Log.d(TAG, "title=" + res_title.get(i).toString());
            listContent = Arrays.asList(res_date.get(i).toString());
            //List<Integer> listResId = Arrays.asList(R.drawable.product_random);
        }
        Log.d(TAG, "listTitle= " + listTitle);*/

        for (int j = length; j > 0; j--) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            //Log.d(TAG, "ListSize=" + listTitle.size());
            Data data = new Data();
            data.setTitle(res_title.get(j).toString());
            //Log.d(TAG, "res_title= " + data.getTitle());
            data.setContent(res_date.get(j).toString());
            //Log.d(TAG, "res_date= " + data.getContent());
            data.setspent(res_spend.get(j).toString());
            //Log.d(TAG, "listContent= "+listContent.get(i));

            if(Integer.parseInt(spend.get(j).toString()) >= 0)
                data.setm_ResId(R.drawable.add_time);
            else
                data.setm_ResId(R.drawable.desease_time);
            //Log.d(TAG, "data= "+data.getm_ResId());
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
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

    public void length_doJSONParser(String str){
        try{
            int result = 0;
            int l = 0;
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("spend");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String length = tt.getString("length");
                l = Integer.parseInt(length);
                //Log.d(TAG, "length= " + l);

                result = l;
                //Log.d(TAG, "result = " + result);
            }
            length = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void title_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("spend");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");

                result.add(i, title);

                //Log.d(TAG, "result = " + result);

            }
            res_title = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void date_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("spend");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("date");

                result.add(i, title);

                //Log.d(TAG, "result = " + result);

            }
            res_date = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void spend_doJSONParser(String str){
        try {
            int h_spent = 0;
            int m_spent = 0;
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("spend");
            //Log.d(TAG, "index = " + index);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                spend.add(tt.getString("spend"));
                spent = Integer.parseInt(spend.get(i).toString());

                if(spent >= 0) {
                    while (spent >= 60) {
                        if (spent >= 3600) {
                            h_spent = spent / 3600;
                            spent = spent % 3600;
                        } else {
                            m_spent = spent / 60;
                            spent = spent % 60;
                        }
                    }
                }else {
                    while (spent <= -60) {
                        if (spent <= -3600) {
                            h_spent = spent / 3600;
                            spent = spent % 3600;
                        } else {
                            m_spent = spent / 60;
                            spent = spent % 60;
                        }
                    }
                }
                if (h_spent == 0) {
                    if (m_spent == 0)
                        result.add(i, spent);
                    else {
                        if(spent != 0)
                            result.add(i, m_spent + "분 " + spent + "초");
                        else
                            result.add(i, m_spent + "분");
                    }
                } else
                    result.add(i, h_spent + "시간 " + m_spent + " 분" + spent + "초");
                //Log.d(TAG, "result = " + result);

            }
            res_spend = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }
}