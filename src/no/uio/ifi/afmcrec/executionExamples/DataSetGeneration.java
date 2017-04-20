/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.executionExamples;

import es.us.isa.utils.BettyException;
import no.uio.ifi.afmcrec.datasetGeneration.DatasetGenerator;

public class DataSetGeneration{
	public static void main(String[] args){
		
		String dataSetName = "0420_TestSet04";
		
		int sizeOfDataSet = 600;
		
		// AFM Parameters
		int numberOfFeatures = 90;
		int percentageOfCrossTreeConstraints = 35;
		int mandatoryProbability = 10;
		int optionalProbability = 10;
		int alternativeProbability = 40;
		int orProbability = 40;
		int attributeRangeFrom = 0;
		int attributeRangeTo = 100;
		int contextMaxSize = 12;
		int contextMaxValue = 10;
		int maxNumberOfVFs = 15;
		
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