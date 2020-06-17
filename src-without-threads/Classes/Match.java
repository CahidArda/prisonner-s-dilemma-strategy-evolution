package Classes;

import java.util.Arrays;

public class Match {
	enum Result {
		R,	//both cooperate
		S,	//betrayed agent in betrayal
		T,	//betraying agent in betrayal
		P;	//both defect
		
		static Result[] toArray(Result a, Result b) {
			Result[] r = new Result[2];
			r[0] = a;
			r[1] = b;
			return r;
		}
	}
	
	final double RScore = 3;
	final double SScore = 0;
	final double TScore = 5;
	final double PScore = 1;
	
	private Agent[] agents;
	private double[] agentScores;
	private int nofGames;
	
	Match(Agent agent1, Agent agent2, int nofGames) {
		agents = new Agent[2];
		agentScores = new double[2];
		
		this.agents[0] = agent1;
		this.agents[1] = agent2;
		this.nofGames = nofGames;
		playRound(Result.toArray(Result.R, Result.R));
		playRound(Result.toArray(Result.T, Result.S));
		playRound(Result.toArray(Result.S, Result.T));
		playRound(Result.toArray(Result.P, Result.P));
	}
	
	void playRound(Result[] results) {
		for (int i=0; i<nofGames; i++) {
			results = getResults(agentsWillCooperate(results));
			
			for (int j=0; j<2; j++) {
				if (results[j]==Result.R) {
					agentScores[j] += RScore;
				} else if (results[j]==Result.S) {
					agentScores[j] += SScore;
				} else if (results[j]==Result.T) {
					agentScores[j] += TScore;
				} else if (results[j]==Result.P) {
					agentScores[j] += PScore;
				}
			}
		}
	}
	
	Result[] getResults(boolean[] cooperationOfAgents) {
		Result[] results = new Result[2]; 
		if (cooperationOfAgents[0] && cooperationOfAgents[1]) {
			results[0] = Result.R;
			results[1] = Result.R;
			
		} else if (!cooperationOfAgents[0] && cooperationOfAgents[1]) {
			results[0] = Result.T;
			results[1] = Result.S;
		} else if (cooperationOfAgents[0] && !cooperationOfAgents[1]) {
			results[0] = Result.S;
			results[1] = Result.T;
		} else {
			results[0] = Result.P;
			results[1] = Result.P;
		}
		
		return results;
	}
	
	boolean[] agentsWillCooperate(Result[] previousResult) {
		boolean[] willCooperate = new boolean[2];
		for (int i=0; i<2; i++) {
			Agent agent = agents[i];
			switch (previousResult[i]) {
			case R:
				willCooperate[i] = agent.decide(0);
				break;
			case S:
				willCooperate[i] = agent.decide(1);
				break;
			case T:
				willCooperate[i] = agent.decide(2);
				break;
			case P:
				willCooperate[i] = agent.decide(3);
				break;
			}
		}
		return willCooperate;
	}
	
	double[] getScores() {
		return agentScores;
	}
}
