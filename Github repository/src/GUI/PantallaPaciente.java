package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import BLL.Paciente;
import BLL.Turno;
import DLL.ControllerPaciente;

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
        setBounds(100, 100, 750, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIcono = new JLabel("");
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setIcon(new ImageIcon("F:\\Facu 3\\Programacion Avanzada\\Gestion-de-Sistema-de-Salud-copia\\Gestion-de-Sistema-de-Salud-main\\Github repository\\src\\img\\paciente.png"));
        lblIcono.setBounds(10, 11, 81, 83);
        contentPane.add(lblIcono);

        JLabel lblTitulo = new JLabel("Bienvenido/a: " + paciente.getNombre());
        lblTitulo.setForeground(Color.DARK_GRAY);
        lblTitulo.setFont(new Font("Tahoma", Font.ITALIC, 20));
        lblTitulo.setBounds(125, 11, 270, 30);
        contentPane.add(lblTitulo);

        JLabel lblFiltro = new JLabel("Filtrar por estado:");
        lblFiltro.setBounds(420, 85, 120, 14);
        contentPane.add(lblFiltro);

        JComboBox comboEstado = new JComboBox();
        comboEstado.setBounds(540, 81, 160, 25);
        contentPane.add(comboEstado);
        comboEstado.addItem("Todos");
        comboEstado.addItem("Pendiente");
        comboEstado.addItem("Atendido");
        comboEstado.addItem("Cancelado");

        model = new DefaultTableModel(new String[]{"ID", "Médico", "Especialidad", "Fecha", "Estado"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 117, 680, 180);
        contentPane.add(scrollPane);

        JLabel lblSeleccionado = new JLabel("Turno seleccionado:");
        lblSeleccionado.setBounds(20, 344, 680, 20);
        contentPane.add(lblSeleccionado);

        JButton btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(101, 52, 130, 23);
        contentPane.add(btnEditarPerfil);

        JButton btnVerPlan = new JButton("Ver Mi Plan");
        btnVerPlan.setBounds(241, 52, 130, 23);
        contentPane.add(btnVerPlan);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(610, 308, 90, 25);
        contentPane.add(btnFiltrar);

        JButton btnReservar = new JButton("Reservar Turno");
        btnReservar.setBounds(20, 375, 160, 25);
        contentPane.add(btnReservar);

        JButton btnHistorial = new JButton("Ver Historial");
        btnHistorial.setBounds(190, 375, 160, 25);
        contentPane.add(btnHistorial);

        JButton btnCancelar = new JButton("Cancelar Turno");
        btnCancelar.setBounds(360, 375, 160, 25);
        contentPane.add(btnCancelar);


        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int idTurno = (int) model.getValueAt(row, 0);
                    List<Turno> turnos = ControllerPaciente.obtenerTurnos(paciente);
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

        btnEditarPerfil.addActionListener(e -> {
            EditarPerfilPaciente editar = new EditarPerfilPaciente(paciente);
            editar.setVisible(true);
            dispose();
        });

        btnVerPlan.addActionListener(e -> paciente.verPlan());

        btnFiltrar.addActionListener(e -> {
            String estado = (String) comboEstado.getSelectedItem();
            cargarTablaFiltrada(estado);
        });

        btnReservar.addActionListener(e -> paciente.reservarTurno());

        btnHistorial.addActionListener(e -> paciente.verHistorialMedico());

        btnCancelar.addActionListener(e -> {
            if (turnoSeleccionado != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Seguro que desea cancelar el turno ID: " + turnoSeleccionado.getIdTurno() + "?",
                        "Confirmar cancelacion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    paciente.cancelarTurno(turnoSeleccionado.getIdTurno());
                    cargarTablaFiltrada("Todos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un turno primero");
            }
        });

        cargarTablaFiltrada("Todos");
    }

    private void cargarTablaFiltrada(String filtro) {
        model.setRowCount(0);
        List<Turno> turnos = ControllerPaciente.obtenerTurnos(paciente);
        for (Turno t : turnos) {
            if (filtro.equals("Todos") || t.getEstado().equalsIgnoreCase(filtro)) {
                model.addRow(new Object[]{
                        t.getIdTurno(), t.getMedico().getNombre(), t.getMedico().getEspecialidad(), t.getFecha(), t.getEstado()
                });
            }
        }
    }
}






