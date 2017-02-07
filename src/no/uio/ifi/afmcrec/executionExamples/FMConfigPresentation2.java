/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigPresentation2 {
	
	public static void main(String[] args){
		
		String input = "./out/data/170112-233525_Stage1/dataset.txt";
		System.out.println("pres1_set1: "+input);
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
		//engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		//engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(6);
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999*10);
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(256);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
		input = "./out/data/170112-233247_Stage1/dataset.txt";
		System.out.println("pres1_set2: "+input);
		engine = new FMReconfigurer(input);
		
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
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(6);
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999*10);
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(256);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
		input = "./out/data/170205-200838_Stage1/dataset.txt";
		System.out.println("set3 same parameters as pres1_set1: "+input);
		engine = new FMReconfigurer(input);

		engine.applyHillClimbing();				
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(1);
		engine.applySimulatedAnnealing();
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999);
		engine.applyGeneticAlgorithm();
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(128);
		engine.setGeneticAlgMutationProbability(0.005);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(6);
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999*10);
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(256);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();

		input = "./out/data/170205-205528_Stage1/dataset.txt";
		System.out.println("set4 100 Large models (no way of knowing if they are void): "+input);
		engine = new FMReconfigurer(input);
		engine.applyHillClimbing();				
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(1);
		engine.applySimulatedAnnealing();
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999);
		engine.applyGeneticAlgorithm();
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(128);
		engine.setGeneticAlgMutationProbability(0.005);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(6);
		engine.setSimAnnealInitialTemperature(4.0);
		engine.setSimAnnealMaxIterations(9999*10);
		engine.setGeneticAlgCrossoverBreakPoints(1);
		engine.setGeneticAlgInitialPopulationSize(256);
		engine.executeReconfig();
		
		engine.setHillClimbMaxPlateauIterations(10);
		engine.setSimAnnealInitialTemperature(2.0);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.executeReconfig();
		
	}
	
}