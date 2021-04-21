package com.example.carsapp_week2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    ArrayList<String> carList;
    ArrayList<String> modelList;
    ArrayList<String> yearList;
    ArrayList<String> seatList;
    ArrayList<String> colorList;
    ArrayList<String> priceList;



    public MyRecyclerAdapter(ArrayList<String> _data,ArrayList<String> _data2, ArrayList<String> _data3, ArrayList<String> _data4, ArrayList<String> _data5,ArrayList<String> _data6)
    {
        carList = _data;
        modelList = _data2;
        yearList = _data3;
        seatList = _data4;
        colorList = _data5;
        priceList = _data6;
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
        holder.makerText.setText(carList.get(position));
        holder.modelText.setText(modelList.get(position));
        holder.yearText.setText(yearList.get(position));
        holder.seatText.setText(seatList.get(position));
        holder.colorText.setText(colorList.get(position));
        holder.priceText.setText(priceList.get(position));

        final int fPosition = position+1;
        String fname = carList.get(position);
        String fmodel = modelList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() { //set back to itemView for students
            @Override public void onClick(View v) {
                Snackbar.make(v, "Car No: " + fPosition + " with the name "+ fname + " and the model "+fmodel+ " is selected" , Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
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
