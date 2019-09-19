package com.example.android.justjava;



import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {



    int NumOfOrders=1;
    boolean Submitted=false;
    int CupPrice=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayQuantity(NumOfOrders);
        displayMessage(OrderSummary());
    }

    public void RefreshAdditions(View view)
    {
        if(Submitted)
            displayMessage(OrderSummary());
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity,int cost) {
        int price = quantity * cost;
        return price;
    }

    public void IncreaseOrder(View view) {
        NumOfOrders++;
     displayQuantity(NumOfOrders);
     if(Submitted)
          displayMessage(OrderSummary());
    }

    public void DecreaseOrder(View view) {
        if(NumOfOrders>1)
           NumOfOrders--;
        else
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();

        displayQuantity(NumOfOrders);
        if(Submitted)
           displayMessage(OrderSummary());
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
    }

    private String OrderSummary()
    {
        Submitted=true;
        CheckBox whippedCream=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox Chocolate=(CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameBox=(EditText) findViewById(R.id.name_box);

        if(whippedCream.isChecked())
            CupPrice++;
        if(Chocolate.isChecked())
            CupPrice+=2;

        EditText UserName = (EditText) findViewById(R.id.name_box);
        String Name = UserName.getText().toString();

        if(TextUtils.isEmpty(Name))
        {
            UserName.setError("Please enter your name");
            return("$5.00");
        }
        String ord = "Name:" +nameBox.getText()+
                   "\nQuantity:" + NumOfOrders +
                   "\nWhipped cream:"+whippedCream.isChecked()+
                   "\nChocolate:"+Chocolate.isChecked()+
                   "\nTotal price:$" + calculatePrice(NumOfOrders, CupPrice) +".00"+
                   "\nThank you:)";
         CupPrice=5;
         composeEmail("hossamalaa69.ha@gmail.com","Order Summary",ord);
            return ord;
    }
    public void composeEmail(String address, String subject,String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}