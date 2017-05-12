# AFMwC-thesisProject

## About

This project is a part of my Master thesis. It contains two tools: 
### A Dataset Generator that creates sets of Context-dependant Feature Models (CFMs).
- Package: **no.uio.ifi.cfmDatasetGenerator**
- Applies the BeTTy framework (www.isa.us.es/betty) to construct Feature Models, and extends them with a context and Validity Formulas
- The generator can be instructed to limit the number of void models, but does not guarantee that no CFMs are void.

### A Reconfiguration Engine that applies metaheuristics to configure sets of CFMs.
- Package: **no.uio.ifi.cfmReconfigurationEngine**
- Applies one or more of the three metaheuristics (Hill-Climbing, Simulated Annealing, and Genetic Algorithm) to find a valid configuration for CFMs.
- Input: See data/PAPER_TESTMODEL/ for example of the dataset input file. The dataset input file contains references to a JSON file for each CFM in the dataset. The format of a CFM is the same as specified by HyVarRec (github.com/HyVar/hyvar-rec).
- Output: A report with an overview of the performance and solution from each metaheuristic execution.  

## Installation

The project can be imported to Eclipse. In the package **no.uio.ifi.cfmExecutionExamples** there are examples of how to run the Dataset Generator and the Reconfiguration Engine. 
