package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BLL.Paciente;
import BLL.PlanSalud;
import DLL.ControllerPaciente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarPerfilPaciente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inpNombre;
    private JTextField inpMail;
    private JTextField inpContrasenia;

    public EditarPerfilPaciente(Paciente paciente) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 307, 472);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(45, 42, 96, 14);
        contentPane.add(lblNombre);

        inpNombre = new JTextField();
        inpNombre.setBounds(45, 87, 184, 20);
        inpNombre.setText(paciente.getNombre());
        contentPane.add(inpNombre);
        inpNombre.setColumns(10);

        JLabel lblMail = new JLabel("Mail");
        lblMail.setBounds(45, 128, 96, 14);
        contentPane.add(lblMail);

        inpMail = new JTextField();
        inpMail.setBounds(45, 173, 184, 20);
        inpMail.setText(paciente.getMail());
        contentPane.add(inpMail);
        inpMail.setColumns(10);

        JLabel lblContraseña = new JLabel("Contraseña");
        lblContraseña.setBounds(45, 204, 96, 14);
        contentPane.add(lblContraseña);

        inpContrasenia = new JTextField();
        inpContrasenia.setBounds(45, 249, 184, 20);
        inpContrasenia.setText(paciente.getContrasenia());
        contentPane.add(inpContrasenia);
        inpContrasenia.setColumns(10);

        JLabel lblPlan = new JLabel("Plan de salud");
        lblPlan.setBounds(45, 290, 96, 14);
        contentPane.add(lblPlan);

        JComboBox comboPlanes = new JComboBox();
        comboPlanes.setBounds(45, 325, 184, 22);
        comboPlanes.addItem(new PlanSalud(1, "Básico", "Cobertura mínima"));
        comboPlanes.addItem(new PlanSalud(2, "Familiar", "Cobertura para grupo familiar"));
        comboPlanes.addItem(new PlanSalud(3, "Premium", "Cobertura completa"));
        comboPlanes.setSelectedIndex(paciente.getPlanId() - 1);
        contentPane.add(comboPlanes);

        JLabel lblMensaje = new JLabel("");
        lblMensaje.setBounds(45, 360, 277, 14);
        contentPane.add(lblMensaje);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(31, 390, 89, 23);
        contentPane.add(btnEditar);

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paciente.setNombre(inpNombre.getText());
                paciente.setMail(inpMail.getText());
                paciente.setContrasenia(inpContrasenia.getText());

                PlanSalud seleccionado = (PlanSalud) comboPlanes.getSelectedItem();
                paciente.setPlanId(seleccionado.getPlanId());

                String mensaje = paciente.editarPerfil();
                lblMensaje.setText(mensaje);
            }
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(157, 390, 89, 23);
        contentPane.add(btnVolver);

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPaciente vista = new PantallaPaciente(paciente);
                vista.setVisible(true);
                dispose();
            }
        });
    }
}

