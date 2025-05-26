package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BLL.Paciente;
import DLL.ControllerPaciente;

public class PantallaPaciente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Paciente paciente;

    public PantallaPaciente(Paciente paciente) {
        this.paciente = paciente;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Bienvenido/a: " + paciente.getNombre());
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTitulo.setBounds(59, 11, 237, 14);
        contentPane.add(lblTitulo);

        JLabel lblImgPaciente = new JLabel("");
        //ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/img/paciente1.png"));
        //Image imagenReducida = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        //lblImgPaciente.setIcon(new ImageIcon(imagenReducida));
        lblImgPaciente.setBounds(0, 11, 60, 60);
        contentPane.add(lblImgPaciente);

        JButton btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(59, 36, 107, 23);
        btnEditarPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paciente.editarPerfil();
            }
        });
        contentPane.add(btnEditarPerfil);

        JButton btnVerPlan = new JButton("Ver Mi Plan");
        btnVerPlan.setBounds(176, 36, 108, 23);
        btnVerPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paciente.verPlan();
            }
        });
        contentPane.add(btnVerPlan);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(10, 96, 300, 25);
        List<String> turnos = ControllerPaciente.obtenerTurnos(paciente);
        for (String turno : turnos) {
            comboBox.addItem(turno);
        }
        contentPane.add(comboBox);

        JButton btnVerHistorial = new JButton("Ver Mi Historial");
        btnVerHistorial.setBounds(294, 36, 130, 23);
        btnVerHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paciente.verHistorialMedico();
            }
        });
        contentPane.add(btnVerHistorial);
    }
}


