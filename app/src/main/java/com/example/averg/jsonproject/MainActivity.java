package com.example.averg.jsonproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        setContentView(R.layout.activity_main);

        // Fetches book in the background, and binds it to the list view
        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... params) {
                return Book.getBooks();
            }
            @Override
            protected void onPostExecute(List<Book> books) {
                ArrayAdapter<Book> bookListAdapter = new BookListAdapter(MainActivity.this, R.layout.book_list_layout, books);
                ListView bookListView = findViewById(R.id.bookListView);
                bookListView.setAdapter(bookListAdapter);
                bookListView.setOnItemClickListener(MainActivity.this);
            }
        }.execute();

        // Setup searchview listener
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        Book book = (Book) av.getItemAtPosition(position);

        Intent detailsActivityIntent = new Intent(this, DetailsActivity.class);
        detailsActivityIntent.putExtra("bookId", book.get("bookId"));
        startActivity(detailsActivityIntent);
    }

    // Code to run when query text is submitted on search view
    @Override
    public boolean onQueryTextSubmit(String s) {
        if(s!=null) {
            new AsyncTask<String, Void, List<Book>>() {
                @Override
                protected List<Book> doInBackground(String... params) {
                    return Book.getBooksByTitle(params[0]);
                }

                @Override
                protected void onPostExecute(List<Book> result) {
                    ArrayAdapter<Book> adapter = new BookListAdapter(MainActivity.this, R.layout.book_list_layout, result);
                    ListView listView = findViewById(R.id.bookListView);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(MainActivity.this);
                }
            }.execute(s);
        }
        /*List<Book> books = Book.getBooksByTitle(s);

        ArrayAdapter<Book> bookListAdapter = new BookListAdapter(MainActivity.this, R.layout.book_list_layout, books);
        ListView bookListView = findViewById(R.id.bookListView);
        bookListView.setAdapter(bookListAdapter);
        bookListView.setOnItemClickListener(MainActivity.this);*/

        return false;
    }

    // Code to run when search bar text changes
    @Override
    public boolean onQueryTextChange(String s) {
        if(s.isEmpty()) {
            new AsyncTask<Void, Void, List<Book>>() {
                @Override
                protected List<Book> doInBackground(Void... params) {
                    return Book.getBooks();
                }

                @Override
                protected void onPostExecute(List<Book> books) {
                    ArrayAdapter<Book> bookListAdapter = new BookListAdapter(MainActivity.this, R.layout.book_list_layout, books);
                    ListView bookListView = findViewById(R.id.bookListView);
                    bookListView.setAdapter(bookListAdapter);
                    bookListView.setOnItemClickListener(MainActivity.this);
                }
            }.execute();

            // Setup searchview listener
            SearchView searchView = findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(this);
        }
        return false;
    }
}