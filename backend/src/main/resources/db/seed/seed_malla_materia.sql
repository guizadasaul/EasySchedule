BEGIN;

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
malla_materia_objetivo AS (
	SELECT mm.id
	FROM malla_materia mm
	JOIN mallas_objetivo mo ON mo.malla_id = mm.malla_id
)
DELETE FROM toma_materia_estudiante t
WHERE t.oferta_id IN (
	SELECT o.id
	FROM ofertas o
	JOIN malla_materia_objetivo mmo ON mmo.id = o.malla_materia_id
);

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
malla_materia_objetivo AS (
	SELECT mm.id
	FROM malla_materia mm
	JOIN mallas_objetivo mo ON mo.malla_id = mm.malla_id
)
DELETE FROM ofertas o
WHERE o.malla_materia_id IN (SELECT id FROM malla_materia_objetivo);

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
malla_materia_objetivo AS (
	SELECT mm.id
	FROM malla_materia mm
	JOIN mallas_objetivo mo ON mo.malla_id = mm.malla_id
)
DELETE FROM estado_materia_estudiante e
WHERE e.malla_materia_id IN (SELECT id FROM malla_materia_objetivo);

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
malla_materia_objetivo AS (
	SELECT mm.id
	FROM malla_materia mm
	JOIN mallas_objetivo mo ON mo.malla_id = mm.malla_id
)
DELETE FROM prerequisitos p
WHERE p.malla_materia_id IN (SELECT id FROM malla_materia_objetivo)
   OR p.prereq_malla_materia_id IN (SELECT id FROM malla_materia_objetivo);

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
)
DELETE FROM malla_materia mm
WHERE mm.malla_id IN (SELECT malla_id FROM mallas_objetivo);

WITH mallas_objetivo AS (
	SELECT m.id AS malla_id, c.codigo AS carrera_codigo
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
plan_regular AS (
	SELECT s.semestre, m.numero
	FROM (VALUES (1),(2),(3),(4),(5),(6),(7),(8)) AS s(semestre)
	JOIN (VALUES (1),(2),(3),(4)) AS m(numero) ON TRUE
),
plan_sem9 AS (
	SELECT 9::INT AS semestre, n.numero
	FROM (VALUES (1),(2)) AS n(numero)
),
plan_total AS (
	SELECT
		pr.semestre,
		'S' || pr.semestre::TEXT || '-M' || pr.numero::TEXT AS sufijo_codigo
	FROM plan_regular pr

	UNION ALL

	SELECT
		ps.semestre,
		'S9-M' || ps.numero::TEXT AS sufijo_codigo
	FROM plan_sem9 ps

	UNION ALL

	SELECT 9, 'TG1'
	UNION ALL
	SELECT 10, 'TG2'
)
INSERT INTO malla_materia (malla_id, materia_id, semestre_sugerido)
SELECT
	mo.malla_id,
	ma.id,
	pt.semestre::SMALLINT
FROM mallas_objetivo mo
JOIN plan_total pt ON TRUE
JOIN materias ma ON ma.codigo = mo.carrera_codigo || '-' || pt.sufijo_codigo
ON CONFLICT (malla_id, materia_id) DO UPDATE
SET semestre_sugerido = EXCLUDED.semestre_sugerido;

COMMIT;
