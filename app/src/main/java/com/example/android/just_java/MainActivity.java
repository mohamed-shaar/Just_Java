/*
  IMPORTANT: Make sure you are using the correct package name.
  This example uses the package name:
  package com.example.android.justjava
  If you get an error when copying this code into Android studio, update it to match teh package name found
  in the project's AndroidManifest.xml file.
 */

package com.example.android.just_java;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    protected int quantity = 0;
    protected int basePrice = 5;
    protected String name = "";
    protected boolean hasWhippedCream = false;
    protected boolean hasChocolate = false;
    protected int addWhippedCream = 0;
    protected int addChocolate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price;
        addingWhippedCream();
        addingChocolate();
        addName();
        price = calculatePrice();
        String priceMessage = orderSummaryMethod(price);
        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "mamo.sha3ar@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        Log.v("MainActivity", "Added intent details");
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
            Log.v("MainActivity", "entered if statement");
        }
        /*Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "coffee order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }*/
    }

    private void displayMessage(String order){
        TextView textView = findViewById(R.id.order_summary_text_view);
        textView.setText(order);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This function increments quantity by 1 every time the plus button is pressed.
     *
     */
    public void increment(View view) {
        if (quantity < 100) {
            displayQuantity(++quantity);
        }
        else {
            Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * This function decrements quantity by 1 every time the minus button is clicked.
     * The quantity doesn't go below 0.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            displayQuantity(--quantity);
        }
        else {
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        return (quantity * basePrice) + (quantity * addWhippedCream) + (quantity * addChocolate);
    }



    /**
     * creates a summary of the order
     *
     * @param price total price of all coffees
     * @return string with the summary
     */
    private String orderSummaryMethod(int price) {
        return  getString(R.string.orderName) + " " + name +
                getString(R.string.orderQuantity) + " " + quantity +
                getString(R.string.addWhippedCream) + " " + hasWhippedCream +
                getString(R.string.addChocolate) + " " + hasChocolate +
                getString(R.string.total) + " " + NumberFormat.getCurrencyInstance().format(price) +
                getString(R.string.thankYou);
    }

    private void addingWhippedCream() {
        CheckBox checkBox = findViewById(R.id.checkBox);
        if (checkBox.isChecked()) {
            hasWhippedCream = true;
            addWhippedCream = 5;
        } else {
            hasWhippedCream = false;
            addWhippedCream = 0;
        }
    }

    private void addingChocolate() {
        CheckBox checkBox = findViewById(R.id.chocolate_checkbox);
        if (checkBox.isChecked()) {
            hasChocolate = true;
            addChocolate = 10;
        } else {
            hasChocolate = false;
            addChocolate = 0;
        }
    }

    private void addName() {
        EditText editText = findViewById(R.id.name_input);
        name = editText.getText().toString();
    }

}