package com.maad.bankticket;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class TakeTurn extends AppCompatActivity {

    private String chosenDepartment;
    private String chosenBranch;
    private Button enterbtn;
    private ProgressBar progress;
    private int ticketNumber;
    private FirebaseFirestore db;
    private int turnNumber;
    private int waitTime;
    private int counterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_turn);

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

    }

    public void takeTurn(View view) {
        enterbtn.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        //chosen branch --> Done
        //chosen department --> Done
        //ticket number "read from firebase then increment ticket number by one (Format: 035)" --> Done
        //upload incremented ticket number to firebase --> Done
        //your turn after X customers "read from firebase how many document tickets" --> Done
        //estimated wait time "number of documents * 5 min" --> Done
        //counter number "ticket number - turn"

        //TODO: you might want to add the ticket document id to it self

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
                    }
                });
    }

    private void registerNewTicket(){


/*
        waitTime = turnNumber * 5;
        counterNumber = ticketNumber - turnNumber;
        TicketModel ticket =
                new TicketModel(chosenBranch, chosenDepartment, ticketNumber
                        , turnNumber, waitTime, counterNumber);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("tickets")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
*/
    }

}

//Admin application will read oldest document, and all tickets will update their data from
//firebase admin app

