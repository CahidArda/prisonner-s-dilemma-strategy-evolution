package Executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import Agent.Generation;

public class Main {
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		int nofGenerations = 1000;
		int nofAgents = 100;
		int nofBatches = 10;
		int nofGames = 10;
		double ratioToRemoveEachRound = 0.2;
		int printEach = 1;
		
		//print best performing agent + worst performing + average behaviour in csv format
				String generationBPAWPAAAFileName = "output/generationBPAWPAAAOutput.csv";
				File generationBPAWPAAAFile = new File(generationBPAWPAAAFileName);
				PrintStream generationBPAWPAAAOutput = new PrintStream(generationBPAWPAAAFile);
				//BRA:best performing agent, WPA:worst performing agent, AA: agent average
				generationBPAWPAAAOutput.println("Generation, BPA-R, BPA-S, BPA-T, BPA-P, SD-R, SD-S, SD-T, SD-P, AA-R, AA-S, AA-T, AA-P, ABPA-R, ABPA-S, ABPA-T, ABPA-P");
		
		Generation g = new Generation(nofAgents, nofBatches, nofGames, ratioToRemoveEachRound);
		for (int i=0; i<nofGenerations;i++) {
			g.runGeneration();
			
			if (i%printEach==0 || i+1==nofGenerations) {
				g.printGenerationBPAWPAAA(generationBPAWPAAAOutput);
				System.out.println(i);
			}
			
		}
	}
}
