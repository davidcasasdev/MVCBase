package vistas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import excepciones.BBDDException;
import excepciones.CantidadDebeSerPositivaException;
import modelo.Editorial;
import net.miginfocom.swing.MigLayout;

public class VentanaMostrarEditoriales extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Controlador controlador;



	/**
	 * Create the frame.
	 */
	public VentanaMostrarEditoriales() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[218.00,grow][grow]", "[][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Listado de editooriales:");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		contentPane.add(lblNewLabel, "cell 0 0 2 1");
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 1 2 1,grow");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"CodEditorial", "Nombre", "A\u00F1o"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Eliminar Editorial");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer codEditorial = getEditorialSeleccionada();
				if(codEditorial ==null ) return;
				try {
					controlador.eliminarEditorial(codEditorial);
				} catch (BBDDException e1) {
					JOptionPane.showMessageDialog(contentPane, 
							"No se ha podido eliminar la editorial",
							"Editorial no eliminado",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnNewButton, "flowx,cell 0 2,alignx center");
		
		JButton btnNewButton_1 = new JButton("Editar Editorial");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer codEditorial = getEditorialSeleccionada();
				try {
					controlador.mostrarEditarEditorial(codEditorial);
				} catch (BBDDException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton_1, "cell 1 2,alignx center");
	}

	protected Integer getEditorialSeleccionada() {
		Integer codEd =null;
		
		int seleccionado = table.getSelectedRow();
		if (seleccionado==-1) {
			JOptionPane.showMessageDialog(contentPane, 
					"Debe seleccionar una editorial",
					"Editorial no seleccionado",
					JOptionPane.ERROR_MESSAGE);
			return codEd;
		}
		codEd = (Integer) table.getValueAt(seleccionado, 0);
		return codEd;
	}

	public void setControlador(Controlador controlador) {
		this.controlador=controlador;
	}

	public void setListaEditoriales(ArrayList<Editorial> listaEditoriales) {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		// vaciamos la tabla
		modelo.setRowCount(0);
		
		for(Editorial ed: listaEditoriales) {
			Object [] fila = {
					ed.getCodEditorial(),ed.getNombre(),ed.getAnio()
			};
			modelo.addRow(fila);
		}
		
	}

}
