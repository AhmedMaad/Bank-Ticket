package com.maad.bankticket;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;


public class TakeTurn extends AppCompatActivity {

    private String chosenDepartment;
    private String chosenBranch;
    private Button enterbtn;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_turn);

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
        //ticket number "read from firebase then increment counter by one (Format: 035)"
        //your turn after X customers "read from firebase how many document tickets"
        //estimated wait time "number of documents * 5 min"
        //counter number "ticket number - turn"

        /*TicketModel ticket = new TicketModel(chosenBranch, chosenDepartment);


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
                });*/
        //TODO: you might want to add the ticket document id to it self
    }


}

//Admin application will read oldest document, and all tickets will update their data from
//firebase admin app