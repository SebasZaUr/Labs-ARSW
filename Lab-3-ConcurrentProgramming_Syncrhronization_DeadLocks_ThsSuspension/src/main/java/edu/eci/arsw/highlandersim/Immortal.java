package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;

    private int health;

    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private boolean isPause,isStoped,death;


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
        this.death = false;
        this.isStoped= false;
    }

    public void run() {
        isPause = false;
        isStoped= false;
        while (true) {
            synchronized (name) {
                if (isPause) {
                    try {
                        name.wait(); // Espera hasta que se reanude
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                    Immortal im;
                synchronized (immortalsPopulation) {
                    int myIndex = immortalsPopulation.indexOf(this);
                    int nextFighterIndex = r.nextInt(immortalsPopulation.size());

                    // Evitar luchar consigo mismo
                    if (nextFighterIndex == myIndex) {
                        nextFighterIndex = (nextFighterIndex + 1) % immortalsPopulation.size();
                    }

                    im = immortalsPopulation.get(nextFighterIndex);
                    if(!death && nextFighterIndex != myIndex) {
                        try {
                            this.fight(im);
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public void fight(Immortal i2){
        if (i2.getHealth() > 0) {
            i2.changeHealth(i2.getHealth() - defaultDamageValue);
            this.health += defaultDamageValue;
            updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
            if (0>=i2.getHealth()){
                i2.pauseThread();
                i2.death = true;
                immortalsPopulation.remove(i2);
                updateCallback.processReport(this + " says: I killed " + i2 + " !\n");
            }
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }
    }

    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    public void pauseThread(){
        isPause = true;
    }

    public void resumeThread(){
        synchronized (name){
            isPause = false;
            name.notify();
        }
    }

    public void  stopedTread(){
        Thread.currentThread().interrupt();
    }

    @Override
    public String toString() {
        return name + "[" + health + "]";
    }
}
