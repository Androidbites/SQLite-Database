package com.example.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class UpdateActivity extends AppCompatActivity {

    TextInputLayout title_input, author_input, pages_input;
    Button update, delete;
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.input_books2);
        author_input = findViewById(R.id.input_author2);
        pages_input = findViewById(R.id.input_pages2);

        update = findViewById(R.id.updateBooks);
        delete = findViewById(R.id.deleteBooks);

        getAndSetIntentData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validatebooks() | !Validateauthor() | !Validatepages()){
                    return;
                }
                title = title_input.getEditText().getText().toString();
                author = author_input.getEditText().getText().toString();
                pages = pages_input.getEditText().getText().toString();

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updataData(id, title, author, pages);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Do you wnat to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(UpdateActivity.this);
                dbHelper.deleteRow(id);
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

    void getAndSetIntentData(){

        if (getIntent().hasExtra("id") && getIntent().hasExtra("book") && getIntent().hasExtra("author") && getIntent().hasExtra("pages")){

            // get Data

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("book");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            // set Data
            title_input.getEditText().setText(title);
            author_input.getEditText().setText(author);
            pages_input.getEditText().setText(pages);


        }

    }

    private boolean Validatebooks() {
        String books = title_input.getEditText().getText().toString().trim();

        if (books.isEmpty()) {
            title_input.setError("Enter Book");
            return false;
        } else {
            title_input.setError(null);
            return true;
        }
    }

    private boolean Validateauthor() {

        String author = author_input.getEditText().getText().toString().trim();

        if (author.isEmpty()) {

            author_input.setError("Enter Author");
            return false;
        } else {

            author_input.setError(null);
            return true;
        }
    }

    private boolean Validatepages() {
        String pages = pages_input.getEditText().getText().toString().trim();

        if (pages.isEmpty()) {
            pages_input.setError("Enter Pages");
            return false;
        } else {
            pages_input.setError(null);
            return true;
        }
    }
}