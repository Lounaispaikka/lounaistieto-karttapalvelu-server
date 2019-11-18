UPDATE portti_view_bundle_seq
SET seqno=-1
WHERE bundle_id IN (SELECT id FROM portti_bundle WHERE bundleinstance = 'lounaistieto-lang-overrides')