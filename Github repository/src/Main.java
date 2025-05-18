import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        String opciones[] = {"Login", "Register", "Salir"};
        boolean flag = true;
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null,
                    "Seleccione una opcion:",
                    "Menu de Medico",
                    0,
                    0,
                    null,
                    opciones,
                    opciones[0]
            );

            switch (opcion) {
                case 0:
                    // LOGIN
                    String nombre = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario");
                    String contrasenia = JOptionPane.showInputDialog(null, "Ingresa tu contrasenia");
                    Usuario u = Usuario.login(nombre, contrasenia);

                    if (u instanceof Paciente) {
                        ((Paciente) u).mostrarMenuPaciente();
                    } else if (u instanceof Medico) {
                        // ((Medico) u).mostrarMenuMedico(); //  AGREGAR ESTO.
                    } else if (u instanceof Administrador) {
                        ((Administrador) u).menuadministrador();
                    } else {
                        System.out.println("Usuario devuelto es nulo");
                    }
                    break;

                case 1:
                    // REGISTRO
                    String nuevoNombre = JOptionPane.showInputDialog("Ingrese nombre");
                    String nuevoEmail = JOptionPane.showInputDialog("Ingrese email");
                    String nuevoTipo = JOptionPane.showInputDialog("Tipo (admin, cliente, etc)");
                    String nuevaPass = JOptionPane.showInputDialog("Ingrese contraseña");

                    // Usuario nuevoUsuario = new Usuario(0, nuevoNombre, nuevoEmail, nuevoTipo, nuevaPass);
                    // Usuario.RegistrarUsuario(nuevoUsuario);
                    break;

                case 2:
                    // SALIR
                    JOptionPane.showMessageDialog(null,"Adios masterrr");
                    flag = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "default");
                    break;
            }

        } while (flag);
    }
}