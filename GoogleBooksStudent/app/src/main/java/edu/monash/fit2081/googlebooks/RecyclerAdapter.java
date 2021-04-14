package edu.monash.fit2081.googlebooks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<GoogleBook> data;

    public RecyclerAdapter(ArrayList<GoogleBook> _data) {
        super();
        data = _data;
        Log.d("stock", "got data with size=" + _data.size());
    }

    @NonNull


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bookTitleText.setText(data.get(position).getBookTitle());
        holder.authorName.setText(data.get(position).getAuthors());
        holder.publishingDate.setText(data.get(position).getPublishedDate());
        final int fPosition = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView bookTitleText;
        public TextView authorName;
        public TextView publishingDate;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookTitleText = itemView.findViewById(R.id.book_title);
            authorName = itemView.findViewById(R.id.author);
            publishingDate = itemView.findViewById(R.id.publishDate);

        }
    }
}
