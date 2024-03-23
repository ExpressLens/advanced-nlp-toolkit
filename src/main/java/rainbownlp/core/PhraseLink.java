package rainbownlp.core;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import rainbownlp.util.FileUtil;
import rainbownlp.util.HibernateUtil;

@Entity
@Table( name = "PhraseLink" )
public class PhraseLink implements Serializable  {
	int PhraseLinkId;
	Phrase fromPhrase;
	Phrase toPhrase;
	LinkType linkType;
	private LinkType linkTypeReal;
	private LinkType linkTypeClosure;
	private LinkType linkTypeIntegrated;
	private String altLinkID;
	
	
	
	public static PhraseLink getInstance(int pLinkID) {
		String hql = "from PhraseLink where phraseLinkId = "+pLinkID;
		PhraseLink phrase_link_obj = 
			(PhraseLink)HibernateUtil.executeReader(hql).get(0);
		return phrase_link_obj;
	}


	public static PhraseLink getInstance(Phrase pfromPhrase, Phrase pToPhrase){
		String hql = "from PhraseLink where fromPhrase = "+
			pfromPhrase.getPhraseId()+" and toPhrase="+pToPhrase.getPhraseId();
		
		List<PhraseLink> link_objects = 
				(List<PhraseLink>) HibernateUtil.executeReader(hql);
	    
	    
		PhraseLink phraseLink_obj;
	    if(link_objects.size()==0)
	    {
	    	phraseLink_obj = createInstance(pfromPhrase, pToPhrase);
	    	
	    }else
	    {
	    	phraseLink_obj = 
	    		link_objects.get(0);
	    }
	    return phraseLink_obj;
	}
	
	
	public static PhraseLink createInstance(Phrase pfromPhrase, Phrase pToPhrase){
	    
		PhraseLink phraseLink_obj;
	   
    	phraseLink_obj = new PhraseLink();
    	phraseLink_obj.setFromPhrase(pfromPhrase);
    	phraseLink_obj.setToPhrase(pToPhrase);
    	phraseLink_obj.setLinkType(LinkType.UNKNOWN);
    	
    	HibernateUtil.save(phraseLink_obj);
    	
    	FileUtil.logLine(null, "created link from phrase id="+ pfromPhrase.getPhraseId() + 
    			"to id="+ pToPhrase.getPhraseId());
	   
	    return phraseLink_obj;
	}
	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getPhraseLinkId() {
		return PhraseLinkId;
	}
	public void setPhraseLinkId(int PhraseLinkId) {
		this.PhraseLinkId = PhraseLinkId;
	}
	
//	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="fromPhrase")
	public Phrase getFromPhrase() {
		return fromPhrase;
	}
	public void setFromPhrase(Phrase firstPhrase) {
		this.fromPhrase = firstPhrase;
	}
	
//	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="toPhrase")
	public Phrase getToPhrase() {
		return toPhrase;
	}
	public void setToPhrase(Phrase secondPhrase) {
		this.toPhrase = secondPhrase;
	}
	public LinkType getLinkType() {
		return linkType;
	}
	public void setLinkType(LinkType pLinkType) {
		this.linkType = pLinkType;
	}
	
	public enum LinkType {
		UNKNOWN(0),
		BEFORE(1),
		AFTER(2), 
		OVERLAP(3);
		
		 private static final Map<Integer,LinkType> lookup 
         = new HashMap<Integer,LinkType>();
		static {
	          for(LinkType l : EnumSet.allOf(LinkType.class))
	               lookup.put(l.getCode(), l);
	     }
		
		private int code;

	     private LinkType(int code) {
	          this.code = code;
	     }

	     public int getCode() { return code; }

	     public static LinkType getEnum(int code) { 
	          return lookup.get(code); 
	     }	
	     
	}	
	
	
	public static boolean sentenceHasLink(Artifact sentence) {
		boolean hasLink =false;
		
		String hql = "from PhraseLink where fromPhrase.startArtifact