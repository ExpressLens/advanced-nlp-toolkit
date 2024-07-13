package rainbownlp.machinelearning.featurecalculator.phrase;

import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.Phrase;
import rainbownlp.core.PhraseLink;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;
import rainbownlp.machinelearning.featurecalculator.FeatureCalculatorUtil;
import rainbownlp.util.StringUtil;

public class PhraseNGram implements IFeatureCalculator {

	@Override
	public void calculateFeatures(MLExample exampleToProcess) {
		Phrase p = exampleToProcess.getRelatedPhrase();
		if(p == null) {
			return;
		}
		
		
		calculateInPhraseNGram(1, p, exampleToProcess, "InPhrase1Gram");
		calculateInPhraseNGram(2, p, exampleToProcess, "InPhrase2Gram");
		
		List<String> beforePhraseNGrams = FeatureCalculatorUtil.getNGramBefore(p.getStartArtifact(), 3);
		List<String> afterPhraseNGrams = FeatureCalculatorUtil.getNGramAfter(p.getE