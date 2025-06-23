package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import BLL.Paciente;
import BLL.Turno;

public class PantallaPaciente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Paciente paciente;
    private Turno turnoSeleccionado;

    public PantallaPaciente(Paciente paciente) {
        this.paciente = paciente;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 570);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIcono = new JLabel("");
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setBounds(10, 11, 81, 83);

        if (paciente.getFotoPerfil() != null) {
            ImageIcon icon = new ImageIcon(paciente.getFotoPerfil());
            Image img = icon.getImage().getScaledInstance(81, 83, Image.SCALE_SMOOTH);
            lblIcono.setIcon(new ImageIcon(img));
        } else {
            lblIcono.setIcon(new ImageIcon("F:\\Facu 3\\Programacion Avanzada\\Gestion-de-Sistema-de-Salud-copia\\Gestion-de-Sistema-de-Salud-main\\Github repository\\src\\img\\paciente.png"));
        }

        contentPane.add(lblIcono);

        JLabel lblTitulo = new JLabel("Bienvenido/a: " + paciente.getNombre());
        lblTitulo.setForeground(Color.DARK_GRAY);
        lblTitulo.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblTitulo.setBounds(125, 11, 270, 30);
        contentPane.add(lblTitulo);

        JButton btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(101, 52, 130, 23);
        contentPane.add(btnEditarPerfil);

        JButton btnVerPlan = new JButton("Ver Mi Plan");
        btnVerPlan.setBounds(241, 52, 130, 23);
        contentPane.add(btnVerPlan);

        JTextArea areaPlan = new JTextArea();
        areaPlan.setEditable(false);
        areaPlan.setLineWrap(true);
        areaPlan.setWrapStyleWord(true);
        areaPlan.setFont(new Font("Tahoma", Font.PLAIN, 14));
        areaPlan.setBorder(BorderFactory.createTitledBorder("Detalle de Plan de Salud"));
        areaPlan.setBounds(20, 430, 330, 100);
        contentPane.add(areaPlan);

        btnVerPlan.addActionListener(e -> {
            areaPlan.setText(paciente.obtenerPlan());
        });

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setBounds(20, 110, 690, 310);
        contentPane.add(tabs);

        JPanel panelTurnos = new JPanel();
        panelTurnos.setLayout(null);
        tabs.addTab("Turnos", null, panelTurnos, null);

        JLabel lblFiltro = new JLabel("Filtrar por estado:");
        lblFiltro.setBounds(10, 10, 120, 14);
        panelTurnos.add(lblFiltro);

        JComboBox comboEstado = new JComboBox();
        comboEstado.setBounds(130, 6, 160, 25);
        comboEstado.addItem("Todos");
        comboEstado.addItem("Pendiente");
        comboEstado.addItem("Atendido");
        comboEstado.addItem("Cancelado");
        panelTurnos.add(comboEstado);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(300, 6, 90, 25);
        panelTurnos.add(btnFiltrar);

        model = new DefaultTableModel(new String[]{"ID", "Médico", "Especialidad", "Fecha", "Estado"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 40, 660, 150);
        panelTurnos.add(scrollPane);

        JLabel lblSeleccionado = new JLabel("Turno seleccionado:");
        lblSeleccionado.setBounds(10, 200, 660, 20);
        panelTurnos.add(lblSeleccionado);

        JButton btnReservar = new JButton("Reservar Turno");
        btnReservar.setBounds(10, 230, 160, 25);
        panelTurnos.add(btnReservar);

        JButton btnCancelar = new JButton("Cancelar Turno");
        btnCancelar.setBounds(180, 230, 160, 25);
        panelTurnos.add(btnCancelar);

        JPanel panelHistorial = new JPanel();
        panelHistorial.setLayout(null);
        tabs.addTab("Historial Médico", null, panelHistorial, null);

        JTextArea areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setLineWrap(true);
        areaHistorial.setWrapStyleWord(true);
        areaHistorial.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JScrollPane scrollHistorial = new JScrollPane(areaHistorial);
        scrollHistorial.setBorder(BorderFactory.createTitledBorder("Historial Medico"));
        scrollHistorial.setBounds(20, 20, 630, 230);
        panelHistorial.add(scrollHistorial);

        JButton btnCargarHistorial = new JButton("Cargar Historial");
        btnCargarHistorial.setBounds(20, 260, 150, 25);
        panelHistorial.add(btnCargarHistorial);

        cargarTablaFiltrada("Todos");

        btnFiltrar.addActionListener(e -> {
            String estado = (String) comboEstado.getSelectedItem();
            cargarTablaFiltrada(estado);
        });

        btnReservar.addActionListener(e -> {
            PantallaReserva reserva = new PantallaReserva(paciente);
            reserva.setVisible(true);
        });

        btnEditarPerfil.addActionListener(e -> {
            EditarPerfilPaciente editar = new EditarPerfilPaciente(paciente);
            editar.setVisible(true);
            dispose();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int idTurno = (int) model.getValueAt(row, 0);
                    List<Turno> turnos = paciente.obtenerTodosMisTurnos();
                    for (Turno t : turnos) {
                        if (t.getIdTurno() == idTurno) {
                            turnoSeleccionado = t;
                            break;
                        }
                    }

                    lblSeleccionado.setText("Turno seleccionado: ID=" + model.getValueAt(row, 0)
                            + ", Médico=" + model.getValueAt(row, 1)
                            + ", Especialidad=" + model.getValueAt(row, 2)
                            + ", Fecha=" + model.getValueAt(row, 3)
                            + ", Estado=" + model.getValueAt(row, 4));
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            if (turnoSeleccionado != null) {
                List<Turno> turnos = paciente.obtenerTodosMisTurnos();
                for (Turno t : turnos) {
                    if (t.getIdTurno() == turnoSeleccionado.getIdTurno()) {
                        turnoSeleccionado = t;
                        break;
                    }
                }
                if (turnoSeleccionado.getEstado().equalsIgnoreCase("Cancelado")) {
                    JOptionPane.showMessageDialog(null, "Este turno ya está cancelado");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que desea cancelar el turno ID: " + turnoSeleccionado.getIdTurno() + "?",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    paciente.cancelarTurno(turnoSeleccionado.getIdTurno());
                    cargarTablaFiltrada("Todos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un turno primero");
            }
        });

        btnCargarHistorial.addActionListener(e -> {
            areaHistorial.setText(paciente.verHistorialMedico());
        });
    }

    private void cargarTablaFiltrada(String filtro) {
        model.setRowCount(0);
        List<Turno> turnos = paciente.obtenerTurnosFiltrados(filtro);
        for (Turno t : turnos) {
            model.addRow(new Object[]{
                    t.getIdTurno(), t.getMedico().getNombre(), t.getMedico().getEspecialidad(), t.getFecha(), t.getEstado()
            });
        }
    }
}






