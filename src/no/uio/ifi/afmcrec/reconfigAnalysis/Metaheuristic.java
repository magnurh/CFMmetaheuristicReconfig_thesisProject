/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.reconfigAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Metaheuristic{
	
	FMwrapper FM;
	
	private int hillClimbAllowedPlateauIterations = 0;
	
	private int hillClimbTotalIterations = 0;
	private int[] hillClimbBestVector = null;
	private int hillClimbBestScore = Integer.MAX_VALUE;
	private long hillClimbsolvingTime;
	
	private int simAnnealTotalIterations = 0;
	private int[] simAnnealBestVector = null;
	private int simAnnealBestScore = Integer.MAX_VALUE;
	private long SimAnnealSolvingTime;
	
	private int geneticAlgTotalIterations = 0;
	private int[] geneticAlgBestVector = null;
	private int geneticAlgBestScore = Integer.MAX_VALUE;
	private long geneticAlgSolvingTime;
	
	Metaheuristic(FMwrapper FM){
		this.FM = FM;
		nullifyLogValues();
	}
	
	public void setAllowedPlateauIterations(int allowedIterations){
		hillClimbAllowedPlateauIterations = allowedIterations;
	}
	
	private void nullifyLogValues(){
		hillClimbTotalIterations = 0;
		hillClimbBestVector = null;
		hillClimbBestScore = Integer.MAX_VALUE;
	}

	public int hillClimbingOrig(int[] candidate){
		long timeBreakA = System.nanoTime();				//
		//nullifyLogValues();
		
		int iterations = 0;
		int plateauCounter = 0;
		ArrayList<int[]> plateauVectors;
		
		int[] vectS = candidate;
		int bestScore = FM.score(candidate);
		long timeBreakB1a = System.nanoTime();				//
		//System.out.format("Init time:\t%012d%n", (timeBreakB1a - timeBreakA));		//
		long timeBreakB1b = System.nanoTime();				//
		while (bestScore > 0 && plateauCounter <= hillClimbAllowedPlateauIterations){
			iterations++;
			long timeBreakC1 = System.nanoTime();				//
			plateauVectors = new ArrayList<int[]>();
			long timeBreakE1 = System.nanoTime();				//
			int[][] neighborhood = FM.N(candidate);
			long timeBreakE2 = System.nanoTime();				//
			//System.out.format("Generate N:\t%012d%n", (timeBreakE2 - timeBreakE1));		//
			
			for (int i = 0; i < neighborhood.length; i++){
				long timeBreakD1 = System.nanoTime();				//
				int[] vectX = neighborhood[i];
				int scoreX = FM.score(vectX);
				long timeBreakD2 = System.nanoTime();				//
				if (scoreX < bestScore){
					vectS = vectX;
					bestScore = scoreX;
					plateauVectors = new ArrayList<int[]>();
					
				}else if (scoreX == bestScore){
					plateauVectors.add(vectX);
				}
				//System.out.format("Neighb %d score: \t%012d%n", i, (timeBreakD2 - timeBreakD1));
			}
			if (bestScore == FM.score(candidate)){
				if (!plateauVectors.isEmpty()){
					int randomIndex = ThreadLocalRandom.current().nextInt(0, plateauVectors.size());
					candidate = plateauVectors.get(randomIndex);
					plateauCounter++;
				}else{
					plateauCounter = hillClimbAllowedPlateauIterations;
				}
				//System.out.println(bestScore+" == "+FM.score(candidate)+" plat: "+plateauCounter);
			}else{
				//System.out.println(bestScore+" < "+FM.score(candidate));
				candidate = vectS;
				plateauCounter = 0; 
			}
			long timeBreakC2 = System.nanoTime();				//
			//System.out.format("Iter %d: \t%012d%n", iterations, (timeBreakC2 - timeBreakC1));
			
		}
		long timeBreakB2 = System.nanoTime();				//
		//System.out.println("Init time:\t"+(timeBreakB1 - timeBreakA));
		//System.out.format("Main loop:\t%012d%n", (timeBreakB2 - timeBreakB1b));
		//System.out.println("Main loop:\t"+(timeBreakB2 - timeBreakB1));
		
		hillClimbTotalIterations += iterations;
		if (bestScore < hillClimbBestScore){
			hillClimbBestVector = vectS;
			hillClimbBestScore = bestScore;
		}
		
/*		System.out.println("Iterations "+iterations+", score "+bestScore+".");		//
		if (bestScore > 0) {
			System.out.println("Global optimal not reached, score: "+bestScore);	//
		}else {
			System.out.println("Global optimal was reached! "+bestScore);			//
		}*/
		return bestScore;
	}

	// Differs from Orig in that it breaks the loop at once if it finds a cand with better score than current best
	public int hillClimbingV2(int[] candidate){

		long timeBreakA = System.nanoTime();				//
		//nullifyLogValues();
		
		int iterations = 0;
		int plateauCounter = 0;
		ArrayList<int[]> plateauVectors;
		
		int[] vectS = candidate;
		int bestScore = FM.score(candidate);
		long timeBreakB1a = System.nanoTime();				//
		//System.out.format("Init time:\t%012d%n", (timeBreakB1a - timeBreakA));		//
		long timeBreakB1b = System.nanoTime();				//
		while (bestScore > 0 && plateauCounter <= hillClimbAllowedPlateauIterations){
			iterations++;
			long timeBreakC1 = System.nanoTime();				//
			plateauVectors = new ArrayList<int[]>();
			long timeBreakE1 = System.nanoTime();				//
			int[][] neighborhood = FM.N(candidate);
			long timeBreakE2 = System.nanoTime();				//
			//System.out.format("Generate N:\t%012d%n", (timeBreakE2 - timeBreakE1));		//
			
			for (int i = 0; i < neighborhood.length; i++){
				long timeBreakD1 = System.nanoTime();				//
				int[] vectX = neighborhood[i];
				int scoreX = FM.score(vectX);
				long timeBreakD2 = System.nanoTime();				//
				if (scoreX < bestScore){
					vectS = vectX;
					bestScore = scoreX;
					plateauVectors = new ArrayList<int[]>();
					break;
				}else if (scoreX == bestScore){
					plateauVectors.add(vectX);
				}
				//System.out.format("Neighb %d score: \t%012d%n", i, (timeBreakD2 - timeBreakD1));
			}
			if (bestScore == FM.score(candidate)){
				if (!plateauVectors.isEmpty()){
					int randomIndex = ThreadLocalRandom.current().nextInt(0, plateauVectors.size());
					candidate = plateauVectors.get(randomIndex);
					plateauCounter++;
				}else{
					plateauCounter = hillClimbAllowedPlateauIterations;
				}
				//System.out.println(bestScore+" == "+FM.score(candidate)+" plat: "+plateauCounter);
			}else{
				//System.out.println(bestScore+" < "+FM.score(candidate));
				candidate = vectS;
				plateauCounter = 0; 
			}
			long timeBreakC2 = System.nanoTime();				//
			//System.out.format("Iter %d: \t%012d%n", iterations, (timeBreakC2 - timeBreakC1));
			
		}
		long timeBreakB2 = System.nanoTime();				//
		//System.out.println("Init time:\t"+(timeBreakB1 - timeBreakA));
		System.out.format("Main loop:\t%012d%n", (timeBreakB2 - timeBreakB1b));
		//System.out.println("Main loop:\t"+(timeBreakB2 - timeBreakB1));
		
		hillClimbTotalIterations += iterations;
		if (bestScore < hillClimbBestScore){
			hillClimbBestVector = vectS;
			hillClimbBestScore = bestScore;
		}
		
/*		System.out.println("Iterations "+iterations+", score "+bestScore+".");		//
		if (bestScore > 0) {
			System.out.println("Global optimal not reached, score: "+bestScore);	//
		}else {
			System.out.println("Global optimal was reached! "+bestScore);			//
		}*/
		return bestScore;
	}

	// Differs from V2 in that it goes through a shuffled set of the neighborhood (and only generates neighbor when it is needed)
	public int hillClimbingV3(int[] candidate){
		long timeBreakA = System.nanoTime();				//
		//nullifyLogValues();
		
		int iterations = 0;
		int plateauCounter = 0;
		ArrayList<int[]> plateauVectors;
		
		int[] vectS = candidate;
		int bestScore = FM.score(candidate);
		
		long timeBreakB1a = System.nanoTime();				//
		
		System.out.format("Init time:\t%012d%n", (timeBreakB1a - timeBreakA));		//
		long timeBreakB1b = System.nanoTime();				//
		while (bestScore > 0 && plateauCounter <= hillClimbAllowedPlateauIterations){
			iterations++;
			long timeBreakC1 = System.nanoTime();				//
			plateauVectors = new ArrayList<int[]>();
			long timeBreakE1 = System.nanoTime();				//
			//int[][] neighborhood = FM.N(candidate);
			ArrayList<Integer> shuffledNeighborhoodIndexes = FM.getShuffledNeighborhoodIndexes();
			long timeBreakE2 = System.nanoTime();				//
			//System.out.format("Generate N:\t%012d%n", (timeBreakE2 - timeBreakE1));		//
			
			for (int i = 0; i < shuffledNeighborhoodIndexes.size(); i++){
				long timeBreakD1 = System.nanoTime();				//
				int[] vectX = FM.getNeighbor(candidate, shuffledNeighborhoodIndexes.get(i));
				if(vectX != null){
					int scoreX = FM.score(vectX);
					long timeBreakD2 = System.nanoTime();				//
					if (scoreX < bestScore){
						vectS = vectX;
						bestScore = scoreX;
						plateauVectors = new ArrayList<int[]>();
						break;
					}else if (scoreX == bestScore){
						plateauVectors.add(vectX);
					}
					//System.out.format("Neighb %d score: \t%012d%n", shuffledNeighborhoodIndexes.get(i), (timeBreakD2 - timeBreakD1));
				}
			}
			if (bestScore == FM.score(candidate)){
				if (!plateauVectors.isEmpty()){
					int randomIndex = ThreadLocalRandom.current().nextInt(0, plateauVectors.size());
					candidate = plateauVectors.get(randomIndex);
					plateauCounter++;
				}else{
					plateauCounter = hillClimbAllowedPlateauIterations;
				}
				//System.out.println(bestScore+" == "+FM.score(candidate)+" plat: "+plateauCounter);
			}else{
				//System.out.println(bestScore+" < "+FM.score(candidate));
				candidate = vectS;
				plateauCounter = 0; 
			}
			long timeBreakC2 = System.nanoTime();				//
			//System.out.format("Iter %d: \t%012d%n", iterations, (timeBreakC2 - timeBreakC1));
			
		}
		long timeBreakB2 = System.nanoTime();				//
		//System.out.println("Init time:\t"+(timeBreakB1 - timeBreakA));
		System.out.format("Main loop:\t%012d%n", (timeBreakB2 - timeBreakB1b));
		//System.out.println("Main loop:\t"+(timeBreakB2 - timeBreakB1));
		
		hillClimbTotalIterations += iterations;
		if (bestScore < hillClimbBestScore){
			hillClimbBestVector = vectS;
			hillClimbBestScore = bestScore;
		}
		
/*		System.out.println("Iterations "+iterations+", score "+bestScore+".");		//
		if (bestScore > 0) {
			System.out.println("Global optimal not reached, score: "+bestScore);	//
		}else {
			System.out.println("Global optimal was reached! "+bestScore);			//
		}*/
		return bestScore;
	}

	// Based on V3. Implements Candidate to take care of scoring for more efficiency
	public int hillClimbing(int[] candidateVector){

		int plateauCounter = 0;
		ArrayList<Candidate> plateauVectors;
		
		// -- New version
		Candidate candidate = new Candidate(FM, candidateVector);
		//int probablyBestScore = candidate.score();
		Candidate candS = candidate;
		// --
		
		//int[] vectS = candidate.getCandidateVector();
		int bestScore = candidate.score();
		
/*		// -- New
		if(bestScore != probablyBestScore){
			System.err.println("Different scoring! correct: "+bestScore+", not correct: "+probablyBestScore);
		}
		// --
*/
		while (bestScore > 0 && plateauCounter <= hillClimbAllowedPlateauIterations){
			hillClimbTotalIterations++;
			plateauVectors = new ArrayList<Candidate>();
			ArrayList<Integer> shuffledNeighborhoodIndexes = FM.getShuffledNeighborhoodIndexes();
			
			for (int i = 0; i < shuffledNeighborhoodIndexes.size(); i++){
				
				// -- New
				Candidate candX = candidate.getNeighbor(shuffledNeighborhoodIndexes.get(i));
				// --
				
				//int[] vectX = FM.getNeighbor(candidate.getCandidateVector(), shuffledNeighborhoodIndexes.get(i));
				
				if(candX != null){
					//int scoreX = FM.score(vectX);
					int scoreX = candX.score();
					
/*					// -- New
					int dynScoreX = candX.score();
					if(scoreX != dynScoreX){
						System.err.println("Different scoring! correct: "+scoreX+", not correct: "+dynScoreX);
					}
					// --
*/					
					if (scoreX < bestScore){
						//vectS = vectX;
						// -- New
						candS = candX;
						// --
						bestScore = scoreX;
						plateauVectors = new ArrayList<Candidate>();
						break;
					}else if (candX.score() == bestScore){
						plateauVectors.add(candX);
					}
				}
			}
			if (bestScore == candidate.score()){
				if (!plateauVectors.isEmpty()){
					int randomIndex = ThreadLocalRandom.current().nextInt(0, plateauVectors.size());
					candidate = plateauVectors.get(randomIndex);
					plateauCounter++;
				}else{
					plateauCounter = hillClimbAllowedPlateauIterations;
				}
				//System.out.println(bestScore+" == "+FM.score(candidate)+" plat: "+plateauCounter);
			}else{
				//System.out.println(bestScore+" < "+FM.score(candidate));
				//candidate = vectS;
				candidate = candS;
				plateauCounter = 0; 
			}
			
		}
		
		if (bestScore < hillClimbBestScore){
			hillClimbBestVector = candS.getCandidateVector();
			hillClimbBestScore = bestScore;
		}
		//System.out.println(hillClimbBestScore);
		
/*		System.out.println("Iterations "+iterations+", score "+bestScore+".");		//
		if (bestScore > 0) {
			System.out.println("Global optimal not reached, score: "+bestScore);	//
		}else {
			System.out.println("Global optimal was reached! "+bestScore);			//
		}*/
		return bestScore;
	}
	
	public int simulatedAnnealing(int[] candidateVector, int maxIterations, double initialTemperature){
		
/*		double p = acceptanceProbabilityTest(10, 11, 5.0);
		System.out.println("P(10, 11, 5.0) = "+p); //
		p = acceptanceProbabilityTest(10, 12, 5.0);
		System.out.println("P(10, 12, 5.0) = "+p); //
		p = acceptanceProbabilityTest(10, 11, 3.5);
		System.out.println("P(10, 11, 3.5) = "+p); //
		p = acceptanceProbabilityTest(10, 12, 3.5);
		System.out.println("P(10, 12, 3.5) = "+p); //
		p = acceptanceProbabilityTest(10, 11, 1.0);
		System.out.println("P(10, 11, 1.0) = "+p); //
		p = acceptanceProbabilityTest(10, 12, 1.0);
		System.out.println("P(10, 12, 1.0) = "+p); //
		p = acceptanceProbabilityTest(10, 11, 0.05);
		System.out.println("P(10, 11, 0.05) = "+p); //
		p = acceptanceProbabilityTest(10, 12, 0.05);
		System.out.println("P(10, 12, 0.05) = "+p); //
*/		
		double t = initialTemperature;
		int kMax = maxIterations; // max number of iterations
		double reductionRate = t/maxIterations;
		double k = 1.0;
		
		Candidate candidate = new Candidate(FM, candidateVector);
		Candidate best = candidate;
		
		while(best.score() > 0 && k < kMax){
			t = t-reductionRate;  

			ArrayList<Integer> shuffledNeighborhoodIndexes = FM.getShuffledNeighborhoodIndexes();
			for (int i = 0; i < shuffledNeighborhoodIndexes.size(); i++){
				Candidate neighbor = candidate.getNeighbor(shuffledNeighborhoodIndexes.get(i));
				if(neighbor != null){
					//System.out.println("P("+candidate.score()+", "+neighbor.score()+", "+t+")");	//
					double acceptanceProb = acceptanceProbability(candidate.score(), neighbor.score(), t);
					//System.out.println(" = "+acceptanceProb); //
					double threshold = ThreadLocalRandom.current().nextDouble();
					//System.out.println(acceptanceProb+" > "+threshold+" : "+(acceptanceProb > threshold));
					if (acceptanceProb > threshold){
						candidate = neighbor;
						if (neighbor.score() < best.score()) {
							best = neighbor;
						}
						break;
					}
				}
			}
			k++;
		}
		simAnnealTotalIterations += k;
		if(best.score() < simAnnealBestScore){
			simAnnealBestVector = best.getCandidateVector();
			simAnnealBestScore = best.score();
		}
		
		return best.score();
	}
	
	private double acceptanceProbability(int currentScore, int neighbScore, double temp){
		if (neighbScore < currentScore) return 1.0;
		//System.out.println("exp(("+currentScore+"-"+neighbScore+")/"+temp+")");
		//System.out.println("exp("+(currentScore - neighbScore)+"/"+temp+")");
		//System.out.println("exp("+(currentScore - neighbScore) / temp+")");
		return Math.exp((currentScore - neighbScore)/temp);
	}
	
	/* TODO:
	 * try check to see if already exists in crossover
	 * try not add parent - instead increase number of children ?
	 * 
	*/
	public int geneticAlgorithm(int initPopSize, int crossOverBreakPoints, double mutationProbability){
		int popSize = initPopSize;
		if (initPopSize % 2 != 0) popSize--;
		
		Candidate best = new Candidate(FM, FM.generateCandidate());
		ArrayList<Candidate> generation = FM.generateCandidates(popSize*4);
		
		double generalFitness = Double.MAX_VALUE;
		double bestGeneralFitness = generalFitness;
		
		int termCount = 0;
		
		while(best.score() > 0 && popSize > 1 && termCount < 40){
			geneticAlgTotalIterations++;
			//System.out.println(geneticAlgTotalIterations+" GenerationSize: "+generation.size());
			
			boolean globalOptFound = false;
			//System.out.println(geneticAlgTotalIterations+" size: "+generation.size());
			for(Candidate p : generation){
				//System.out.print(p.score()+",");
				if(p.score() < best.score()){
					best = p;
					if (best.score() == 0) {
						globalOptFound = true;
						break;		// No need to go through the rest of the iteration
					}
				}
			}
			//System.out.println();
			if(!globalOptFound){
				generation = naturalSelection(generation, popSize);
				//System.out.println(geneticAlgTotalIterations+" size: "+generation.size());
				double newGeneralFitness = calculateGeneralFitness(generation);
				if(newGeneralFitness >= bestGeneralFitness) {
					termCount++;
				}
				else bestGeneralFitness = newGeneralFitness;
				generalFitness = newGeneralFitness;
				
				
				//System.out.println(geneticAlgTotalIterations+" General fitness: "+generalFitness+", Best: "+best.score());
				
				ArrayList<Candidate> newGeneration = new ArrayList<Candidate>();
				for(int i = 0; i < popSize; i++){
					Candidate parent1 = generation.get(i);
					Candidate parent2 = generation.get(++i);
					ArrayList<Candidate> children = crossover(parent1, parent2, crossOverBreakPoints, mutationProbability);
					newGeneration.addAll(children);
				}
				generation = newGeneration;
				//popSize -= 2;
			}
			
		}
		
		//
/*		if (best.score() > 0){
			System.out.println("Score: "+best.score()+", Iterations: "+geneticAlgTotalIterations);
			for(Candidate p: generation){
				int[] config = p.getCandidateVector();
				System.out.println(FMReconfigurer.resultAsString(config, FM.getNumberOfFeatures()));
			}
		}*/
		//
		
		if(best.score() < geneticAlgBestScore){
			geneticAlgBestScore = best.score();
			geneticAlgBestVector = best.getCandidateVector();
		}
		
		return best.score();
	}
	
	private ArrayList<Candidate> crossover(Candidate parent1, Candidate parent2, int noBreakPoints, double mutationProbability){
		int vectLength = parent1.getCandidateVector().length;
		if(vectLength != parent2.getCandidateVector().length) {
			System.err.println("Genetic alg: Two parents cannot have different vector length: "+vectLength+" != "+parent2.getCandidateVector().length);
		}else if(noBreakPoints >= vectLength/2){
			System.err.println("Warning! There are too many breakpoints set in genetic algorithm");
		}
		int[] breakPoints = generateBreakPoints(noBreakPoints, vectLength);
		int k = 0;
		
		int[] childVector1 = new int[vectLength];
		int[] childVector2 = new int[vectLength];
		int[] parentToC1 = parent1.getCandidateVector();
		int[] parentToC2 = parent2.getCandidateVector();
		
		for(int i = 0; i < vectLength; i++){
			if(k < breakPoints.length && breakPoints[k] == i){
				int[] parentToCBuffer = parentToC1;
				parentToC1 = parentToC2;
				parentToC2 = parentToCBuffer;
				k++;
			}
			double mutation = ThreadLocalRandom.current().nextDouble();
			if(mutationProbability > mutation){
				childVector1[i] = FM.tweak(i, parentToC1[i]);
				//System.out.println("Mutation ("+mutationProbability+" > "+mutation+"): "+parentToC1[i]+" --> "+childVector1[i]);
			}else{
				childVector1[i] = parentToC1[i];
			}
			mutation = ThreadLocalRandom.current().nextDouble();
			if(mutationProbability > mutation){
				childVector2[i] = FM.tweak(i, parentToC2[i]);
				//System.out.println("Mutation ("+mutationProbability+" > "+mutation+"): "+parentToC2[i]+" --> "+childVector2[i]);
			}else{
				childVector2[i] = parentToC2[i];
			}
		}
		
		Candidate c1 = new Candidate(FM, childVector1);
		Candidate c2 = new Candidate(FM, childVector2);
		//System.out.println("Parent score: "+parent1.score()+" & "+parent2.score()+", Child score: "+c1.score()+", "+c2.score());
		//System.out.println(FM.eval.getVectorAsString(breakPoints));
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		result.add(c1);
		result.add(c2);
		result.add(parent1);			// This improves result but makes a more homogeneous group
		result.add(parent2);			// This improves result but makes a more homogeneous group
		return result;
	}
	
	private double calculateGeneralFitness(ArrayList<Candidate> generation){
		double sumScore = 0.0;
		for(Candidate c : generation){
			sumScore += c.score();
		}
		return sumScore/generation.size();
	}
	
	private int[] generateBreakPoints(int noBreakPoints, int vectLength){
		int[] breakPoints = new int[noBreakPoints];
		for (int i = 0; i < noBreakPoints; i++){
			int newBP = ThreadLocalRandom.current().nextInt(1, vectLength);
			for(int j = 0; j < i; j++){
				if(newBP == breakPoints[j]){
					newBP = ThreadLocalRandom.current().nextInt(1, vectLength);
					j = 0;
				}
			}
			breakPoints[i] = newBP;
		}
		Arrays.sort(breakPoints);
		return breakPoints;
	}
	
	
	private ArrayList<Candidate> naturalSelection(ArrayList<Candidate> generation, int popSize){
		generation.sort((c1, c2) -> c1.score() - c2.score());
		ArrayList<Candidate> newSelection = new ArrayList<Candidate>();
		
		int i = 0;
		while(i < popSize){
			Candidate c = generation.get(i);
			//System.out.print(c.score()+", ");
			newSelection.add(c);
			i++;
		}
		int replacements = popSize/16;
		for(int j = 0; j < replacements; j++){
			int toBeReplaced = ThreadLocalRandom.current().nextInt(5, i);
			int toReplace = ThreadLocalRandom.current().nextInt(i, generation.size());
			newSelection.remove(toBeReplaced);
			newSelection.add(toBeReplaced, generation.get(toReplace));
		}
		//System.out.println();
		
		// Introducing foreign genes
		int inttroducedSpecies = popSize/16;
		for(int j = 0; j < inttroducedSpecies; j++){
			int toBeReplaced = ThreadLocalRandom.current().nextInt(5, i);
			//int toReplace = ThreadLocalRandom.current().nextInt(i, generation.size());
			newSelection.remove(toBeReplaced);
			newSelection.add(toBeReplaced, new Candidate(FM, FM.generateCandidate()));
		}
		
		
		/*Candidate highestScoreSelected = null;
		for(Candidate c : generation){
			if (highestScoreSelected == null){
				highestScoreSelected = c;
				newSelection.add(c);
			}else if(c.score() >= highestScoreSelected.score()){
				if(newSelection.size() < popSize){
					highestScoreSelected = c;
					newSelection.add(c);
				}else if(false){
					// TODO: implement random selection
				}
			}else{
				newSelection.remove(highestScoreSelected);
				newSelection.add(c);
				int highest = 0;
				for(Candidate s : newSelection){
					if(s.score() > highest){
						highest = s.score();
						highestScoreSelected = s;
					}
				}
			}
		}*/
		//System.out.println("New Gen size: "+newSelection.size());		//
		Collections.shuffle(newSelection);
		return newSelection;
	}
	
	public int evaluate(int[] candidate){
		return FM.score(candidate);
	}
	
	public int getHillClimbIterations(){
		return hillClimbTotalIterations;
	}
	
	public int[] getHillClimbResultVector(){
		return hillClimbBestVector;
	}
	
	public int getHillClimbResultScore(){
		return hillClimbBestScore;
	}

	public long getHillClimbSolvingTime() {
		return hillClimbsolvingTime;
	}

	public void setHillClimbSolvingTime(long hillClimbSolvingTime) {
		this.hillClimbsolvingTime = hillClimbSolvingTime;
	}
	
	public int getSimAnnealIterations(){
		return simAnnealTotalIterations;
	}
	
	public int[] getSimAnnealResultVector(){
		return simAnnealBestVector;
	}
	
	public int getSimAnnealResultScore(){
		return simAnnealBestScore;
	}

	public long getSimAnnealSolvingTime() {
		return SimAnnealSolvingTime;
	}

	public void setSimAnnealSolvingTime(long simAnnealSolvingTime) {
		SimAnnealSolvingTime = simAnnealSolvingTime;
	}
	
	public int getGeneticAlgIterations(){
		return geneticAlgTotalIterations;
	}
	
	public int[] getGeneticAlgResultVector(){
		return geneticAlgBestVector;
	}
	
	public int getGeneticAlgResultScore(){
		return geneticAlgBestScore;
	}

	public long getGeneticAlgSolvingTime() {
		return geneticAlgSolvingTime;
	}

	public void setGeneticAlgSolvingTime(long geneticAlgSolvingTime) {
		this.geneticAlgSolvingTime = geneticAlgSolvingTime;
	}
	
}