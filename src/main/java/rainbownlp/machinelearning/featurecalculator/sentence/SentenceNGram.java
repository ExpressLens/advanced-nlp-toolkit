package rainbownlp.machinelearning.featurecalculator.sentence;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.PhraseLink;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;
import rainbownlp.util.StringUtil;

public class SentenceNGram implements IFeatureCalculator {

	@Override
	public void calculateFeatures(MLExample exampleToProcess) {
		if(exampleToProcess.getRelatedPhrase() == null)
		{
			PhraseLink sentencesLink = exampleToProcess.getRelatedPhraseLink();
			Artifact firstSentence = sentencesLink.getFirstPhrase().getStartArtifact();
			Artifact secondSentence = sentencesLink.getSecondPhrase().getStartArtifact();
			calculateSentenceNGram(1, firstSentence, exampleToProcess, "FirstSentence1Gram");
			calculateSentenceNGram(1, secondSentence, ex