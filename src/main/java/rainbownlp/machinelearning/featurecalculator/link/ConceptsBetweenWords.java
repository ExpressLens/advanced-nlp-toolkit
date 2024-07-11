/**
 * 
 */
package rainbownlp.machinelearning.featurecalculator.link;

import java.util.ArrayList;
import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.Phrase;
import rainbownlp.core.PhraseLink;
import rainbownlp.core.FeatureValuePair.FeatureName;
import rainbownlp.machinelearning.IFeatureCalculator;
import rainbownlp.machinelearning.MLExample;
import rainbownlp.machinelearning.MLExampleFeature;

/**
 * @author ehsan
 * 
 */
public class ConceptsBetweenWords implements IFeatureCalculator {
	public static void main(String[] args) throws Exception
	{
//		List<MLExample> trainExamples = 
//		MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupTimexEvent, true);
//		List<MLExample> trainExamples2 = 
//			MLExample.getAllExamples(LinkExampleBuilder.ExperimentGroupEventEvent, true);
//		List<MLExample> all_train_examples = new ArrayList<MLExample>();
//		all_train_examples.addAll(trainExamples);
//		all_train_examples.addAll(trainExamples2);
//		
//		for ( MLExample example_to_process: all_train_examples )
//		{
//			ConceptsBetweenWords n_grams =  new ConceptsBetweenWords();
//			
//			n_grams.calculateFeatures(example_to_process);
//		}
	}
	
		@Override
	public void calculateFeatures(MLExample exampleToProcess) {
			
			PhraseLink phraseLink = exampleToProcess.getRelatedPhraseLink();
			Phrase phrase1 = phraseLink.getFirstPhrase();
			Phrase phrase2 = phraseLink.getSecondPhrase();
			
			Artifact curArtifact = phrase1.getEndArtifact().getNextArtifact();
			Artifact toArtifact = phrase2.getStartArtifact();
			Integer count_words_between=0;
			
			while(curArtifact!=null && 
					!curArtifact.equals(toArtifact))
			{
				String curContent = curArtifact.getContent();
				FeatureValuePair wordBetweenFeature = FeatureValuePair.getInstance(
						FeatureName.LinkWordBetween, 
						curContent, "1");
				
				MLExampleFeature.setFeatureExample(exampleToProcess, wordBetweenFeature);