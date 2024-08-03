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
			tf = 