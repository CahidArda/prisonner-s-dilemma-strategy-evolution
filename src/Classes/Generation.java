package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Generation {
	TreeMap<Agent, Double> agentScores;
	int nofAgents;
	int nofGamesEachRound;
	int generation;
	double ratioToRemoveEachGeneration;
	
	Generation(int nofAgents, int nofGamesEachRound, double ratioToRemoveEachGeneration) {
		agentScores = new TreeMap<Agent, Double>();
		this.nofAgents = nofAgents;
		this.nofGamesEachRound = nofGamesEachRound;
		this.ratioToRemoveEachGeneration = ratioToRemoveEachGeneration;
		generation = 0;
		fillGenerationWithAgents();
	}
	
	Generation(String fileName) throws FileNotFoundException {
		File fl = new File(fileName);
		Scanner sc = new Scanner(fl);
		
		agentScores = new TreeMap<Agent, Double>();
		generation = sc.nextInt();
		nofAgents = sc.nextInt();
		nofGamesEachRound = sc.nextInt();
		ratioToRemoveEachGeneration = sc.nextInt();
		Agent.setId(sc.nextInt());
		
		for (int i=0; i<nofAgents; i++) {
			agentScores.put(new Agent(
					sc.nextInt(),
					sc.nextDouble(),
					sc.nextDouble(),
					sc.nextDouble(),
					sc.nextDouble()
					), sc.nextDouble());
		}
		
		sc.close();
	}
	
	void runGeneration() {
		for (Agent agent1: agentScores.keySet()) {
			for (Agent agent2: agentScores.keySet()) {
				if (agent1.compareTo(agent2)<0) {
					double[] scores = new Match(agent1, agent2, nofGamesEachRound).getScores();
					agentScores.replace(agent1, agentScores.get(agent1)+scores[0]);
					agentScores.replace(agent2, agentScores.get(agent2)+scores[1]);
				}
			}
		}
	}
	
	void updateToNextGeneration() {
		generation++;
		//remove worst performing agents
		while (nofAgents*(1-ratioToRemoveEachGeneration) < agentScores.keySet().size()) {
			//System.out.println("will remove agent with id: " + getWorstAgent().getAgentId());
			agentScores.remove(getWorstAgent());
		}
		
		//set old agents' score to 0
		//mutate existing agents
		for (Agent a: agentScores.keySet()) {
			agentScores.replace(a, 0.0);
			if (a.getAgentId()/2*2==a.getAgentId()) {
				a.mutate();
			}
		}
		
		int i=1;
		while (agentScores.keySet().size()<nofAgents) { //add new agents in an evolutionary way
			agentScores.put(new Agent(getBestAgent(i),getBestAgent(++i)),0.0);
		}
		//fillGenerationWithAgents(); //add new agents randomly after every generation
	}
	
	void flushGeneration() {
		agentScores.clear();
	}
	
	void fillGenerationWithAgents() {
		while (agentScores.keySet().size()<nofAgents) {
			agentScores.put(new Agent(),0.0);
		}
	}
	
	void print() {
		System.out.println(agentScores);
	}
	
	void printGeneration(String folderName) throws FileNotFoundException {
		File generationOutputFile = new File(folderName + "/g" + generation + ".txt");
		PrintStream generationOutput = new PrintStream(generationOutputFile);
		
		generationOutput.println(String.format(
				"%d %d %d %f %d",
				generation,
				nofAgents,
				nofGamesEachRound,
				ratioToRemoveEachGeneration,
				Agent.getId()
				));
		
		for (Agent a: agentScores.keySet()) {
			generationOutput.println(String.format(
					"%d %f %f %f %f %f",
					a.getAgentId(),
					a.getBehavior()[0],
					a.getBehavior()[1],
					a.getBehavior()[2],
					a.getBehavior()[3],
					agentScores.get(a)
					));
		}
		
		generationOutput.close();
	}
	
	void printGenerationBPAWPAAA(PrintStream generationDetailsOutput) {
		Agent bestAgent = getBestAgent(1);
		Agent worstAgent = getWorstAgent();
		
		double[] totalBehavior = new double[4];
		for (Agent a: agentScores.keySet()) {
			for (int i=0; i<4; i++) {
				totalBehavior[i] += a.getBehavior()[i];
			}
		}
		
		final int nofBestAgents = 10;
		double[] averageOfBestAgents = new double[4];
		for (int i=1; i<nofBestAgents; i++) {
			for (int j=0; j<4; j++) {
				averageOfBestAgents[j] += getBestAgent(i).getBehavior()[j];
			}
		}
		
		generationDetailsOutput.println(String.format(
				"%d,%s,%s,%f,%f,%f,%f,%f,%f,%f,%f",
				generation,
				bestAgent.getBehaviourForCsv(),
				worstAgent.getBehaviourForCsv(),
				totalBehavior[0]/nofAgents,
				totalBehavior[1]/nofAgents,
				totalBehavior[2]/nofAgents,
				totalBehavior[3]/nofAgents,
				averageOfBestAgents[0]/nofBestAgents,
				averageOfBestAgents[1]/nofBestAgents,
				averageOfBestAgents[2]/nofBestAgents,
				averageOfBestAgents[3]/nofBestAgents
			));

	}
	
	Agent getWorstAgent() {
		Agent agentWithLowestScore=null;
		for (Agent a: agentScores.keySet()) {
			if (agentWithLowestScore==null || agentScores.get(agentWithLowestScore)>agentScores.get(a)) {
				agentWithLowestScore = a;
			}
		}
		return agentWithLowestScore;
	}
	
	Agent getBestAgent(int rank) {
		Agent agentWithHighestScore=null;
		for (Agent a: agentScores.keySet()) {
			if (agentWithHighestScore==null || agentScores.get(agentWithHighestScore)<agentScores.get(a)) {
				agentWithHighestScore = a;
			}
		}
		if (rank==1) {
			return agentWithHighestScore;
		} else {
			double agentScore = agentScores.get(agentWithHighestScore);
			agentScores.remove(agentWithHighestScore);
			Agent a = getBestAgent(rank-1);
			agentScores.put(agentWithHighestScore, agentScore);
			return a;
		}
		
	}
}
