package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

public class PiDigitThread extends Thread{
    private int start;
    private int end;
    private byte[] digits;

    public PiDigitThread(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    public void run(){
        digits = PiDigits.getDigits(start,end);
    }

    public byte[] getDigits(){return digits;}
}
