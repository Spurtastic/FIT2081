package com.example.carsapp_week2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsapp_week2.provider.Car;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    List<Car> carData = new ArrayList<>();



    public MyRecyclerAdapter(){

    }

    public void setCarData(List<Car> data){
        this.carData = data;
    }

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {

        holder.makerText.setText(carData.get(position).getMaker_name());
        holder.modelText.setText(carData.get(position).getModel_name());
        holder.yearText.setText(String.valueOf(carData.get(position).getYear()));
        holder.seatText.setText(String.valueOf(carData.get(position).getSeat_no()));
        holder.colorText.setText(carData.get(position).getColor());
        holder.priceText.setText(String.valueOf(carData.get(position).getPrice()));





        final int fPosition = position+1;
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override public void onClick(View v) {
                Snackbar.make(v, "Car No: " + fPosition + "is selected" , Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carData.size();
    }



    // to bind the bind view holder components to the card view
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView makerText;
        public TextView modelText;
        public TextView yearText;
        public TextView seatText;
        public TextView colorText;
        public TextView priceText;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            makerText = itemView.findViewById(R.id.maker_card);
            modelText = itemView.findViewById(R.id.model_card);
            yearText = itemView.findViewById(R.id.year_card);
            seatText = itemView.findViewById(R.id.seat_card);
            colorText = itemView.findViewById(R.id.color_card);
            priceText = itemView.findViewById(R.id.price_card);



        }
    }
}
