/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];

    public Integer turn;

    public Object lock;


    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];


        this.turn = 0;

        this.lock = new Object();

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA,i,turn,lock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1,i,turn,lock);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
           pft[i].start();
        }

        try {
            while (true) {
                Thread.sleep(TMILISECONDS);
                for (PrimeFinderThread thread : pft) {
                    thread.pauseThread();
                }
                waitForEnter();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void waitForEnter() {
        System.out.println("Press ENTER to resume...");
        try {
            System.in.read();
            for (PrimeFinderThread thread : pft) {
                thread.resumeThread();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
