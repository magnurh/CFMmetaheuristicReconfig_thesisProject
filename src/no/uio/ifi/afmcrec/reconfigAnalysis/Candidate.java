/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.reconfigAnalysis;

import java.util.ArrayList;
import java.util.HashSet;

public class Candidate{
	
	FMwrapper FM;
	Candidate parent = null;
	int neighborhoodNumber = -1;
	int[] candidateVector;
	String candidateVectorStr = "";
	HashSet<String> unsatisfiedConstraints;
	int finalScore = -1;
	
	Candidate(FMwrapper FM, int[] candidateVector){
		this.FM = FM;
		this.candidateVector = candidateVector;
	}
	
	Candidate(FMwrapper FM, int[] candidateVector, Candidate parent, int neighborhoodNumber){
		this.FM = FM;
		this.candidateVector = candidateVector;
		this.parent = parent;
		this.neighborhoodNumber = neighborhoodNumber;
	}
	
	public int score(){
		if(unsatisfiedConstraints == null){
			if(parent == null){
				unsatisfiedConstraints = FM.scoreAndReturnUnsatConstraints(candidateVector);
			}else{
				unsatisfiedConstraints = FM.dynamicScoring(candidateVector, parent.getUnsatisfiedConstraints(), neighborhoodNumber);
			}
		}
/*		if (unsatisfiedConstraints.size() == 0) {
			FM.printAllConstraintsWithAssignments(candidateVector);
		}*/
		finalScore = unsatisfiedConstraints.size();
		return finalScore;
	}
	
	public Candidate getNeighbor(int neighborIndex){
		int[] neighborVector = FM.getNeighbor(candidateVector, neighborIndex);
		//if(neighborVector == null) System.err.println("Index: "+neighborIndex+" Produces empty neighbor vector "+neighborVector);
		if(neighborVector != null){
			Candidate neighbor = new Candidate(FM, neighborVector, this, neighborIndex);
			return neighbor;
		}
		return null;
	}
	
	public int[] getCandidateVector(){
		return candidateVector;
	}
	
	public HashSet<String> getUnsatisfiedConstraints(){
		if (unsatisfiedConstraints == null) score();
		return unsatisfiedConstraints;
	}
	
	private String resultAsString(int[] v, int sizeAFM){
		String res = "";
		String deliminator = "";
		for (int i = 0; i < v.length; i++){
			if (i >= sizeAFM) deliminator = "-";
			res += deliminator+v[i];
		}
		return res;
	}
	
	public String getConfigAsString(){
		if(candidateVectorStr.length() == 0){
			candidateVectorStr = resultAsString(candidateVector, FM.getNumberOfFeatures());
		}
		return candidateVectorStr;
	}
	
	@Override
	public boolean equals(Object candidate)
	{
	    boolean isEqual= false;

	    if (candidate != null && candidate instanceof Candidate)
	    {
	        isEqual = (this.getConfigAsString() == ((Candidate) candidate).getConfigAsString());
	    }

	    return isEqual;
	}

	@Override
	public int hashCode() {
	    return this.getConfigAsString().hashCode();
	}
	
}