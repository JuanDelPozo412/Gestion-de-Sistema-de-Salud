package GUI;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaHome frame = new PantallaHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PantallaHome() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 730, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Bienvenido a Medifed");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLUE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 20, 714, 30);
        contentPane.add(lblTitulo);


        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/Medifed.jpg"));
        Image image = originalIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        JLabel lblImagen = new JLabel(scaledIcon);
        lblImagen.setBounds(165, 80, 400, 200);
        contentPane.add(lblImagen);

        JButton btnLogin = new JButton("Iniciar Sesion");
        btnLogin.setBounds(150, 330, 180, 40);
        contentPane.add(btnLogin);

        JButton btnRegister = new JButton("Registrarse");
        btnRegister.setBounds(400, 330, 180, 40);
        contentPane.add(btnRegister);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                PantallaLogin login = new PantallaLogin();
                login.setVisible(true);
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                PantallaRegister register = new PantallaRegister();
                register.setVisible(true);
            }
        });
    }
}
