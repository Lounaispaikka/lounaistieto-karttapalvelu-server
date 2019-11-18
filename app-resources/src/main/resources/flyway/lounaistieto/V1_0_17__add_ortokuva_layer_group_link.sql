INSERT INTO oskari_maplayer_group_link(maplayerid, groupid)
VALUES (
  (SELECT id FROM oskari_maplayer WHERE type='wmtslayer' AND url='https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts' AND locale LIKE '%Ortokuva%'),
  (SELECT id FROM oskari_maplayer_group WHERE locale LIKE '%Taustakartat%')
);