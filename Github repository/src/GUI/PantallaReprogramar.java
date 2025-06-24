package GUI;

import BLL.Medico;
import BLL.Turno;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaReprogramar extends JFrame {
    private JDateChooser calendario;
    private JComboBox<String> comboHora;
    private Turno turnoParaReprogramar;
    private PantallaMedico pantallaPrincipal;
    private Medico medico;

    public PantallaReprogramar(Turno turno, PantallaMedico pantallaPrincipal, Medico medico) {
        this.turnoParaReprogramar = turno;
        this.pantallaPrincipal = pantallaPrincipal;
        this.medico = medico;

        setTitle("Reprogramar Turno");
        setSize(420, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Reprogramando Turno ID: " + turno.getIdTurno());
        lblTitulo.setBounds(30, 20, 300, 20);
        add(lblTitulo);

        JLabel lblPaciente = new JLabel("Paciente: " + turno.getPaciente().getNombre());
        lblPaciente.setBounds(30, 45, 300, 20);
        add(lblPaciente);

        JLabel lblFechaActual = new JLabel("Fecha Actual: " + turno.getFecha().toString());
        lblFechaActual.setBounds(30, 70, 300, 20);
        add(lblFechaActual);

        JLabel lblNuevaFecha = new JLabel("Nueva Fecha:");
        lblNuevaFecha.setBounds(30, 110, 100, 25);
        add(lblNuevaFecha);

        calendario = new JDateChooser();
        calendario.setBounds(150, 110, 200, 25);
        calendario.setMinSelectableDate(new Date());
        add(calendario);

        JLabel lblNuevaHora = new JLabel("Nueva Hora:");
        lblNuevaHora.setBounds(30, 150, 100, 25);
        add(lblNuevaHora);

        comboHora = new JComboBox<>(new String[]{
                "08:00:00", "09:00:00", "10:00:00", "11:00:00",
                "12:00:00", "14:00:00", "15:00:00", "16:00:00"
        });
        comboHora.setBounds(150, 150, 200, 25);
        add(comboHora);

        JButton btnConfirmar = new JButton("Confirmar Reprogramación");
        btnConfirmar.setBounds(100, 200, 200, 30);
        add(btnConfirmar);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaSeleccionada = calendario.getDate();
                if (fechaSeleccionada == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una nueva fecha.");
                    return;
                }

                String horaSeleccionada = (String) comboHora.getSelectedItem();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = sdf.format(fechaSeleccionada);
                Timestamp nuevaFechaHora = Timestamp.valueOf(fechaFormateada + " " + horaSeleccionada);

                boolean exito = medico.reprogramarTurno(turnoParaReprogramar.getIdTurno(), nuevaFechaHora);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "¡Turno reprogramado con éxito!");
                    pantallaPrincipal.cargarTurnos();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo reprogramar el turno.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}