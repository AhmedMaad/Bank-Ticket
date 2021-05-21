package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Ticket extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView ticketnumber;
    private TextView turnnumber;
    private TextView counternumber;
    private TextView waitingtime;
    private TextView branch;
    private TextView department;
    private TicketModel ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        db = FirebaseFirestore.getInstance();

        ticketnumber = findViewById(R.id.ticketnumber);
        turnnumber = findViewById(R.id.turnnumber);
        counternumber = findViewById(R.id.counternumber);
        waitingtime = findViewById(R.id.waitingtime);
        branch = findViewById(R.id.branch);
        department = findViewById(R.id.department);


        db.collection("tickets")
                .document(Helper.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(Ticket.this, R.string.taketurn, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Ticket.this, TakeTurn.class);
                            startActivity(i);
                            finish();
                        } else {
                            ticket = documentSnapshot.toObject(TicketModel.class);
                            ticketnumber.setText(convertTicketNumberToStringFormat(ticket.getTicketNumber()));
                            turnnumber.setText(String.valueOf(ticket.getTurn()));
                            counternumber.setText(String.valueOf(ticket.getCounterNumber()));
                            waitingtime.setText(String.valueOf(ticket.getWaitTime()));
                            branch.setText(ticket.getBranch());
                            department.setText(ticket.getDepartment());
                        }
                    }
                });
    }

    private String convertTicketNumberToStringFormat(int ticketNumber) {
        if (ticketNumber < 10)
            return "00" + ticketNumber;
        else if (ticketNumber <= 99)
            return "0" + ticketNumber;
        else
            return String.valueOf(ticketNumber);
    }

    public void back(View view) {
        onBackPressed();
    }
}