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
create a new document loader 