export interface PerfilResponse {
	id: number;
	username: string;
	nombre: string | null;
	apellido: string | null;
	email: string | null;
	carnetIdentidad: string | null;
	fechaNacimiento: string | null;
	fechaRegistro: string | null;
	semestreActual: number | null;
	carrera: string | null;
	mallaId: number | null;
	universidad: string | null;
}

export interface MallaResponse {
	id: number;
	carrera: string;
	universidad: string;
	version: string;
}

export interface PerfilUpdateRequest {
	username: string;
	nombre: string;
	apellido: string;
	email: string;
	carnetIdentidad: string;
	fechaNacimiento: string;
	carrera: string;
	universidad: string;
}
