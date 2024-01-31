package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

public class PiDigitThread extends Thread{
    private int start;
    private final int count;
    private byte[] digits;

    public PiDigitThread(int start, int count){
        this.start = start;
        this.count = count;
    }

    @Override
    public void run(){
        digits = PiDigits.getDigits(start,count);
    }

    public byte[] getDigits(){return digits;}
}
