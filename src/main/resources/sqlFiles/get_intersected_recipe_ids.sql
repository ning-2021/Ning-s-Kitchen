--based on the selected tags, classify them into the types they belong to
--union all recipes together into an array per tag if the type is non-d-r; do intersection when d-r
--return the intersection of above arrays
CREATE OR REPLACE FUNCTION get_intersected_recipe_ids(tag_ids INT[]) RETURNS BIGINT[] AS $$
DECLARE
    rec BIGINT[] := '{}';
    res BIGINT[] := '{}';
BEGIN
    FOR rec IN
        SELECT rt.recipe_ids_for_tag_id_array
        FROM (
            SELECT type_id, ARRAY_AGG(id) AS tag_ids_for_same_type_id
            FROM tags
            WHERE id = ANY(tag_ids)
            GROUP BY type_id
        ) AS t
        JOIN LATERAL (
            SELECT
            CASE WHEN t.type_id = 1 THEN (
                --the intersection of recipe_ids for each_tag regarding d-r type:
                    SELECT ARRAY_AGG(recipe_id)
                    FROM (
                        SELECT recipe_id
                        FROM recipes_tags
                        WHERE tag_id = ANY(t.tag_ids_for_same_type_id)
                        GROUP BY recipe_id
                        HAVING COUNT(tag_id) = ARRAY_LENGTH(t.tag_ids_for_same_type_id, 1)
                    ) AS intersected_recipes
                )
                ELSE (
                    SELECT ARRAY_AGG(DISTINCT(recipe_id))
                    FROM UNNEST(t.tag_ids_for_same_type_id) AS tagId_from_same_typeId
                    JOIN recipes_tags
                    ON recipes_tags.tag_id = tagId_from_same_typeId
                )
            END AS recipe_ids_for_tag_id_array
        ) AS rt
        ON true
    LOOP
        IF rec = '{}' THEN RETURN res;
        ELSE
            IF res = '{}' THEN res := rec;
            ELSE
            res := ARRAY(
                SELECT elem FROM UNNEST(res) AS elem
                WHERE elem = ANY(rec)
            );
            END IF;
        END IF;
    END LOOP;
    RETURN res;
END $$ LANGUAGE plpgsql;