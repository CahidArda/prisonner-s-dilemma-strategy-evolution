package EvolutionMechanism;
import Agent.Agent;

public class Batch implements Runnable {
	private Agent[] a, b;
	private int nofGames;
	private Match m;
	
	private static int lastId;
	private int id;
	
	public Batch(Agent[] a, int nofGames) {
		id = lastId++;
		this.a = a;
		this.nofGames = nofGames;
		m = new Match(nofGames);
	}
	
	public Batch(Agent[] a, Agent[] b, int nofGames) {
		this.a = a;
		this.b = b;
		this.nofGames = nofGames;
		m = new Match(nofGames);
	}
	
	public void run() {
		//System.out.println("Batch id " + id + " started.");
		if (b==null) {
			for (int i=0; i<a.length-1; i++) {
				for (int j=i+1; j<a.length; j++) {
					
					m.addNextAgents(a[i], a[j]);
					m.playMatch();
				}
			}
		} else {
			for (int i=0; i<a.length; i++) {
				for (int j=0; j<b.length; j++) {

					m.addNextAgents(a[i], b[j]);
					m.playMatch();
				}
			}
		}
		//System.out.println("Batch id " + id + " ended.");
	}
}
