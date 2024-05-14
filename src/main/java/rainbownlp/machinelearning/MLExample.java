
package rainbownlp.machinelearning;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import rainbownlp.core.Artifact;
import rainbownlp.core.Artifact.Type;
import rainbownlp.core.FeatureValuePair;
import rainbownlp.core.Phrase;
import rainbownlp.core.PhraseLink;
import rainbownlp.core.PhraseLink.LinkType;
import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;
import rainbownlp.util.ConfigurationUtil;

@Entity
@Table( name = "MLExample" )
public class MLExample  implements Serializable {
	int exampleId;


	String predictedClass;
	String expectedClass;
	boolean forTrain;
	String corpusName;
	String predictionEngine;
	Artifact relatedArtifact;
	Phrase relatedPhrase;
	
	PhraseLink relatedPhraseLink;
	private String associatedFilePath;
	private double predictionWeight;
	private int expectedReal;
	private int expectedClosure;
	private int expectedIntegrated;
	private String expectedClassOptionalCategory;
	private String predictedClassOptionalCategory;
	private String relatedConcept;
	
	@Transient
	List<MLExampleFeature> exampleFeatures;
	static public Session hibernateSession; 
	@Transient
	public List<MLExampleFeature> getExampleFeatures()
	{
		if(exampleFeatures==null)
		{
			if(hibernateSession == null)
				hibernateSession = HibernateUtil.sessionFactory.openSession();
			String hql = "from MLExampleFeature where relatedExample = "+
			 getExampleId()+ 
			 " order by featureValuePair.tempFeatureIndex";
			exampleFeatures = (List<MLExampleFeature>) HibernateUtil.executeReader(hql, null,null, hibernateSession);
		}
		return exampleFeatures;
	}

	@Transient
	public MLExampleFeature getExampleFeatureById(int featureValuePairId)
	{
		
		if(hibernateSession == null)
			hibernateSession = HibernateUtil.sessionFactory.openSession();
		String hql = "from MLExampleFeature where relatedExample="+
				getExampleId()+ " and featureValuePair="+featureValuePairId;
		List<MLExampleFeature> exampleFeatures = (List<MLExampleFeature>) HibernateUtil.executeReader(hql,null,null,hibernateSession);
		if(exampleFeatures!=null && exampleFeatures.size()>0)
			return exampleFeatures.get(0);
		else
			return null;
	}

	public String getPredictionEngine() {
		return predictionEngine;
	}

	public void setPredictionEngine(String pPredictionEngine) {
		predictionEngine = pPredictionEngine;
	}


	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch=FetchType.LAZY )
    @JoinColumn(name="relatedPhrase")
	public Phrase getRelatedPhrase() {
		return relatedPhrase;
	}

	public void setRelatedPhrase(Phrase relatedPhrase) {
		this.relatedPhrase = relatedPhrase;
	}
	
	public String getCorpusName() {
		return corpusName;
	}
	
	public void setCorpusName(String pCorpusName) {
		corpusName = pCorpusName;
	}
	
	public boolean getForTrain() {
		return forTrain;
	}
		
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch=FetchType.LAZY )
    @JoinColumn(name="relatedArtifact")
	public Artifact getRelatedArtifact() {
		return relatedArtifact;
	}

	public void setRelatedArtifact(Artifact relatedArtifact) {
		this.relatedArtifact = relatedArtifact;
	}

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch=FetchType.LAZY )
    @JoinColumn(name="relatedPhraseLink")
	public PhraseLink getRelatedPhraseLink() {
		return relatedPhraseLink;
	}

	public void setRelatedPhraseLink(PhraseLink relatedPhraseLink) {
		this.relatedPhraseLink = relatedPhraseLink;
	}

	public void setForTrain(boolean isForTrain) {
		forTrain = isForTrain;
	}
	
	public String getPredictedClass() {
		return predictedClass;
	}
	
	public void setPredictedClass(String pPredictedClass) {
		predictedClass = pPredictedClass;
	}
	
	public String getExpectedClass() {
		return expectedClass;
	}
	public void setPredictedClass(Integer pPredictedClass) {
		setPredictedClass(pPredictedClass.toString());
	}	
	public void setExpectedClass(Integer pExpectedClass) {
		setExpectedClass(pExpectedClass.toString());
	}	
	public void setExpectedClass(String pExpectedClass) {
		expectedClass = pExpectedClass;
	}
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getExampleId() {
		return exampleId;
	}

	public void setExampleId(int exampleId) {
		this.exampleId = exampleId;
	}
	@Temporal(TemporalType.TIMESTAMP)
    Date updateTime;
	
	
	@PrePersist
    protected void onCreate() {
		updateTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	updateTime = new Date();
    }

	public static MLExample getInstanceForArtifact(Artifact artifact,
			String experimentgroup) {
		String hql = "from MLExample where relatedArtifact = "+
				artifact.getArtifactId() + " and corpusName = '"+
				experimentgroup+"'";
			List<MLExample> example_objects = 
					getExamplesList(hql);
		    
			MLExample example_obj;
		    if(example_objects.size()==0)
		    {
		    	example_obj = new MLExample();
		  
		    	
		    	example_obj.setCorpusName(experimentgroup);
		    	example_obj.setRelatedArtifact(artifact);
		    	
		    	if(ConfigurationUtil.SaveInGetInstance)
			    	saveExample(example_obj);
		    }else
		    {
		    	example_obj = 
		    			example_objects.get(0);
		    }
		    return example_obj;
	}

	public void calculateFeatures(
			List<IFeatureCalculator> featureCalculators) throws Exception {
		for(IFeatureCalculator feature_calculator : featureCalculators)
		{
			Date before = new Date(); 
			feature_calculator.calculateFeatures(this);
			Date after = new Date(); 
			FileUtil.logLine(null, feature_calculator.getClass().toString()+"       "+(after.getTime()-before.getTime()));
		}
		
	}

	public static MLExample getInstanceForLink(PhraseLink phrase_link,
			String experimentgroup) {
		String hql = "from MLExample where relatedPhraseLink = "+
				phrase_link.getPhraseLinkId() + " and corpusName = '"+
						experimentgroup+"'";
			List<MLExample> example_objects = 
					getExamplesList(hql);
		    
			MLExample example_obj;
		    if(example_objects.size()==0)
		    {
		    	example_obj = new MLExample();
		  
		    	
		    	example_obj.setCorpusName(experimentgroup);
		    	example_obj.setRelatedPhraseLink(phrase_link);
		    	if(phrase_link.getFromPhrase().getStartArtifact()!=null)
		    		example_obj.setAssociatedFilePath(phrase_link.getFromPhrase().getStartArtifact().getAssociatedFilePath());
		    	
		    	if(ConfigurationUtil.SaveInGetInstance)
		    		saveExample(example_obj);
		    }else
		    {
		    	example_obj = 
		    			example_objects.get(0);
		    }
		    return example_obj;
	}


	public static void saveExample(MLExample example)
	{
		if(hibernateSession == null)
			hibernateSession = HibernateUtil.loaderSession;
		
		HibernateUtil.save(example, hibernateSession);
	}
	static List<MLExample> getExamplesList(String hql, Integer limit)
	{
		List<MLExample> examples;
		if(hibernateSession == null)
			hibernateSession = HibernateUtil.loaderSession;
		if(!hibernateSession.isOpen())
			hibernateSession = HibernateUtil.sessionFactory.openSession();
		examples = 
			(List<MLExample>) HibernateUtil.executeReader(hql, null, limit, hibernateSession);
		return examples;
	}
	static List<MLExample> getExamplesList(String hql, HashMap<String, Object> params)
	{
		List<MLExample> examples;
		if(hibernateSession == null)
			hibernateSession = HibernateUtil.loaderSession;
		
		examples = 
			(List<MLExample>) HibernateUtil.executeReader(hql, params, null, hibernateSession);
		return examples;
	}
	public static List<MLExample> getAllExamples(String experimentgroup, boolean for_train, Integer limit)
	{
		return getAllExamples(experimentgroup, for_train, "exampleId", limit);
	}
	
	public static List<MLExample> getAllExamples(String experimentgroup, boolean for_train)
	{
		return getAllExamples(experimentgroup, for_train, "exampleId", null);
	}

	public static List<MLExample> getAllExamples(String experimentgroup, boolean for_train, String orderByPhrase, Integer limit)
	{
		String hql = "from MLExample where corpusName = '"+
						experimentgroup+"' and forTrain="+(for_train?1:0)
						+"  order by "+orderByPhrase;
		
		return getExamplesList(hql, limit);
	}

	public static List<MLExample> getAllExamples(boolean for_train, Integer limit)
	{
		String hql = "from MLExample where  forTrain="+(for_train?1:0)
						+"  order by exampleId";
		
		return getExamplesList(hql, limit);
	}
	public static List<MLExample> getAllExamples(boolean for_train)
	{
		String hql = "from MLExample where  forTrain="+(for_train?1:0)
						+"  order by exampleId";
		
		return getExamplesList(hql);
	}
	private static List<MLExample> getExamplesList(String hql) {
		return getExamplesList(hql, Integer.MAX_VALUE);
	}

	public static List<MLExample> getExampleById(int example_id, String experimentgroup)
	{
		String hql = "from MLExample where corpusName = '"+
						experimentgroup+"' and exampleId="+example_id
						+" order by exampleId";
		return getExamplesList(hql);
	}
	public static MLExample getExampleById(int example_id)
	{
		String hql = "from MLExample where exampleId="+example_id;
		List<MLExample> example_objects = 
			(List<MLExample>) HibernateUtil.executeReader(hql);
    
		MLExample example_obj=null;
	    if(example_objects.size()!=0)
	    {
	    	example_obj = 
	    		example_objects.get(0);
	    }
	    return example_obj;
	}
	public static List<MLExample> getAllExamples(String experimentgroup, boolean for_train, int limit)
	{
		String hql = "from MLExample where corpusName = '"+
						experimentgroup+"'  and forTrain="+(for_train?1:0)+" order by exampleId";
		return getExamplesList(hql);
	}
	public static List<MLExample> getLastExamples(String experimentgroup, boolean for_train, int limit)
	{
		String hql = "from MLExample where corpusName = '"+
			experimentgroup+"'  and forTrain="+(for_train?1:0)+
				"order by exampleId desc";
		return getExamplesList(hql);
	}
	
	public static List<MLExample> getExampleByExpectedClass(String experimentgroup,boolean for_train, int expectedClass)
	{
		String hql = "from MLExample where corpusName = '"+
						experimentgroup+"' and expectedClass="+expectedClass
						+" and  forTrain="+(for_train?1:0)+" order by exampleId";
		return getExamplesList(hql);
	}
	
	public static List<MLExample> getExamplesInDocument(String experimentgroup, 
			String doc_path)
	{
		
		String hql = "FROM MLExample "  +
				"where corpusName =:corpusName " +
				" and associatedFilePath = '" +
				doc_path + "' " +
						"order by exampleId desc";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("corpusName", experimentgroup);
		
		return getExamplesList(hql, params);
	}
	/**
	 * Get examples in document with the given expectedClass
	 * @param experimentgroup
	 * @param doc_path
	 * @return
	 */
	public static List<MLExample> getExamplesInDocument(String experimentgroup, 
			String doc_path,Integer expectedClass)
	{
		
		String hql = "FROM MLExample "  +
				"where expectedClass = :expectedClass and corpusName =:corpusName " +
				" and associatedFilePath = '" +
				doc_path + "' " +
						"order by exampleId desc";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("corpusName", experimentgroup);
		params.put("expectedClass", expectedClass);
		
		return getExamplesList(hql, params);
	}
	
	public static List<MLExample> getExamplesByDocument(String experimentgroup, 
			boolean for_train, int num_of_documents)
	{
		List<Artifact> docs = Artifact.listByType(Type.Document, for_train);
		if(docs.size()<num_of_documents)
			num_of_documents = docs.size();
		
		String docPaths = "";
		for(int i=0;i<num_of_documents;i++)
			docPaths = docPaths.concat(", '"+docs.get(i).getAssociatedFilePath()+"'");
		docPaths = docPaths.replaceFirst(",", "");
		
		String hql = "FROM MLExample "  +
				"where corpusName =:corpusName " +
				" and forTrain="+(for_train?1:0) +" and associatedFilePath in (" +
						docPaths + ") " +
						"order by associatedFilePath desc";
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("corpusName", experimentgroup);
		
		return getExamplesList(hql, params);
	}
	
	public static List<MLExample> getExamplesByEventTypeByDocument(String experimentgroup, 
			boolean for_train, int num_of_documents, String type1,
			String type2,String order)
	{
		List<Artifact> docs = Artifact.listByType(Type.Document,for_train);
		if(docs.size()<num_of_documents)
			num_of_documents = docs.size();
		
		String docPaths = "";
		if (order.equals("top"))
		{
			for(int i=0;i<num_of_documents;i++)
				docPaths = docPaths.concat(", '"+docs.get(i).getAssociatedFilePath()+"'");
			docPaths = docPaths.replaceFirst(",", "");
		}
		else if(order.equals("last"))
		{
			for(int i=docs.size()-1;i>docs.size()-num_of_documents-1;i--)
				docPaths = docPaths.concat(", '"+docs.get(i).getAssociatedFilePath()+"'");
			docPaths = docPaths.replaceFirst(",", "");
		}
		Integer type1_from_fvpIds = FeatureValuePair.getRelatedFromEventTypeFValuePairIds(type1);
		Integer type1_to_fvpIds = FeatureValuePair.getRelatedToEventTypeFValuePairIds(type1);
		Integer type2_from_fvpIds = FeatureValuePair.getRelatedFromEventTypeFValuePairIds(type2);
		Integer type2_to_fvpIds = FeatureValuePair.getRelatedToEventTypeFValuePairIds(type2);
		
//		String from_fvpIds = "";
//		for(Integer id: fromFeatureValuePairIds)
//		{
//			from_fvpIds = from_fvpIds.concat(", '"+id+"'");
//		}
//		from_fvpIds = from_fvpIds.replaceFirst(",", "");
//		