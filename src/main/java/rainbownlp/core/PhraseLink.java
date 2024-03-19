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
    	phraseLink_obj.setFromPhrase(pfromPhra