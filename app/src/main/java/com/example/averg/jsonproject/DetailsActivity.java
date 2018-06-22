package com.example.averg.jsonproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        setContentView(R.layout.activity_details);

        // Fetch URL from the intent
        Intent intent = getIntent();
        String bookId = intent.getStringExtra("bookId");

        // Get data from the URL
        Book b = Book.getBookById(bookId);

        // Reload the UI
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(b.get("title"));

        TextView authorTextView = findViewById(R.id.authorTextView);
        authorTextView.setText(b.get("author"));

        TextView priceTextView = findViewById(R.id.priceTextView);
        priceTextView.setText(b.get("price"));

        TextView synopsisTextView = findViewById(R.id.synopsisTextView);
        synopsisTextView.setText(b.get("synopsis"));

        ImageView bookImageView = findViewById(R.id.bookImageView);
        bookImageView.setImageBitmap(Book.getBookImage(bookId));

    }
}
