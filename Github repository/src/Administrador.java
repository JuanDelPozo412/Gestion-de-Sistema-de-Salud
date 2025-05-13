import javax.swing.*;
import java.time.LocalDate;

public class Administrador extends Usuario {
    private int idAdmin;
    private String cargo;

    public Administrador(String nombre, String apellido, String email, String dni, String contrasenia, LocalDate fechaNacimiento, int idUsuario, String cargo, int idAdmin) {
        super(nombre, apellido, email, dni, contrasenia, fechaNacimiento, idUsuario);
        this.cargo = cargo;
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "cargo='" + cargo + '\'' +
                '}';
    }

    @Override
    public void login(String mailIngresado, String contraseniaIngresada) {
        super.login(mailIngresado, contraseniaIngresada);
        menuadministrador();
    }

    public void menuadministrador () {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/admin.png"));
        String[] opciones = new String[]{"Ver pacientes", "Ver medicos", "Ver personal administrativo", "Ver administrador", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu administradores",
                    0,
                    0,
                    icon,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Lista de pacientes:");
                    crud();

                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Lista de medicos:");
                    crud();

                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Lista de personal administrativo:");
                    crud();

                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Lista de administrador:");
                    crud();

                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;

            }
        } while (opcion != 4);
    }

    public void crud () {
        String[] opciones = new String[]{"Agregar", "Modificar", "Eliminar", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "Menu administradores",
                    0,
                    0,
                    null,
                    opciones,
                    opciones
            );
            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null,"Agregar");

                    break;
                case 1:
                    JOptionPane.showMessageDialog(null,"Modifciar");

                    break;
                case 2:
                    JOptionPane.showMessageDialog(null,"Eliminar");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;


            }
        } while (opcion != 3);
    }
}
