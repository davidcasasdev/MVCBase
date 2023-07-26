package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import excepciones.BBDDException;
import excepciones.CantidadDebeSerPositivaException;
import modelo.Libro;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMostrarLibros extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Controlador controlador;



	/**
	 * Create the frame.
	 */
	public VentanaMostrarLibros() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[218.00,grow][grow]", "[][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Listado de libros:");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		contentPane.add(lblNewLabel, "cell 0 0 2 1");
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 1 2 1,grow");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "T\u00EDtulo", "CodEditorial", "A\u00F1o", "N\u00FAm. P\u00E1ginas", "Precio", "Cantidad", "precioCD"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Integer.class, Integer.class, Double.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Eliminar Libro");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn = getIsbnSeleccionado();
				if(isbn ==null ) return;
				try {
					controlador.eliminarLibro(isbn);
				} catch (BBDDException | CantidadDebeSerPositivaException e1) {
					JOptionPane.showMessageDialog(contentPane, 
							"No se ha podido eliminar el libro",
							"Libro no eliminado",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnNewButton, "flowx,cell 0 2,alignx center");
		
		JButton btnNewButton_1 = new JButton("Editar Libro");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn = getIsbnSeleccionado();
				try {
					controlador.mostrarEditarLibro(isbn);
				} catch (CantidadDebeSerPositivaException | BBDDException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton_1, "cell 1 2,alignx center");
	}

	protected String getIsbnSeleccionado() {
		String isbn =null;
		
		int seleccionado = table.getSelectedRow();
		if (seleccionado==-1) {
			JOptionPane.showMessageDialog(contentPane, 
					"Debe seleccionar un libro",
					"Libro no seleccionado",
					JOptionPane.ERROR_MESSAGE);
			return isbn;
		}
		isbn = (String) table.getValueAt(seleccionado, 0);
		return isbn;
	}

	public void setControlador(Controlador controlador) {
		this.controlador=controlador;
	}

	public void setListaLibros(ArrayList<Libro> listaLibros) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		// vaciamos la tabla
		modelo.setRowCount(0);
		
		for(Libro l: listaLibros) {
			Object [] fila = {
					l.getIsbn(),l.getTitulo(),l.getCodEditorial(),l.getAnio(), 
					l.getNumPags(),l.getPrecio(), l.getCantidad(),l.getPrecioCD()
			};
			modelo.addRow(fila);
		}
		
	}

}
