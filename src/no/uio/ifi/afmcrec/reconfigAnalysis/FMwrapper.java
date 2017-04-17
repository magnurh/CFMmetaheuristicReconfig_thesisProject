package no.uio.ifi.afmcrec.reconfigAnalysis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FMwrapper{
	
	private FeatureModel fm;
	private String modelName;
	public ExpressionEvaluator eval;
	private int numberOfFeatures;
	private int numberOfAttributes;
	private int candidateLength;
	private int contextSize;
	private HashSet<Integer> alwaysSelected;
	private HashMap<String, Integer> savedScores = new HashMap<String, Integer>();
	
	ArrayList<int[]> neighborhoodChangeIndex = new ArrayList<int[]>();
	ArrayList<Integer> shuffledNeighborhoodIndexes = new ArrayList<Integer>();
	
	FMwrapper(String dir, String modelName, int numberOfFeatures, int numberOfAttributes, int size, int contextSize){
		fm = importFM(dir+"/"+modelName, size);
		this.modelName = modelName;
		eval = new ExpressionEvaluator(fm);
		this.numberOfFeatures = numberOfFeatures;
		fm.setNumberOfFeatures(numberOfFeatures);
		this.numberOfAttributes = numberOfAttributes;
		fm.setNumberOfAttributes(numberOfAttributes);
		this.candidateLength = size;
		fm.setSize(size);
		this.contextSize = contextSize;
		fm.setContextSize(contextSize);
		alwaysSelected = fm.getSelectedFeatures();
	}
	
	// Returns number of constraints falsified by vector
	public int score(int[] vect){
		String vectStr = eval.getVectorAsString(vect);
		if (savedScores.containsKey(vectStr)){
			return savedScores.get(vectStr);
		}
		HashSet<String> unsatConstraints = new HashSet<String>();
		ArrayList<String> constraints = fm.getConstraints();
		int notSatConstraints = 0;
		for (String r : constraints){
			if(!eval.evaluate(r, vect)) {
				unsatConstraints.add(r);
				notSatConstraints += 1;
			}
		}
		//System.out.println("Actually unsatisfied constraints");			//
		//System.out.println(vectStr);									//
		//for(String r : unsatConstraints) System.out.println(r);			//
		savedScores.put(vectStr, notSatConstraints);
		return notSatConstraints;
	}
	
	public HashSet<String> scoreAndReturnUnsatConstraints(int[] vect){
		//TODO: add saving and retrieving of set of unsatisfied constraints
		String vectStr = eval.getVectorAsString(vect);
		HashSet<String> unsatisfiedConstraints = new HashSet<String>();
		ArrayList<String> constraints = fm.getConstraints();
		for (String r : constraints){
			if(!eval.evaluate(r, vect)) unsatisfiedConstraints.add(r);
		}
		//System.out.println(vectStr);
		//for(String c : unsatisfiedConstraints) System.out.println(c);//
		//savedScores.put(vectStr, unsatisfiedConstraints.size());
		return unsatisfiedConstraints;
	}
	
	public void printAllConstraintsWithAssignments(int[] vect){
		for (int i = 0; i < vect.length; i++){
			System.out.println(i+": "+vect[i]);
		}
		ArrayList<String> constraints = fm.getConstraints();
		for (String r : constraints){
			eval.printConstraintWithAssignment(r, vect);
		}
	}
	
	public HashSet<String> dynamicScoring(int[] vect, HashSet<String> parentUnsatisfiedConstraints, int neighborhoodNumber){
		String vectStr = eval.getVectorAsString(vect);
		if (savedScores.containsKey(vectStr)){
			//TODO: add storing and retrieving of unsatisfied constraints
			//return savedScores.get(vectStr);
		}
		HashSet<String> currentUnsatisfiedConstraints = new HashSet<String>(parentUnsatisfiedConstraints);
		int changeIndex = getChangeIndex(neighborhoodNumber);
		ArrayList<String> constraintsToBeEvaluated = fm.getConstraintsContainingId(changeIndex);
		//System.out.println(vectStr);			//
		for (String constraint : constraintsToBeEvaluated){
			boolean evaluation = eval.evaluate(constraint, vect);
			//System.out.println(constraint+" : "+evaluation+" ("+changeIndex+")");		//
			if(evaluation){
				if(currentUnsatisfiedConstraints.contains(constraint)){
					currentUnsatisfiedConstraints.remove(constraint);
				}
			}else{
				currentUnsatisfiedConstraints.add(constraint);
			}
		}
		//for(String c : currentUnsatisfiedConstraints) System.out.println(c+" ("+changeIndex+")"); 	//
		//System.out.println("Score: "+currentUnsatisfiedConstraints.size());
		return currentUnsatisfiedConstraints;
	}
	
	public int score(int[] vect, int maxScore){
		String vectStr = eval.getVectorAsString(vect);
		if (savedScores.containsKey(vectStr)){
			return savedScores.get(vectStr);
		}
		
		ArrayList<String> constraints = fm.getConstraints();
		int notSatConstraints = 0;
		for (String r : constraints){
			if(!eval.evaluate(r, vect)) notSatConstraints += 1;
			if(notSatConstraints > maxScore) return notSatConstraints;
		}
		savedScores.put(vectStr, notSatConstraints);
		return notSatConstraints;
	}
	
	public boolean evaluate(String exp, int[] vect){
		return eval.evaluate(exp, vect);
	}
	
	public int[] generateCandidate(){
		// TODO: move filling of neighborhoodChangeIndex to separate function
		//neighborhoodChangeIndex = new ArrayList<int[]>();		
		boolean changeIndexNotFilled = neighborhoodChangeIndex.size() == 0;
		int[] newCandidate = new int[candidateLength];
		
		for (int i = 0; i < candidateLength; i++){
			if (alwaysSelected.contains(i)){
				newCandidate[i] = 1;
			}else if (i < numberOfFeatures){
				newCandidate[i] = ThreadLocalRandom.current().nextInt(0, 2);
				if (changeIndexNotFilled) neighborhoodChangeIndex.add(new int[]{i, 0});
			}else{
				int[] attrRange = fm.getAttributeRange(i);
/*				System.out.print("Range for attr "+i+":");		//
				for (int l = 0; l < attrRange.length; l++){		//
					System.out.print(attrRange[l]+",");				//	
				}								//
				System.out.println();							//
*/				int valIndex = ThreadLocalRandom.current().nextInt(0, attrRange.length);
				newCandidate[i] = attrRange[valIndex];
				if (changeIndexNotFilled) neighborhoodChangeIndex.add(new int[]{i, 1});
				if (changeIndexNotFilled) neighborhoodChangeIndex.add(new int[]{i, -1});
			}
		}
		return newCandidate;
	}
	
	public int[] generateTrivialCandidate() {
		int[] newCandidate = new int[candidateLength];
		Iterator<Integer> select = alwaysSelected.iterator();
		while(select.hasNext()){
			newCandidate[select.next()] = 1;
		}
		return newCandidate;
	}
	
	public ArrayList<Candidate> generateCandidates(int size){
		ArrayList<Candidate> candidates = new ArrayList<Candidate>();
		int i = 0;
		while(i < size){
			Candidate c = new Candidate(this, generateCandidate());
			candidates.add(c);
			i++;
		}
		return candidates;
	}
	
	// Returns neighborhood of input vector v 
	public int[][] N(int[] v){
		
		// The maximum size of a neighborhood will be the number of binary variables (features that can vary) 
		// plus two times nonBinary variables (attributes - they may change in either direction)
		int nSize = numberOfFeatures - alwaysSelected.size() + (numberOfAttributes*2);
		//System.out.println("Size of N = "+numberOfFeatures+" - "+alwaysSelected.size()+" + "+numberOfAttributes+"*2 = "+nSize);
		int cutOffNeighborhood = 0;
		int[][] neighborhood = new int[nSize][candidateLength];
		
		int j = 0;
		
		for (int i = 0; i < nSize; i++){
			while(alwaysSelected.contains(j)){
				j++;
			}
			if(j < numberOfFeatures){
				System.arraycopy(v, 0, neighborhood[i], 0, candidateLength);
				neighborhood[i][j] = negate(neighborhood[i][j]);
			}else if(j >= numberOfFeatures && j < candidateLength){
				boolean increaseSuccessfull = false;
				System.arraycopy(v, 0, neighborhood[i], 0, candidateLength);
			
				int attInc = inc(j, v[j]);
				if (attInc == -1){
					cutOffNeighborhood++;
				}else{
					neighborhood[i][j] = attInc;
					increaseSuccessfull = true;
					//System.out.println(""+attInc+" at ("+i+","+j+") -> "+neighborhood[i][j]);				//
				}
				
				int attDec = dec(j, v[j]);
				if (attDec == -1){
					cutOffNeighborhood++;
				}else{
					if (increaseSuccessfull){
						i++;
						System.arraycopy(v, 0, neighborhood[i], 0, candidateLength);
					}
					neighborhood[i][j] = attDec;
					//System.out.println(""+attDec+" at ("+i+","+j+") -> "+neighborhood[i][j]);				//
				}
			}
			j++;
							
		}
		
		int[][] finalNeighborhood;
		
		if (cutOffNeighborhood > 0){
			//System.out.print("Cut away from neighborhood: "+nSize+" - "+cutOffNeighborhood);		//
			finalNeighborhood = new int[nSize-cutOffNeighborhood][candidateLength];
			for (int i = 0; i < finalNeighborhood.length; i++){
				finalNeighborhood[i] = neighborhood[i];																	
			}
			//System.out.println(" = "+finalNeighborhood.length);			//
		}else{
			finalNeighborhood = neighborhood;
		}
/*		
		System.out.println("\nNeighborhood: ");														//
		for (int i = 0; i < finalNeighborhood.length; i++){											//
			System.out.print(i+"\t");																//
			for (int k = 0; k < candidateLength; k++) System.out.print(finalNeighborhood[i][k]+" ");	//
			System.out.println();																	//
		}																							//
		System.out.println();																		//
*/		
		return finalNeighborhood;
	}
	
	
	public int tweak(int id, int value){
		if (alwaysSelected.contains(id)) return 1;
		else if(id < numberOfFeatures) return negate(value);
		else if(id < numberOfFeatures + numberOfAttributes){
			boolean increase = ThreadLocalRandom.current().nextBoolean();
			if(increase){
				int res = inc(id, value);
				if (res != -1) return res;
				else return dec(id, value);
			}else{
				int res = dec(id, value);
				if (res != -1) return res;
				else return inc(id, value);
			}
		}else{
			System.err.println(id+" >= "+(numberOfFeatures + numberOfAttributes));
			return -1;
		}
	}
	
	// Example: [0,5,24]
	private int inc(int attId, int v){
		Attribute a = fm.getAttribute(attId);
		int[] attRange = a.getRange();
		
/*		System.out.print("Inc "+v+" in (");					//
		for (int i = 0; i < attRange.length-1; i++){		//
			System.out.print(attRange[i]+", ");
		}													//
		System.out.print(attRange[attRange.length-1]+") -> ");	//
*/		
		
		if (a.isRangeComplete()) {
//			System.out.println(""+incByInterval(attRange, v));	//
			return incByInterval(attRange, v);
		}
		
		int max = attRange[attRange.length-1];
		if (v >= max) {
//			System.out.println("false");				//
			return -1;
		}
//		System.out.println(""+(v+1));					//
		return ++v;
	}
	
	private int incByInterval(int[] interval, int v){
		for (int i = 0; i < interval.length-1; i++){
			if (v >= interval[i] && v < interval[i+1]){
				return interval[i+1];
			}
		}
		return -1;
	}
	
	private int dec(int attId, int v){
		Attribute a = fm.getAttribute(attId);
		int[] attRange = a.getRange();
		
/*		System.out.print("Dec "+v+" in (");					//
		for (int i = 0; i < attRange.length-1; i++){		//
			System.out.print(attRange[i]+", ");
		}													//
		System.out.print(attRange[attRange.length-1]+") -> ");	//
*/		
		if (a.isRangeComplete()) {
//			System.out.println(""+decByInterval(attRange, v));	//
			return decByInterval(attRange, v);
		}
		
		int min = attRange[0];
		if (v <= min) {
//			System.out.println("false");				//
			return -1;
		}
		
//		System.out.println(""+(v-1));					//
		return --v;
	}
	
	private int decByInterval(int[] interval, int v){
		for (int i = interval.length-1; i > 0; i--){
			if (v <= interval[i] && v > interval[i-1]){
				return interval[i-1];
			}
		}
		return -1;
	}
	
	private int negate(int v){
		if (v == 0) return 1;
		else if (v == 1) return 0;
		else {
			System.err.println("Non-binary value "+v+" cannot be negated.");
			return -1;
		}
	}
	
	
	private FeatureModel importFM(String dir, int size){
		FeatureModel fm = null;
		
		JSONParser parser = new JSONParser();
		try {
			JSONObject fmJSON = (JSONObject) parser.parse(new FileReader(dir));
			fm = new FeatureModel(fmJSON, size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fm;
		
	}
	
	public int getChangeIndex(int neighborIndex){
		int[] positionToChange = neighborhoodChangeIndex.get(neighborIndex);
		return positionToChange[0];
	}

	public int[] getNeighbor(int[] candidate, int neighborIndex) {
		int[] neighbor = new int[candidate.length];
		System.arraycopy(candidate, 0, neighbor, 0, candidate.length);
		int[] positionToChange = neighborhoodChangeIndex.get(neighborIndex);
		int pos = positionToChange[0];
		
		//System.out.println("Neighb index "+neighborIndex+" gives position "+pos+" in "+eval.getVectorAsString(candidate));
		
		int direction = positionToChange[1];
		if(pos >= numberOfFeatures){
			if (direction > 0){
				neighbor[pos] = inc(pos, candidate[pos]);
			}else if (direction < 0){
				neighbor[pos] = dec(pos, candidate[pos]);
			}else{
				System.err.println("Attribute changeIndex with direction 0: "+pos+" >= Features: "+numberOfFeatures+" (neighbor "+neighborIndex+")");
			}
			if (neighbor[pos] == -1) return null;
		}else{
			neighbor[pos] = negate(candidate[pos]);
		}
/*		System.out.println("Original candidate:");
		System.out.println("Neighbor "+neighborIndex+" - changed at position "+pos);
		for(int i = 0; i < candidate.length; i++){
			System.out.print(i+" ");
		}
		System.out.println();
		for(int i = 0; i < candidate.length; i++){
			String padding = " ";
			if(i > 9 && i < 100) padding = "  ";
			else if(i > 99 && i < 1000) padding = "   ";
			System.out.print(candidate[i]+padding);
		}
		System.out.println();
		for(int i = 0; i < candidate.length; i++){
			String padding = " ";
			if(i > 9 && i < 100) padding = "  ";
			else if(i > 99 && i < 1000) padding = "   ";
			System.out.print(neighbor[i]+padding);
		}
		System.out.println();*/
		return neighbor;
	}

	public ArrayList<Integer> getShuffledNeighborhoodIndexes() {
		if (shuffledNeighborhoodIndexes.size() != neighborhoodChangeIndex.size()){
			//System.out.println("size of N_indexes "+shuffledNeighborhoodIndexes.size()+", Size of N_ChangeIndex: "+neighborhoodChangeIndex.size());
			//System.out.println("SizeOfNeighborhood: "+neighborhoodChangeIndex.size()+", Always selected: "+alwaysSelected.size());
			shuffledNeighborhoodIndexes = new ArrayList<Integer>();
			for (int i = 0; i < neighborhoodChangeIndex.size(); i++){
				shuffledNeighborhoodIndexes.add(i);
			}
		}
		Collections.shuffle(shuffledNeighborhoodIndexes);
		return shuffledNeighborhoodIndexes;
	}
	
	public int getNumberOfAttributes(){
		return numberOfAttributes;
	}
	
	public int getNumberOfFeatures(){
		return numberOfFeatures;
	}
	
	public int getCandidateLength(){
		return candidateLength;
	}
	
	public int getContextSize(){
		return contextSize;
	}
	
	public String getModelName() {
		return modelName;
	}
	
}
