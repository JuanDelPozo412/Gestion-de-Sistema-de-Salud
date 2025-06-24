package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BLL.Medico;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarPerfilMedico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inpNombre;
    private JTextField inpMail;
    private JTextField inpContrasenia;

    public EditarPerfilMedico(Medico medico) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 314, 448);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nombre");
        lblNewLabel.setBounds(40, 57, 46, 14);
        contentPane.add(lblNewLabel);

        inpNombre = new JTextField();
        inpNombre.setText(medico.getNombre());
        inpNombre.setBounds(40, 82, 86, 20);
        contentPane.add(inpNombre);
        inpNombre.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Email");
        lblNewLabel_1.setBounds(40, 129, 46, 14);
        contentPane.add(lblNewLabel_1);

        inpMail = new JTextField();
        inpMail.setText(medico.getMail());
        inpMail.setBounds(40, 154, 86, 20);
        contentPane.add(inpMail);
        inpMail.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Contrasenia");
        lblNewLabel_2.setBounds(40, 204, 86, 14);
        contentPane.add(lblNewLabel_2);

        inpContrasenia = new JTextField();
        inpContrasenia.setText(medico.getContrasenia());;
        inpContrasenia.setBounds(40, 229, 86, 20);
        contentPane.add(inpContrasenia);
        inpContrasenia.setColumns(10);


        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(37, 318, 89, 23);
        contentPane.add(btnEditar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(154, 318, 89, 23);
        contentPane.add(btnVolver);
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = inpNombre.getText();
                String nuevoMail = inpMail.getText();
                String nuevaContrasenia = inpContrasenia.getText();

                medico.setNombre(nuevoNombre);
                medico.setMail(nuevoMail);
                medico.setContrasenia(nuevaContrasenia);

                String mensajeResultado = medico.editarPerfil();


                JOptionPane.showMessageDialog(EditarPerfilMedico.this, mensajeResultado);
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PantallaMedico vista = new PantallaMedico(medico);
                vista.setVisible(true);
                dispose();
            }
        });

    }
}