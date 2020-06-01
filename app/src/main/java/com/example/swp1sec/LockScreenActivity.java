package com.example.swp1sec;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import me.relex.circleindicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LockScreenActivity extends  AppCompatActivity{
    private static final String TAG = "LockScreenActivity";
    TextView textDate;
    TextView textTime;
    FragmentPagerAdapter adapterViewPager;
    String outPut;
    ArrayList res1;
    ArrayList res2;
    int ind;


    //private static String IP_ADDRESS = "159.89.193.200";
    //private static String TAG = "db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        String ex_url = "http://159.89.193.200/ex_json.php";
        String hb_url = "http://159.89.193.200/hb_json.php";
        String nm_url = "http://159.89.193.200/nm_json.php";
        String date_url = "http://159.89.193.200/getdate.php";

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        NetworkTask date_networkTask = new NetworkTask(date_url, null);
        try{
            outPut = date_networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        date_title_doJSONParser(outPut);
        Log.d(TAG, "title = " + res1);
        date_dday_doJSONParser(outPut);
        //Log.d(TAG, "ind = " + ind);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), res1, res2);

        //list.add(EX_ArrayList);
        vpPager.setAdapter(adapterViewPager);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
        //MyPagerAdapter myPagerAdapter = new MyPagerAdapter(adapterViewPager);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);




        // 부모 리스트
        ArrayList<HashMap<String, String>> groupData = new ArrayList<>();
        // 자식 리스트
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<>();

        // 부모 리스트에 요소를 추가한다.
        HashMap<String, String> groupA = new HashMap<>();
        groupA.put("group", "할 일");
        HashMap<String, String> groupB = new HashMap<>();
        groupB.put("group", "주요 일정");
        HashMap<String, String> groupC = new HashMap<>();
        groupC.put("group", "습관");

        groupData.add(groupA);
        groupData.add(groupB);
        groupData.add(groupC);

        // 자식 리스트에 요소를 추가한다. (1)
        NetworkTask nm_networkTask = new NetworkTask(nm_url, null);
        try{
            outPut = nm_networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        nm_doJSONParser(outPut);

        ArrayList<HashMap<String, String>> childListA = new ArrayList<>();

        HashMap<String, String> childAA = new HashMap<>();
        childAA.put("group", "할 일");
        childAA.put("name", res1.get(0).toString());
        childListA.add(childAA);

        HashMap<String, String> childAB = new HashMap<>();
        childAB.put("group", "할 일");
        childAB.put("name", res1.get(0).toString());
        childListA.add(childAB);

        HashMap<String, String> childAC = new HashMap<>();
        childAC.put("group", "할 일");
        childAC.put("name", res1.get(0).toString());
        childListA.add(childAC);

        childData.add(childListA);

        // 자식 리스트에 요소를 추가한다. (2)
        NetworkTask ex_networkTask = new NetworkTask(ex_url, null);
        try{
            outPut = ex_networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        ex_doJSONParser(outPut);
        //Log.d(TAG, "ind = " + ind);

        ArrayList<HashMap<String, String>> childListB = new ArrayList<>();

        HashMap<String, String> childBA = new HashMap<>();
        childBA.put("group", "주요 일정");
        childBA.put("name", res1.get(0).toString());
        childListB.add(childBA);

        HashMap<String, String> childBB = new HashMap<>();
        childBB.put("group", "주요 일정");
        childBB.put("name", res1.get(0).toString());
        childListB.add(childBB);

        HashMap<String, String> childBC = new HashMap<>();
        childBC.put("group", "주요 일정");
        childBC.put("name", res1.get(0).toString());
        childListB.add(childBC);

        childData.add(childListB);

        // 자식 리스트에 요소를 추가한다. (3)
        NetworkTask hb_networkTask = new NetworkTask(hb_url, null);
        try{
            outPut = hb_networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        //outPut = networkTask.getTv_outPut();
        //String ex_title = tv_outPut;
        //Log.d(TAG,"outPut: "+ outPut);

        hb_doJSONParser(outPut);
        //Log.d(TAG, "ind = "+ind);

        ArrayList<HashMap<String, String>> childListC = new ArrayList<>();

        HashMap<String, String> childCA = new HashMap<>();
        childCA.put("group", "습관");
        childCA.put("name", res1.get(0).toString());
        childListC.add(childCA);

        HashMap<String, String> childCB = new HashMap<>();
        childCB.put("group", "습관");
        childCB.put("name", res1.get(0).toString());
        childListC.add(childCB);

        HashMap<String, String> childCC = new HashMap<>();
        childCC.put("group", "습관");
        childCC.put("name", res1.get(0).toString());
        childListC.add(childCC);

        childData.add(childListC);

        // 부모 리스트와 자식 리스트를 포함한 Adapter를 생성
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"group"}, new int[] { android.R.id.text1},
                childData, android.R.layout.simple_expandable_list_item_2,
                new String[] {"name", "group"}, new int[] {android.R.id.text1, android.R.id.text2});

        // ExpandableListView에 Adapter를 설정
        ExpandableListView listView
                = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(adapter);
        //여기까지 ExpandableListView

        Button closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 텍스트뷰 받아와서
        textDate = findViewById(R.id.tvdate);
        textTime = findViewById(R.id.tvclock);

        // 쓰레드를 이용해서 시계표시
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (!isInterrupted()) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Calendar calendar = Calendar.getInstance(); // 칼렌다 변수
                            int year = calendar.get(Calendar.YEAR); // 년
                            int month = calendar.get(Calendar.MONTH); // 월
                            int day = calendar.get(Calendar.DAY_OF_MONTH); // 일
                            int Date = calendar.get(Calendar.DAY_OF_WEEK); //요일
                            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 시
                            int minute = calendar.get(Calendar.MINUTE); // 분

                            switch (Date){
                                case 1:
                                    textDate.setText(month + " / " + day + " 일요일");
                                case 2:
                                    textDate.setText(month + " / " + day + " 월요일");
                                case 3:
                                    textDate.setText(month + " / " + day + " 화요일");
                                case 4:
                                    textDate.setText(month + " / " + day + " 수요일");
                                case 5:
                                    textDate.setText(month + " / " + day + " 목요일");
                                case 6:
                                    textDate.setText(month + " / " + day + " 금요일");
                                case 7:
                                    textDate.setText(month + " / " + day + " 토요일");
                            }

                            if(minute < 10) {
                                textTime.setText(hour + " : 0" + minute);
                            }else{
                                textTime.setText(hour + " : " + minute);
                            }

                        }
                    });
                    try {
                        Thread.sleep(1000); // 1000 ms = 1초
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } // while
            } // run()
        }; // new Thread() { };

        thread.start();

    }//OnCreate end

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

    public void ex_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("exercise");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");

                result.add(i, title);

                //Log.d(TAG, "result = " + result);

            }
            res1 = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "ex_doJSONParser = ", e);}
    }

    public void hb_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("habit");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");

                result.add(i, title);
                ind = i;
                //Log.d(TAG,"ind = " + ind);
                //Log.d(TAG, "result = " + result);
            }
            res1 = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "hb_doJSONParser = ", e);}
    }

    public void nm_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("normal");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");

                result.add(i, title);
                //Log.d(TAG, "result = " + result);
            }
            res1 = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "nm_doJSONParser = ", e);}
    }
    public void date_title_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("dday");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject tt = index.getJSONObject(i);
                String title = tt.getString("title");

                result.add(i, title);

                //Log.d(TAG, "result = " + result);

            }
            res1 = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "date_title_doJSONParser = ", e);}
    }
    public void date_dday_doJSONParser(String str){
        try{
            ArrayList result = new ArrayList();
            JSONObject object = new JSONObject(str);
            JSONArray index = object.getJSONArray("dday");
            //Log.d(TAG, "index = " + index);
            for(int i = 0; i < index.length(); i++){
                JSONObject dd = index.getJSONObject(i);
                String dday = dd.getString("dday");

                result.add(i, dday);

                //Log.d(TAG, "result = " + result);

            }
            res2 = result;
            //Log.d(TAG,"tv_OUTPUT = " + res);
        }catch (JSONException e){
            Log.d(TAG, "doJSONParser = ", e);}
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        private ArrayList title;
        private ArrayList dday;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList title, ArrayList dday) {
            super(fragmentManager);
            this.title = title;
            this.dday = dday;
            //Log.d(TAG, "title= "+title);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0,"first_fragment");
                case 1:
                    return SecondFragment.newInstance(1,"second_fragment");
                case 2:
                    return ThirdFragment.newInstance(2, "third_fragment");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
