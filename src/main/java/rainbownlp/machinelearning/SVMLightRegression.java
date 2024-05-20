package rainbownlp.machinelearning;

import rainbownlp.util.ConfigurationUtil;

public class SVMLightRegression extends SVMLightBasedLearnerEngine {
	private SVMLightRegression()
	{
		
	}

	public static LearnerEngine getLearnerEngine(String pTaskName) {
		SVMLightRegression learnerEngine = new SVMLightRegression();
		learnerEngine.setTaskName(pTaskName);
		
		return learnerEngine;
	}

	@Override
	protected boolean isBinaryClassification() {
		return false;
	}

	@Override
	protected String getTrainCommand() {
		String myShellScript = 
				ConfigurationUtil.getValue("SVMLightLearnerPath")
					+ " -z r -t " +ConfigurationUtil.getValue("SVMKernel")
//					+ " -c " +Configurati