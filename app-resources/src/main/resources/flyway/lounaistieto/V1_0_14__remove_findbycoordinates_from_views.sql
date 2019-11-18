DELETE FROM portti_view_bundle_seq
WHERE bundle_id IN (SELECT id FROM portti_bundle WHERE bundleinstance = 'findbycoordinates')