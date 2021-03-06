package com.example.sqlitedatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList book_id, book_title, book_author, book_pages;
    Activity activity;

    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_pages){

        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.activity = activity;
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {

        holder.id_tv.setText(String.valueOf(book_id.get(position)));
        holder.book_tv.setText(String.valueOf(book_title.get(position)));
        holder.author_tv.setText(String.valueOf(book_author.get(position)));
        holder.pages_tv.setText(String.valueOf(book_pages.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(book_id.get(position)));
                intent.putExtra("book",String.valueOf(book_title.get(position)));
                intent.putExtra("author",String.valueOf(book_author.get(position)));
                intent.putExtra("pages",String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent,1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_tv, book_tv, author_tv, pages_tv;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_tv = itemView.findViewById(R.id.book_id_txt);
            book_tv = itemView.findViewById(R.id.book_title_txt);
            author_tv = itemView.findViewById(R.id.book_author_txt);
            pages_tv = itemView.findViewById(R.id.book_pages_txt);

            linearLayout = itemView.findViewById(R.id.mainLayout);


        }


    }
}
