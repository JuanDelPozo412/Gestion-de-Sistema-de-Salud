import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String opciones[] = {"Login", "Register", "Salir"};
        boolean flag = true;
        int opcion;
        int opcionregister;
        int seleccion;
        String opcionesRegister[] = {"administrador", "medico", "paciente"};

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
                    String nuevoApellido = JOptionPane.showInputDialog("Ingrese apellido");
                    String nuevoEmail = JOptionPane.showInputDialog("Ingrese email");
                    String nuevoDni = JOptionPane.showInputDialog("Ingrese Dni");
                    String nuevoContrasenia = JOptionPane.showInputDialog("Ingrese su contrasenia");
                    Date nuevoFechaNacimiento = Date.valueOf(JOptionPane.showInputDialog("Ingrese su fecha de nacimiento en el formato dd/mm/aaaa"));
                    seleccion = JOptionPane.showOptionDialog(
                            null,
                            "Seleccione una opcion:",
                            "Menu de Medico",
                            0,
                            0,
                            null,
                            opcionesRegister,
                            opcionesRegister[0]
                    );
                    String nuevoTipoDeUsuario = opcionesRegister[seleccion];

                     Usuario nuevoUsuario = new Usuario(0, nuevoNombre,nuevoApellido, nuevoEmail,nuevoDni ,nuevoContrasenia, nuevoFechaNacimiento,nuevoTipoDeUsuario);
                     Usuario.RegistrarUsuario(nuevoUsuario);
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