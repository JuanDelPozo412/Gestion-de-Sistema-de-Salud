package GUI;

import javax.swing.*;
import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;
import DLL.ControllerAdministrador;

import java.awt.*;
import java.sql.Date;

public class PantallaAgregarUsuario extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtMail;
    private JTextField txtDni;
    private JTextField txtPass;
    private JTextField txtFecha;
    private JTextField txtCampoExtra;
    private JComboBox<String> comboTipo;
    private JLabel lblCampoExtra;

    public PantallaAgregarUsuario(Administrador administrador) {
        setTitle("Agregar Usuario");
        setBounds(100, 100, 500, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Agregar Usuario");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBounds(160, 10, 200, 30);
        contentPane.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 60, 100, 20);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 60, 250, 25);
        contentPane.add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(40, 95, 100, 20);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(150, 95, 250, 25);
        contentPane.add(txtApellido);

        JLabel lblMail = new JLabel("Mail:");
        lblMail.setBounds(40, 130, 100, 20);
        contentPane.add(lblMail);

        txtMail = new JTextField();
        txtMail.setBounds(150, 130, 250, 25);
        contentPane.add(txtMail);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(40, 165, 100, 20);
        contentPane.add(lblDni);

        txtDni = new JTextField();
        txtDni.setBounds(150, 165, 250, 25);
        contentPane.add(txtDni);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(40, 200, 100, 20);
        contentPane.add(lblPass);

        txtPass = new JTextField();
        txtPass.setBounds(150, 200, 250, 25);
        contentPane.add(txtPass);

        JLabel lblFecha = new JLabel("Fecha Nac. (yyyy-mm-dd):");
        lblFecha.setBounds(40, 235, 200, 20);
        contentPane.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(220, 235, 180, 25);
        contentPane.add(txtFecha);

        JLabel lblTipo = new JLabel("Tipo Usuario:");
        lblTipo.setBounds(40, 270, 100, 20);
        contentPane.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setBounds(150, 270, 250, 25);
        comboTipo.addItem("administrador");
        comboTipo.addItem("medico");
        comboTipo.addItem("paciente");
        contentPane.add(comboTipo);

        lblCampoExtra = new JLabel("Cargo:");
        lblCampoExtra.setBounds(40, 305, 100, 20);
        contentPane.add(lblCampoExtra);

        txtCampoExtra = new JTextField();
        txtCampoExtra.setBounds(150, 305, 250, 25);
        contentPane.add(txtCampoExtra);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(190, 350, 100, 30);
        contentPane.add(btnGuardar);

        comboTipo.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            if (tipo.equals("administrador")) {
                lblCampoExtra.setText("Cargo:");
            } else if (tipo.equals("medico")) {
                lblCampoExtra.setText("Especialidad:");
            } else {
                lblCampoExtra.setText("Plan ID:");
            }
        });

        btnGuardar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String mail = txtMail.getText().trim();
                String dni = txtDni.getText().trim();
                String pass = txtPass.getText().trim();
                String fechaStr = txtFecha.getText().trim();
                String tipo = (String) comboTipo.getSelectedItem();
                String extra = txtCampoExtra.getText().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || mail.isEmpty() || dni.isEmpty() ||
                        pass.isEmpty() || fechaStr.isEmpty() || extra.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }

                Date fechaNacimiento = Date.valueOf(fechaStr);

                Usuario nuevoUsuario = null;
                switch (tipo) {
                    case "administrador":
                        nuevoUsuario = new Administrador(0, nombre, apellido, mail, dni, pass, fechaNacimiento, tipo, extra);
                        break;
                    case "medico":
                        nuevoUsuario = new Medico(0, nombre, apellido, mail, dni, pass, fechaNacimiento, tipo, extra);
                        break;
                    case "paciente":
                        int planId = Integer.parseInt(extra);
                        nuevoUsuario = new Paciente(0, nombre, apellido, mail, dni, pass, fechaNacimiento, tipo, null, planId);
                        break;
                }

                if (nuevoUsuario != null) {
                    boolean ok = ControllerAdministrador.agregarUsuario(nuevoUsuario);
                    JOptionPane.showMessageDialog(null, ok ? "Usuario agregado correctamente." : "Error al agregar usuario.");
                    if (ok) dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
    }
}