package GUI;

import BLL.Paciente;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import DLL.ControllerPaciente;

public class PantallaReserva extends JFrame {
    private JComboBox<String> comboEspecialidad;
    private JComboBox<String> comboMedico;
    private JComboBox<String> comboHora;
    private JDateChooser calendario;
    private JButton btnReservar;
    private JLabel lblMensaje;
    private Paciente paciente;

    public PantallaReserva(Paciente paciente) {
        this.paciente = paciente;

        setTitle("Reservar Turno");
        setSize(400, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        double precioFinal = ControllerPaciente.calcularPrecioFinal(paciente);

        JLabel lblPrecioTitulo = new JLabel("Precio Final (con desc.):");
        lblPrecioTitulo.setBounds(30, 190, 200, 25);
        add(lblPrecioTitulo);

        JLabel lblPrecioValor = new JLabel(String.format("$ %.2f", precioFinal));
        lblPrecioValor.setBounds(220, 190, 150, 25);
        lblPrecioValor.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(lblPrecioValor);

        JLabel lblEspecialidad = new JLabel("Especialidad:");
        lblEspecialidad.setBounds(30, 30, 100, 20);
        add(lblEspecialidad);


        comboEspecialidad = new JComboBox<>();
        comboEspecialidad.setBounds(150, 30, 200, 25);
        add(comboEspecialidad);

        JLabel lblMedico = new JLabel("Médico:");
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
        btnReservar.setBounds(100, 240, 180, 30);
        add(btnReservar);

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(30, 280, 340, 30);
        add(lblMensaje);


        ArrayList<String> especialidades = paciente.obtenerEspecialidades();
        for (String esp : especialidades) {
            comboEspecialidad.addItem(esp);
        }

        comboEspecialidad.addActionListener(e -> {
            comboMedico.removeAllItems();
            String especialidad = (String) comboEspecialidad.getSelectedItem();
            ArrayList<String> medicos = paciente.obtenerMedicosPorEspecialidad(especialidad);
            for (String med : medicos) {
                comboMedico.addItem(med);
            }
        });

        btnReservar.addActionListener(e -> {
            Date fechaSeleccionada = calendario.getDate();
            String horaSeleccionada = (String) comboHora.getSelectedItem();
            String especialidad = (String) comboEspecialidad.getSelectedItem();
            String medicoSeleccionado = (String) comboMedico.getSelectedItem();

            if (fechaSeleccionada == null || horaSeleccionada == null || medicoSeleccionado == null) {
                lblMensaje.setText("Complete todos los campos");
                return;
            }

            LocalDate hoy = LocalDate.now();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(fechaSeleccionada);
            LocalDate fechaTurno = LocalDate.parse(fechaFormateada);

            if (fechaTurno.isBefore(hoy)) {
                lblMensaje.setText("No se puede reservar en fechas pasadas");
                return;
            }

            if (paciente.tieneTurno(medicoSeleccionado, fechaFormateada + " " + horaSeleccionada)) {
                lblMensaje.setText("Ya tienes un turno con ese medico en esa fecha y hora");
                return;
            }

            String resultado = paciente.reservarTurno(
                    especialidad,
                    medicoSeleccionado,
                    fechaFormateada,
                    horaSeleccionada
            );
            lblMensaje.setText(resultado);
        });

        setVisible(true);
    }
}

