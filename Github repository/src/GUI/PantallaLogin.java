//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package GUI;

import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PantallaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField InputEmail;
    private JPasswordField InputContraseña;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaLogin frame = new PantallaLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public PantallaLogin() {
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 783, 578);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout((LayoutManager)null);
        JLabel Titulo = new JLabel("Sistema Gestion Salud");
        Titulo.setHorizontalAlignment(2);
        Titulo.setForeground(Color.BLUE);
        Titulo.setFont(new Font("Segoe UI Emoji", 0, 19));
        Titulo.setBounds(40, 0, 215, 112);
        this.contentPane.add(Titulo);
        JLabel LabelImagenLogin = new JLabel("");
        LabelImagenLogin.setBackground(Color.WHITE);
        LabelImagenLogin.setHorizontalAlignment(0);
        LabelImagenLogin.setIcon(new ImageIcon(PantallaLogin.class.getResource("/img/imagenLoginRegister.png")));
        LabelImagenLogin.setBounds(396, 0, 371, 539);
        this.contentPane.add(LabelImagenLogin);
        this.InputEmail = new JTextField();
        this.InputEmail.setBounds(58, 179, 147, 30);
        this.contentPane.add(this.InputEmail);
        this.InputEmail.setColumns(10);
        JLabel LabelEmail = new JLabel("Nombre");
        LabelEmail.setFont(new Font("Tahoma", 0, 13));
        LabelEmail.setBounds(58, 145, 136, 23);
        this.contentPane.add(LabelEmail);
        JLabel LabelContrasenia = new JLabel("Contraseña");
        LabelContrasenia.setFont(new Font("Tahoma", 0, 13));
        LabelContrasenia.setBounds(58, 220, 136, 23);
        this.contentPane.add(LabelContrasenia);
        this.InputContraseña = new JPasswordField();
        this.InputContraseña.setBounds(58, 264, 147, 30);
        this.contentPane.add(this.InputContraseña);
        final JButton BotonLogueo = new JButton("Iniciar Sesion");
        BotonLogueo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Usuario logueado = Usuario.login(PantallaLogin.this.InputEmail.getText(), PantallaLogin.this.InputContraseña.getText());
                if (logueado == null) {
                    JOptionPane.showMessageDialog(BotonLogueo, "No se encontro el usuario");
                } else if (logueado instanceof Paciente) {
                    PantallaPaciente vista = new PantallaPaciente((Paciente)logueado);
                    vista.setVisible(true);
                    PantallaLogin.this.dispose();
                } else if (!(logueado instanceof Medico) && logueado instanceof Administrador) {
                }

            }
        });
        BotonLogueo.setBounds(58, 337, 122, 23);
        this.contentPane.add(BotonLogueo);
    }
}
