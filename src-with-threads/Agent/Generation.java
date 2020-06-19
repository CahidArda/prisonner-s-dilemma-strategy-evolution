package Agent;

import java.io.PrintStream;
import java.util.Random;

import EvolutionMechanism.Arena;
import EvolutionMechanism.Batch;

public class Generation {
	private int nofAgents;
	private int nofBatches;
	private int nofGames;
	private double ratioToRemoveEachRound;
	private Random random;
	
	int generationId;
	long totalScore = 0;
	
	Agent[][] agents;
	Arena arena;
	
	public Generation(int nofAgents, int nofBatches, int nofGames, double ratioToRemoveEachRound) {
		this.nofAgents = nofAgents;
		this.nofBatches = nofBatches;
		this.nofGames = nofGames;
		this.ratioToRemoveEachRound = ratioToRemoveEachRound;
		
		random = new Random();
		agents = new Agent[nofBatches][nofAgents/nofBatches];
		arena = new Arena(nofGames);

		for (int i=0; i<nofAgents; i++) {
			addAgentToGeneration();
		}
	}
	
	private void prepareArenaBeforeRunningGeneration() {
		for (int i=0; i<nofBatches; i++) {
			for (int j=i+1; j<nofBatches; j++) {
				Thread t = new Thread(new Batch(agents[i], agents[j], nofGames));
				t.setName("indexes " + i + ", " + j);
				arena.addToQueue(t);
			}
			Thread t = new Thread(new Batch(agents[i], nofGames));
			t.setName("index " + i);
			arena.addToQueue(t);
		}
	}
	
	private void calculateScores() throws InterruptedException {
		arena.runArena();
	}
	
	final double mutationChance = 0.2;
	private void updateGenerationAfterRunning() {
		generationId++;
		//remove worst performing agents
		for (int i=0; i<nofAgents*ratioToRemoveEachRound; i++) {
			removeWorstAgent();
		}
		
		for (int i=0; i<nofAgents*ratioToRemoveEachRound; i++) {
			addAgentToGeneration(new Agent(getBestAgent(i+1), getBestAgent(i+2)));
		}
				
		//set old agents' score to 0
		//TODO mutate existing agents
		for (Agent[] aa: agents) {
			for (Agent a: aa) {
				totalScore += a.getScore();
				a.setScore(0);
				if (random.nextDouble()<mutationChance) {
					a.mutate();
				}
			}
		}
	}
	
	public void runGeneration() throws InterruptedException {
		//System.out.println("running generation: " + generationId);
		prepareArenaBeforeRunningGeneration();
		calculateScores();
		updateGenerationAfterRunning();
	}
	
	private Agent getBestAgent(int rank) {
		Agent agentWithHighestScore=null;
		int highestScoreBatchIndex = 0, highestScoreIndex = 0;
		
		for (int batchIndex = 0; batchIndex<nofBatches; batchIndex++) {
			for (int index = 0; index<agents[0].length; index++) {
				if (agents[batchIndex][index]!=null && (agentWithHighestScore==null || agentWithHighestScore.getScore()<agents[batchIndex][index].getScore())) {
					agentWithHighestScore = agents[batchIndex][index];
					highestScoreBatchIndex = batchIndex;
					highestScoreIndex = index;
				}
			}
		}
		if (rank==1) {
			return agentWithHighestScore;
		} else {
			agents[highestScoreBatchIndex][highestScoreIndex] = null;
			Agent a = getBestAgent(rank-1);
			agents[highestScoreBatchIndex][highestScoreIndex] = agentWithHighestScore;
			return a;
		}
	}
	
	private Agent getWorstAgent(int rank) {
		Agent agentWithLowestScore=null;
		int lowestScoreBatchIndex = 0, lowestScoreIndex = 0;
		
		for (int batchIndex = 0; batchIndex<nofBatches; batchIndex++) {
			for (int index = 0; index<agents[0].length; index++) {
				if (agents[batchIndex][index]!=null && (agentWithLowestScore==null || agentWithLowestScore.getScore()>agents[batchIndex][index].getScore())) {
					agentWithLowestScore = agents[batchIndex][index];
					lowestScoreBatchIndex = batchIndex;
					lowestScoreIndex = index;
				}
			}
		}
		if (rank==1) {
			return agentWithLowestScore;
		} else {
			agents[lowestScoreBatchIndex][lowestScoreIndex] = null;
			Agent a = getWorstAgent(rank-1);
			agents[lowestScoreBatchIndex][lowestScoreIndex] = agentWithLowestScore;
			return a;
		}
	}
	
	private void addAgentToGeneration() {
		for (int batchIndex=0; batchIndex<nofBatches; batchIndex++) {
			for (int index=0; index<agents[0].length; index++) {
				if (agents[batchIndex][index]==null) {
					agents[batchIndex][index] = new Agent();
					return;
				}
			}
		}
	}
	
	private void addAgentToGeneration(Agent a) {
		for (int batchIndex=0; batchIndex<nofBatches; batchIndex++) {
			for (int index=0; index<agents[0].length; index++) {
				if (agents[batchIndex][index]==null) {
					agents[batchIndex][index] = a;
					return;
				}
			}
		}
	}
	
	private void removeWorstAgent() {
		Agent agentWithLowestScore=null;
		int lowestScoreBatchIndex = 0, lowestScoreIndex = 0;
		
		for (int batchIndex = 0; batchIndex<nofBatches; batchIndex++) {
			for (int index = 0; index<agents[0].length; index++) {
				if (agents[batchIndex][index]!=null && (agentWithLowestScore==null || agentWithLowestScore.getScore()>agents[batchIndex][index].getScore())) {
					agentWithLowestScore = agents[batchIndex][index];
					lowestScoreBatchIndex = batchIndex;
					lowestScoreIndex = index;
				}
			}
		}
		agents[lowestScoreBatchIndex][lowestScoreIndex] = null;
	}
	
	public void printGenerationBPAWPAAA(PrintStream generationDetailsOutput) {
		Agent bestAgent = getBestAgent(1);
		
		double[] averageBehavior = new double[4];
		
		for (Agent[] aa: agents) {
			for (Agent a: aa) {
				for (int i=0; i<4; i++) {
					averageBehavior[i] += a.getBehavior()[i];
				}
			}
		}
		averageBehavior[0] /= nofAgents;
		averageBehavior[1] /= nofAgents;
		averageBehavior[2] /= nofAgents;
		averageBehavior[3] /= nofAgents;
		
		double[] diviationSum = new double[4];
		for (Agent[] aa: agents) {
			for (Agent a: aa) {
				for (int i=0; i<4; i++) {
					diviationSum[i] += (averageBehavior[i]-a.getBehavior()[i])*(averageBehavior[i]-a.getBehavior()[i]);
				}
			}
		}
		
		final int nofAgentsIncludedInAverage = (int) (nofAgents*0.1);
		double[] averageOfBestAgents = new double[4];
		for (int i=0; i<nofAgentsIncludedInAverage; i++) {
			for (int j=0; j<4; j++) {
				averageOfBestAgents[j] += getBestAgent(i+1).getBehavior()[j];
			}
		}
		
		generationDetailsOutput.println(String.format(
				"%d,%s,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%d",
				generationId,
				bestAgent.getBehaviorForCsv(),
				Math.pow(diviationSum[0]/(nofAgents-1), 0.5),
				Math.pow(diviationSum[1]/(nofAgents-1), 0.5),
				Math.pow(diviationSum[2]/(nofAgents-1), 0.5),
				Math.pow(diviationSum[3]/(nofAgents-1), 0.5),
				averageBehavior[0],
				averageBehavior[1],
				averageBehavior[2],
				averageBehavior[3],
				averageOfBestAgents[0]/nofAgentsIncludedInAverage,
				averageOfBestAgents[1]/nofAgentsIncludedInAverage,
				averageOfBestAgents[2]/nofAgentsIncludedInAverage,
				averageOfBestAgents[3]/nofAgentsIncludedInAverage,
				totalScore
			));
		totalScore = 0;
	}
	
	public void printAgentsToConsole() {
		for (Agent[] aa: agents) {
			for (Agent a: aa) {
				System.out.println(a);
			}
		}
	}
	
}
