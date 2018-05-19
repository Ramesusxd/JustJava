package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    int numberOfCoffees = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Create summary of the order.
     *
     * @param name is what shows in the edittext field
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.first_last_name);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderNumber = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderNumber);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }





    }

    /**
     * Calculates the price of the order.
     *
     * @ return total price
     */
    private int calculatePrice(boolean addWhippedcream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedcream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        int price = numberOfCoffees * basePrice;
        return price;
    }

    /**
     * @param price of the order
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String orderNumber = "Name: " + name;
        orderNumber += " \nAdd Whipped Cream? " + addWhippedCream;
        orderNumber += " \nAdd chocolate? " + addChocolate;
        orderNumber += " \nQuantity: " + numberOfCoffees;
        orderNumber += " \nTotal: $" + price;
        orderNumber += " \nThank you!";
        return orderNumber;

    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffees == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }




}