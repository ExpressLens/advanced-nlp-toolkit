package rainbownlp.preprocess;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import rainbownlp.core.Artifact;
import rainbownlp.util.FileUtil;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenizer {
	String txt_file_path;
	String tokenization_file;
	String original_txt_content = "";
	String compressedText;
	public static void main(String[] args) throws SQLException
	{
		fixDashSplitted();
	}
	static TokenizerFactory<Word> tf;
	
	public static List<Word> getTokens(String sentence)
	{
		if(tf == null)
			tf = PTBTokenizer.factory();
		
		List<Word> tokens_words = tf.getTokenizer(new StringReader(sentence)).tokenize();
		
		
		return tokens_words;
	}
	public static void fixDashSplitted() throws SQLException
	{
//		String q = "SELECT * from Artifact where text_content like '%-%' and artifact_type = 'Word'";
//		ResultSet artifacts = Util.db.executeReader(q);
//		while(artifacts.next())
//		{
//			int artifact_id = artifacts.getInt("artifact_id");
//			Artifact curArti = new Artifact(artifact_id);
//			fixMergedNameEntity(curArti);
//		}
	}
	public ArrayList<String> paragraphs = new ArrayList<String>();
	public HashMap<Integer, String> sentences = new HashMap<Integer, String>();
	public HashMap<Integer,List<Integer>> sentences_tokens_indexes = new HashMap<Integer, List<Integer>>();
	public HashMap<Integer,List<Word>> sentences_tokens = new HashMap<Integer,List<Word>>();
	public Hashtable<Integer, List<String>> sentences_tokens_string = new Hashtable<Integer, List<String>>();
	
	public static void fixMergedNameEntity(Artifact curArtifact) throws SQLException
	{
//		String originalContent = curArtifact.getTextContent();
//		String[] parts = originalContent.split("-");
//		String previousContent = "";
//		for(int i=0;i<parts.length;i++)
//		{
//			String content = parts[i];
//			int j=i;
//			do
//			{
//				if((NameEntityTable.possibleNameEntity(content)||
//						EventTriggers.isPossibleTrigger(content))
//						&& !content.equals(originalContent) &&
//						content.length()>1)
//				{
//					Util.log("Fixing:"+originalContent, Level.INFO);
//					if(i==0)
//					{//NE is at the beginning
//						//shorten current artifact
//						curArtifact.setTextContent(content);
//						//add a new artifact at the end 
//						String remainingContent = "";
//						for(int k=j+1;k<parts.length;k++){
//							if(!remainingContent.equals(""))
//								remainingContent+= "-";
//							remainingContent+=parts[k];
//						}
//						
//						Artifact dashArtifact = new Artifact("-",
//								Type.Word, curArtifact.associatedFilePath,
//								curArtifact.getStartIndex()+content.length(),
//								curArtifact.getParen