package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import BLL.Medico;
import BLL.Paciente;
import BLL.Turno;
import DLL.ControllerMedico;


public class PantallaMedico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Medico medico;
    private Turno turnoSeleccionado;

    public PantallaMedico(Medico medico) {
        this.medico = medico;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 892, 435);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        model = new DefaultTableModel(new String[] {"ID Turno", "Paciente", "Fecha y Hora", "Especialidad", "Estado"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(51, 119, 705, 159);
        contentPane.add(scrollPane);

        JLabel lblBienvenida = new JLabel("Bienvenido/a Dr./a: " + medico.getNombre());
        lblBienvenida.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblBienvenida.setBounds(51, 11, 250, 38);
        contentPane.add(lblBienvenida);

        JButton btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(51, 51, 120, 23);
        contentPane.add(btnEditarPerfil);

        JButton btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBounds(180, 51, 120, 23);
        contentPane.add(btnCerrarSesion);


        JLabel lblFiltrarEstado = new JLabel("Filtrar por estado:");
        lblFiltrarEstado.setBounds(537, 73, 114, 23);
        contentPane.add(lblFiltrarEstado);

        JComboBox<String> comboBoxEstado = new JComboBox<>();
        comboBoxEstado.setBounds(657, 73, 99, 22);
        contentPane.add(comboBoxEstado);
        comboBoxEstado.addItem("Todos");
        comboBoxEstado.addItem("Pendiente");
        comboBoxEstado.addItem("Atendido");
        comboBoxEstado.addItem("Cancelado");

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarTablaTurnosMedico((String)comboBoxEstado.getSelectedItem());
            }
        });
        btnFiltrar.setBounds(670, 289, 89, 23);
        contentPane.add(btnFiltrar);

        JLabel lblTurnoSeleccionadoInfo = new JLabel("Turno Seleccionado: ");
        lblTurnoSeleccionadoInfo.setBounds(53, 311, 607, 14);
        contentPane.add(lblTurnoSeleccionadoInfo);

        JButton btnAtenderTurno = new JButton("Atender Turno");
        btnAtenderTurno.setBounds(51, 336, 114, 23);
        contentPane.add(btnAtenderTurno);

        JButton btnReprogramarTurno = new JButton("Reprogramar Turno");
        btnReprogramarTurno.setBounds(175, 336, 127, 23);
        contentPane.add(btnReprogramarTurno);

        JButton btnCancelarTurno = new JButton("Cancelar Turno");
        btnCancelarTurno.setBounds(312, 336, 114, 23);
        contentPane.add(btnCancelarTurno);

        JButton btnAgregarDiagnostico = new JButton("Agregar Diagnostico");
        btnAgregarDiagnostico.setBounds(436, 336, 129, 23);
        contentPane.add(btnAgregarDiagnostico);

        JButton btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.setBounds(571, 336, 89, 23);
        contentPane.add(btnVerDetalles);

        cargarTablaTurnosMedico("Todos");
    }


    private void cargarTablaTurnosMedico(String filtro) {
        model.setRowCount(0);

        List<Turno> turnos = ControllerMedico.obtenerTodosLosTurnosDeMedico(medico.getIdUsuario());

        if (turnos != null) {
            for (Turno t : turnos) {
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
        } else {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los turnos del médico");
        }
    }
}