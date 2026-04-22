BEGIN;

-- Limpia prerequisitos de mallas activas para recalcular reglas coherentes.
WITH mallas_objetivo AS (
	SELECT m.id AS malla_id
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
mm_objetivo AS (
	SELECT mm.id
	FROM malla_materia mm
	JOIN mallas_objetivo mo ON mo.malla_id = mm.malla_id
)
DELETE FROM prerequisitos p
WHERE p.malla_materia_id IN (SELECT id FROM mm_objetivo)
   OR p.prereq_malla_materia_id IN (SELECT id FROM mm_objetivo);

WITH mallas_codigo AS (
	SELECT m.id AS malla_id, c.codigo AS carrera_codigo
	FROM mallas m
	JOIN carreras c ON c.id = m.carrera_id
	JOIN universidades u ON u.id = c.universidad_id
	WHERE m.active = TRUE
	  AND c.active = TRUE
	  AND u.active = TRUE
),
prereq_plan AS (
	-- Semestres 2..8: cada materia Mx depende de Mx del semestre previo.
	SELECT
		s.semestre AS semestre_obj,
		'S' || s.semestre::TEXT || '-M' || m.numero::TEXT AS sufijo_obj,
		'S' || (s.semestre - 1)::TEXT || '-M' || m.numero::TEXT AS sufijo_req
	FROM (VALUES (2),(3),(4),(5),(6),(7),(8)) AS s(semestre)
	JOIN (VALUES (1),(2),(3),(4)) AS m(numero) ON TRUE

	UNION ALL

	-- Semestre 9: materias regulares dependen de su linea en semestre 8.
	SELECT 9, 'S9-M1', 'S8-M1'
	UNION ALL
	SELECT 9, 'S9-M2', 'S8-M2'

	UNION ALL

	-- Taller de Grado I: requiere dos materias de 8vo.
	SELECT 9, 'TG1', 'S8-M3'
	UNION ALL
	SELECT 9, 'TG1', 'S8-M4'

	UNION ALL

	-- Taller de Grado II: requiere Taller de Grado I.
	SELECT 10, 'TG2', 'TG1'
),
pares AS (
	SELECT
		mm_obj.id AS malla_materia_id,
		mm_req.id AS prereq_malla_materia_id
	FROM mallas_codigo mc
	JOIN prereq_plan pp ON TRUE
	JOIN materias mat_obj ON mat_obj.codigo = mc.carrera_codigo || '-' || pp.sufijo_obj
	JOIN materias mat_req ON mat_req.codigo = mc.carrera_codigo || '-' || pp.sufijo_req
	JOIN malla_materia mm_obj ON mm_obj.malla_id = mc.malla_id AND mm_obj.materia_id = mat_obj.id
	JOIN malla_materia mm_req ON mm_req.malla_id = mc.malla_id AND mm_req.materia_id = mat_req.id
)
INSERT INTO prerequisitos (malla_materia_id, prereq_malla_materia_id)
SELECT p.malla_materia_id, p.prereq_malla_materia_id
FROM pares p
WHERE NOT EXISTS (
	SELECT 1
	FROM prerequisitos pr
	WHERE pr.malla_materia_id = p.malla_materia_id
	  AND pr.prereq_malla_materia_id = p.prereq_malla_materia_id
);

COMMIT;
