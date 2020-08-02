package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddActicity extends AppCompatActivity {

    TextInputLayout mBookEditText, mAuthorEditText, mPagesEditText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acticity);

        mBookEditText = findViewById(R.id.input_books);
        mAuthorEditText = findViewById(R.id.input_author);
        mPagesEditText = findViewById(R.id.input_pages);

        button = findViewById(R.id.addBooks);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Validatebooks() | !Validateauthor() | !Validatepages()){
                    return;
                }

                // here to start inserting data

                MyDatabaseHelper dbHelper = new MyDatabaseHelper(AddActicity.this);
                dbHelper.addBooks(mBookEditText.getEditText().getText().toString().trim(),
                        mAuthorEditText.getEditText().getText().toString().trim(),
                        Integer.parseInt(mPagesEditText.getEditText().getText().toString().trim()));

                mBookEditText.getEditText().setText(null);
                mAuthorEditText.getEditText().setText(null);
                mPagesEditText.getEditText().setText(null);
            }
        });

    }

    private boolean Validatebooks() {
            String books = mBookEditText.getEditText().getText().toString().trim();

            if (books.isEmpty()) {
                mBookEditText.setError("Enter Book");
                return false;
            } else {
                mBookEditText.setError(null);
                return true;
            }
        }

        private boolean Validateauthor() {

            String author = mAuthorEditText.getEditText().getText().toString().trim();

            if (author.isEmpty()) {

                mAuthorEditText.setError("Enter Author");
                return false;
            } else {

                mAuthorEditText.setError(null);
                return true;
            }
        }

        private boolean Validatepages() {
            String pages = mPagesEditText.getEditText().getText().toString().trim();

            if (pages.isEmpty()) {
                mPagesEditText.setError("Enter Pages");
                return false;
            } else {
                mPagesEditText.setError(null);
                return true;
            }
        }
    }

