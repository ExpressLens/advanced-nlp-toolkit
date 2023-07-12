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
	
	//the mapping of lemmas to originals
	//TODO: if we have repeated that are different originals it will be overwritten
	public HashMap<String, String> lemmaTokenMap = new HashMap<String, String>();
	
	// this hash keep the location of each observed offset in the sentence
	public HashMap<Integer, Clause> clauseMap = new HashMap<Integer, Clause>();
	//////////////////////////////////////
	//this array will keep all the lines that the governor or dependent clause could not be resolved
	ArrayList<DependencyLine> phrases = new ArrayList<DependencyLine>();
	public String filename;
	public String[] normalized_dependencies;
	public ArrayList<String> getPhrases()
	{
		ArrayList<String> phrase_strings = new ArrayList<String>();
		for (DependencyLine depLine:phrases)
		{
			if (depLine.firstOffset<depLine.secondOffset)
			{
				phrase_strings.add(depLine.firstPart+" "+depLine.secondPart);
			}
			else
			{
				phrase_strings.add(depLine.secondPart+" "+depLine.firstPart);
			}
		}
		return phrase_strings;
