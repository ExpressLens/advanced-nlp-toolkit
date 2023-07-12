package rainbownlp.analyzer.sentenceclause;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rainbownlp.core.Artifact;
import rainbownlp.parser.DependencyLine;
import rainbownlp.parser.StanfordParser;
import rainbownlp.util.StanfordDependencyUtil;


public class SentenceClauseManager {

	private Artifact relatedSentence;
	private String sentContent;
	
	private String posTags;
	private String stanDependenciesStr;
	
	public ArrayList<DependencyLine> sentDepLines = new ArrayList<DependencyLine>();

	ArrayList<Clause> clauses;

	public HashMap<Integer, String> offsetMap = new HashMap<Integer, String>();
	
	//this keeps the offsets as the key and the value is the lemma
	public HashMap<Integer, String> lemmaMap = new HashMap<Integer, String>();
	
	// the same as above just has all the original tokens
	public HashMap<Integer, String> tokenMap = new HashMap<Integer, String>();
	
	//the m