/**
 * @author ehsan
 */
package rainbownlp.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;

/**
 * Store feature and value binded together
 */
@Entity
@Table( name = "FeatureValuePair" )
public class FeatureValuePair {
	public static void main(String[] args){
		FeatureValuePair.resetIndexes();
	}
	public enum FeatureName {
		// Document Features
		JournalTitle,
		CompletedYear,
		CreatedYear,
		RevisedYear,
		MESHHeading,


		// Paragraph Features
		// PositionInDoc,

		// Sentence Features
		ProteinCountInSentence,
		SentenceTFIDF,

		// Words Features
		POS,
		POSNext1,
		POSNext2,
		POSPre1,
		POSPre2,

		PorterStem, 
		WordnetStem, 
		OriginalWord, 
		NameEntity, 
		StartWithUppercase, 
		AllUppercase, 
		AllLowercase, 
		HasSpecialChars, 
		HasDigit, 

		CommaLeftCount,
		CommaRightCount,
		QuoteLeftCount,
		QuoteRightCount,
		ProteinCountInWindow,

		SimilarityToGene_expression, 
		SimilarityToTranscription, 
		SimilarityToProtein_catabolism, 
		SimilarityToLocalization, 
		SimilarityToBinding, 
		SimilarityToPhosphorylation, 
		SimilarityToRegulation, 
		SimilarityToPositive_regulation, 
		SimilarityToNegative_regulation,
		PositionInDoc,

		//coreference features
		AnaphoraIsSubject,
		AntecedentInFirstSubject,
		AntecedentInHeader,
		AntecedentIsSubject,
		Appositive,
		NumberAgreement,
		SentenceDistance, TWOGram, TWOGramBackward, ThreeGram, ThreeGramBackward, NellLink, 
		ProblemCountInSentence, TestCountInSentence, TreatmentCountInSentence, TestsBeforeWord, 
		TreatmentsBeforeWord, ProblemsBeforeWord, ProblemPossibleCountInSentence, ProblemHypoCountInSentence, 
		ProblemConditionalCountInSentence, ProblemAWSECountInSentence, ProblemAbsentCountInSentence, ProblemPresentCountInSentence, EdgeType,
		WordWindowNext, WordWindowPre, EdgeParsePath, EdgeParseDistance, DependencyLinkedTokens,

		TimexCount, ClinicalEventsCount, LinkWordBetween, LinkArgumentType, LinkFromPhrasePolarity, LinkFromPhraseModality, LinkFromPhraseType, LinkToPhraseModality, LinkToPhraseType, 
		LinkToPhrasePolarity, LinkToPhraseTimexMod, LinkFromPhraseTimexMod, 
		InterMentionLocationType, AreDirectlyConnected, HaveCommonGovernors, AreConjunctedAnd,
		//NGrams
		NonNormalizedNGram2, NonNormalizedNGram3, NorBetweenNGram2, NorBetweenNGram3, Link2GramBetween, 
		Link2GramFrom,Link2GramTo,

		//Link Args basic features
		FromPhraseContent, ToPhraseContent, FromPhrasePOS, ToPhrasePOS,

		LinkBetweenWordCount, LinkBetweenPhraseCount, 

		//ParseDependency features
		FromPhraseRelPrep, ToPhraseRelPrep, FromPhraseGovVerb, ToPhraseGovVerb,  FromPhraseGovVerbTense,
		FromPhraseGovVerbAux, toPhraseGovVerbAux, areGovVerbsConnected,
		normalizedDependencies,
		//pattern statistics
		POverlapGivenPattern, PBeforeGivenPattern, PAfterGivenPatt