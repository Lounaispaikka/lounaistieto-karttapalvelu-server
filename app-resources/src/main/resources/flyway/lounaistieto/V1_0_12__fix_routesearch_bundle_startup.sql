UPDATE portti_view_bundle_seq
SET startup='{
"instanceProps": {},
"title": "Route Search",
"bundleinstancename": "routesearch",
"fi": "Reittihaku",
"sv": "Ruttsök",
"en": "Route Search",
"bundlename": "routesearch",
"metadata": {
"Import-Bundle": {
"routesearch": {
"bundlePath": "/Oskari/packages/paikkatietoikkuna/bundle/"
}
},
"Require-Bundle-Instance": [

]
}
}'
WHERE bundle_id IN (SELECT id FROM portti_bundle WHERE bundleinstance = 'routesearch')