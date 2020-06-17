package Agent;

import java.util.Random;

public class Agent implements Comparable<Agent> {
	private Random random;
	private static int lastId = 0;
	private int id;
	private double[] behavior;
	private long score;
	
	//TODO switch to protected
	protected Agent() {
		id = lastId++;
		behavior = new double[4];
		random=new Random();
		for (int i=0; i<4; i++) {
			double val = Math.pow(Math.E, 10*(random.nextDouble() - 0.5) );
			behavior[i] = val/(val+1);
			if (behavior[i]<0.001) {
				behavior[i] = 0.002;
			} else if (behavior[i]>0.999) {
				behavior[i] = 0.998;
			}
		}
	}
	
	//mate two agents
	protected Agent(Agent a1, Agent a2) {
		id = lastId++;
		behavior = new double[4];
		random=new Random();
		double[] b1 = a1.getBehavior();
		double[] b2 = a2.getBehavior();
		for (int i=0; i<4; i++) {
			behavior[i] = (b1[i] + b2[i])/2;
		}
		mutate();
	}
	
	final private double mutationBound = 0.1;
	protected void mutate() {
		for (int i=0; i<4; i++) {
			behavior[i] += (random.nextDouble()-0.5)*mutationBound*2;
			if (behavior[i]<0.001) {
				behavior[i] = 0.002;
			} else if (behavior[i]>0.999) {
				behavior[i] = 0.998;
			}
		}
	}
	

	public boolean decide(int behaviourIndex) {
		synchronized (this) {
			return (random.nextDouble()<behavior[behaviourIndex]);
		}
	}
	
	public String toString() {
		return (String.format("Agent Id: %d\tAgent Score: %d", id, score));
	}
	
	int getId() {
		synchronized (this) {
			return id;
		}
	}
	
	public void addToScore(long s) {
		synchronized (this) {
			score+=s;
		}
	}
	
	void setScore(long s) {
		synchronized (this) {
			score = s;
		}
		
	}
	
	double getScore() {
		synchronized (this) {
			return score;
		}
	}
	
	protected String getBehaviorForCsv() {
		synchronized (this) {
			return (String.format("%f, %f, %f, %f", behavior[0], behavior[1], behavior[2], behavior[3]));
		}
	}
	
	protected double[] getBehavior() {
		synchronized (this) {
			return behavior;
		}
	}
	
	public int compareTo(Agent other) {
		return (int) (getScore()-other.getScore());
	}
}
