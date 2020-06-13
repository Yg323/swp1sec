package com.example.swp1sec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Category extends AppCompatActivity {
    private RecyclerView recy_category;
    private CategoryAdapterS categoryAdapterS;
    private ArrayList<CategoryItem> categoryArrayList;
    private String catejsonString;
    private static String URL = "http://159.89.193.200//getcategory.php";
    private static String TAG = "getcategory";
    private String email, cal_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        email = PreferenceManager.getString(Category.this, "email");
        cal_title = PreferenceManager.getString(Category.this,"cal_title");
                // 카테고리 목록 출력
        recy_category = (RecyclerView) findViewById(R.id.recy_category);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recy_category.setLayoutManager(LayoutManager);
        categoryArrayList = new ArrayList<>();

        categoryAdapterS = new CategoryAdapterS(this, categoryArrayList);
        recy_category.setAdapter(categoryAdapterS);

        categoryArrayList.clear();
        categoryAdapterS.notifyDataSetChanged();

        GetData habittask = new GetData(); //밑에 만들었던 클래스 만들고
        habittask.execute(URL, email,cal_title); //task 실행

        categoryAdapterS.setOnItemClickListener(new CategoryAdapterS.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CategoryItem categoryItem = categoryArrayList.get(position);
                int division = categoryItem.getDivision();
                final int cateid = categoryItem.getId();
                final String catetitle = categoryItem.getTitle();
                switch (division) {
                    case 0:
                        AlertDialog.Builder dlg = new AlertDialog.Builder(Category.this);
                        dlg.setTitle("시험-과제-휴강 선택"); //제목
                        //dlg.setMessage("안녕하세요 계발에서 개발까지 입니다."); // 메시지
                        //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작
                        dlg.setItems(R.array.LA, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                                switch (pos) {
                                    case 0:{
                                        Intent intent = new Intent(getApplicationContext(), CreateSubject.class);
                                        intent.putExtra("pos", 0);
                                        intent.putExtra("divison", 0);
                                        //PreferenceManager.setInt(Category.this, "di",0);
                                        intent.putExtra("cateid", cateid);
                                        startActivity(intent);
                                        break;
                                    }
                                    case 1:{
                                        Intent intent2 = new Intent(getApplicationContext(), CreateSubject.class);
                                        intent2.putExtra("pos", 1);
                                        intent2.putExtra("division", 1);
                                        //PreferenceManager.setInt(Category.this, "di",1);
                                        intent2.putExtra("cateid", cateid);
                                        startActivity(intent2);
                                        break;
                                    }
                                    case 2: {
                                        Intent intent3 = new Intent(getApplicationContext(), CreateSubject.class);
                                        intent3.putExtra("pos", 2);
                                        intent3.putExtra("division", 0);
                                        //PreferenceManager.setInt(Category.this, "di",0);
                                        intent3.putExtra("cateid", cateid);
                                        startActivity(intent3);
                                        break;
                                    }
                                }
                            }
                        });
                        dlg.show();



                        /*Intent intent = new Intent(v.getContext(), CreateSubject.class);
                        intent.putExtra("cateid", cateid);
                        v.getContext().startActivity(intent);*/
                        break;

                    case 1:
                        Intent intent2 = new Intent(v.getContext(), CreateExercise.class);
                        intent2.putExtra("cateid", cateid);
                        v.getContext().startActivity(intent2);
                        break;

                    case 2:
                        Intent intent3 = new Intent(v.getContext(), CreateNormal.class);
                        intent3.putExtra("cateid", cateid);
                        v.getContext().startActivity(intent3);
                        break;
                }
                //Toast toast = Toast.makeText(v.getContext(), "division = " + division, Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                //toast.show();
            }
        });
    }
    // 습관 리사이클러뷰
    private class GetData extends AsyncTask<String, Void, String> { //php읽어서 디비에서 데이터 가져오는 전체 프로세스를 클래스로 생성
        //모든일은 background 에서 AsyncTask로 발생
        //결과만 눈에 보임 -> 리사이클러뷰에 값출력
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Category.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) { //이게 onCreate에서 task.execute(PHPURL, email) 할때 발생하는 일
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                //mTextViewResult.setText(errorString);
            }
            else {
                catejsonString = result; //크롬으로 확인했던 문자열 받아오고
                ShowResult(); //밑에 ShowResult함수 실행
            }
        }

        @Override
        protected String doInBackground(String... params) { //task.excute로 넘겨주는 매개변수들

            String serverURL = params[0]; //PHPURL
            String email = (String)params[1]; //email
            String cal_title = (String)params[2]; //캘린더 이름

            String postParameters = "email=" + email +"&" +"cal_title="+cal_title;; //php 파일에 $_POST 변수가 받기 위한 코드

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

        String TAG_JSON = "data"; //jsonencode 문자열에서 "data":[]인 jsonarray를 가져오기 위한 태그
        String TAG_TITLE = "cate_title";
        String TAG_COLOR = "color";
        String TAG_DIVISION = "division";
        String TAG_ID = "id";

        try {
            JSONObject jsonObject = new JSONObject(catejsonString); // 전체 문자열이 {}로 묶여있으니까 {} 이만큼을 jsonObject로 받아와
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // 그 jsonObject 에서 "data":[{"title":"~~"}, ... {"title":"~~"}]얘를 jsonArray로 받아와

            for (int i = 0; i < jsonArray.length(); i++) { //"data":[{"title":"~~"}, ... {"title":"~~"}] 아까 얘에서 각각 {"title":"~~"} 이렇게 묶여있는 jsonObject가져오기
                JSONObject item = jsonArray.getJSONObject(i);
                //반복문인점 주의!
                String Title = item.getString(TAG_TITLE); //그럼 거기서 이제 "title"에 해당하는 문자열 값 가져와서 저장
                String color = item.getString(TAG_COLOR);
                int division = item.getInt(TAG_DIVISION);
                int cateid = item.getInt(TAG_ID);

                CategoryItem categoryItem = new CategoryItem(Title);
                categoryItem.setTitle(Title);
                categoryItem.setColor(color);
                categoryItem.setDivision(division);
                categoryItem.setId(cateid);

                categoryArrayList.add(categoryItem); //받아온값이들어있는 dayHabit 객체들을 ArrayList<DayHabit>에 차례로 집어넣고
                categoryAdapterS.notifyDataSetChanged(); //집어넣었으니까 어댑터한테 값 새로 들어갔다고 알려줌 -> 리사이클러뷰 새로고침
            }


        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

    public void subjectclick(){

        AlertDialog.Builder dlg = new AlertDialog.Builder(Category.this);
        dlg.setTitle("시험-과제-휴강 선택"); //제목
        //dlg.setMessage("안녕하세요 계발에서 개발까지 입니다."); // 메시지
        //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작
        dlg.setItems(R.array.LA, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int pos)
            {
                switch (pos) {
                    case 0:{
                        Intent intent = new Intent(getApplicationContext(), CreateSubject.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(getApplicationContext(), CreateSubject.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(getApplicationContext(), CreateSubject.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
        dlg.show();
    }



}















