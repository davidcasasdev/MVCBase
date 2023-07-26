package vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import excepciones.BBDDException;
import modelo.Editorial;
import net.miginfocom.swing.MigLayout;

public class NuevaEditorialDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private Controlador controlador;
	private JSpinner spinnerAnio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NuevaEditorialDialog dialog = new NuevaEditorialDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NuevaEditorialDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		{
			JLabel lblNewLabel = new JLabel("Introduzca los datos de la editorial");
			lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel, "cell 0 0 2 1");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_1, "cell 0 2,alignx trailing");
		}
		{
			txtNombre = new JTextField();
			contentPanel.add(txtNombre, "cell 1 2,growx");
			txtNombre.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Año:");
			lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
			contentPanel.add(lblNewLabel_1, "cell 0 4");
		}
		{
			spinnerAnio = new JSpinner();
			spinnerAnio.setModel(new SpinnerNumberModel(Integer.valueOf(LocalDate.now().getYear()), Integer.valueOf(1900), null, Integer.valueOf(1)));
			contentPanel.add(spinnerAnio, "cell 1 4");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Insertar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Editorial ed = validarDatos();
						if (ed!=null) {
							try {
								controlador.insertarEditorial(ed);
							} catch (BBDDException e1) {
								JOptionPane.showMessageDialog(contentPanel, 
										e1.getMessage(),
										"Error insertando los datos",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected Editorial validarDatos() {
		Editorial ed = null;
		
		String nombre = txtNombre.getText();
		int anio = (int) spinnerAnio.getValue();
		
		if (nombre==null || nombre.isBlank()) {
			JOptionPane.showMessageDialog(contentPanel, 
					"Debe introducir el nombre de la editorial",
					"error en los datos",JOptionPane.ERROR_MESSAGE);
			return ed;
		}
		ed = new Editorial(nombre, anio);
		return ed;
	}

	public void setControlador(Controlador controlador) {
		this.controlador=controlador;
	}

}
