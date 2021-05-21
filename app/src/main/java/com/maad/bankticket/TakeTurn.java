package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.Help;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class TakeTurn extends ParentActivity {

    private String chosenDepartment;
    private String chosenBranch;
    private Button enterbtn;
    private ProgressBar progress;
    private int ticketNumber;
    private FirebaseFirestore db;
    private int turnNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_turn);
        setTitle(R.string.taketurn);

        db = FirebaseFirestore.getInstance();

        enterbtn = findViewById(R.id.enterbtn);
        progress = findViewById(R.id.progress);

        String[] departments = {getString(R.string.customerservice), getString(R.string.teller), getString(R.string.reception)};
        String[] branches = {getString(R.string.dokki), getString(R.string.maadi), getString(R.string.helwan)};

        Spinner DepartmentSpinner = findViewById(R.id.spinner1);
        Spinner branchSpinner = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        DepartmentSpinner.setAdapter(adapter);
        branchSpinner.setAdapter(adapter2);

        DepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenDepartment = departments[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenBranch = branches[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //TODO: DELETE THIS LINE
        Helper.USER_ID = "7dYryy0KZ2aqjekFZvBrF2qLJSs2";

        //Prevent the user from taking another turn if he has an existing ticket
        db
                .collection("tickets")
                .document(Helper.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Toast.makeText(TakeTurn.this, R.string.alreadyhaveticket, Toast.LENGTH_SHORT).show();
                            openTicketDetails();
                        } else
                            enterbtn.setVisibility(View.VISIBLE);
                    }
                });

    }

    public void takeTurn(View view) {
        enterbtn.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        getLastTicketNumber();
    }

    private void getLastTicketNumber() {
        db
                .collection("ticketNumber")
                .document("ticketNumber")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String lastTicketNumberString = documentSnapshot.getString("ticket");
                        ticketNumber = Integer.parseInt(lastTicketNumberString);
                        ++ticketNumber;
                        Log.d("trace", "New Ticket number: " + ticketNumber);
                        uploadNewTicketNumber();
                    }
                });

    }

    private void uploadNewTicketNumber() {
        Map<String, String> map = new HashMap<>();

        if (ticketNumber < 10)
            map.put("ticket", "00" + ticketNumber);
        else if (ticketNumber <= 99)
            map.put("ticket", "0" + ticketNumber);
        else
            map.put("ticket", String.valueOf(ticketNumber));

        db
                .collection("ticketNumber")
                .document("ticketNumber")
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("trace", "Ticket number updated");
                        readNumberOfDocumentTickets();
                    }
                });

    }

    private void readNumberOfDocumentTickets() {
        db.collection("tickets")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        turnNumber = queryDocumentSnapshots.getDocuments().size();
                        Log.d("trace", "Turn number after: " + turnNumber);
                        registerNewTicket();
                    }
                });
    }

    private void registerNewTicket() {
        int waitTime = turnNumber * 5;
        int counterNumber = ticketNumber - turnNumber;

        //Add time of request (e.g. 20:15)
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        String requestTime = hour + ":" + minute;

        TicketModel ticket =
                new TicketModel(chosenBranch, chosenDepartment, ticketNumber
                        , turnNumber, waitTime, counterNumber, requestTime);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db
                .collection("tickets")
                .document(Helper.USER_ID)
                .set(ticket)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TakeTurn.this, R.string.ticketadded
                                , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void openTicketDetails() {
        Intent i = new Intent(TakeTurn.this, Ticket.class);
        startActivity(i);
        finish();
    }

}
