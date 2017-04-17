/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import es.us.isa.utils.BettyException;
import no.uio.ifi.afmcrec.datasetGeneration.DatasetGenerator;

public class DataSetGeneration{
	public static void main(String[] args){
		
		String dataSetName = "0308_TestingGenAlg";
		
		int sizeOfDataSet = 1000;
		
		// AFM Parameters
		int numberOfFeatures = 30;
		int percentageOfCrossTreeConstraints = 30;
		int mandatoryProbability = 33;
		int optionalProbability = 17;
		int alternativeProbability = 30;
		int orProbability = 20;
		int attributeRangeFrom = 0;
		int attributeRangeTo = 100;
		int contextMaxSize = 8;
		int contextMaxValue = 10;
		int maxNumberOfVFs = 12;
		
		DatasetGenerator generator = new DatasetGenerator(dataSetName);
		generator.setMainParameters(sizeOfDataSet, numberOfFeatures, percentageOfCrossTreeConstraints, maxNumberOfVFs);
		generator.setRelationshipParameters(mandatoryProbability, optionalProbability, alternativeProbability, orProbability);
		generator.setMaxAttributeRange(attributeRangeFrom, attributeRangeTo);
		generator.setContextSizeAndRange(contextMaxSize, contextMaxValue);
		
/*		try {
			generator.generateDataSet();
		} catch (BettyException b) {
			System.err.println(b.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}*/
		
		try {
			generator.generateCFMDataSet(true);
		} catch (BettyException b) {
			System.err.println(b.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}