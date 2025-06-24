package GUI;

import BLL.Medico;
import BLL.Turno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PantallaMedico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Medico medico;
    private Turno turnoSeleccionado;
    private JLabel lblSeleccionado;
    private List<Turno> listaDeTurnosCompleta;

    public PantallaMedico(Medico medico) {
        this.medico = medico;
        setTitle("Portal del Médico - Dr/a. " + medico.getApellido());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 892, 435);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        model = new DefaultTableModel(new String[]{"ID Turno", "Paciente", "Fecha y Hora", "Especialidad", "Estado"}, 0) {
            // para q no se pueda editar la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(51, 119, 750, 159);
        contentPane.add(scrollPane);

        JLabel lblBienvenida = new JLabel("Bienvenido/a Dr./a: " + medico.getNombre() + " " + medico.getApellido());
        lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblBienvenida.setBounds(51, 11, 400, 38);
        contentPane.add(lblBienvenida);

        JButton btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(51, 60, 120, 23);
        contentPane.add(btnEditarPerfil);

        JLabel lblFiltrarEstado = new JLabel("Filtrar por estado:");
        lblFiltrarEstado.setBounds(537, 85, 114, 23);
        contentPane.add(lblFiltrarEstado);

        JComboBox<String> comboBoxEstado = new JComboBox<>();
        comboBoxEstado.setBounds(657, 85, 144, 22);
        contentPane.add(comboBoxEstado);
        comboBoxEstado.addItem("Todos");
        comboBoxEstado.addItem("Pendiente");
        comboBoxEstado.addItem("Atendido");
        comboBoxEstado.addItem("Cancelado");

        comboBoxEstado.addActionListener(e -> {
            String filtro = (String) comboBoxEstado.getSelectedItem();
            filtrarTabla(filtro);
        });

        lblSeleccionado = new JLabel("Turno Seleccionado: Ninguno");
        lblSeleccionado.setBounds(53, 289, 607, 14);
        contentPane.add(lblSeleccionado);

        JButton btnAtenderTurno = new JButton("Atender Turno");
        btnAtenderTurno.setBounds(51, 336, 133, 23);
        contentPane.add(btnAtenderTurno);

        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        btnCancelarTurno.setBounds(198, 336, 127, 23);
        contentPane.add(btnCancelarTurno);

        btnEditarPerfil.addActionListener(e -> {
            EditarPerfilMedico editar = new EditarPerfilMedico(medico);
            editar.setVisible(true);
            dispose();
        });

        JButton btnVerDetalles = new JButton("Ver Turno Puntual");
        btnVerDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton btnReprogramarTurno = new JButton("Reprogramar Turno");
        btnReprogramarTurno.setBounds(494, 336, 166, 23);
        contentPane.add(btnReprogramarTurno);

        btnVerDetalles.setBounds(335, 336, 149, 23);
        contentPane.add(btnVerDetalles);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int idTurnoSeleccionado = (int) model.getValueAt(table.getSelectedRow(), 0);

                for (Turno t : listaDeTurnosCompleta) {
                    if (t.getIdTurno() == idTurnoSeleccionado) {
                        turnoSeleccionado = t;
                        break;
                    }
                }
                lblSeleccionado.setText("Turno Seleccionado: ID " + turnoSeleccionado.getIdTurno() + " paciente " + turnoSeleccionado.getPaciente().getNombre());
            }
        });

        btnAtenderTurno.addActionListener(e -> {
            if (turnoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un turno de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            medico.atenderTurno(turnoSeleccionado);
            cargarTurnos();
        });

        btnCancelarTurno.addActionListener(e -> {
            if (turnoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un turno de la tabla.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!turnoSeleccionado.getEstado().equalsIgnoreCase("Pendiente")) {
                JOptionPane.showMessageDialog(this, "Solo se pueden cancelar turnos pendientes.", "Acción inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea cancelar el turno ID " + turnoSeleccionado.getIdTurno() + "?",
                    "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (medico.cancelarTurno(turnoSeleccionado.getIdTurno())) {
                    JOptionPane.showMessageDialog(this, "Turno cancelado exitosamente.");
                    cargarTurnos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo cancelar el turno.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVerDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turnoSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un turno de la tabla primero.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String detalles = "--- Detalles del Turno ---\n\n" +
                        "ID Turno: " + turnoSeleccionado.getIdTurno() + "\n" +
                        "Paciente: " + turnoSeleccionado.getPaciente().getNombre() + "\n" +
                        "Fecha/Hora: " + turnoSeleccionado.getFecha() + "\n" +
                        "Especialidad del Turno: " + turnoSeleccionado.getMedico().getEspecialidad() + "\n" +
                        "Estado: " + turnoSeleccionado.getEstado();

                JOptionPane.showMessageDialog(null, detalles, "Detalles del Turno", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnReprogramarTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (turnoSeleccionado == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un turno de la tabla para reprogramar.", "Acción Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!turnoSeleccionado.getEstado().equalsIgnoreCase("Pendiente")) {
                    JOptionPane.showMessageDialog(null, "Solo se pueden reprogramar los turnos que esten 'Pendientes'.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PantallaReprogramar pantallaRepro = new PantallaReprogramar(turnoSeleccionado, PantallaMedico.this, medico);
                pantallaRepro.setVisible(true);
            }
        });

        cargarTurnos();
    }

    public void cargarTurnos() {
        listaDeTurnosCompleta = medico.obtenerMisTurnos();
        if (listaDeTurnosCompleta == null) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los turnos del médico");
            listaDeTurnosCompleta = new ArrayList<>();
        }
        filtrarTabla("Todos");
        turnoSeleccionado = null;
        lblSeleccionado.setText("Turno Seleccionado: Ninguno");
    }

    private void filtrarTabla(String filtro) {
        model.setRowCount(0);

        for (Turno t : listaDeTurnosCompleta) {
            if (filtro.equals("Todos") || t.getEstado().equalsIgnoreCase(filtro)) {
                model.addRow(new Object[]{
                        t.getIdTurno(),
                        t.getPaciente().getNombre(),
                        t.getFecha(),
                        t.getMedico().getEspecialidad(),
                        t.getEstado()
                });
            }
        }
    }
}