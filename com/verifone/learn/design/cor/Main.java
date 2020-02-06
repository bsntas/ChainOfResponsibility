package com.verifone.learn.design.cor;

public class Main {
    public static void main(String[] args) {
        SupportMaster master = SupportMaster.getInstance();
        master.registerWorker(new SupportWorker());
        master.registerWorker(new SupportWorker());
        master.registerWorker(new SupportWorker());
        master.registerWorker(new SupportWorker());
        master.registerWorker(new SupportWorker());
        for (int i= 0; i < 10; i++) {
            master.report("Issue" + i, "Please fix the issue " + i);
        }
    }
}