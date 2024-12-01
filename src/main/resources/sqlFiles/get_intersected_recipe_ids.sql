--based on the selected tags, classify them into the types they belong to
--for each type, union all recipes for these tags together and store in an array respectively
--return the intersection of these arrays
CREATE OR REPLACE FUNCTION get_intersected_recipe_ids(tag_ids INT[]) RETURNS INT[] AS $$
DECLARE
    rec INT[];
    res INT[] := NULL;
BEGIN
    FOR rec IN
        SELECT
            rt.recipe_ids_for_tag_id_array
        FROM (
            SELECT
                type_id,
                ARRAY_AGG(id) AS tag_ids_for_same_type_id
            FROM
                tags
            WHERE
                id = ANY(tag_ids)
            GROUP BY
                type_id
        ) AS t
        JOIN LATERAL (
            SELECT
                ARRAY_AGG(DISTINCT(recipe_id)) AS recipe_ids_for_tag_id_array
            FROM
                UNNEST(t.tag_ids_for_same_type_id) AS tagId_from_same_typeId
            JOIN
                recipes_tags ON recipes_tags.tag_id = tagId_from_same_typeId
        ) AS rt
        ON true
    LOOP
        IF res IS NULL THEN
            res := rec;
        ELSE
            res := ARRAY(
                SELECT elem FROM UNNEST(res) AS elem
                WHERE elem = ANY(rec)
            );
        END IF;
    END LOOP;
    RETURN res;
END $$ LANGUAGE plpgsql;