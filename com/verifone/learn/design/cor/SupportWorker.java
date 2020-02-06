package com.verifone.learn.design.cor;

public class SupportWorker {

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean processTicket(Ticket ticket) {
        if (ticket.getSeverity() == level) {
            System.out.println("Support level " + level + " working on ticket id " + ticket.getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ticket id " + ticket.getId() + " resolved by Support level " + level);
            return true;
        }
        System.out.println("Ticket id " + ticket.getId() + " passed on by Support level " + level);
        return false;
    }
}