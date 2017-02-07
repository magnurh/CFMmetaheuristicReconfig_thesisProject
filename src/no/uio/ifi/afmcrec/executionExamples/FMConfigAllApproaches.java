/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigAllApproaches {
	
	public static void main(String[] args){
		
		String input = "./out/data/170112-233525_Stage1/dataset.txt";
		
		FMReconfigurer engine = new FMReconfigurer(input);
		engine.applyHillClimbing();
		//engine.applySimulatedAnnealing();
		//engine.applyGeneticAlgorithm();
		

		engine.setHillClimbMaxPlateauIterations(10);
		engine.setHillClimbNumberOfExecutions(6);
		engine.executeReconfig();
		
/*		engine.setSimAnnealMaxIterations(9999*10);
		engine.setSimAnnealInitialTemperature(2.0);
		
		engine.setGeneticAlgInitialPopulationSize(128);
		engine.setGeneticAlgCrossoverBreakPoints(4);
		engine.setGeneticAlgMutationProbability(0.005);*/
		
		
		
		//engine.setHillClimbMaxPlateauIterations(20);
		//engine.setHillClimbNumberOfExecutions(24);
		//engine.setSimAnnealMaxIterations(9999*10);
		
/*		engine.setSimAnnealInitialTemperature(10.0);
		engine.executeReconfig(input);
		
		engine.setSimAnnealInitialTemperature(4.0);
		engine.executeReconfig(input);
		
		engine.setSimAnnealInitialTemperature(2.0);
		engine.executeReconfig(input);
		*/

/*		System.out.println("Running first set: plateau: 10");
		engine.setHillClimbMaxPlateauIterations(10);
		engine.executeReconfig(input);
		
		engine.setHillClimbMaxPlateauIterations(0);
		engine.setHillClimbNumberOfExecutions(5);
		System.out.println("Running first set: re-tries: 5");
		engine.executeReconfig(input);
		
		engine.setHillClimbMaxPlateauIterations(10);
		System.out.println("Running first set: plateau: 10, retries: 5");
		engine.executeReconfig(input);*/
		
	}
}