-- VIEW opendata_image

-- DROP VIEW IF EXISTS opendata_image;
-- 
-- CREATE VIEW opendata_image AS
-- SELECT id, name, (CASE WHEN (caption = '') THEN alt ELSE caption END) as comment
-- FROM txp_image;

-- VIEW opendata_venues

-- DROP VIEW IF EXISTS opendata_venues;
-- 
-- CREATE VIEW opendata_venues AS
-- SELECT ID,venue_name, Category1 as category, Category2 as region, lat, lon, venue_horaire_human_readable, venue_adresse, venue_city, venue_website
-- FROM reacteur_venues
-- WHERE Status >= 4;

-- VIEW opendata_expos

-- DROP VIEW IF EXISTS opendata_expos;
-- 
-- CREATE VIEW opendata_expos AS
-- SELECT ID, LastMod,Title,Body,Image, Category2 as category, artists, venue_id, expo_url, festival_id, event_date, event_type
-- FROM reacteur_expos_tmp;




-- VIEW opendata_nantes

DROP VIEW IF EXISTS opendata_nantes;

CREATE VIEW opendata_nantes AS
SELECT 
e.Title as e_name, e.Body as e_description, e.event_type as e_category, e.artists as e_artists, e.expo_url as e_website, festival.Title as e_festival, (CASE WHEN (e.Category2 = 'festivals') THEN 1 ELSE 0 END) as e_is_festival, e.event_date as e_date,
v.venue_name as v_name, v.Category1 as v_category, v.Category2 as v_region, v.lat as v_latitude, v.lon as v_longitude, v.venue_horaire_human_readable as v_horaires, v.venue_adresse as v_adresse, v.venue_city as v_city, v.venue_website v_website,
i.name as i_name, (CASE WHEN (i.caption = '') THEN i.alt ELSE i.caption END) as i_description
FROM reacteur_expos_tmp e
LEFT JOIN reacteur_expos_tmp festival ON festival.ID = e.festival_id
LEFT JOIN reacteur_venues v ON v.ID = e.venue_id
LEFT JOIN txp_image i ON i.id = e.Image
WHERE v.Status >= 4;