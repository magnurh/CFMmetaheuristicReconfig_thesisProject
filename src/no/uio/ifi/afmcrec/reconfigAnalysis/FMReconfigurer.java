/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.reconfigAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import no.uio.ifi.afmcrec.datasetGeneration.AFMDatasetGenerator;

public class FMReconfigurer{
	
	private String input;
	
	private boolean useHillClimbing = false;
	private boolean useSimulatedAnnealing = false;
	private boolean useGeneticAlgorithm = false;
	private String[] approaches;
	
	// Hill-climbing spec
	private int hillClimbNumberOfExecutions = 1;
	private int hillClimbPlateauIterations = 0;
	
	// Sim-annealing spec
	private int simAnnealMaxIterations = 100;
	private double simAnnealInitialTemp = 5.0;
	
	// Genetic Alg spec
	private int geneticAlgInitPopSize = 12;
	private int geneticAlgCrossoverBreakPoints = 2;
	private double geneticAlgMutationProbability = 0.02;		// Try setting as 1/candidatelength as default
		
	
//	long startTime = 0;
//	long endTime = 0;
//	long totalSolvingTime = 0;
	
	private int progressPrintInterval = 0;
	
	private ArrayList<String> modelNames = new ArrayList<String>();
	HashMap<String, Integer> inputParameters = new HashMap<String, Integer>();
	
	TreeMap<String, Metaheuristic> results = new TreeMap<String, Metaheuristic>();
	private ArrayList<String> hyvarrecResults = new ArrayList<String>();
	private boolean[] isVoidIndex;
	private int voidModels = 0;
	
	public FMReconfigurer(String input){
		this.input = input;
	}
	
/*	public static void main(String[] args){
		

		System.out.println("Running first set: simple");
		executeReconfig(input, 0, 0);

		System.out.println("Running first set: plateau: 10");
		executeReconfig(input, 10, 0);
		
		System.out.println("Running first set: re-tries: 5");
		executeReconfig(input, 0, 5);
		
		System.out.println("Running first set: plateau: 10, retries: 5");
		executeReconfig(input, 10, 5);

		input = "./out/data/170112-233247_Stage1/dataset.txt";
		System.out.println("Running second set: simple");
		executeReconfig(input, 0, 0);

		System.out.println("Running second set: plateau: 10");
		executeReconfig(input, 10, 0);
		
		System.out.println("Running second set: re-tries: 5");
		executeReconfig(input, 0, 5);
		
		System.out.println("Running second set: plateau: 10, retries: 5");
		executeReconfig(input, 10, 5);

	}*/
	
	public void executeReconfig2(String input){
		String timestamp = AFMDatasetGenerator.timeStamp();
		String output = "./out/analyses/"+timestamp+"_analysis.txt";
		
		try{
			BufferedReader file = new BufferedReader(new FileReader(input));
			String logPath = file.readLine();
			String dir = file.readLine();
			
			String line = null;
			while((line = file.readLine()) != null){
			
				if(useHillClimbing){
					
				}
				if(useSimulatedAnnealing){
					System.err.println("Warning: Simulated annealing is not yet implemented");
				}
				if(useGeneticAlgorithm){
					System.err.println("Warning: Genetic algorithm is not yet implemented");
				}
			
			}
		}catch(IOException e){
			System.err.println(e.getMessage());
		}
	}

	
	private void executeMetaheuristics(FMwrapper fm, Metaheuristic solver){
		//TODO: Consider making list of candidates as shared starting point for all approaches
		
		if(useHillClimbing){

			solver.setAllowedPlateauIterations(hillClimbPlateauIterations);
			boolean foundGlobalOptimal = false;
			//int best = Integer.MAX_VALUE;
			//Metaheuristic bestTrial = null;
			int retries = 0;

			long startTime = System.nanoTime();
			while(retries < hillClimbNumberOfExecutions && !foundGlobalOptimal){
				int[] cand = fm.generateCandidate();					
				int score = solver.hillClimbing(cand);
				if (score == 0) {
					foundGlobalOptimal = true;
				}
				retries++;
			}
			//System.out.println("Final Score: "+solver.getHillClimbResultScore());
			long endTime = System.nanoTime();
			solver.setHillClimbSolvingTime(endTime-startTime);
			//fm.eval.printStoredEvaluations();		//	
		}
		if(useSimulatedAnnealing){
			long startTime = System.nanoTime();
			int[] cand = fm.generateCandidate();
			int score = solver.simulatedAnnealing(cand, simAnnealMaxIterations, simAnnealInitialTemp);
			long endTime = System.nanoTime();
			solver.setSimAnnealSolvingTime(endTime-startTime);
			//System.err.println("Warning: Simulated annealing is not yet implemented");
		}
		if(useGeneticAlgorithm){
			long startTime = System.nanoTime();
			int score = solver.geneticAlgorithm(geneticAlgInitPopSize, geneticAlgCrossoverBreakPoints, geneticAlgMutationProbability);
			long endTime = System.nanoTime();
			solver.setGeneticAlgSolvingTime(endTime-startTime);
			//System.err.println("Warning: Genetic algorithm is not yet implemented");
		}
	}
	
	public void executeReconfig(){	
		progressPrintInterval = 0;
		
		int dataSetSize = 0;
		int counter = 0;
		
		try{
			BufferedReader file = new BufferedReader(new FileReader(input));
			
			boolean readModel = false;
			String line = null;
			String logPath = file.readLine();
			String dir = file.readLine();
			while((line = file.readLine()) != null){
				if (readModel){
					String[] mData = line.split("\\s+");
					String modelName = mData[0];
					modelNames.add(modelName);
					//System.out.println(modelName);
					//String fmPath = dir+"/"+modelName;
					int noFeatures = Integer.parseInt(mData[1]);
					int noAttributes = Integer.parseInt(mData[2]);
					int size = Integer.parseInt(mData[3]);
					int contextSize = Integer.parseInt(mData[4]);
					
					FMwrapper fm = new FMwrapper(dir, modelName, noFeatures, noAttributes, size, contextSize);
					Metaheuristic solver = new Metaheuristic(fm);
					
					executeMetaheuristics(fm, solver);
					results.put(modelName, solver);
					
					printProgress(dataSetSize, ++counter);
					
				}else if(line.equals("Feature_models")) {
						readModel = true;
						file.readLine();					
				}else{
					String[] l = line.split(": ");
					if (l.length > 1){
						int value = Integer.parseInt(l[1]);
						inputParameters.put(l[0], value);
						if (l[0].equals("Size_dataSet")){
							dataSetSize = value;
							isVoidIndex = new boolean[dataSetSize];
						}
					}
				}
			}
			file.close();
		}catch (IOException e){
			System.err.println(e.getMessage());
		}
		
		readHyvarrecResults(input);
		writeResults(input, counter);
	}
	
	private void writeResults(String input, int counter){
		String timestamp = AFMDatasetGenerator.timeStamp();
		String output = "./out/analyses/"+timestamp+"_analysis.txt";
		
		counter -= voidModels;
		
		int hcScoreSum = 0;
		int hcIterSum = 0;
		int hcSuccesses = 0;
		int hcIterSumSuccesses = 0;
		long hcTotalTime = 0;
		
		int saScoreSum = 0;
		int saIterSum = 0;
		int saSuccesses = 0;
		int saIterSumSuccesses = 0;
		long saTotalTime = 0;
		
		int geScoreSum = 0;
		int geIterSum = 0;
		int geSuccesses = 0;
		int geIterSumSuccesses = 0;
		long geTotalTime = 0;
		
		int i = 0;
		for (Metaheuristic m : results.values()){
			if(i >= isVoidIndex.length || !isVoidIndex[i++]){
				int hcScore = m.getHillClimbResultScore();
				int hcIter = m.getHillClimbIterations();
				long hcTime = m.getHillClimbSolvingTime();
				hcScoreSum += hcScore;
				hcIterSum += hcIter;
				hcTotalTime += hcTime; 
				if (hcScore == 0) {
					hcSuccesses++;
					hcIterSumSuccesses += hcIter;
				}
				int saScore = m.getSimAnnealResultScore();
				int saIter = m.getSimAnnealIterations();
				long saTime = m.getSimAnnealSolvingTime();
				saScoreSum += saScore;
				saIterSum += saIter;
				saTotalTime += saTime; 
				if (saScore == 0) {
					saSuccesses++;
					saIterSumSuccesses += saIter;
				}
				int geScore = m.getGeneticAlgResultScore();
				int geIter = m.getGeneticAlgIterations();
				long geTime = m.getGeneticAlgSolvingTime();
				geScoreSum += geScore;
				geIterSum += geIter;
				geTotalTime += geTime; 
				if (geScore == 0) {
					geSuccesses++;
					geIterSumSuccesses += geIter;
				}
			}
		}
		
		System.out.println("Sum score: "+saScoreSum);
		System.out.println("Sum iterations: "+saIterSum);
		System.out.println("Successes: "+saSuccesses);
		System.out.println("Sum iterations successes: "+saIterSumSuccesses);
		System.out.println("Total count: "+counter);
		
		double hcSuccessRate = hcSuccesses*1.0/counter;
		double hcAvgDist = hcScoreSum*1.0/counter;
		double hcAvgIter = hcIterSum*1.0/counter;
		double hcAvgIterSucc = hcIterSumSuccesses*1.0/hcSuccesses;
		double hcAvgIterNonSucc = (hcIterSum - hcIterSumSuccesses) * 1.0 / (counter - hcSuccesses);
		String hcAvgSolvingTime = convertNanoToTimeFormat(hcTotalTime / counter);
		
		double saSuccessRate = saSuccesses*1.0/counter;
		double saAvgDist = saScoreSum*1.0/counter;
		double saAvgIter = saIterSum*1.0/counter;
		double saAvgIterSucc = saIterSumSuccesses*1.0/saSuccesses;
		double saAvgIterNonSucc = (saIterSum - saIterSumSuccesses) * 1.0 / (counter - saSuccesses);
		String saAvgSolvingTime = convertNanoToTimeFormat(saTotalTime / counter);
		
		double geSuccessRate = geSuccesses*1.0/counter;
		double geAvgDist = geScoreSum*1.0/counter;
		double geAvgIter = geIterSum*1.0/counter;
		double geAvgIterSucc = geIterSumSuccesses*1.0/geSuccesses;
		double geAvgIterNonSucc = (geIterSum - geIterSumSuccesses) * 1.0 / (counter - geSuccesses);
		String geAvgSolvingTime = convertNanoToTimeFormat(geTotalTime / counter);
		
		try {
			FileWriter an = new FileWriter(new File(output));
			an.write(output+"\n");
			an.write(input+"\n");
			an.write("Models: "+counter+" (void: "+voidModels+")\n");
			for (String par : inputParameters.keySet()){
				an.write(par+": "+inputParameters.get(par)+"\n");
			}
			an.write("\nHILL-CLIMBING\n");
			if(useHillClimbing){
				an.write("Allowed_plateau_iterations: "+hillClimbPlateauIterations+"\n");
				an.write("Number_of_random_retries: "+hillClimbNumberOfExecutions+"\n");
				an.write("SuccessRate: "+String.format("%.3f", hcSuccessRate)+"\n");
				an.write("Avg_distance_from_global_optimal: "+String.format("%.3f", +hcAvgDist)+"\n");
				an.write("Avg_Iterations: "+String.format("%.2f", hcAvgIter)+"\n");
				an.write("-Success_cases: "+String.format("%.2f", hcAvgIterSucc)+"\n");
				an.write("-Non-success_cases: "+String.format("%.2f", hcAvgIterNonSucc)+"\n");
				an.write("Avg_SolvingTime: "+hcAvgSolvingTime+"\n");
			}else{
				an.write("--Not used--\n");
			}
			
			an.write("\nSIMULATED ANNEALING\n");
			if(useSimulatedAnnealing){
				an.write("Max_iterations: "+simAnnealMaxIterations+"\n");
				an.write("Initial_temperature: "+simAnnealInitialTemp+"\n");
				an.write("SuccessRate: "+String.format("%.3f", saSuccessRate)+"\n");
				an.write("Avg_distance_from_global_optimal: "+String.format("%.3f", saAvgDist)+"\n");
				an.write("Avg_Iterations: "+String.format("%.2f", saAvgIter)+"\n");
				an.write("-Success_cases: "+String.format("%.2f", saAvgIterSucc)+"\n");
				an.write("-Non-success_cases: "+String.format("%.2f", saAvgIterNonSucc)+"\n");
				an.write("Avg_SolvingTime: "+saAvgSolvingTime+"\n");
			}else{
				an.write("--Not used--\n");
			}
			
			
			an.write("\nGENETIC ALGORITHM\n");
			if(useGeneticAlgorithm){
				an.write("Init_Pop_Size: "+geneticAlgInitPopSize+"\n");
				an.write("Crossover_points: "+geneticAlgCrossoverBreakPoints+"\n");
				an.write("Mutation_prob: "+geneticAlgMutationProbability+"\n");
				an.write("SuccessRate: "+String.format("%.3f", geSuccessRate)+"\n");
				an.write("Avg_distance_from_global_optimal: "+String.format("%.3f", geAvgDist)+"\n");
				an.write("Avg_Iterations: "+String.format("%.2f", geAvgIter)+"\n");
				an.write("-Success_cases: "+String.format("%.2f", geAvgIterSucc)+"\n");
				an.write("-Non-success_cases: "+String.format("%.2f", geAvgIterNonSucc)+"\n");
				an.write("Avg_SolvingTime: "+geAvgSolvingTime+"\n");
			}else{
				an.write("--Not used--\n");
			}
			
			an.write("\nMODEL\t\tAPPROACH\tTIME\tITERATIONS\tSCORE\tRESULT");
			int index = 0;
			int sizeAFM = inputParameters.get("Size_AFM");
			for(String modelname : results.keySet()){
				for (int j = 0; j < approaches.length; j++){
					Metaheuristic m = results.get(modelname);
					if(approaches[j].equals("HC")){
						String result = resultAsString(m.getHillClimbResultVector(), sizeAFM);
						String time = convertNanoToTimeFormat(m.getHillClimbSolvingTime());
						an.write("\n"+modelname+"\tHC\t"+time+"\t"+m.getHillClimbIterations()+"\t"+m.getHillClimbResultScore()+"\t"+result);
					}else if(approaches[j].equals("SA")){
						String result = resultAsString(m.getSimAnnealResultVector(), sizeAFM);
						String time = convertNanoToTimeFormat(m.getSimAnnealSolvingTime());
						an.write("\n"+modelname+"\tSA\t"+time+"\t"+m.getSimAnnealIterations()+"\t"+m.getSimAnnealResultScore()+"\t"+result);
					}else if(approaches[j].equals("GE")){
						String result = resultAsString(m.getGeneticAlgResultVector(), sizeAFM);
						String time = convertNanoToTimeFormat(m.getGeneticAlgSolvingTime());
						an.write("\n"+modelname+"\tGE\t"+time+"\t"+m.getGeneticAlgIterations()+"\t"+m.getGeneticAlgResultScore()+"\t"+result);
					}else if (index < hyvarrecResults.size()){
						int[] hyvarrecRes = transformHyvarrecResult(hyvarrecResults.get(index), m.FM.getCandidateLength());
						String result = resultAsString(hyvarrecRes, sizeAFM);
						an.write("\n"+modelname+"\t"+approaches[j]+"\t\t\t\t\t"+result);
					}
				}
				index++;
			}
			
			System.out.println(output);
			System.out.println("Number of void models: "+voidModels);
			
			an.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void executeReconfigOrig(String input){
		progressPrintInterval = 0;
		
		int dataSetSize = 0;
		int counter = 0;

		String timestamp = AFMDatasetFactory.timeStamp();
		String output = "./out/analyses/"+timestamp+"_analysis.txt";
		
		HashMap<String, Integer> inputParameters = new HashMap<String, Integer>();
		
		TreeMap<String, Metaheuristic> results = new TreeMap<String, Metaheuristic>();
		
		HashMap<String, Integer> hillClimbIterations = new HashMap<String, Integer>();
		HashMap<String, Integer> hillClimbScore = new HashMap<String, Integer>();
		HashMap<String, int[]> hillClimbResult = new HashMap<String, int[]>();
		
		HashMap<String, Integer> simAnnealingIterations = new HashMap<String, Integer>();
		HashMap<String, Integer> simAnnealingScore = new HashMap<String, Integer>();
		HashMap<String, int[]> simAnnealingResult = new HashMap<String, int[]>();
		
		try{
			BufferedReader file = new BufferedReader(new FileReader(input));
			
			boolean readModel = false;
			String line = null;
			String logPath = file.readLine();
			String dir = file.readLine();
			while((line = file.readLine()) != null){
				if (readModel){
					String[] mData = line.split("\\s+");
					String modelName = mData[0];
					modelNames.add(modelName);
					//System.out.println(modelName);
					String fmPath = dir+"/"+modelName;
					int noFeatures = Integer.parseInt(mData[1]);
					int noAttributes = Integer.parseInt(mData[2]);
					int size = Integer.parseInt(mData[3]);
					int contextSize = Integer.parseInt(mData[4]);
					FMwrapper fm = new FMwrapper(fmPath, noFeatures, noAttributes, size, contextSize);
					
					Metaheuristic solver = new Metaheuristic(fm);
					solver.setAllowedPlateauIterations(hillClimbPlateauIterations);
					
					int best = Integer.MAX_VALUE;
					Metaheuristic bestTrial = null;
					int retries = 0;
					long solvingTime = 0;
					
					while(retries <= maxNumberOfReTries && best != 0){
						startTime = System.nanoTime();				//
						int[] cand = fm.generateCandidate();					
						int score = solver.hillClimbing(cand);
						if (score < best) {
							best = score;
							bestTrial = solver;
						}
						retries++;
						endTime = System.nanoTime();				//
						//System.out.println("Solving time: "+(endTime-startTime)+" nano sec");	//
						totalSolvingTime += (endTime - startTime);
						System.out.println("Avg. solving time: "+convertNanoToTimeFormat(endTime-startTime));
						//System.out.println("Iterations:\t"+solver.getHillClimbIterations());
					}
					//fm.eval.printStoredEvaluations();		//
					results.put(modelName, bestTrial);
					printProgress(dataSetSize, ++counter);
					
				}else if(line.equals("Feature_models")) {
						readModel = true;
						file.readLine();					
				}else{
					String[] l = line.split(": ");
					if (l.length > 1){
						int value = Integer.parseInt(l[1]);
						inputParameters.put(l[0], value);
						if (l[0].equals("Size_dataSet")){
							dataSetSize = value;
						}
					}
				}
			}
			System.out.println("Avg solving time: "+convertNanoToTimeFormat(totalSolvingTime/counter));
			file.close();
		}catch (IOException e){
			System.err.println(e.getMessage());
		}
		
		int score_Sum = 0;
		int iter_Sum = 0;
		int successes = 0;
		int iter_Sum_Successes = 0;
		
		for (Metaheuristic m : results.values()){
			int score = m.getHillClimbResultScore();
			int iter = m.getHillClimbIterations();
			score_Sum += score;
			iter_Sum += iter;
			if (score == 0) {
				successes++;
				iter_Sum_Successes += iter;
			}
		}
		
		System.out.println("Sum score: "+score_Sum);
		System.out.println("Sum iterations: "+iter_Sum);
		System.out.println("Successes: "+successes);
		System.out.println("Sum iterations successes: "+iter_Sum_Successes);
		System.out.println("Total count: "+counter);
		
		double successRate = successes*1.0/counter;
		double avg_dist = score_Sum*1.0/counter;
		double avg_iter = iter_Sum*1.0/counter;
		double avg_iter_succ = iter_Sum_Successes*1.0/successes;
		double avg_iter_nonSucc = (iter_Sum - iter_Sum_Successes) * 1.0 / (counter - successes);
		String avg_solvingTime = convertNanoToTimeFormat(totalSolvingTime / counter);
		
		readHyvarrecResults();
		
		try {
			FileWriter an = new FileWriter(new File(output));
			an.write(output+"\n");
			an.write(input+"\n");
			for (String par : inputParameters.keySet()){
				an.write(par+": "+inputParameters.get(par)+"\n");
			}
			an.write("\nHILL-CLIMBING\n");
			an.write("Allowed_plateau_iterations: "+hillClimbPlateauIterations+"\n");
			an.write("Number_of_random_retries: "+maxNumberOfReTries+"\n");
			an.write("SuccessRate: "+successRate+"\n");
			an.write("Avg_distance_from_global_optimal: "+avg_dist+"\n");
			an.write("Avg_Iterations: "+avg_iter+"\n");
			an.write("-Success_cases: "+avg_iter_succ+"\n");
			an.write("-Non-success_cases: "+avg_iter_nonSucc+"\n");
			an.write("Avg_SolvingTime: "+avg_solvingTime+"\n");
			
			an.write("\nMODEL\tAPPROACH\tITERATIONS\tSCORE\tRESULT");
			int index = 0;
			int sizeAFM = inputParameters.get("size_AFM");
			for(String modelname : results.keySet()){
				for (int i = 0; i < approaches.length; i++){
					Metaheuristic m = results.get(modelname);
					if(approaches[i].equals("HC")){
						String result = resultAsString(m.getHillClimbResultVector(), sizeAFM);
						an.write("\n"+modelname+"\tHC\t"+m.getHillClimbIterations()+"\t"+m.getHillClimbResultScore()+"\t"+result);
					}else{
						if (index < hyvarrecResults.size()){
							int[] hyvarrecRes = transformHyvarrecResult(hyvarrecResults.get(index), m.FM.getCandidateLength());
							String result = resultAsString(hyvarrecRes, sizeAFM);
							an.write("\n"+modelname+"\t"+approaches[i]+"\t\t\t\t"+result);
						}
					}
				}
				index++;
			}
			
			System.out.println(output);
			System.out.println("Number of void models: "+voidModels);
			
			an.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/

	public static String resultAsString(int[] v, int sizeAFM){
		String res = "";
		String deliminator = "";
		for (int i = 0; i < v.length; i++){
			if (i >= sizeAFM) deliminator = "-";
			res += deliminator+v[i];
		}
		return res;
	}
	
	private static int[] transformHyvarrecResult(String hyvarrecRes, int length){
		int[] result = new int[length];
		JSONParser parser = new JSONParser();
		try {
			JSONObject JSONRes = (JSONObject) parser.parse(hyvarrecRes);
			if(JSONRes.get("result").equals("sat")){
				JSONArray featureArr = (JSONArray) JSONRes.get("features");
				Iterator features = featureArr.iterator();
				while(features.hasNext()){
					String f = (String) features.next();
					int i = ExpressionEvaluator.getInt(f);
					if(i != -1 && i < result.length) result[i] = 1;
				}
				JSONArray attributeArr = (JSONArray) JSONRes.get("attributes");
				Iterator attributes = attributeArr.iterator();
				while(attributes.hasNext()){
					JSONObject a = (JSONObject) attributes.next();
					String id = (String) a.get("id");
					String value = (String) a.get("value");
					int i = ExpressionEvaluator.getInt(id);
					if(i != -1 && i < result.length){
						if(value.equals("None")){
							//TODO: Check what None means in hyvarrec
							result[i] = 0;
						}else{
							int v = Integer.parseInt(value);
							result[i] = v;
						}
					}
					
				}
			}else{
				result = new int[]{-1};
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void readHyvarrecResults(String input){
		String filePath = removeFilenameFromPath(input)+"/hyvarrecResult.txt";
		boolean hyvarrecResultsLoaded = false;
		if(approaches != null){
			for (String appr : approaches){
				if (appr.compareTo("HVR") == 0){
					hyvarrecResultsLoaded = true;
					break;
				}
			}
		}
		if(!hyvarrecResultsLoaded){
			File f = new File(filePath);
			if (f.exists()){
				addApproach("HVR");
				
				String line = "";
				try{
					BufferedReader file = new BufferedReader(new FileReader(filePath));
					int i = 0;
					while((line = file.readLine()) != null){
						if(line.compareTo("{\"result\":\"unsat\"}") == 0) {
							if (i < isVoidIndex.length) isVoidIndex[i] = true;
							voidModels++;
						}
						hyvarrecResults.add(line);
					}
					file.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private void addApproach(String approach){
		String[] newApproaches;
		if (approaches == null){
			newApproaches = new String[1];
		}else{
			newApproaches = new String[approaches.length+1];
			for(int i = 0; i < approaches.length; i++){
				newApproaches[i] = approaches[i];
			}
		}
		newApproaches[newApproaches.length-1] = approach;
		approaches = newApproaches;
	}
	
	private String removeFilenameFromPath(String path){
		char[] p = path.toCharArray();
		int cutOffIndex = 0;
		for (int i = 0; i < p.length; i++){
			if(p[i] == '/') cutOffIndex = i;
		}
		return path.substring(0, cutOffIndex);
	}
	
	private void printProgress(int goal, int count){
		int printPerInterval = 2;
		String printValue = "";
		double progress = (count*1.0/goal)*100;
		while (progress >= progressPrintInterval){
			printValue = progressPrintInterval+"%";
			progressPrintInterval += printPerInterval;
		}
		if(printValue.length() > 0) System.out.println(printValue);
	}
	
	private String convertNanoToTimeFormat(long nanos){
		
		long millis = nanos / 1000000;
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;
		long hour = millis / (1000 * 60 * 60);
		millis = millis % 1000;

		return String.format("%02d:%02d:%02d:%03d", hour, minute, second, millis);
	}
	
	private static void test(FeatureModel fm){
		
		ExpressionEvaluator eval = new ExpressionEvaluator(fm);
		
		int[] val1 = new int[]{1,1,0,0,1,2};
		int[] val2 = new int[]{0,1,0,0,0,3};
		int[] val3 = new int[]{1,0,0,0,1,2};
		int[] val4 = new int[]{1,1,1,1,0,0};
		
		
		String exp1a = "feature[_id0] = 1"; //val1=true, val2=false
		String exp1b = "(feature[_id0] = 0)"; //val1=false, val2=true
		String exp2 = "(feature[_id0] = 1 impl feature[_id1] = 1)"; //val1=val2=true, val3=false
		
		String exp3 = "(feature[_id0] = 1 impl (((context[_idc0] !=  3 ) impl (attribute[_idatt5] >  3 ))))";
			// exp3: val1=false unless idc=3
		String exp4 = "(feature[_id0] = 1 impl (context[_idc0] >  2 ))"; 
			// exp4: val2=true
		String exp5 = "feature[_id0] = 1 impl (feature[_id1] = 1 and feature[_id2] = 1 and feature[_id3] = 1)";
			// exp5: val1=val3=false, val2=val4=true
		String exp6 = "(feature[_id0] = 1 or feature[_id1] = 1 or feature[_id2] = 1) impl feature[_id3] = 1";
			// exp6: val1=val2=val3=false, val4=true 
		
	    String exp7 = "feature[_id1] = 1 impl (feature[_id2] + feature[_id3] + feature[_id4] = 1)";
	    	// exp7: val2=val4=false, val1=val3=true
	    String exp8 = "feature[_id0] = (feature[_id2] + feature[_id3] + feature[_id4])";
	    	// exp8: val1=val2=val3=true, val4=false
		
	    //System.out.println("Exp1a, val2: "+eval.evaluate(exp1a, val2));		//false
		//System.out.println("Exp1b, val2: "+eval.evaluate(exp1b, val2)); 	//true
		//System.out.println("Exp2, val3: "+eval.evaluate(exp2, val3));		//false
		//System.out.println("exp3, val1: "+eval.evaluate(exp3, val1));		//true if idc = 3, false otherwise
		//System.out.println("exp4, val3: "+ eval.evaluate(exp4, val3));		//true if idc > 2, false otherwise
		
		System.out.println("exp5, val2: "+eval.evaluate(exp5, val2));		//true
		System.out.println("exp6, val2: "+eval.evaluate(exp6, val2));		//false
		System.out.println("exp7, val4: "+eval.evaluate(exp7, val4));		//false
		System.out.println("exp8, val2: "+eval.evaluate(exp8, val2));		//true
	}

	public void applyHillClimbing() {
		useHillClimbing = true;
		addApproach("HC");
	}
	
	public void applySimulatedAnnealing(){
		useSimulatedAnnealing = true;
		addApproach("SA");
	}
	
	public void applyGeneticAlgorithm(){
		useGeneticAlgorithm = true;
		addApproach("GE");
	}
	
	public void clearApproches(){
		useHillClimbing = false;
		useSimulatedAnnealing = false;
		useGeneticAlgorithm = false;
	}
	
	public void applyAllApproaches(){
		applyHillClimbing();
		applySimulatedAnnealing();
		applyGeneticAlgorithm();
	}
	
	public void setHillClimbNumberOfExecutions(int numberOfExecutions){
		if (numberOfExecutions < 1){
			System.err.println("Warning: Hill-climbing cannot have less than 1 iteration");
			useHillClimbing = false;
		}
		hillClimbNumberOfExecutions = numberOfExecutions;
	}
	
	public void setHillClimbMaxPlateauIterations(int plateauIterations){
		hillClimbPlateauIterations = plateauIterations;
	}
	
	public void setSimAnnealMaxIterations(int maxIterations){
		simAnnealMaxIterations = maxIterations;
	}
	
	public void setSimAnnealInitialTemperature(double initialTemperature){
		simAnnealInitialTemp = initialTemperature;
	}
	
	public void setGeneticAlgInitialPopulationSize(int size){
		geneticAlgInitPopSize = size;
	}
	
	public void setGeneticAlgCrossoverBreakPoints(int breakpoints){
		geneticAlgCrossoverBreakPoints = breakpoints;
	}
	
	public void setGeneticAlgMutationProbability(double mutationProb){
		geneticAlgMutationProbability = mutationProb;
	}
}