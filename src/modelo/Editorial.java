/**
 * 
 */
package modelo;

import java.util.Objects;

/**
 * @author David
 *
 */
public class Editorial {

	private Integer codEditorial;
	private String nombre;
	private int anio;
	
	
	public Editorial(Integer codEditorial, String nombre, int anio) {
		super();
		this.codEditorial = codEditorial;
		this.nombre = nombre;
		this.anio = anio;
	}


	public Editorial(String nombre, int anio) {
		super();
		this.nombre = nombre;
		this.anio = anio;
		this.codEditorial=null;
	}


	public Integer getCodEditorial() {
		return codEditorial;
	}


	protected void setCodEditorial(Integer codEditorial) {
		this.codEditorial = codEditorial;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getAnio() {
		return anio;
	}


	public void setAnio(int anio) {
		this.anio = anio;
	}


	@Override
	public String toString() {
		return "codEditorial: " + codEditorial + "\nnombre: " + nombre + "\nanio: " + anio;
	}


	@Override
	public int hashCode() {
		return Objects.hash(codEditorial);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editorial other = (Editorial) obj;
		return Objects.equals(codEditorial, other.codEditorial);
	}
	
	
	
	
}
