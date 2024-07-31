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
	 * Simply call loadSentences, override this method for 