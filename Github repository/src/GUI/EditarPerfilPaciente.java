package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import BLL.Paciente;
import BLL.PlanSalud;
import DLL.ControllerPaciente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class EditarPerfilPaciente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inpNombre;
    private JTextField inpMail;
    private JTextField inpContrasenia;
    private JLabel lblFoto;
    private byte[] nuevaFoto;

    public EditarPerfilPaciente(Paciente paciente) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 307, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblFoto = new JLabel();
        lblFoto.setBounds(110, 10, 80, 80);
        if (paciente.getFotoPerfil() != null) {
            ImageIcon icon = new ImageIcon(paciente.getFotoPerfil());
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblFoto.setIcon(new ImageIcon(img));
        } else {
            lblFoto.setIcon(new ImageIcon("src/img/paciente.png"));
        }
        contentPane.add(lblFoto);

        JButton btnCambiarFoto = new JButton("Cambiar Foto");
        btnCambiarFoto.setBounds(95, 95, 120, 23);
        contentPane.add(btnCambiarFoto);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(45, 130, 96, 14);
        contentPane.add(lblNombre);

        inpNombre = new JTextField();
        inpNombre.setBounds(45, 155, 184, 20);
        inpNombre.setText(paciente.getNombre());
        contentPane.add(inpNombre);
        inpNombre.setColumns(10);

        JLabel lblMail = new JLabel("Mail");
        lblMail.setBounds(45, 190, 96, 14);
        contentPane.add(lblMail);

        inpMail = new JTextField();
        inpMail.setBounds(45, 215, 184, 20);
        inpMail.setText(paciente.getMail());
        contentPane.add(inpMail);
        inpMail.setColumns(10);

        JLabel lblContraseña = new JLabel("Contraseña");
        lblContraseña.setBounds(45, 250, 96, 14);
        contentPane.add(lblContraseña);

        inpContrasenia = new JTextField();
        inpContrasenia.setBounds(45, 275, 184, 20);
        inpContrasenia.setText(paciente.getContrasenia());
        contentPane.add(inpContrasenia);
        inpContrasenia.setColumns(10);

        JLabel lblPlan = new JLabel("Plan de salud");
        lblPlan.setBounds(45, 310, 96, 14);
        contentPane.add(lblPlan);

        JComboBox comboPlanes = new JComboBox();
        comboPlanes.setBounds(45, 335, 184, 22);

        List<PlanSalud> planes = ControllerPaciente.obtenerPlanes();
        for (PlanSalud plan : planes) {
            comboPlanes.addItem(plan);
        }

        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getPlanId() == paciente.getPlanId()) {
                comboPlanes.setSelectedIndex(i);
                break;
            }
        }

        contentPane.add(comboPlanes);

        JLabel lblMensaje = new JLabel("");
        lblMensaje.setBounds(45, 370, 277, 14);
        contentPane.add(lblMensaje);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(31, 400, 89, 23);
        contentPane.add(btnEditar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(157, 400, 89, 23);
        contentPane.add(btnVolver);

        btnCambiarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int resultado = fc.showOpenDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    try {
                        File archivo = fc.getSelectedFile();
                        nuevaFoto = Files.readAllBytes(archivo.toPath());
                        ImageIcon nuevaIcon = new ImageIcon(nuevaFoto);
                        Image img = nuevaIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        lblFoto.setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar imagen");
                    }
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paciente.setNombre(inpNombre.getText());
                paciente.setMail(inpMail.getText());
                paciente.setContrasenia(inpContrasenia.getText());

                PlanSalud seleccionado = (PlanSalud) comboPlanes.getSelectedItem();
                paciente.setPlanId(seleccionado.getPlanId());

                if (nuevaFoto != null) {
                    paciente.actualizarFotoPerfil(nuevaFoto);
                }

                String mensaje = paciente.editarPerfil();
                lblMensaje.setText(mensaje);
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaPaciente vista = new PantallaPaciente(paciente);
                vista.setVisible(true);
                dispose();
            }
        });
    }
}



