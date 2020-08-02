package com.example.sqlitedatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    TextView noBooks;
    ImageView empty;

    MyDatabaseHelper myHelper;
    ArrayList<String> book_id, book_title, book_author, book_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noBooks = findViewById(R.id.noBooktv);
        empty = findViewById(R.id.noBooks);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.add_button);

        myHelper = new MyDatabaseHelper(MainActivity.this);

        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddActicity.class);
                startActivity(intent);
            }
        });

        storeDataInArray();

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,this, book_id, book_title, book_author, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArray(){

        Cursor cursor = myHelper.readAlldata();

        if (cursor.getCount() == 0){

            noBooks.setVisibility(View.VISIBLE);
            empty.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {

            while (cursor.moveToNext()){

                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));

            }
            noBooks.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.delete_menu){
            confirmDialog();
        }else if (id == item.getItemId()){
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you wnat to delete All Book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
                dbHelper.deleteAlldata();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}