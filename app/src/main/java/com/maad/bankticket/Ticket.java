package com.maad.bankticket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Ticket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        /* convert int ot string with this format and set thte text to text view
        * if (ticketNumber < 10)
            map.put("ticket", "00" + ticketNumber);
        else if (ticketNumber <= 99)
            map.put("ticket", "0" + ticketNumber);
        else
            map.put("ticket", String.valueOf(ticketNumber));*/


    }
}