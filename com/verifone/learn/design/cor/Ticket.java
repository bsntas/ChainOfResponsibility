package com.verifone.learn.design.cor;

public class Ticket {
    private int ticketId;
    private String subject;
    private String body;
    private int severity;

    private static int nextId = 1;

    public Ticket (String subject, String body) {
        this.ticketId = nextId++;
        this.subject = subject;
        this.body = body;
    }

    public int getId () {
        return ticketId;
    }

    public String getSubject () {
        return subject;
    }

    public String getBody () {
        return body;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getSeverity () {
        return severity;
    }
}