UPDATE oskari_appsetup SET page = 'geoportal' where page='index';
UPDATE oskari_appsetup SET page = 'embedded' where page='published';
UPDATE oskari_appsetup SET application = 'geoportal' where application='full-map';
UPDATE oskari_appsetup SET application = 'embedded' where application='servlet_published_ol3';
