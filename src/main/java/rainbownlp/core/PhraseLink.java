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
	private S