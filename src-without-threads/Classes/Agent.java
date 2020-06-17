package Classes;

import java.util.Arrays;
import java.util.Random;

public class Agent implements Comparable<Agent> {	
	
	private static int id = 0;
	private static Random random = new Random(0);
	
	private double[] behaviour = new double[4];
	private int agentId;
	
	Agent() {
		agentId = id++;
		for (int i=0; i<behaviour.length; i++) {
			double val = Math.pow(Math.E, 10*(random.nextDouble() - 0.5) );
			behaviour[i] = val/(val+1);
			
			
			// behaviour[i] = random.nextDouble(); //random linear selection
			if (behaviour[i]<0.001) {
				i--;
			}
		}
	}
	
	Agent(int agentId, double a, double b, double c, double d) {
		this.agentId = agentId;
		behaviour[0] = a;
		behaviour[1] = b;
		behaviour[2] = c;
		behaviour[3] = d;
	}
	
	//mate two agents
	Agent(Agent a1, Agent a2) {
		agentId = id++;
		double[] b1 = a1.getBehavior();
		double[] b2 = a2.getBehavior();
		for (int i=0; i<4; i++) {
			behaviour[i] = (b1[i]+b2[i])/2;
		}
	}
	
	final private double mutationBound = 0.1;
	void mutate() {
		for (int i=0; i<4; i++) {
			behaviour[i] += (random.nextDouble()-0.5)*mutationBound*2;
			if (behaviour[i]<0.001) {
				behaviour[i] = 0.002;
			} else if (behaviour[i]>0.999) {
				behaviour[i] = 0.998;
			}
		}
	}
	
	final private double tremblingHand = 0.01;
	boolean decide(int previous_result) {
		boolean decision = (random.nextDouble() < behaviour[previous_result]);
		return (random.nextDouble()>tremblingHand?decision:!decision);
	}
	
	double[] getBehavior() {
		return behaviour;
	}
	
	String getBehaviourForCsv() {
		return (String.format("%f, %f, %f, %f", behaviour[0], behaviour[1], behaviour[2], behaviour[3]));
	}
	
	public String toString() {
		return String.format("\nAgent id: %d \t-#-  Agent behavior: [%.3f, %.3f, %.3f, %.3f] ", agentId, behaviour[0],behaviour[1],behaviour[2],behaviour[3]);
	}

	@Override
	public int compareTo(Agent arg0) {
		Agent other_a = (Agent) arg0;
		return agentId - other_a.agentId;
	}
	
	int getAgentId() {
		return agentId;
	}
	
	static int getId() {
		return id;
	}
	
	static void setId(int newId) {
		id = newId;
	}
	
}
