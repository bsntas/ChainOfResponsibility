package com.verifone.learn.design.cor;

import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.Random;

public class SupportMaster {
    private Stack<SupportWorker> active;
    private Stack<SupportWorker> passive;
    private Queue<Ticket> backlog; 

    private static SupportMaster master;
    private TicketSolver solver;

    private int workerCount;

    private SupportMaster () {
        active = new Stack<>();
        passive = new Stack<>();
        backlog = new ConcurrentLinkedQueue<>();
        workerCount = 0;
        solver = new TicketSolver();
        solver.start();
    }

    public static SupportMaster getInstance() {
        if (master == null)
            master = new SupportMaster();
        return master;
    }

    public void registerWorker (SupportWorker worker) {
        Stack<SupportWorker> temp = new Stack<>();

        synchronized (active) {
            while (!active.empty()) {
                temp.push(active.pop());
            }
            worker.setLevel(workerCount++);
            active.push(worker);
            while (!temp.empty()) {
                active.push(temp.pop());
            }
        }
    }

    public Ticket report (String sub, String body) {
        Ticket ticket = new Ticket(sub, body);

        Random rand = new Random(ticket.getId());
        ticket.setSeverity(rand.nextInt(workerCount));
        System.out.println(ticket.getSeverity());

        synchronized(backlog) {
            backlog.add(ticket);
        }

        return ticket;
    }

    class TicketSolver extends Thread {
        public void run () {
            while (true) {
                Ticket ticket = null;
                synchronized(backlog) {
                    if (backlog.isEmpty())
                        continue;
                    ticket = backlog.remove();
                }
                boolean processed = false;
                while (!processed) {
                    SupportWorker worker = null;
                    synchronized(active) {
                        if (active.empty())
                            break;
                        worker = active.peek();
                    }
                    System.out.println ("Ticket id " + ticket.getId() + " with severity: " + ticket.getSeverity() + " assigned to worker: " + worker.getLevel());
                    processed = worker.processTicket(ticket);
                    if (!processed) {
                        synchronized(active) {
                            passive.push(active.pop());
                        }
                    }
                }
        
                if (!processed) {
                    System.out.println("Sorry, no support workers could process ticket id " + ticket.getId());
                }
        
                synchronized(active) {
                    while (!passive.empty()) {
                        active.push(passive.pop());
                    }
                }
            }
        }
    }
}