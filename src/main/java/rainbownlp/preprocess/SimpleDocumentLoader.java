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
			List<String> tokens = 
					docTokenizer.sentences_tokens_string.get(curSentenceIndex);

			if(tokens.size()==0)
				continue;

			Artifact new_sentence = Artifact.getInstance(Artifact.Type.Sentence,
					parentArtifact.getAssociatedFilePath(), tokens_starts.get(0));//line number start from 1
			new_sentence.setParentArtifact(parentArtifact);
			new_sentence.setLineIndex(curSentenceIndex+1);
			new_sentence.setContent(tokenizedSentence);
			new_sentence.setArtifactOptionalCategory(dsType.name());
			if (previous_sentence != null) {
				new_sentence.setPreviousArtifact(previous_sentence);
				previous_sentence.setNextArtifact(new_sentence);
				HibernateUtil.save(previous_sentence);
			}

			HibernateUtil.save(new_sentence);

			loadWords(new_sentence,tokens, tokens_starts, curSentenceIndex);

			setencesArtifacts.add(new_sentence);

			//Pattern.insert(Util.getSentencePOSsPattern(curSentence));
			previous_sentence = new_sentence;
			HibernateUtil.clearLoaderSession();
		}
	}

	protected void loadWords(Artifact parentSentence,  List<Stri