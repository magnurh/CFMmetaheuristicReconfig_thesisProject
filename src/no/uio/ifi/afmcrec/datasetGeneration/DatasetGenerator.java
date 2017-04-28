/**
 * 
 */
/**
 * @author Magnus
 *
 */
package no.uio.ifi.afmcrec.datasetGeneration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import es.us.isa.BeTTy.Generators.OnlyValidModelSATGenerator;
import es.us.isa.FAMA.models.FAMAAttributedfeatureModel.FAMAAttributedFeatureModel;
import es.us.isa.FAMA.models.FAMAfeatureModel.FAMAFeatureModel;
import es.us.isa.FAMA.models.FAMAfeatureModel.Feature;
import es.us.isa.FAMA.models.FAMAfeatureModel.Relation;
import es.us.isa.FAMA.models.domain.Range;
import es.us.isa.benchmarking.Benchmark;
import es.us.isa.benchmarking.FAMABenchmark;
import es.us.isa.benchmarking.RandomExperiment;
import es.us.isa.generator.FM.AbstractFMGenerator;
import es.us.isa.generator.FM.FMGenerator;
import es.us.isa.generator.FM.GeneratorCharacteristics;
import es.us.isa.generator.FM.attributed.AttributedCharacteristic;
import es.us.isa.generator.FM.attributed.AttributedFMGenerator;
import es.us.isa.utils.BettyException;
import es.us.isa.utils.FMWriter;

public class DatasetGenerator {
	
	String generalNameOfDataset;
	
	// Variables for filing
	//private String timeStamp;
	//private String directory;
	
	/** 
	 * Parameters 
	 **/
	// Main parameters
	private int sizeDataSet = 10;
	private int numberOfFeatures = 50;
	private int percentageCTC = 20;
	//private int extendedCTC = 10;
	
	// Relationship parameters
	private int probAlt = 0;
	private int probOr = 0;
	private int probMand = 0;
	private int probOpt = 0;
	
	//Attributes
	private int maxAttrPerFeature = 1;
	private int minAttrRange = 0;
	private int maxAttrRange = 100;
	
	//Context
	private int contextMaxSize = 10;
	private int contextMaxValue = 10;
	
	//Validity Formulas
	private int maxNumberOfVFs = 12;
	
	
	public DatasetGenerator(String dataSetName, int sizeDataSet, int numberOfFeatures, int percentageCTC, int maxNumberOfVFs){
		this.generalNameOfDataset = dataSetName;
		this.sizeDataSet = sizeDataSet;
		this.numberOfFeatures = numberOfFeatures;
		this.percentageCTC = percentageCTC;
		double percentageVF = maxNumberOfVFs*0.01;
		this.maxNumberOfVFs = (int) (numberOfFeatures*percentageVF);
	}
	
	/**
	 * Sets the main parameters. 
	 * Default dataset size is 10. 
	 * Default numberOfFeatures is 50.
	 * Default percentage of CTC is 30
	 * Default max number of VF's is 12
	 * 
	 * @param sizeDataSet
	 * @param numberOfFeatures
	 * @param percentageCTC
	 * @param maxNumberOfVFs
	 */
	public void setMainParameters(int sizeDataSet, int numberOfFeatures, int percentageCTC, int maxNumberOfVFs){
		this.sizeDataSet = sizeDataSet;
		this.numberOfFeatures = numberOfFeatures;
		this.percentageCTC = percentageCTC;
		this.maxNumberOfVFs = maxNumberOfVFs;
	}
	
	/**
	 * Sets the probability of a feature to be in a mandatory-, optional-, alternative- or or-relation. 
	 * By default it is left to BeTTy to set these parameters.
	 * @param probMand
	 * @param probOpt
	 * @param probAlt
	 * @param probOr
	 */
	public void setRelationshipParameters(int probMand, int probOpt, int probAlt, int probOr){
		if (probMand + probOpt + probAlt + probOr != 100){
			System.err.println("Warning: Relationship probabilities should add up to 100 %");
		}
		this.probMand = probMand;
		this.probOpt = probOpt;
		this.probAlt = probAlt;
		this.probOr = probOr;
	}
	
	/**
	 * Default values are 0 and 100
	 * @param min
	 * @param max
	 */
	public void setMaxAttributeRange(int min, int max){
		minAttrRange = min;
		maxAttrRange = max;
	}
	
	/**
	 * Default values are 10 and 10
	 * @param maxSize
	 * @param maxValue
	 */
	public void setRelativeContextSizeAndRange(int maxSizePercentageOfModelSize, int maxValue){
		double percentageContextSize = maxSizePercentageOfModelSize*0.01;
		contextMaxSize = (int) (numberOfFeatures * percentageContextSize);
		contextMaxValue = maxValue;
	}
	
	/**
	 * Default values are 10 and 10
	 * @param maxSize
	 * @param maxValue
	 */
	public void setFixedContextSizeAndRange(int maxSize, int maxValue){
		contextMaxSize = maxSize;
		contextMaxValue = maxValue;
	}
	
	
	/**
	 * Runs BeTTy to generate a *non-attributed* feature model. 
	 * Attributes are added along with VF's and models are stored in JSON format.
	 * If testForVoid is true it will skip any void models from BeTTy, 
	 * but it does not guarantee that the final model is satisfiable. 
	 * Be aware of possible memory overflow
	 * This is the second version of generateDataSet.
	 * 
	 * @param testForVoid
	 * @throws Exception
	 * @throws BettyException
	 */
	public void generateCFMDataSet(boolean testForVoid) throws Exception, BettyException {

		String directory = createDirectory();
		StringBuilder log = startLog(directory);
        GeneratorCharacteristics characteristics = setCharacteristics(new GeneratorCharacteristics());
        StringBuilder hyvarrecInputScript = new StringBuilder("#!/bin/bash\n\n");
        //NoProductsFitness noProdF = new NoProductsFitness();
        
		for (int i = 1; i <= sizeDataSet; i++){
			int seedIncr = ThreadLocalRandom.current().nextInt(sizeDataSet*999);
			characteristics.setSeed(characteristics.getSeed()+seedIncr);
			FMGenerator gen = new FMGenerator();
			OnlyValidModelSATGenerator genV;
	        FAMAFeatureModel fm;
	        if(testForVoid){
	        	genV = new OnlyValidModelSATGenerator(gen);
	        	fm = (FAMAFeatureModel) genV.generateFM(characteristics);
	        }else{
	        	fm = (FAMAFeatureModel) gen.generateFM(characteristics);
	        }
	        //Benchmark b;
			int customRestrictionsScore = customRestrictions(fm);
			int restrCounter = 0;
			FAMAFeatureModel fmTestRestr = fm;
			while(customRestrictionsScore < 0 && restrCounter < 20){
				System.out.println("checking custom restrictions "+restrCounter); 	//
				seedIncr = ThreadLocalRandom.current().nextInt(sizeDataSet*999);
				characteristics.setSeed(characteristics.getSeed()+seedIncr);
		        gen = new FMGenerator();
		        if(testForVoid){
		        	genV = new OnlyValidModelSATGenerator(gen);
		        	fmTestRestr = (FAMAFeatureModel) genV.generateFM(characteristics);
		        }else{
		        	fmTestRestr = (FAMAFeatureModel) gen.generateFM(characteristics);
		        }
		        int newRestrictionsScore = customRestrictions(fmTestRestr);
		        if (newRestrictionsScore > customRestrictionsScore){
		        	customRestrictionsScore = newRestrictionsScore;
		        	fm = fmTestRestr;
		        }
		        restrCounter++;
			}
			System.out.println("CRS: "+customRestrictionsScore);
	        //boolean isVoid = true;
	        
/*	        while(testForVoid && isVoid){
	        	//TODO: add isTooSimple-function, if possible
	        	double noProd = noProdF.fitness(fm);
	        	System.out.println(noProd);
	        	if(noProd == 0.0) {
	        		seedIncr = ThreadLocalRandom.current().nextInt(sizeDataSet*999);
	        		characteristics.setSeed(characteristics.getSeed()+seedIncr);
	        		fm = (FAMAFeatureModel) gen.generateFM(characteristics);
	        	}else isVoid = false;
	        }*/
	        
	        //FMStatistics fmStat = new FMStatistics(fm);
	        //System.out.println(fmStat);
	        
			String fmFilename = String.format("%04d", i)+"_aFM.afm";
			String fmFileDir = "./out/data/"+directory+"/afm/"+fmFilename;
			FMWriter writer = new FMWriter();
	        writer.saveFM(fm, fmFileDir);

	        FMextender fmParser = new FMextender(numberOfFeatures-1, contextMaxSize, contextMaxValue, maxNumberOfVFs, minAttrRange, maxAttrRange);
	        JSONObject afmcJSON = fmParser.generateAFMwithContext(fmFileDir);
	        String jsonFilename = String.format("%04d", i)+"_aFMwC.json";
	        writeJsonToFile(jsonFilename, directory, afmcJSON);
	        
	        updateLog(log, jsonFilename, fmParser);
	        updateHyvarRecScript(hyvarrecInputScript, jsonFilename, i);
	        
		}
		writeLog("./out/data/"+directory+"/dataset.txt", log.toString());
		writeLog("./out/data/"+directory+"/hyvarrecInputScript.sh", hyvarrecInputScript.toString());
	}
	
	/**
	 * Runs BeTTy to generate *attributed* feature models with external CTCs. 
	 * VF's are added based on these external CTC's and models are stored as JSON.
	 * This is the first version of generateDataSet
	 * 
	 * @throws Exception
	 * @throws BettyException
	 */
	public void generateCFMDataSetWithoutRestrictions() throws Exception, BettyException {
		
		String directory = createDirectory();
		StringBuilder log = startLog(directory);
		AttributedCharacteristic characteristics = setCharacteristics();
        StringBuilder hyvarrecInputScript = new StringBuilder("#!/bin/bash\n\n");
		
		for (int i = 1; i <= sizeDataSet; i++){
			int seedIncr = ThreadLocalRandom.current().nextInt(99999);
			characteristics.setSeed(characteristics.getSeed()+seedIncr);
	        
	        AbstractFMGenerator gen = new FMGenerator();
	        AttributedFMGenerator generator = new AttributedFMGenerator(gen);
	        FAMAAttributedFeatureModel afm = (FAMAAttributedFeatureModel) generator.generateFM(characteristics);
	        
			String afmFilename = String.format("%04d", i)+"_aFM.afm";
			String afmFileDir = "./out/data/"+directory+"/afm/"+afmFilename;
			FMWriter writer = new FMWriter();
	        writer.saveFM(afm, afmFileDir);
	        
	        AFMextender afmParser = new AFMextender(numberOfFeatures-1, contextMaxSize, contextMaxValue);
	        JSONObject afmcJSON = afmParser.generateAFMwithContext(afmFileDir);
	        String jsonFilename = String.format("%04d", i)+"_aFMwC.json";
	        writeJsonToFile(jsonFilename, directory, afmcJSON);
	        
	        updateLog(log, jsonFilename, afmParser);
	        updateHyvarRecScript(hyvarrecInputScript, jsonFilename, i);

		}
		writeLog("./out/data/"+directory+"/dataset.txt", log.toString());
		writeLog("./out/data/"+directory+"/hyvarrecInputScript.sh", hyvarrecInputScript.toString());
	}
	
	private int customRestrictions(FAMAFeatureModel fm){
		Feature root = fm.getRoot();
		int depth = 5;
		int paths = countMandAltPaths(fm, root, depth);
		System.out.println(paths);
		int threshold = Integer.max(Integer.min(30, (int) (numberOfFeatures*0.10)), depth);
		System.out.println("t: "+threshold);
		return paths - threshold;
	}
	
	private int countMandAltPaths(FAMAFeatureModel fm, Feature root, int level){		
		if (level == 0) {
			//System.out.println("/");
			return 1;
		}
		int paths = 1;
		Iterator<Relation> relations = root.getRelations();
		//System.out.println("("+level+") "+root+": ");
		while(relations.hasNext()){
			Relation rel = relations.next();
			Iterator<Feature> children = rel.getDestination();
			if(rel.isMandatory()){
				while(children.hasNext()){
					//System.out.print("!");
					paths += countMandAltPaths(fm, children.next(), level-1);
					//System.out.println("p: "+paths);
				}
			}else if(rel.isAlternative() || rel.isOr()){
				int smallestRes = Integer.MAX_VALUE;
				while(children.hasNext()){
					//System.out.print("[");
					int res = countMandAltPaths(fm, children.next(), level-1);
					if(res < smallestRes) smallestRes = res;
				}
				paths += smallestRes;
				//System.out.println("p: "+paths);
				
			}
		}
		return paths;
	}
	
	private String createDirectory(){
		String timeStamp = timeStamp();
		String directory = generalNameOfDataset+"/"+timeStamp;
		new File("./out/data/"+directory+"/afm").mkdirs();
		new File("./out/data/"+directory+"/afmWithContext").mkdirs();
		return directory;
	}
	
	private StringBuilder updateHyvarRecScript(StringBuilder hyvarrecInputScript, String fileName, int iteration){
        if (iteration > 1) hyvarrecInputScript.append("echo >> hyvarrecResult.txt\n");
        hyvarrecInputScript.append("curl -H \"Content-Type: application/json\" -X POST -d @./afmWithContext/"+fileName+" http://localhost:4000/process >> hyvarrecResult.txt\n");
        if( iteration*1.0 % (sizeDataSet*1.0 / 20) == 0) hyvarrecInputScript.append("echo \"progress: "+iteration+"/"+sizeDataSet+"\"\n");
        return hyvarrecInputScript;
	}
	
	private StringBuilder updateLog(StringBuilder log, String fileName, VarModelExtender parser){
        int noAttr = parser.getNumberOfAttributes();
        int totSize = parser.getHighestID()+1;
        int contextSize = parser.getContextSize();
        log.append(fileName+"\t"+numberOfFeatures+"\t"+noAttr+"\t"+totSize+"\t"+contextSize+"\n");
        return log;
	}
	
	private StringBuilder startLog(String directory){
		StringBuilder log = new StringBuilder();
		log.append("./out/data/"+directory+"/dataset.txt");
		log.append("\n./out/data/"+directory+"/afmWithContext");
		log.append("\nSize_dataSet: "+sizeDataSet);
		log.append("\nSize_AFM: "+numberOfFeatures);
		log.append("\nPercentage_CTC: "+percentageCTC);
		log.append("\nMax_number_of_Validity_Formulas: "+maxNumberOfVFs);
		log.append("\nProbability_mandatory: "+probMand);
		log.append("\nProbability_optional: "+probOpt);
		log.append("\nProbability_ALT: "+probAlt);
		log.append("\nProbability_OR: "+probOr);
		log.append("\nContext_MaxSize: "+contextMaxSize);
		log.append("\nContext_MaxValue: "+contextMaxValue);
		log.append("\n\nFeature_models");
		log.append("\nFILENAME\t#FEATURES\t#ATTRIBUTES\tTOTAL_SIZE\tCONTEXTSIZE\n");
		return log;
		
	}
	
	private void writeLog(String fileName, String log){
		try {
			FileWriter f = new FileWriter(new File(fileName));
			f.write(log.toString());
			f.close();
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}
	
	public static String timeStamp(){
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");
		return dateTime.format(formatter);
	}
	
	private void writeJsonToFile(String fileName, String directory, JSONObject afmcJSON){
        String jsonFileDir = "./out/data/"+directory+"/afmWithContext/"+fileName;
        try {
        	FileWriter file = new FileWriter(jsonFileDir);
    		file.write(afmcJSON.toJSONString());
    		file.flush();
    		file.close();
        } catch (IOException e){
        	System.err.println(e.getMessage());
        }
	}
	
	private GeneratorCharacteristics setCharacteristics(GeneratorCharacteristics characteristics){
        characteristics.setNumberOfFeatures(numberOfFeatures);		// Number of features
        characteristics.setPercentageCTC(percentageCTC);
        
        if (probAlt + probOr + probMand + probOpt != 0){
	        characteristics.setProbabilityAlternative(probAlt);
	        characteristics.setProbabilityOr(probOr);
	        characteristics.setProbabilityMandatory(probMand);
	        characteristics.setProbabilityOptional(probOpt);
        }
		return characteristics;
	}
	
	private AttributedCharacteristic setCharacteristics(){
		AttributedCharacteristic characteristics = new AttributedCharacteristic();
        characteristics.setNumberOfFeatures(numberOfFeatures);		// Number of features
        characteristics.setPercentageCTC(percentageCTC);
        characteristics.setNumberOfExtendedCTC(maxNumberOfVFs);
        
        if (probAlt + probOr + probMand + probOpt != 0){
	        characteristics.setProbabilityAlternative(probAlt);
	        characteristics.setProbabilityOr(probOr);
	        characteristics.setProbabilityMandatory(probMand);
	        characteristics.setProbabilityOptional(probOpt);
        }
        
		characteristics.setAttributeType(AttributedCharacteristic.INTEGER_TYPE);
		characteristics
				.setDefaultValueDistributionFunction((AttributedCharacteristic.UNIFORM_DISTRIBUTION));
		
		characteristics.addRange(new Range(minAttrRange, maxAttrRange));
		characteristics.setNumberOfAttibutesPerFeature(maxAttrPerFeature);
		String argumentsDistributionFunction[] = { ""+minAttrRange, ""+maxAttrRange };
		
		characteristics
				.setDistributionFunctionArguments(argumentsDistributionFunction);
		characteristics.setHeadAttributeName("Attr");
		return characteristics;
	}
}