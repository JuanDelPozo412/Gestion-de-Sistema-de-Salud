package GUI;

import BLL.Paciente;
import DLL.ControllerPaciente;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PantallaReserva extends JFrame {
    private JComboBox<String> comboEspecialidad;
    private JComboBox<String> comboMedico;
    private JComboBox<String> comboHora;
    private JDateChooser calendario;
    private JButton btnReservar;
    private Paciente paciente;

    public PantallaReserva(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Reservar Turno");
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, 30, 100, 20);
        add(lblEspecialidad);

        comboEspecialidad = new JComboBox<>();
        comboEspecialidad.setBounds(150, 30, 200, 25);
        add(comboEspecialidad);

        JLabel lblMedico = new JLabel("Medico:");
        lblMedico.setBounds(30, 70, 100, 20);
        add(lblMedico);

        comboMedico = new JComboBox<>();
        comboMedico.setBounds(150, 70, 200, 25);
        add(comboMedico);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(30, 110, 100, 20);
        add(lblFecha);

        calendario = new JDateChooser();
        calendario.setBounds(150, 110, 200, 25);
        calendario.setMinSelectableDate(new Date());
        add(calendario);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(30, 150, 100, 20);
        add(lblHora);

        comboHora = new JComboBox<>(new String[]{
                "08:00:00", "09:00:00", "10:00:00",
                "11:00:00", "12:00:00", "14:00:00",
                "15:00:00", "16:00:00"
        });
        comboHora.setBounds(150, 150, 200, 25);
        add(comboHora);

        btnReservar = new JButton("Reservar Turno");
        btnReservar.setBounds(100, 200, 180, 30);
        add(btnReservar);


        ArrayList<String> especialidades = ControllerPaciente.obtenerEspecialidades();
        for (String esp : especialidades) {
            comboEspecialidad.addItem(esp);
        }


        comboEspecialidad.addActionListener(e -> {
            comboMedico.removeAllItems();
            String especialidad = (String) comboEspecialidad.getSelectedItem();
            ArrayList<String> medicos = ControllerPaciente.obtenerMedicosPorEspecialidad(especialidad);
            for (String med : medicos) {
                comboMedico.addItem(med);
            }
        });

        btnReservar.addActionListener(e -> {
            Date fechaSeleccionada = calendario.getDate();
            String horaSeleccionada = (String) comboHora.getSelectedItem();
            String especialidad = (String) comboEspecialidad.getSelectedItem();
            String medicoTexto = (String) comboMedico.getSelectedItem();

            if (fechaSeleccionada == null || horaSeleccionada == null || medicoTexto == null) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
                return;
            }

            LocalDate hoy = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(fechaSeleccionada);
            LocalDate fechaTurno = LocalDate.parse(fechaFormateada);

            if (fechaTurno.isBefore(hoy)) {
                JOptionPane.showMessageDialog(null, "No se puede reservar en fechas pasadas");
                return;
            }


            if (ControllerPaciente.existeTurno(paciente.getIdUsuario(), medicoTexto, fechaFormateada + " " + horaSeleccionada)) {
                JOptionPane.showMessageDialog(null, "Ya tienes un turno con ese medico en esa fecha y hora");
                return;
            }

            ControllerPaciente.reservarTurnoDesdePantalla(paciente, especialidad, medicoTexto, fechaFormateada, horaSeleccionada);
        });

        setVisible(true);
    }
}

