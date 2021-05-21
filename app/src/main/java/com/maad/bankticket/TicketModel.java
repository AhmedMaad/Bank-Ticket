package com.maad.bankticket;

public class TicketModel {

    //chosen branch
    //chosen department
    //ticket number "read from firebase then increment counter by one (Format: 035)"
    //your turn after X customers "read from firebase how many document tickets"
    //estimated wait time "number of documents * 5 min"
    //counter number "ticket number - turn"
    private String branch;
    private String department;
    private int ticketNumber;
    private int turn;
    private int waitTime;
    private int counterNumber;

    public TicketModel(){}

    public TicketModel(String branch, String department, int ticketNumber, int turn, int waitTime, int counterNumber) {
        this.branch = branch;
        this.department = department;
        this.ticketNumber = ticketNumber;
        this.turn = turn;
        this.waitTime = waitTime;
        this.counterNumber = counterNumber;
    }

    public String getBranch() {
        return branch;
    }

    public String getDepartment() {
        return department;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getTurn() {
        return turn;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getCounterNumber() {
        return counterNumber;
    }

}
