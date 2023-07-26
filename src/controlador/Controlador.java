package controlador;

import java.util.ArrayList;

import dao.EditorialesDAO;
import dao.LibrosDAO;
import excepciones.BBDDException;
import excepciones.CantidadDebeSerPositivaException;
import modelo.Editorial;
import modelo.Libro;
import vistas.NuevaEditorialDialog;
import vistas.NuevoLibroDialog;
import vistas.VentanaMostrarEditoriales;
import vistas.VentanaMostrarLibros;
import vistas.VentanaPpal;

public class Controlador {
	
	// Lista de Libros
	private ArrayList<Libro> listaLibros;
	private ArrayList<Editorial> listaEditoriales;
	// Referencias a las ventanas de la aplicación
	private VentanaPpal vPrincipal;
	private NuevoLibroDialog dNuevoLibro;
	private NuevaEditorialDialog dNuevaEditorial;
	private VentanaMostrarLibros vMostrarLibros;
	private VentanaMostrarEditoriales vMostrarEditoriales;
	
	// Definimos los objetos de acceso a datos (DAO)
	LibrosDAO daoLibro;
	EditorialesDAO daoEditoriales;
	
	public Controlador() {
		
		// Instanciamos las ventanas/cuadro de diálogo
		this.vPrincipal = new VentanaPpal();
		this.dNuevoLibro = new NuevoLibroDialog();
		this.dNuevaEditorial = new NuevaEditorialDialog();
		this.vMostrarLibros = new VentanaMostrarLibros();
		this.vMostrarEditoriales = new VentanaMostrarEditoriales();
		
		// Pasamos una copia del controlador a las vistas.
		this.vPrincipal.setControlador(this);
		this.dNuevoLibro.setControlador(this);
		this.dNuevaEditorial.setControlador(this);
		this.vMostrarLibros.setControlador(this);
		this.vMostrarEditoriales.setControlador(this);
		
		
		// Instanciamos el DAO del Libro
		this.daoLibro = new LibrosDAO();
		this.daoEditoriales = new EditorialesDAO();
	}
	
	public void iniciarPrograma() {
		this.vPrincipal.setVisible(true);
	}

	public void mostrarInsertarLibro() throws CantidadDebeSerPositivaException, BBDDException {
		this.dNuevoLibro.setModal(true);
		this.dNuevoLibro.limpiar();
		
		ArrayList<Editorial> listaEditoriales = this.daoEditoriales.getAllEditoriales();
		this.dNuevoLibro.setListaEditoriales(listaEditoriales);
		
		this.dNuevoLibro.setVisible(true);
	}
	
	public void mostrarEditarLibro(String isbn) throws CantidadDebeSerPositivaException, BBDDException {
		Libro l = this.daoLibro.getLibro(isbn);
		
		ArrayList<Editorial> listaEditoriales = this.daoEditoriales.getAllEditoriales();
		this.dNuevoLibro.setListaEditoriales(listaEditoriales);
		
		this.dNuevoLibro.setLibro(l);
		this.dNuevoLibro.setVisible(true);
		
		
	}

	public void insertaLibro(Libro l) throws BBDDException {
		this.daoLibro.insertarLibro(l);
		this.dNuevoLibro.setVisible(false);
		
	}
	
	public void editarLibro(Libro l) throws BBDDException, CantidadDebeSerPositivaException {
		this.daoLibro.editarLibro(l);
		this.dNuevoLibro.setVisible(false);
		mostrarLibros();
	}

	public void mostrarInsertarEditorial() {
		this.dNuevaEditorial.setModal(true);
		this.dNuevaEditorial.setVisible(true);
		
	}

	public void insertarEditorial(Editorial ed) throws BBDDException {
		this.daoEditoriales.insertarEditorial(ed);
		this.dNuevaEditorial.setVisible(false);
		
	}

	public void editarEditorial(Editorial ed) throws BBDDException {
		this.daoEditoriales.editarEditorial(ed);
		this.dNuevaEditorial.setVisible(false);
		mostrarEditoriales();
	}
	
	public void mostrarLibros() throws CantidadDebeSerPositivaException, BBDDException {
		
		// incvocamos al doa de libros para recoger la lista de libros de la tabla
		this.listaLibros = daoLibro.getAllLibros();
		this.vMostrarLibros.setListaLibros(this.listaLibros);
		this.vMostrarLibros.setVisible(true);
		
	}

	public void eliminarLibro(String isbn) throws BBDDException, CantidadDebeSerPositivaException {
		this.daoLibro.eliminarLibro(isbn);
		mostrarLibros();
	}
	
	public void mostrarEditoriales() throws BBDDException {
		
		// incvocamos al doa de libros para recoger la lista de libros de la tabla
		this.listaEditoriales = daoEditoriales.getAllEditoriales();
		this.vMostrarEditoriales.setListaEditoriales(this.listaEditoriales);
		this.vMostrarEditoriales.setVisible(true);
		
	}

	public void eliminarEditorial(int codEditorial) throws BBDDException {
		this.daoEditoriales.eliminarEditorial(codEditorial);
		mostrarEditoriales();
	}
	
	
	public void mostrarEditarEditorial(int codEditorial) throws BBDDException {
		Editorial ed = this.daoEditoriales.getEditorial(codEditorial);
		
		this.dNuevaEditorial.setEditorial(ed);
		this.dNuevaEditorial.setVisible(true);
		
		
	}


	
	
	
	
	
	

}
