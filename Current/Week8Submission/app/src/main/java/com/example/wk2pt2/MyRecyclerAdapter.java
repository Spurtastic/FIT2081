package com.example.wk2pt2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wk2pt2.provider.Car;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    List<Car> carData = new ArrayList<Car>();

    public MyRecyclerAdapter(){

    }

    public void setCarData(List<Car> carData) {
        this.carData = carData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("week6App","onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.makerTv.setText(carData.get(position).getMaker());
        holder.modelTv.setText(carData.get(position).getModel());
        holder.yearTv.setText(String.valueOf(carData.get(position).getYear()));
        holder.colorTv.setText(carData.get(position).getColor());
        holder.seatsTv.setText(String.valueOf(carData.get(position).getSeats()));
        holder.priceTv.setText(String.valueOf(carData.get(position).getPrice()));
        Log.d("week6App","onBindViewHolder");

//        final int fposition = position + 1;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "The maker of the car is " + carData.get(position).getMaker() + " The model of the car is " + carData.get(position).getModel() + " and the car has " + carData.get(position).getSeats() + " seats",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        if (carData == null)
            return 0;
        else
            return carData.size();
    }

    public void setCar(List<Car> newData){
        carData = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView makerTv;
        public TextView modelTv;
        public TextView yearTv;
        public TextView colorTv;
        public TextView seatsTv;
        public TextView priceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            makerTv = itemView.findViewById(R.id.makerTv);
            modelTv = itemView.findViewById(R.id.modelTv);
            yearTv = itemView.findViewById(R.id.yearTv);
            colorTv = itemView.findViewById(R.id.colorTv);
            seatsTv = itemView.findViewById(R.id.seatsTv);
            priceTv = itemView.findViewById(R.id.priceTv);
        }
    }
}

