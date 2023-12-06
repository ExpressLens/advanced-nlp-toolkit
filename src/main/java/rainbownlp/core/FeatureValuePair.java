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
