UPDATE portti_view
SET application_dev_prefix='/applications/lounaistieto';

UPDATE portti_view
SET application='full-map' WHERE page='index';

UPDATE portti_view
SET application='servlet_published_ol3' WHERE page='published';