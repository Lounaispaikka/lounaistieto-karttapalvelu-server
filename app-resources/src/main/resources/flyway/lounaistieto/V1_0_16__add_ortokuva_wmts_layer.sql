INSERT INTO oskari_maplayer(type, dataprovider_id, name,
                            url, locale, opacity,
                            style, minscale, maxscale,
                            params, options, gfi_type,
                            srs_name, version, attributes,
                            capabilities)
VALUES ('wmtslayer', (SELECT id FROM oskari_dataprovider WHERE locale LIKE '%Maanmittauslaitos%'), 'ortokuva',
        'https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts', '{"fi":{"subtitle":"","name":"Ortokuva"},"sv":{"subtitle":"","name":"Ortokuva"},"en":{"subtitle":"","name":"Ortokuva"}}', 100,
        'default', -1, -1,
        '{}', '{"requestEncoding":"REST","urlTemplate":"https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts/1.0.0/ortokuva/default/{TileMatrixSet}/{TileMatrix}/{TileRow}/{TileCol}.jpg","format":"image/jpeg"}', NULL,
        'EPSG:3067', '1.0.0', '{}',
        '{"srs":["EPSG:3067"],"tileMatrixIds":[{"EPSG:3067":"ETRS-TM35FIN"}]}');