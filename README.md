Advanced NLP Toolkit
==========

Advanced NLP Toolkit (ANLP-Toolkit) is an open-source framework for diverse natural language processing tasks including: supervised name entity recognition and relationship extraction.

Configuration
==========
ANLP-Toolkit is based on Hibernate and can be configured to use different data sources (e.g. MySQL).
Hibernate settings are in hibernate.cfg.xml, ensure to adjust connection.url, connection.username 
and connection.password as per your requirements. 
Other application settings are in configuration.conf, update the paths and ensure their existence.  

We provide instructions on how to use ANLP-Toolkit for various use-cases.

Relationship Extraction
==========
For relationship extraction, the framework first loads the text document and entities annotations. 

1. Load test/train documents into the framework by using 'SimpleDocumentLoader' or 
create a new document loader by implementing 'IDocumentAnalyzer' interface (for test/train sets).

2. Load annotations by creating instances of 'Phrase' and 'PhraseLink'. Make sure they are persisted with HibernateUtil (for test/train sets).

3. Create ML examples by crafting Phrase/PhraseLink and MLExample objects (for test/train sets)

4. Compute features for each MLExample object.
 
5. Train a ML model with the train examples.

6. Evaluate the model using test examples.

Temporal relationship extraction example used for I2B2 shared task submission (https://www.i2b2.org/NLP/TemporalRelations/) :
https://github.com/ExpressLens/temporal-relation.git

How to cite in your research?
==========
If you use ANLP-Toolkit in your experiments, please refer to the following publication:

Emadzadeh, E.; Jonnalagadda, S.; Gonzalez, G., 'Evaluating Distributional Semantic and Feature Selection for Extracting Relationships from Biological Text,' Machine Learning and Applications and Workshops (ICMLA), 2011 10th International Conference on , vol.2, no., pp.66,71, 18-21 Dec. 2011

For Questions/Comments
==========
Join us on GitHub for any question or comments.