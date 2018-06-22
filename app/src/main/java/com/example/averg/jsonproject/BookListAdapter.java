package com.example.averg.jsonproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BookListAdapter extends ArrayAdapter<Book> {

    private List<Book> items;
    int resource;

    public BookListAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a view from layout
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);

        // Set textviews in layout with Book data
        Book book = items.get(position);
        if (book != null) {
            // Set title
            TextView title = (TextView) v.findViewById(R.id.book_list_title);
            title.setText(book.get("title"));

            // Set author
            TextView author = (TextView) v.findViewById(R.id.book_list_author);
            author.setText(book.get("author"));

            // Set image
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(StaticConstants.JSON_ADDRESS_IMAGE + book.get("isbn") + ".jpg").getContent());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            ImageView bookImageView = v.findViewById(R.id.bookImageView);
            bookImageView.setImageBitmap(bitmap);
            //image.setImageBitmap(Employee.getPhoto(true, eid));
        }
        return v;
    }


}
