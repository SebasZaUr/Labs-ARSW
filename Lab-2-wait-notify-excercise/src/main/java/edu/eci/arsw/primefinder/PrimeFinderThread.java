package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PrimeFinderThread extends Thread{


	private static Object lock;
	int a,b;

	Integer id;
	private static Integer turn;

	private List<Integer> primes;

	private boolean wait;
	public PrimeFinderThread(int a, int b, int id,int turn,Object lock) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
		this.id = id;
		PrimeFinderThread.turn = turn;
		this.wait=false;
		PrimeFinderThread.lock = lock;
	}

	@Override
	public void run(){
		synchronized (lock) {
			//System.out.println("Thread: " + id + " Start");
				while (id!=turn){
					try {
						System.out.println(" Thread: " + id + " Wait");
						lock.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				//System.out.println("Turn: " + turn + " Thread: " + id + " Check");
				//System.out.println("running");
				for (int i = a; i < b; i++) {

					synchronized (this) {
						while (wait) {
							try {
								this.wait();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
							if (isPrime(i)){
								primes.add(i);
								System.out.println(i);
							}
					}
				}
				turn++;
			lock.notifyAll();
		}

	}

	public synchronized void pauseThread() {
		wait = true;
	}

	public synchronized void resumeThread() {
		wait = false;
		notify();
	}

	boolean isPrime(int n) {
		boolean ans;
		if (n > 2) {
			ans = n%2 != 0;
			for(int i = 3;ans && i*i <= n; i+=2 ) {
				ans = n % i != 0;
			}
		} else {
			ans = n == 2;
		}
		return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

}
