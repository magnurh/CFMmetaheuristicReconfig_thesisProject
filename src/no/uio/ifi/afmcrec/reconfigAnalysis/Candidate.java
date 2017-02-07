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
	
}