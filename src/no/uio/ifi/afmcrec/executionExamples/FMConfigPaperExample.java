package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigPaperExample {

	public static void main(String[] args) {
		String input = "./data/PAPER_TESTMODEL/dataset.txt";
		System.out.println("Example from thesis paper: "+input);
		FMReconfigurer engine = new FMReconfigurer(input);
		
		engine.applyHillClimbing();				
		engine.applySimulatedAnnealing();
		engine.applyGeneticAlgorithm();
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(1);
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999);
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(128);
		engine.setGeneticAlgMutationProbability(0.005);
		engine.executeReconfig();

	}

}
