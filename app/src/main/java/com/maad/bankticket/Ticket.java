package com.maad.bankticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Ticket extends ParentActivity {

    private FirebaseFirestore db;
    private TextView ticketnumber;
    private TextView turnnumber;
    private TextView counternumber;
    private TextView waitingtime;
    private TextView branch;
    private TextView department;
    private TicketModel ticket;
    private String requestTime;
    private int waitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        setTitle(R.string.ticket);

        db = FirebaseFirestore.getInstance();

        ticketnumber = findViewById(R.id.ticketnumber);
        turnnumber = findViewById(R.id.turnnumber);
        counternumber = findViewById(R.id.counternumber);
        waitingtime = findViewById(R.id.waitingtime);
        branch = findViewById(R.id.branch);
        department = findViewById(R.id.department);

        searchInHelwanTickets();
    }

    private void searchInHelwanTickets() {
        db.collection("HelwanTickets")
                .document(Helper.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            searchInMaadiTickets();
                        } else {
                            ticket = documentSnapshot.toObject(TicketModel.class);
                            ticketnumber.setText(convertTicketNumberToStringFormat(ticket.getTicketNumber()));
                            turnnumber.setText(String.valueOf(ticket.getTurn()));
                            counternumber.setText(String.valueOf(ticket.getCounterNumber()));
                            branch.setText(ticket.getBranch());
                            department.setText(ticket.getDepartment());
                            requestTime = ticket.getRequestTime();
                            waitTime = ticket.getWaitTime();
                            calculateActualWaitTime(waitTime);
                        }
                    }
                });
    }

    private void searchInMaadiTickets() {
        db.collection("MaadiTickets")
                .document(Helper.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            searchInDokkiTickets();
                        } else {
                            ticket = documentSnapshot.toObject(TicketModel.class);
                            ticketnumber.setText(convertTicketNumberToStringFormat(ticket.getTicketNumber()));
                            turnnumber.setText(String.valueOf(ticket.getTurn()));
                            counternumber.setText(String.valueOf(ticket.getCounterNumber()));
                            branch.setText(ticket.getBranch());
                            department.setText(ticket.getDepartment());
                            requestTime = ticket.getRequestTime();
                            waitTime = ticket.getWaitTime();
                            calculateActualWaitTime(waitTime);
                        }
                    }
                });
    }

    private void searchInDokkiTickets() {
        db.collection("DokkiTickets")
                .document(Helper.USER_ID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            Toast.makeText(Ticket.this, R.string.taketurn, Toast.LENGTH_SHORT).show();
                            openTakeTurnActivity();
                        } else {
                            ticket = documentSnapshot.toObject(TicketModel.class);
                            ticketnumber.setText(convertTicketNumberToStringFormat(ticket.getTicketNumber()));
                            turnnumber.setText(String.valueOf(ticket.getTurn()));
                            counternumber.setText(String.valueOf(ticket.getCounterNumber()));
                            branch.setText(ticket.getBranch());
                            department.setText(ticket.getDepartment());
                            requestTime = ticket.getRequestTime();
                            waitTime = ticket.getWaitTime();
                            calculateActualWaitTime(waitTime);
                        }
                    }
                });
    }

    private void openTakeTurnActivity() {
        Intent i = new Intent(Ticket.this, TakeTurn.class);
        startActivity(i);
        finish();
    }

    private void calculateActualWaitTime(int generalTimeInMinutes) {

        int waitHours = generalTimeInMinutes / 60;
        int waitMinutes = generalTimeInMinutes % 60;

        String[] requestTimes = requestTime.split(":");

        int requestHours = Integer.parseInt(requestTimes[0]);
        int requestMinutes = Integer.parseInt(requestTimes[1]);

        int serveHours = waitHours + requestHours;
        int serveMinutes = waitMinutes + requestMinutes;
        if (serveMinutes > 59) {
            ++serveHours;
            serveMinutes -= 60;
        }

        String serveTime = serveHours + ":" + serveMinutes;
        Log.d("trace", "Serve time = " + serveTime);

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        String currentTime = hour + ":" + minute;
        Log.d("trace", "Current time = " + currentTime);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            Date serveTimeFormat = format.parse(serveTime);
            Date currentTimeFormat = format.parse(currentTime);
            long difference = serveTimeFormat.getTime() - currentTimeFormat.getTime();
            if (difference < 0) {
                Log.d("trace", "Time passed (invalid ticket)");
                //We should delete the ticket at this point
                Toast.makeText(this, R.string.turnpassed, Toast.LENGTH_SHORT).show();
                openTakeTurnActivity();
            } else {
                Log.d("trace", "Show waiting time counter with diff: " + difference);
                startTimer(difference);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void startTimer(long timeInSeconds) {

        //First user has no wait time so no  need for a timer
        new CountDownTimer(timeInSeconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                waitingtime.setText(String.valueOf(millisUntilFinished / 1000));
                if (millisUntilFinished <= 60000)
                    waitingtime.setTextColor(getResources().getColor(R.color.red));
            }

            @Override
            public void onFinish() {
                Log.d("trace", "Time counter finished");
                //tell the user that this is his turn and wait 5 seconds then delete the ticket
                LinearLayout parent = findViewById(R.id.parent);
                Snackbar
                        .make(parent, R.string.yourturn, BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        deleteTicket();
                    }
                }, 3000);
            }

        }.start();
    }

    private void deleteTicket() {
        db
                .collection("HelwanTickets")
                .document(Helper.USER_ID)
                .delete();
        db
                .collection("MaadiTickets")
                .document(Helper.USER_ID)
                .delete();
        db
                .collection("DokkiTickets")
                .document(Helper.USER_ID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Ticket.this);
                        builder
                                .setTitle(getString(R.string.customernumber) + ticketnumber.getText())
                                .setMessage(R.string.thanks)
                                .setPositiveButton(R.string.finish, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .setCancelable(false)
                                .show();
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