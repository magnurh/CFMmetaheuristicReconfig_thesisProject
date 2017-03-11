/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigAllApproaches {
	
	public static void main(String[] args){
		
		//String input = "./out/data/0308_TestingRulestructure/170308-125122/dataset.txt";
		String[] inputs = {
				"./out/data/0308_TestingGenAlg/170310-103306/dataset.txt"};
	
		for (int i = 0; i < inputs.length; i++){
			System.out.println("----- "+i);
			FMReconfigurer engine = new FMReconfigurer(inputs[i]);
			engine.applyHillClimbing();
			engine.applySimulatedAnnealing();
			engine.applyGeneticAlgorithm();
			
	
			engine.setHillClimbMaxPlateauIterations(10);
			engine.setHillClimbNumberOfExecutions(6);
			
			engine.setSimAnnealMaxIterations(9999*10);
			engine.setSimAnnealInitialTemperature(2.0);
			
			engine.setGeneticAlgInitialPopulationSize(256);
			engine.setGeneticAlgCrossoverBreakPoints(4);
			engine.setGeneticAlgMutationProbability(0.04);
			
			engine.executeReconfig();
		}
		
		
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