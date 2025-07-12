package GUI;

import BLL.Administrador;
import BLL.Usuario;
import DLL.ControllerAdministrador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;

public class PantallaAdministrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboTipoUsuario;
    private Administrador administrador;

    public PantallaAdministrador(Administrador administrador) {
        this.administrador = administrador;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Bienvenido/a: " + administrador.getNombre());
        lblTitulo.setForeground(Color.DARK_GRAY);
        lblTitulo.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblTitulo.setBounds(32, 11, 300, 30);
        contentPane.add(lblTitulo);

        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setBounds(400, 50, 100, 14);
        contentPane.add(lblFiltro);

        comboTipoUsuario = new JComboBox<>();
        comboTipoUsuario.setBounds(500, 46, 180, 25);
        contentPane.add(comboTipoUsuario);
        comboTipoUsuario.addItem("Todos");
        comboTipoUsuario.addItem("administrador");
        comboTipoUsuario.addItem("medico");
        comboTipoUsuario.addItem("paciente");

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Mail", "DNI", "Tipo Usuario"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 90, 700, 280);
        contentPane.add(scrollPane);

        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        btnAgregarUsuario.setBounds(20, 400, 140, 30);
        contentPane.add(btnAgregarUsuario);

        JButton btnModificarUsuario = new JButton("Modificar Usuario");
        btnModificarUsuario.setBounds(180, 400, 150, 30);
        contentPane.add(btnModificarUsuario);

        JButton btnEliminarUsuario = new JButton("Eliminar Usuario");
        btnEliminarUsuario.setBounds(340, 400, 140, 30);
        contentPane.add(btnEliminarUsuario);

        JButton btnVerUsuarios = new JButton("Ver Usuarios");
        btnVerUsuarios.setBounds(500, 400, 140, 30);
        contentPane.add(btnVerUsuarios);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(600, 370, 100, 25);
        contentPane.add(btnFiltrar);

        btnAgregarUsuario.addActionListener(e -> {
            PantallaAgregarUsuario ventana = new PantallaAgregarUsuario(administrador);
            ventana.setVisible(true);
        });

        btnModificarUsuario.addActionListener(e -> {
            administrador.modificarUsuario();
            cargarTablaFiltrada(Objects.requireNonNull(comboTipoUsuario.getSelectedItem()).toString());
        });

        btnEliminarUsuario.addActionListener(e -> {
            administrador.eliminarUsuario();
            cargarTablaFiltrada(Objects.requireNonNull(comboTipoUsuario.getSelectedItem()).toString());
        });

        btnVerUsuarios.addActionListener(e -> {
            administrador.verUsuarios();
        });

        btnFiltrar.addActionListener(e -> {
            String filtroSeleccionado = (String) comboTipoUsuario.getSelectedItem();
            cargarTablaFiltrada(filtroSeleccionado);
        });

        cargarTablaFiltrada("Todos");
    }

    private void cargarTablaFiltrada(String filtro) {
        model.setRowCount(0);
        LinkedList<Usuario> usuarios = ControllerAdministrador.obtenerUsuarios();

        for (Usuario u : usuarios) {
            if (u.getTipoUsuario() == null) continue;

            if ("Todos".equalsIgnoreCase(filtro) ||
                    u.getTipoUsuario().equalsIgnoreCase(filtro)) {

                model.addRow(new Object[]{
                        u.getIdUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getMail(),
                        u.getDni(),
                        u.getTipoUsuario()
                });
            }
        }
    }
}


