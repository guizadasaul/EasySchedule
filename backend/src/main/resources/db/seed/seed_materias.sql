BEGIN;

WITH carreras_codigo AS (
	SELECT DISTINCT c.codigo
	FROM carreras c
	JOIN universidades u ON u.id = c.universidad_id
	WHERE c.active = TRUE
	  AND u.active = TRUE
),
materias_regular AS (
	SELECT
		cc.codigo || '-S' || s.semestre::TEXT || '-M' || m.numero::TEXT AS codigo,
		'Materia ' || cc.codigo || ' S' || s.semestre::TEXT || ' M' || m.numero::TEXT AS nombre,
		CASE
			WHEN s.semestre <= 2 THEN 5
			WHEN s.semestre <= 8 THEN 4
			ELSE 4
		END AS creditos,
		TRUE AS active
	FROM carreras_codigo cc
	JOIN (VALUES (1),(2),(3),(4),(5),(6),(7),(8)) AS s(semestre) ON TRUE
	JOIN (VALUES (1),(2),(3),(4)) AS m(numero) ON TRUE

	UNION ALL

	SELECT
		cc.codigo || '-S9-M' || m.numero::TEXT AS codigo,
		CASE
			WHEN m.numero = 1 THEN 'Integracion Profesional ' || cc.codigo
			ELSE 'Electiva de Profundizacion ' || cc.codigo
		END AS nombre,
		4 AS creditos,
		TRUE AS active
	FROM carreras_codigo cc
	JOIN (VALUES (1),(2)) AS m(numero) ON TRUE
),
materias_tg AS (
	SELECT
		cc.codigo || '-TG1' AS codigo,
		'Taller de Grado I ' || cc.codigo AS nombre,
		5 AS creditos,
		TRUE AS active
	FROM carreras_codigo cc

	UNION ALL

	SELECT
		cc.codigo || '-TG2' AS codigo,
		'Taller de Grado II ' || cc.codigo AS nombre,
		6 AS creditos,
		TRUE AS active
	FROM carreras_codigo cc
),
materias_base AS (
	SELECT * FROM materias_regular
	UNION ALL
	SELECT * FROM materias_tg
)
INSERT INTO materias (codigo, nombre, creditos, active)
SELECT mb.codigo, mb.nombre, mb.creditos, mb.active
FROM materias_base mb
ON CONFLICT (codigo) DO UPDATE
SET nombre = EXCLUDED.nombre,
	creditos = EXCLUDED.creditos,
	active = EXCLUDED.active;

COMMIT;
