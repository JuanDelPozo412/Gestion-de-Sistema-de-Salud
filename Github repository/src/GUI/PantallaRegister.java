package GUI;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BLL.Usuario;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;

public class PantallaRegister extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField inputNombre;
    private JTextField inputApellido;
    private JTextField inputDni;
    private JTextField inputMail;
    private JTextField inputContrasenia;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaRegister frame = new PantallaRegister();
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
    public PantallaRegister() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 782, 578);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        inputNombre = new JTextField();
        inputNombre.setBounds(38, 96, 193, 20);
        contentPane.add(inputNombre);
        inputNombre.setColumns(10);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(PantallaRegister.class.getResource("/img/Resize iamge.png")));
        lblNewLabel.setBounds(352, 0, 414, 539);
        contentPane.add(lblNewLabel);

        JLabel LabelTituloRegister = new JLabel("Sistema Gestion Salud");
        LabelTituloRegister.setForeground(Color.BLUE);
        LabelTituloRegister.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 19));
        LabelTituloRegister.setBounds(38, 11, 257, 42);
        contentPane.add(LabelTituloRegister);

        JLabel labelNombre = new JLabel("Nombre");
        labelNombre.setBounds(38, 64, 46, 14);
        contentPane.add(labelNombre);

        JLabel labelApellido = new JLabel("Apellido");
        labelApellido.setBounds(38, 143, 46, 14);
        contentPane.add(labelApellido);

        JLabel labelMail = new JLabel("Mail");
        labelMail.setBounds(38, 214, 46, 14);
        contentPane.add(labelMail);

        JLabel labelDni = new JLabel("Dni");
        labelDni.setBounds(38, 280, 46, 14);
        contentPane.add(labelDni);

        JLabel lblNewLabel_1_4 = new JLabel("Contrasenia");
        lblNewLabel_1_4.setBounds(38, 351, 65, 14);
        contentPane.add(lblNewLabel_1_4);

        inputApellido = new JTextField();
        inputApellido.setColumns(10);
        inputApellido.setBounds(38, 168, 193, 20);
        contentPane.add(inputApellido);

        inputDni = new JTextField();
        inputDni.setColumns(10);
        inputDni.setBounds(38, 303, 193, 20);
        contentPane.add(inputDni);

        inputMail = new JTextField();
        inputMail.setColumns(10);
        inputMail.setBounds(38, 239, 193, 20);
        contentPane.add(inputMail);

        inputContrasenia = new JTextField();
        inputContrasenia.setColumns(10);
        inputContrasenia.setBounds(38, 376, 193, 20);
        contentPane.add(inputContrasenia);

        JComboBox selectTipo = new JComboBox();
        selectTipo.setModel(new DefaultComboBoxModel(new String[] {"Paciente", "Medico", "Administrador"}));
        selectTipo.setBounds(38, 441, 193, 22);
        contentPane.add(selectTipo);



        JLabel labelTipoUsuario = new JLabel("Tipo de usuario");
        labelTipoUsuario.setBounds(38, 416, 84, 14);
        contentPane.add(labelTipoUsuario);


        JButton botonRegistro = new JButton("Registrarse");
        botonRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Usuario registrar = new Usuario(inputNombre.getText(),inputApellido.getText(),inputMail.getText(),inputDni.getText(),inputContrasenia.getText(),new Date(),(String)selectTipo.getSelectedItem());
                Usuario.RegistrarUsuario(registrar);

            }
        });
        botonRegistro.setBounds(85, 482, 89, 23);
        contentPane.add(botonRegistro);

    }
}
