package GUI;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BLL.Usuario;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class PantallaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField InputEmail;
    private JPasswordField InputContraseña;

    /**
     * Launch the application.
     */
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

    /**
     * Create the frame.
     */
    public PantallaLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 783, 578);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel Titulo = new JLabel("Sistema Gestion Salud");
        Titulo.setHorizontalAlignment(SwingConstants.LEFT);
        Titulo.setForeground(Color.BLUE);
        Titulo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 19));
        Titulo.setBounds(40, 0, 215, 112);
        contentPane.add(Titulo);

        JLabel LabelImagenLogin = new JLabel("");
        LabelImagenLogin.setBackground(Color.WHITE);
        LabelImagenLogin.setHorizontalAlignment(SwingConstants.CENTER);
        LabelImagenLogin.setIcon(new ImageIcon(PantallaLogin.class.getResource("/img/imagenLoginRegister.png")));
        LabelImagenLogin.setBounds(396, 0, 371, 539);
        contentPane.add(LabelImagenLogin);

        InputEmail = new JTextField();
        InputEmail.setBounds(58, 179, 147, 30);
        contentPane.add(InputEmail);
        InputEmail.setColumns(10);

        JLabel LabelEmail = new JLabel("Nombre");
        LabelEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
        LabelEmail.setBounds(58, 145, 136, 23);
        contentPane.add(LabelEmail);

        JLabel LabelContrasenia = new JLabel("Contraseña");
        LabelContrasenia.setFont(new Font("Tahoma", Font.PLAIN, 13));
        LabelContrasenia.setBounds(58, 220, 136, 23);
        contentPane.add(LabelContrasenia);

        InputContraseña = new JPasswordField();
        InputContraseña.setBounds(58, 264, 147, 30);
        contentPane.add(InputContraseña);

        JButton BotonLogueo = new JButton("Iniciar Sesion");
        BotonLogueo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Usuario logueado = Usuario.login(InputEmail.getText(), InputContraseña.getText());
                if(logueado == null) {
                    JOptionPane.showMessageDialog(BotonLogueo, "No se encontro el usuario");
                }else JOptionPane.showMessageDialog(BotonLogueo, "Usuario logueado");


            }
        });
        BotonLogueo.setBounds(58, 337, 122, 23);
        contentPane.add(BotonLogueo);
    }
}
