package GUI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.Color;

public class PantallaRegister extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inputNombre;
    private JTextField inputApellido;
    private JTextField inputDni;
    private JTextField inputMail;
    private JTextField inputContrasenia;
    private JTextField inputDatoExtra;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaRegister frame = new PantallaRegister();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PantallaRegister() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 832, 628);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        inputNombre = new JTextField();
        inputNombre.setBounds(38, 96, 193, 20);
        contentPane.add(inputNombre);
        inputNombre.setColumns(10);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaRegister.class.getResource("/img/imagenLoginRegister.png")));
        lblNewLabel.setBounds(352, -20, 464, 628);
        contentPane.add(lblNewLabel);

        JLabel LabelTituloRegister = new JLabel("Sistema Gestion Salud");
        LabelTituloRegister.setForeground(Color.BLUE);
        LabelTituloRegister.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 19));
        LabelTituloRegister.setBounds(38, 11, 257, 42);
        contentPane.add(LabelTituloRegister);

        JLabel labelNombre = new JLabel("Nombre");
        labelNombre.setBounds(38, 64, 46, 14);
        contentPane.add(labelNombre);

        JLabel labelApellido = new JLabel("Apellido");
        labelApellido.setBounds(38, 143, 46, 14);
        contentPane.add(labelApellido);

        JLabel labelMail = new JLabel("Mail");
        labelMail.setBounds(38, 214, 46, 14);
        contentPane.add(labelMail);

        JLabel labelDni = new JLabel("Dni");
        labelDni.setBounds(38, 280, 46, 14);
        contentPane.add(labelDni);

        JLabel lblNewLabel_1_4 = new JLabel("Contrasenia");
        lblNewLabel_1_4.setBounds(38, 351, 120, 14);
        contentPane.add(lblNewLabel_1_4);

        inputApellido = new JTextField();
        inputApellido.setColumns(10);
        inputApellido.setBounds(38, 168, 193, 20);
        contentPane.add(inputApellido);

        inputDni = new JTextField();
        inputDni.setColumns(10);
        inputDni.setBounds(38, 303, 193, 20);
        contentPane.add(inputDni);

        inputMail = new JTextField();
        inputMail.setColumns(10);
        inputMail.setBounds(38, 239, 193, 20);
        contentPane.add(inputMail);

        inputContrasenia = new JTextField();
        inputContrasenia.setColumns(10);
        inputContrasenia.setBounds(38, 376, 193, 20);
        contentPane.add(inputContrasenia);

        JComboBox selectTipo = new JComboBox();
        selectTipo.setModel(new DefaultComboBoxModel(new String[]{"Paciente", "Medico", "Administrador"}));
        selectTipo.setBounds(38, 441, 193, 22);
        contentPane.add(selectTipo);

        JLabel labelTipoUsuario = new JLabel("Tipo de usuario");
        labelTipoUsuario.setBounds(38, 416, 120, 14);
        contentPane.add(labelTipoUsuario);

        inputDatoExtra = new JTextField();
        inputDatoExtra.setBounds(38, 474, 193, 22);
        inputDatoExtra.setVisible(false);
        contentPane.add(inputDatoExtra);

        selectTipo.addActionListener(e -> {
            String tipo = (String) selectTipo.getSelectedItem();
            if (tipo.equalsIgnoreCase("Paciente")) {
                inputDatoExtra.setVisible(false);
            } else {
                inputDatoExtra.setVisible(true);
                if (tipo.equalsIgnoreCase("Medico")) {
                    inputDatoExtra.setToolTipText("Especialidad");
                    inputDatoExtra.setText("");
                } else if (tipo.equalsIgnoreCase("Administrador")) {
                    inputDatoExtra.setToolTipText("Cargo");
                    inputDatoExtra.setText("");
                }
            }
        });

        JButton botonRegistro = new JButton("Registrarse");
        botonRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true;
                StringBuilder errorMessage = new StringBuilder();
                String nombre = inputNombre.getText();
                String apellido = inputApellido.getText();
                String mail = inputMail.getText();
                String dni = inputDni.getText();
                String contrasenia = inputContrasenia.getText();
                Date fechaNacimiento = new Date(); // Fecha fija por ahora
                String tipo = (String) selectTipo.getSelectedItem();
                String datoExtra = inputDatoExtra.getText();

                if (inputNombre.getText().trim().isEmpty()) {
                    isValid = false;
                    errorMessage.append("El campo Nombre no puede estar vacío.\n");
                    inputNombre.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if (inputApellido.getText().isEmpty()) {
                    isValid = false;
                    errorMessage.append("El campo Apellido no puede estar vacío.\n");
                    inputApellido.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if (inputDni.getText().trim().isEmpty()) {
                    isValid = false;
                    errorMessage.append("El campo DNI no puede estar vacío.\n");
                    inputDni.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } else if (!dni.matches("\\d+")) {
                    isValid = false;
                    errorMessage.append("El campo DNI debe contener solo números.\n");
                    inputDni.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if (inputMail.getText().isEmpty()) {
                    isValid = false;
                    errorMessage.append("El campo Mail no puede estar vacío.\n");
                    inputMail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } else if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                    isValid = false;
                    errorMessage.append("El formato del Mail es inválido.\n");
                    inputMail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
                if (inputContrasenia.getText().isEmpty()) {
                    isValid = false;
                    errorMessage.append("El campo Contraseña no puede estar vacío.\n");
                    inputContrasenia.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                } else if (contrasenia.length() < 6) {
                    isValid = false;
                    errorMessage.append("La Contraseña debe tener al menos 6 caracteres.\n");
                    inputContrasenia.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }

                if (isValid) {
                    Usuario registrar = null;

                    switch (tipo.toLowerCase()) {
                        case "paciente":
                            registrar = new Paciente(0, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipo.toLowerCase(), null, 1);
                            break;
                        case "medico":
                            registrar = new Medico(0, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipo.toLowerCase(), datoExtra);
                            break;
                        case "administrador":
                            registrar = new Administrador(0, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipo.toLowerCase(), datoExtra);
                            break;
                    }

                    if (registrar != null) {
                        Usuario.RegistrarUsuario(registrar);
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                        PantallaLogin login = new PantallaLogin();
                        login.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, errorMessage.toString(), "Errores de Validación", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botonRegistro.setBounds(85, 520, 120, 23);
        contentPane.add(botonRegistro);
    }
}

