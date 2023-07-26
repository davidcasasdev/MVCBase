package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import excepciones.BBDDException;
import excepciones.CantidadDebeSerPositivaException;
import modelo.Editorial;
import modelo.Libro;
import utilidades.ConexionBD;

public class EditorialesDAO {

	private ConexionBD conexion;
	
	private Statement sentencia;
	private PreparedStatement sentenciaPrep;
	private ResultSet resultado;
	private Connection con;
	
	public EditorialesDAO() {
		this.conexion = new ConexionBD();
	}
	
	/**
	 * Método de la clase q ue devuelve todos los libros de la tabla Libros
	 * @return Arraylist<Libro> con los libros o un ArrayList vacío en caso de que 
	 * no devuelva resultados. 
	 * @throws CantidadDebeSerPositivaException cuando se recogen valores negativos en cantidad
	 * @throws BBDDException se produce error en la base de datos
	 */
	public ArrayList<Editorial> getAllEditoriales() 
			throws BBDDException {
		// instanciamos la lista
		ArrayList<Editorial> lista = new ArrayList<Editorial>();
		
		
		String consulta = "select * from editoriales";
		
		try {
			// Conectamos con la base de datos
			con = this.conexion.getConexion();
			// Crea el objeto Statement con el que se pueden lanzar consultas
			sentencia = con.createStatement();
			// Se ejecuta la consulta y se recoge el ResultSet (resultado)
			resultado = sentencia.executeQuery(consulta);
			
			// Hacemos un bucle para recorrer el cursor con los resultados
			// next devuelve true mientras haya datos, false en caso contrario
			while(resultado.next()) {
				
				// recogemos todos los datos invocando a los método  getters correspondientes
				
				String nombre = resultado.getString("nombre");
				int codEditorial = resultado.getInt("CodEditorial");
				int anio = resultado.getInt("anio");
				
				
				Editorial ed = new Editorial(codEditorial, nombre, anio);
				
				
				lista.add(ed);
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta "+e.getMessage());
			throw new BBDDException("Error al realizar la consulta. Consulte con el administrador");
		} finally {
			try {
				resultado.close();
				sentencia.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("error liberando recursos. " + e.getMessage());
			}
			
		}
		return lista;
	}
	
	/**
	 * Método que devuelve un Libro dado el isbn pasado como parámetro
	 * @param isbn isbn a buscar
	 * @return Editorial con los datos del libro buscado si se ha encontrado, o 
	 * null si el Editorial no existe
	 * @throws BBDDException 
	 */
	public Editorial getEditorial(int codEditorial) throws BBDDException {
		Editorial ed = null;
		
		String consulta = "select * from editoriales where codEditorial = "+codEditorial;
		
		try {
			// Conectamos con la base de datos
			con = this.conexion.getConexion();
			// Crea el objeto Statement con el que se pueden lanzar consultas
			sentencia = con.createStatement();
			// Se ejecuta la consulta y se recoge el ResultSet (resultado)
			resultado = sentencia.executeQuery(consulta);
			
			// Hacemos un bucle para recorrer el cursor con los resultados
			// next devuelve true mientras haya datos, false en caso contrario
			if(resultado.next()) {
				
				// recogemos todos los datos invocando a los método  getters correspondientes
				String titulo = resultado.getString("nombre");
				int anio = resultado.getInt("anio");
				
				// Instanciamos el objeto de tipo Libro
				ed = new Editorial(codEditorial, titulo, anio);
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta "+e.getMessage());
			throw new BBDDException("Error al realizar la consulta. Consulte con el administrador");
		} finally {
			try {
				resultado.close();
				sentencia.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("error liberando recursos. " + e.getMessage());
			}
			
		}
		return ed;
	}
	
	/**
	 * Método que inserta un Editorial en la base de datos
	 * @param ed Editorial a insertar
	 * 
	 */
	public void insertarEditorial(Editorial ed) throws BBDDException {
		try {
			con=this.conexion.getConexion();
			
			String consulta= "";
			if (ed.getCodEditorial()==null) {
				consulta= "insert into editoriales (nombre, anio) "
						+ "values(?, ?)";
				sentenciaPrep=con.prepareStatement(consulta);
			} else {
				consulta= "insert into editoriales (nombre, anio,codEditorial) "
						+ "values(?, ?, ?)";
				sentenciaPrep=con.prepareStatement(consulta);
				sentenciaPrep.setInt(3, ed.getCodEditorial());
			}
			
			
			sentenciaPrep=con.prepareStatement(consulta);
			
			// incializamos la sentencia preparada indicando porque valor debe sustituir 
			// las interrogaciones
			sentenciaPrep.setString(1, ed.getNombre());
			sentenciaPrep.setInt(2, ed.getAnio());
			
			
			sentenciaPrep.executeUpdate();
			
			
		} catch (SQLException e1) {
			System.out.println("Error al insertar "+e1.getMessage()+e1.getErrorCode());
			// controlamos si se ha el duplicado la calve primaria
			if (e1.getErrorCode()==1062) {
				throw new BBDDException("Error insertando. Clave duplicado");
			} else if (e1.getErrorCode()==1216) {
				throw new BBDDException("Código Editorial no existe");
			}
			throw new BBDDException("Error al insertar");
		} finally {
			try {
				sentenciaPrep.close();
				conexion.desconectar();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Método de la clase que edita un libro en la base de datos
	 * @param l Libro a insertar
	 * @throws ErrorBBDDException en caso de que no se haya podido editar
	 */
	public void editarEditorial (Editorial ed) throws BBDDException {
		try {
			con=this.conexion.getConexion();
			String consulta= "update editoriales set nombre=?, anio=?"
					+ " where codEditorial=? ";
			
			sentenciaPrep=con.prepareStatement(consulta);
			
			// incializamos la sentencia preparada indicando porque valor debe sustituir 
			// las interrogaciones
			
			sentenciaPrep.setString(1, ed.getNombre());
			sentenciaPrep.setInt(2, ed.getAnio());
			sentenciaPrep.setInt(3, ed.getCodEditorial());
			
			sentenciaPrep.executeUpdate();
			
			
		} catch (SQLException e1) {
			System.out.println("Error al insertar "+e1.getMessage()+e1.getErrorCode());

			throw new BBDDException("Error editando la Editorial.");
		} finally {
			try {
				sentenciaPrep.close();
				conexion.desconectar();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Método que elimina un Editorial de la tabla de la base de datos
	 * @param codEditorial int con el codEditorial de la Editorial a borrar
	 * @return true en caso de borrado satisfactorio y false en caso contrario
	 * @throws BBDDException 
	 */
	public boolean eliminarEditorial(int codEditorial) throws BBDDException {
		boolean res=false;
		try {
			con=this.conexion.getConexion();
			String consulta= "delete from editoriales where codEditorial=?";
			
			sentenciaPrep=con.prepareStatement(consulta);
			
			// incializamos la sentencia preparada indicando porque valor debe sustituir 
			// las interrogaciones
			sentenciaPrep.setInt(1, codEditorial);

			sentenciaPrep.executeUpdate();
			res=true;
	
		} catch (SQLException e1) {
			System.out.println("Error al eliminar "+e1.getMessage());
			throw new BBDDException("Error al eliminar la Editorial");
		} finally {
			try {
				sentenciaPrep.close();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			conexion.desconectar();
		}
		return res;
	}
}
