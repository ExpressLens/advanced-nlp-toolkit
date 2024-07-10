package rainbownlp.machinelearning.featurecalculator;

import java.util.ArrayList;
import java.util.List;

import rainbownlp.core.Artifact;

public class FeatureCalculatorUtil {

	public static List<String> getNGramBefore(Artifact startArtifact, int n) {
		ArrayList<String> ngrams = new ArrayList<String>();
		int stepRemained = n;
		Artif