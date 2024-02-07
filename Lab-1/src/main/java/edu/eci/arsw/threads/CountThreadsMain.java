/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]) throws InterruptedException {
        CountThread thread1 = new CountThread(0,99);
        CountThread thread2 = new CountThread(99,199);
        CountThread thread3 = new CountThread(200,299);
        byte[] list = PiDigits.getDigits(0, 50000,200);
        for (int i =1;i<=32;i++){
            long startTime = System.currentTimeMillis();
            list = PiDigits.getDigits(0, 5000,i);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Tiempo de ejecución del hilo " + i+ " : " + executionTime + " milisegundos" );
        }
    }
    
}
