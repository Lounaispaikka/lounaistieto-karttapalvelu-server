UPDATE oskari_maplayer
  SET url='http://avoindata.maanmittauslaitos.fi/mapcache/wmts',
    capabilities='{"srs":["EPSG:3067"],"tileMatrixIds":[{"EPSG:3067":"ETRS-TM35FIN"}]}',
    options='{"requestEncoding":"REST","urlTemplate":"http://avoindata.maanmittauslaitos.fi/mapcache/wmts/1.0.0/taustakartta/default/{TileMatrixSet}/{TileMatrix}/{TileRow}/{TileCol}.png","format":"image/png"}'
WHERE type='wmtslayer' AND name='taustakartta';


UPDATE oskari_maplayer
  SET url='http://avoindata.maanmittauslaitos.fi/mapcache/wmts',
    capabilities='{"srs":["EPSG:3067"],"tileMatrixIds":[{"EPSG:3067":"ETRS-TM35FIN"}]}',
    options='{"requestEncoding":"REST","urlTemplate":"http://avoindata.maanmittauslaitos.fi/mapcache/wmts/1.0.0/maastokartta/default/{TileMatrixSet}/{TileMatrix}/{TileRow}/{TileCol}.png","format":"image/png"}'
WHERE type='wmtslayer' AND name='maastokartta';