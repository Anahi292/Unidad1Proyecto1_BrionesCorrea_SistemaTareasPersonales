package modelo;

public class Tarea {

	private String descripcion;
	private String categoria;
	private String notas;
	private boolean urgente;
	private String estado;

	// CONSTRUCTOR PARA EL CONTROLADOR
	public Tarea(String descripcion, String categoria, String notas, boolean urgente, String estado) {
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.notas = notas;
		this.urgente = urgente;
		this.estado = estado;
	}

	// CONSTRUCTOR 
	public Tarea(String descripcion) {
		this(descripcion, "", "", false, "Pendiente");
	}

	// GETTERS Y SETTERS
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	// TO STRING PARA MOSTRAR EN TABLAS
	@Override
	public String toString() {
		return descripcion + (categoria != null && !categoria.isEmpty() ? " [" + categoria + "]" : "")
				+ (urgente ? " (URGENTE)" : "");
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		if (descripcion == null)
			return other.descripcion == null;
		return descripcion.equalsIgnoreCase(other.descripcion);
	}

	@Override
	public int hashCode() {
		return descripcion == null ? 0 : descripcion.toLowerCase().hashCode();
	}
}
