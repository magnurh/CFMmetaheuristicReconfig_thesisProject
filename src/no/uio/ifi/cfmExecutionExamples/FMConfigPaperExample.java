package no.uio.ifi.cfmExecutionExamples;

import no.uio.ifi.cfmReconfigurationEngine.FMReconfigurer;

public class FMConfigPaperExample {

	public static void main(String[] args) {
		String input = "./data/PAPER_TESTMODEL/dataset.txt";
		System.out.println("Example from thesis paper: "+input);
		FMReconfigurer engine = new FMReconfigurer(input);
		
		engine.applyHillClimbing();				
		engine.applySimulatedAnnealing();
		engine.applyGeneticAlgorithm();
		
		engine.setHillClimbMaxPlateauIterations(8);
		engine.setHillClimbNumberOfExecutions(1);
		engine.setSimAnnealInitialTemperature(200.0);
		engine.setSimAnnealMaxIterations(1000);
		engine.setGeneticAlgCrossoverBreakPoints(2);
		engine.setGeneticAlgInitialPopulationSize(64);
		engine.setGeneticAlgMutationProbability(0.02);
		engine.setGeneticAlgRandomSelectionProbability(0.05);
		engine.executeReconfig();

	}

}