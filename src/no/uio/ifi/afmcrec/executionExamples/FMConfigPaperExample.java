package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigPaperExample {

	public static void main(String[] args) {
		String input = "./data/PAPER_TESTMODEL/dataset.txt";
		System.out.println("Example from thesis paper: "+input);
		FMReconfigurer engine = new FMReconfigurer(input);
		
		//engine.applyHillClimbing();				
		//engine.applySimulatedAnnealing();
		engine.applyGeneticAlgorithm();
		
		engine.setHillClimbMaxPlateauIterations(3);
		engine.setHillClimbNumberOfExecutions(1);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setSimAnnealMaxIterations(20);
		engine.setGeneticAlgCrossoverBreakPoints(2);
		engine.setGeneticAlgInitialPopulationSize(8);
		engine.setGeneticAlgMutationProbability(0.02);
		engine.executeReconfig();

	}

}
