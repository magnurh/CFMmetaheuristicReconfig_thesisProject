package no.uio.ifi.afmcrec.executionExamples;

import no.uio.ifi.afmcrec.reconfigAnalysis.FMReconfigurer;

public class FMConfigTuningSA {
	
	public static void main(String[] args){
/*		String[] inputs = {
			"./out/data/0426_NEWtunigMetaheuristics/170426-175629/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175636/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175640/dataset.txt"
			};*/
	
/*		String[] inputs = {
			"./out/data/0426_NEWtunigMetaheuristics/170426-175808/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175820/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175836/dataset.txt"
			};*/
	
		String[] inputs = {
			"./out/data/0426_NEWtunigMetaheuristics/170426-175911/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175924/dataset.txt",
			"./out/data/0426_NEWtunigMetaheuristics/170426-175942/dataset.txt"
		};
		
		for (int i = 0; i < inputs.length; i++){
			System.out.println("----- "+i);
			FMReconfigurer engine = new FMReconfigurer(inputs[i]);
			engine.applySimulatedAnnealing();
		
			engine.setSimAnnealMaxIterations(100000);
			engine.setSimAnnealNumberOfExecutions(1);
			engine.setSimAnnealInitialTemperature(1.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(2.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(3.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(4.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(5.0);
			engine.executeReconfig(100);
			engine.setSimAnnealMaxIterations(10000);
			engine.setSimAnnealNumberOfExecutions(10);
			engine.setSimAnnealInitialTemperature(1.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(2.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(3.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(4.0);
			engine.executeReconfig(100);
			engine.setSimAnnealInitialTemperature(5.0);
			engine.executeReconfig(100);
			
		}
		
		
	}

}
