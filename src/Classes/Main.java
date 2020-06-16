package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;

public class Main {
	
	public static void runGeneration(Generation g, int nofGenerations, int printEvery, PrintStream generationBPAWPAAAOutput) throws FileNotFoundException {
		for (int i=0; i<nofGenerations; i++) {
			g.runGeneration();
			
			if (i%printEvery==0 || i+1==nofGenerations) {
				g.printGenerationBPAWPAAA(generationBPAWPAAAOutput);
				System.out.println(i);
			}
			
			if (i+1!=nofGenerations) {
				g.updateToNextGeneration();
			}
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//print best performing agent + worst performing + average behaviour in csv format
		String generationBPAWPAAAFileName = "output/generationBPAWPAAAOutput.csv";
		File generationBPAWPAAAFile = new File(generationBPAWPAAAFileName);
		PrintStream generationBPAWPAAAOutput = new PrintStream(generationBPAWPAAAFile);
		//BRA:best performing agent, WPA:worst performing agent, AA: agent average
		generationBPAWPAAAOutput.println("Generation, BPA-R, BPA-S, BPA-T, BPA-P, WPA-R, WPA-S, WPA-T, WPA-P, AA-R, AA-S, AA-T, AA-P, TBPA-R, TBPA-S, TBPA-T, TBPA-P");
		
		
		int testMode = 2;
		
		if (testMode==0) {
			Generation myG = new Generation(100, 20, 0.15); //nofAgents, nofGamesEachRound, ratioToRemove
			myG.runGeneration();
			myG.printGenerationBPAWPAAA(generationBPAWPAAAOutput);
			myG.printGeneration("output/");
			
			myG.updateToNextGeneration();
			myG.runGeneration();
			
			myG.printGenerationBPAWPAAA(generationBPAWPAAAOutput);
			myG.printGeneration("output/");
			
		} else if (testMode==1) {
			//parameters
			int nofAgents = 100;
			int nofGamesEachRound = 20;
			double ratioToRemove = 0.15;
			
			int nofGenerations = 1000;
			int printEvery = 10;
			
			
			Generation myG = new Generation(nofAgents, nofGamesEachRound, ratioToRemove); //nofAgents, nofGamesEachRound, ratioToRemove
			
			for (int i=0; i<nofGenerations; i++) {
				myG.runGeneration();
				
				if (i%printEvery==0 || i+1==nofGenerations) {
					myG.printGeneration("output/");
					System.out.println(i);
				}
				
				if (i+1!=nofGenerations) {
					myG.updateToNextGeneration();
				}
			}
			myG.printGenerationBPAWPAAA(generationBPAWPAAAOutput);
			
		} else if (testMode==2) {
			Generation myG = new Generation(100, 20, 0.15); //nofAgents, nofGamesEachRound, ratioToRemove			
			runGeneration(myG, 50000, 25, generationBPAWPAAAOutput);
			myG.printGeneration("output/");
		}
	}
}
