package com.example.averg.jsonproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, String> {

    public Book(String bookId){
        put("bookId", bookId);
    }

    public Book(String bookId, String title, String categoryId, String isbn, String author, String stock, String price, String synopsis, String storeWideDiscount, String finalPrice, String active) {

        put("bookId", bookId);
        put("title", title);
        put("categoryId", categoryId);
        put("isbn", isbn);
        put("author", author);
        put("stock", stock);
        put("price", price);
        put("synopsis", synopsis);
        put("storeWideDiscount", storeWideDiscount);
        put("finalPrice", finalPrice);
        put("active", active);

    }

    // Fetch a list of book Ids
    public static List<String> getBookIds(){
        List<String> bookIds = new ArrayList<String>();

        JSONArray a = JSONParser.getJSONArrayFromUrl(StaticConstants.JSON_ADDRESS + "/booksid");

        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                bookIds.add(b.getString(b.getString("BookID")));
            }
        } catch (Exception e) {
            Log.e("NewsItem", "JSONArray error");
            e.printStackTrace();
        }

        return bookIds;
    }

    // Get all details of a book with a url
    public static Book getBookById(String bookId){
        Book book = null;
        JSONObject b = JSONParser.getJSONFromUrl(StaticConstants.JSON_ADDRESS + "Books/" + bookId);
        try {
            book = new Book(
                    b.getString("BookID"),
                    b.getString("Title"),
                    b.getString("CategoryID"),
                    b.getString("ISBN"),
                    b.getString("Author"),
                    b.getString("Stock"),
                    b.getString("Price"),
                    b.getString("Synopsis"),
                    b.getString("SWDiscount"),
                    b.getString("FinalPrice"),
                    "true");
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return(book);
    }


    // Fetch a list of books
    public static List<Book> getBooks() {
        List<Book> list = new ArrayList<Book>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(StaticConstants.JSON_ADDRESS + "/Books");
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Book(
                        b.getString("BookID"),
                        b.getString("Title"),
                        b.getString("CategoryID"),
                        b.getString("ISBN"),
                        b.getString("Author"),
                        b.getString("Stock"),
                        b.getString("Price"),
                        b.getString("Synopsis"),
                        b.getString("SWDiscount"),
                        b.getString("FinalPrice"),
                        "true"));
            }
        } catch (Exception e) {
            Log.e("NewsItem", "JSONArray error");
            e.printStackTrace();
        }


        return(list);
    }

    // Fetches the associated image for the book
    public static Bitmap getBookImage(String bookId){
        // Set image
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(StaticConstants.JSON_ADDRESS_IMAGE + getBookById(bookId).get("isbn") + ".jpg").getContent());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }


}
