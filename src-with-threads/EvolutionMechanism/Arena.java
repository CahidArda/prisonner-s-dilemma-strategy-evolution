package EvolutionMechanism;

import java.util.LinkedList;
import java.util.Queue;

public class Arena {
	private Queue<Thread> threads;
	
	public Arena(int nofGames) {
		threads = new LinkedList<Thread>();
	}
	
	public void runArena() throws InterruptedException {
		//System.out.println("Arena starting");
		//System.out.println("Number of threads in line: " + threads.size());
		for (int i=0; i<threads.size(); i++) {
			Thread t = threads.poll();
			t.start();
			//System.out.println("Called batch: " + t.getName());
			threads.add(t);
		}
		for (int i=threads.size(); 0<i; i--) {
			Thread t = threads.poll();
			t.join();
		}
		//System.out.println("Arena over");
	}
	
	public void addToQueue(Thread t) {
		threads.add(t);
	}
	
	
}
