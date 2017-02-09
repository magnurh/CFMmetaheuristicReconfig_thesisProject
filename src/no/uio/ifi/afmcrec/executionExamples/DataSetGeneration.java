/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import es.us.isa.utils.BettyException;
import no.uio.ifi.afmcrec.datasetGeneration.AFMDatasetGenerator;

public class DataSetGeneration{
	public static void main(String[] args){
		
		String dataSetName = "0209_TestingValidity";
		
		int sizeOfDataSet = 100;
		
		// AFM Parameters
		int numberOfFeatures = 1000;
		int percentageOfCrossTreeConstraints = 30;
		int mandatoryProbability = 32;
		int optionalProbability = 18;
		int alternativeProbability = 28;
		int orProbability = 22;
		int attributeRangeFrom = 0;
		int attributeRangeTo = 100;
		int contextMaxSize = 10;
		int contextMaxValue = 10;
		int maxNumberOfVFs = 12;
		
		AFMDatasetGenerator generator = new AFMDatasetGenerator(dataSetName);
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
			generator.generateDataSetWithCustomAttributeFunction(true);
		} catch (BettyException b) {
			System.err.println(b.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}