package com.example.swp1sec;

import android.content.Context;
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

import java.util.Arrays;
import java.util.List;

public class History extends Fragment {
        Store_main activity;
        H_RecyclerAdapter adapter;
        String TAG = "Purchase";
        ViewGroup rootView;

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
        // 임의의 데이터입니다.
        List<String> listTitle = Arrays.asList(" 랜덤박스 구입", " 선택 박스 구입", " 랜덤 박스 구입", " 선택 박스 구입", " 랜덤 박스 구입", " 선택 박스 구입", " 랜덤 박스 구매", " 랜덤 박스 구매", " 랜덤 박스 구매");
        List<String> listContent = Arrays.asList(
                "  테마 랜덤박스를 \n  구입했습니다.",
                "  앱에 적용 가능한 테마 중 한가지를 \n  선택 구매합니다.",
                "  앱에 적용 가능한 색상 팔레트 중 한가지를 \n  무작위로 획득합니다.",
                "  앱에 적용 가능한 색상 팔레트 중 한가지를 \n  선택 구매합니다.",
                "  앱에 적용 가능한 알람 소리 중 한가지를 \n  무작위로 획득합니다.",
                "  앱에 적용 가능한 알람 소리 중 한가지를 \n  선택 구매합니다.",
                "  앱에 적용 가능한 색상 팔레트 중 한가지를 \n  선택 구매합니다.",
                "  앱에 적용 가능한 알람 소리 중 한가지를 \n  무작위로 획득합니다.",
                "  앱에 적용 가능한 알람 소리 중 한가지를 \n  선택 구매합니다."
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.product_random,
                R.drawable.product_choose,
                R.drawable.product_random,
                R.drawable.product_choose,
                R.drawable.product_random,
                R.drawable.product_choose,
                R.drawable.product_choose,
                R.drawable.product_random,
                R.drawable.product_choose
        );
        for (int i = 0; i < listTitle.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            //Log.d(TAG, "ListSize=" + listTitle.size());
            Data data = new Data();
            data.setTitle(listTitle.get(i));
            Log.d(TAG, "listTitle= "+listTitle.get(i));
            data.setContent(listContent.get(i));
            Log.d(TAG, "listContent= "+listContent.get(i));
            data.setResId(listResId.get(i));
            Log.d(TAG, "data= "+data.getResId());
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}