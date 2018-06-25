package com.example.averg.jsonproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetailsActivity extends Activity {

    String bookId;
    Book b;
    protected static int FLIPMODE_FLIP = 0;
    protected static int FLIPMODE_UNFLIP = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        setContentView(R.layout.activity_details);

        // Fetch URL from the intent
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        // Get data from the URL
        b = Book.getBookById(bookId);

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

    // Turn updatable textviews into edit text. Hides self and show cancel / submit buttons
    protected void onUpdateInformationButtonClick(View v){
        this.flipVisibility(FLIPMODE_FLIP);

        // Convert textview into Edit text
        TextView synopsisTextView = (TextView) findViewById(R.id.synopsisTextView);
        EditText synopsisEditText = (EditText) findViewById(R.id.synopsisEditText);

        synopsisEditText.setText(synopsisTextView.getText());

    }

    // Turns edittexts into textviews, hide self and reveal update information button again
    protected void onCancelButtonClick(View v){
        this.flipVisibility(FLIPMODE_UNFLIP);
        refreshBookDetails();
    }

    protected void onSubmitButtonClick(View v){
        this.flipVisibility(FLIPMODE_UNFLIP);

        // Save book attributes
        this.saveBookAttributes();

        // Create a JSON object from the book
        JSONObject postBook = new JSONObject();

        try {
            postBook.put("Author", b.get("author"));
            postBook.put("BookID", b.get("bookId"));
            postBook.put("CategoryID", b.get("categoryId"));
            postBook.put("CategoryName", "poopoohead");
            postBook.put("FinalPrice", b.get("finalPrice"));
            postBook.put("ISBN", b.get("isbn"));
            postBook.put("Price", b.get("price"));
            postBook.put("SWDiscount", b.get("swDiscount"));
            postBook.put("Stock", b.get("stock"));
            postBook.put("Synopsis", b.get("synopsis"));
            postBook.put("Title", b.get("title"));


            // Send the object to the server.
            String result = JSONParser.postStream(StaticConstants.JSON_ADDRESS+"/Update", postBook.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        refreshBookDetails();
    }


    /*
      internal methods
     */

    // Flips the visibility of the submit, cancel, updateinformation buttons
    protected void flipVisibility(int flipMode){
        if(flipMode == FLIPMODE_FLIP){
            ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
            switcher.showNext(); //or switcher.showPrevious();

            // Show buttons
            findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
            findViewById(R.id.submitButton).setVisibility(View.VISIBLE);

            // Hide self
            findViewById(R.id.updateInformationButton).setVisibility(View.GONE);

        }

        else if(flipMode == FLIPMODE_UNFLIP){
            ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
            switcher.showNext(); //or switcher.showPrevious();

            // Convert EditText into TextView
            TextView synopsisTextView = (TextView) switcher.findViewById(R.id.synopsisTextView);
            EditText synopsisEditText = (EditText) switcher.findViewById(R.id.synopsisEditText);

            synopsisTextView.setText(b.get("synopsis"));

            // Hide buttons
            findViewById(R.id.cancelButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.submitButton).setVisibility(View.INVISIBLE);

            // Show get information button
            findViewById(R.id.updateInformationButton).setVisibility(View.VISIBLE);
        }

        else{
            Toast t = new Toast(getApplicationContext());
            t.setText("Invalid Toast integer provided! (flipVisibility method)");
            t.setDuration(Toast.LENGTH_LONG);
            t.show();
        }
    }

    // Refreshes the textview with new Book data
    protected void refreshBookDetails(){

        // Refresh information in the book model
        b = Book.getBookById(bookId);

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

    // Saves the attributes of the book based on the editTexts
    // TODO: Do for other attributes other than synopsis
    protected void saveBookAttributes(){

        EditText synopsisEditText = (EditText) findViewById(R.id.synopsisEditText);
        b.put("synopsis", synopsisEditText.getText().toString());

    }

}
