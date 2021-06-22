package com.example.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;

import java.util.ArrayList;
import java.util.List;

public class ListsFragment extends Fragment {
    public ArrayList<String> parkNameList2;
    private ListView listView;
    private ArrayAdapter arrayAdapter;


    public ListsFragment() {
        // Required empty public constructor
    }


    public static ListsFragment newInstance() {
        ListsFragment fragment = new ListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkNameList2=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_lists, container, false);
        listView=rootView.findViewById(R.id.listView2);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            Repository.getParks(new AsyncResponse() {
                @Override
                public void processPark(List<Park> parks) {
                    for (Park park : parks) {
                        Log.d("TAG", "processPark: in listView" + park.getName());
                        parkNameList2.add(park.getName());
                    }
                }
            });

            arrayAdapter = new ArrayAdapter<>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    parkNameList2
            );
            listView.setAdapter(arrayAdapter);
        }catch (Exception e){
            Toast.makeText(this.getContext(),"Sorry! code in background here is getting errors. Hence commented",Toast.LENGTH_LONG).show();
        }
    }
}
