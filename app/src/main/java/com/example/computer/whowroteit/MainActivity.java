package com.example.computer.whowroteit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.book_input);
        mTitleText = findViewById(R.id.title_text);
        mAuthorText = findViewById(R.id.author_text);
    }

    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString().trim();
    }
}
