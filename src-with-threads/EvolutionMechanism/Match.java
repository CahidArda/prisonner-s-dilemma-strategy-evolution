package EvolutionMechanism;
import Agent.Agent;

import java.util.Random;


public class Match {
	Random random = new Random();
	static int lastId;
	int id;
	
	int nofGames;
	Agent a;
	Agent b;
	
	Match(int nofGames) {
		id = lastId++;
		this.nofGames = nofGames;
	}
	
	void addNextAgents(Agent a, Agent b) {
		this.a = a;
		this.b = b;
	}
	
	
	//TODO check for synchronization
	public void playMatch() {
		playRound(3,3);
		playRound(0,5);
		playRound(5,0);
		playRound(1,1);
	}
	
	final int RScore = 3;
	final int SScore = 0;
	final int TScore = 5;
	final int PScore = 1;
	
	//R S T P
	void playRound(int outcome0, int outcome1) {
		for (int i=0; i<nofGames; i++) {
			boolean cooperate0 = a.decide(outcomeToIndex(outcome0));
			boolean cooperate1 = b.decide(outcomeToIndex(outcome0));
			
			if (cooperate0) {
				if (cooperate1) {
					outcome0 = RScore;
					outcome1 = RScore;
				} else {
					outcome0 = SScore;
					outcome1 = TScore;
				}
			} else {
				if (cooperate1) {
					outcome0 = TScore;
					outcome1 = SScore;
				} else {
					outcome0 = PScore;
					outcome1 = PScore;
				}
			}
			a.addToScore(outcome0);
			b.addToScore(outcome1);
		}
	}
	
	int outcomeToIndex(int outcome) {
		switch  (outcome) {
		case(RScore):
			return 0;
		case(SScore):
			return 1;
		case(TScore):
			return 2;
		case(PScore):
			return 3;
		}
		return 4;
	}
}
