package com.example.parks.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parks.R;
import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {

    private OnParkClickListener onParkClickListener;


    public ParkRecyclerViewAdapter(List<Park> parkList1) {
        this.parkList=parkList1;

    }
    List<Park> parkList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.park_row,parent,false);
        return new ViewHolder(view,onParkClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Park park=parkList.get(position);
      //  Log.d("TAG", "onBindViewHolder: "+ park.getImages().get(0).getUrl());
        holder.name.setText(park.getName());
        holder.type.setText(park.getDesignation());
        holder.state.setText("State: "+park.getStates());

        if(park.getImages().size()>0) {
            Picasso.get()
                    .load(park.getImages().get(0).getUrl())
                    .fit()
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount()
    {
        return parkList.size();
    }

    public void SetOnParkClickedListener(OnParkClickListener Listener){
        onParkClickListener=Listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView type;
        public TextView state;
        public ImageView imageView;
        OnParkClickListener onParkClickListener;

        public ViewHolder(@NonNull View itemView, final OnParkClickListener mListener) {
            super(itemView);
            name=itemView.findViewById(R.id.park_row_name);
            type=itemView.findViewById(R.id.park_row_designation);
            state=itemView.findViewById(R.id.park_row_parkState);
            imageView=itemView.findViewById(R.id.park_row_imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            Park park=parkList.get(position);
                            mListener.OnParkClicked(park);
                        }
                    }
                }
            });
        }
    }
}


