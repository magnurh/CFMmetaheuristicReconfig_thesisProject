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
				"./out/data/0420_TestSet03/170420-092601/dataset.txt",
				"./out/data/0420_TestSet03/170420-092606/dataset.txt"
				};
	
		for (int i = 0; i < inputs.length; i++){
			System.out.println("----- "+i);
			FMReconfigurer engine = new FMReconfigurer(inputs[i]);
			engine.applyHillClimbing();
			engine.applySimulatedAnnealing();
			engine.applyGeneticAlgorithm();
			
	
			engine.setHillClimbMaxPlateauIterations(20);
			engine.setHillClimbNumberOfExecutions(20);
			
			engine.setSimAnnealMaxIterations(9999*10);
			engine.setSimAnnealInitialTemperature(2.0);
			engine.setSimAnnealNumberOfExecutions(1);
			
			engine.setGeneticAlgInitialPopulationSize(128);
			engine.setGeneticAlgCrossoverBreakPoints(4);
			engine.setGeneticAlgMutationProbability(0.08);
			
			for(int j = 0; j < 1; j++){
				engine.executeReconfig(500);
			}
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