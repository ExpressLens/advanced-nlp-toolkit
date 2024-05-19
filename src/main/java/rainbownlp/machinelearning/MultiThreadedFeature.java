package rainbownlp.machinelearning;

import java.sql.SQLException;
import java.util.List;

import rainbownlp.util.FileUtil;

public abstract class MultiThreadedFeature extends Thread implements IFeatureCalculator {
	protected List<MLExample> examples;
	public MultiThreadedFeature(List<MLExample> examplesToProcess){
		examples = examplesToProcess;
	}
	@Override
	public abstract void calculateFeatures(MLExample exampleToProcess)
			throws SQLException, Exception;

	@Override
	public void run() {
		int counter = 0;
		for (MLExample example:examples)
		{
			try {
				calculateFeatures(example);
			} catch (SQLException e) {
				