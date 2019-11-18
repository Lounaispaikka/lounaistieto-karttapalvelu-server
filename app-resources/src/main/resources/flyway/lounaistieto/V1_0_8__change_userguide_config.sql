UPDATE portti_view_bundle_seq
SET config=''
WHERE bundle_id IN (SELECT id FROM portti_bundle WHERE bundleinstance = 'userguide')
