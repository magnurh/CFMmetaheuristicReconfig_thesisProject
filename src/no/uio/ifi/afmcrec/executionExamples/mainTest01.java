package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class mainTest01 {

	public static void main(String[] args) {
		
				
//		String[] inputs = {
//			"./out/data/0419_TestSet01/170419-155930/dataset.txt",
//			"./out/data/0419_TestSet01/170419-160153/dataset.txt",
//			"./out/data/0419_TestSet01/170419-160228/dataset.txt",
//			"./out/data/0419_TestSet01/170419-160336/dataset.txt",
//			"./out/data/0419_TestSet01/170419-160402/dataset.txt",
//			"./out/data/0419_TestSet01/170419-160434/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192334/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192558/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192628/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192652/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192729/dataset.txt",
//			"./out/data/0419_TestSet02/170419-192748/dataset.txt"
//		};


//		String[] inputs = {
//			"./out/data/0420_TestSet03/170420-092421/dataset.txt",
//			"./out/data/0420_TestSet03/170420-092427/dataset.txt",
//			"./out/data/0420_TestSet03/170420-092432/dataset.txt",
//			"./out/data/0420_TestSet03/170420-092553/dataset.txt",
//			"./out/data/0420_TestSet03/170420-092601/dataset.txt",
//			"./out/data/0420_TestSet03/170420-092606/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094155/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094231/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094238/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094252/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094302/dataset.txt",
//			"./out/data/0420_TestSet04/170420-094325/dataset.txt"
//		};


				
//		String[] inputs = {
//			"./out/data/0420_TestSet05/170430-165928/dataset.txt",
//			"./out/data/0420_TestSet05/170430-170432/dataset.txt"
//		};

//		String[] inputs = {"./out/data/0420_TestSet05/170430-170432/dataset.txt"};
		
		String[] inputs = {
				"./out/data/0420_TestSet05/170430-170432/dataset.txt",
				"./out/data/0420_TestSet05/170430-170446/dataset.txt",
				"./out/data/0420_TestSet06/170430-173012/dataset.txt"};
		
//		String[] inputs = {
//			"./out/data/0420_TestSet05/170430-170343/dataset.txt",
//			"./out/data/0420_TestSet05/170430-170425/dataset.txt",
//			"./out/data/0420_TestSet05/170430-170446/dataset.txt"
//		};

		
//		String[] inputs = {
//			"./out/data/0420_TestSet06/170430-170927/dataset.txt",
//			"./out/data/0420_TestSet06/170430-171842/dataset.txt",
//			"./out/data/0420_TestSet06/170430-173012/dataset.txt"
//		};
		
//		String[] inputs = {
//			"./out/data/0420_TestSet06/170430-172023/dataset.txt",
//			"./out/data/0420_TestSet06/170430-172338/dataset.txt",
//			"./out/data/0420_TestSet06/170430-172613/dataset.txt",
//		};

	
		for (int i = 0; i < inputs.length; i++){
			System.out.println("----- "+i);
			FMReconfigurer engine = new FMReconfigurer(inputs[i]);
			//engine.applyHillClimbing();
			engine.applySimulatedAnnealing();
			//engine.applyGeneticAlgorithm();
			
			engine.setHillClimbMaxPlateauIterations(10);
			engine.setHillClimbNumberOfExecutions(40);
			
			engine.setSimAnnealMaxIterations(1000);
			engine.setSimAnnealInitialTemperature(500);
			engine.setSimAnnealNumberOfExecutions(40);
			
//			engine.setGeneticAlgInitialPopulationSize(128);
//			engine.setGeneticAlgInitialPopulationSize(192);
			engine.setGeneticAlgInitialPopulationSize(256);
			engine.setGeneticAlgCrossoverBreakPoints(1);
			engine.setGeneticAlgMutationProbability(0.03);
			engine.setGeneticAlgRandomSelectionProbability(0.08);
			
			engine.executeReconfig(50);
			
			
			
			engine.setHillClimbMaxPlateauIterations(10);
			engine.setHillClimbNumberOfExecutions(60);
			
			engine.setSimAnnealMaxIterations(1000);
			engine.setSimAnnealInitialTemperature(600);
			engine.setSimAnnealNumberOfExecutions(40);
			
			engine.setGeneticAlgInitialPopulationSize(256);
			engine.setGeneticAlgCrossoverBreakPoints(4);
			engine.setGeneticAlgMutationProbability(0.03);
			engine.setGeneticAlgRandomSelectionProbability(0.08);
			
			engine.executeReconfig(50);
			
			
			
			engine.setHillClimbMaxPlateauIterations(20);
			engine.setHillClimbNumberOfExecutions(40);
			
			engine.setSimAnnealMaxIterations(1000);
			engine.setSimAnnealInitialTemperature(800);
			engine.setSimAnnealNumberOfExecutions(40);
			
			engine.setGeneticAlgInitialPopulationSize(256);
			engine.setGeneticAlgCrossoverBreakPoints(1);
			engine.setGeneticAlgMutationProbability(0.03);
			engine.setGeneticAlgRandomSelectionProbability(0.06);
			
			engine.executeReconfig(50);
			
		}

	}

}
