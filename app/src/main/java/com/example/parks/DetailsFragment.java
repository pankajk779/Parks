package com.example.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parks.adapter.ViewPagerAdapter;
import com.example.parks.model.Activities;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;
import com.example.parks.model.Topics;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    private ParkViewModel parkViewModel;
    private Park selectedPark;
    private ViewPager2 viewPager;
    private TextView name,url,desc,openingHrs,topics,nameType,activities,
            monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    private ViewPagerAdapter viewPagerAdapter;
    private WormDotsIndicator wormDotsIndicator;




    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_details,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewPager=view.findViewById(R.id.fragment_details_view_pager);
        parkViewModel=new ViewModelProvider(getActivity())
                .get(ParkViewModel.class);
        selectedPark=parkViewModel.getSelectedPark().getValue();


        name=view.findViewById(R.id.name);
        nameType=view.findViewById(R.id.name_type);
        url=view.findViewById(R.id.url);
        desc=view.findViewById(R.id.desc);
        openingHrs=view.findViewById(R.id.standard_hrs);
        topics=view.findViewById(R.id.topics);
        activities=view.findViewById(R.id.activites);
        wormDotsIndicator=view.findViewById(R.id.worm_dots_indicator);

        monday=view.findViewById(R.id.monday);
        tuesday=view.findViewById(R.id.tuesday);
        wednesday=view.findViewById(R.id.wednesday);
        thursday=view.findViewById(R.id.thursday);
        friday=view.findViewById(R.id.friday);
        saturday=view.findViewById(R.id.saturday);
        sunday=view.findViewById(R.id.sunday);




        name.setText(selectedPark.getName());
        nameType.setText(selectedPark.getDesignation());
        url.setText(selectedPark.getUrl());
        desc.setText(selectedPark.getDescription());

        monday.setText(   "Monday:    "+selectedPark.getOperatingHours().get(0).getStandardHours().getMonday());
        tuesday.setText(  "Tuesday:   "+selectedPark.getOperatingHours().get(0).getStandardHours().getTuesday());
        wednesday.setText("Wednesday: "+selectedPark.getOperatingHours().get(0).getStandardHours().getWednesday());
        thursday.setText( "Thursday:  "+selectedPark.getOperatingHours().get(0).getStandardHours().getThursday());
        friday.setText(   "Friday:    "+selectedPark.getOperatingHours().get(0).getStandardHours().getFriday());
        saturday.setText( "Saturday:  "+selectedPark.getOperatingHours().get(0).getStandardHours().getSaturday());
        sunday.setText(   "Sunday:    "+selectedPark.getOperatingHours().get(0).getStandardHours().getSunday());

        StringBuilder stringBuilder=new StringBuilder();
        List<Activities> activitiesList=new ArrayList<>();
        activitiesList=selectedPark.getActivities();
        for (int i = 0; i <activitiesList.size() ; i++) {
            stringBuilder.append(" | "+activitiesList.get(i).getName());
        }
        activities.setText(stringBuilder);

        StringBuilder stringBuilderTopics=new StringBuilder();
        List<Topics> topicsList=new ArrayList<>();
        topicsList=selectedPark.getTopics();
        for (int j = 0; j < topicsList.size(); j++) {
            stringBuilderTopics.append(" | "+topicsList.get(j).getName());
        }
        topics.setText(stringBuilderTopics);



        viewPagerAdapter=new ViewPagerAdapter(selectedPark.getImages());
        viewPager.setAdapter(viewPagerAdapter);
        wormDotsIndicator.setViewPager2(viewPager);

        }



}

