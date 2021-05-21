package com.maad.bankticket;

public class TicketModel {

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
