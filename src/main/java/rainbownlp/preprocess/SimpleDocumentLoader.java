package rainbownlp.preprocess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;
import edu.stanford.nlp.process.PTBTokenizer;

public class SimpleDocumentLoader extends DocumentAnalyzer {
	protected List<Artifact> documents;
	protected String documentExtension = "txt";
	public List<Artifact> getDocuments() {
		return documents;
	}
	/**
	 * Simply call loadSentences, override this method for more complex loaders
	 * @param doc
	 * @throws IOException
	 * @throws Exception 
	 */
	protected void processDocument(Artifact doc) throws Exception{
		loadSentences(doc);
	}

	/**
	 * Use tokenizer to find sentences
	 * @param parentArtifact can be document, paragraph or any other document section
	 * @throws IOException
	 */
	protected void loadSentences(Artifact parentArtifact) throws IOException {
		Tokenizer docTokenizer = new Tokenizer(parentArtifact.getAssociatedFilePath());
		HashMap<Integer, String> setences = docTokenizer.getSentences();
		List<Artifact> setencesArtifacts = new ArrayList<Artifact>();
		Artifact previous_sentence = null;

		for(int curSentenceIndex=0;
				curSentenceIndex<setences.keySet().size();curSentenceIndex++){
			//			System.out.print("\r Loading sentences for: "+parentDoc.get_associatedFilePath()+ " "+
			//					curSentenceIndex + "/ " + setences.size()+longspace);

			String tokenizedSentence = setences.get(curSentenceIndex);
			List<Integer> tokens_starts = 
					docTokenizer.sentences_tokens_indexes.get(curSentenceIndex);
			List<String>