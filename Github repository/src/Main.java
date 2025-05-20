import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,"Si no funciona me mato");
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
                    String nombre = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario");
                    String contrasenia = JOptionPane.showInputDialog(null, "Ingresa tu contrasenia");
                    Usuario u = Usuario.login(nombre, contrasenia);

                    if (u instanceof Paciente) {
                        ((Paciente) u).mostrarMenuPaciente();
                    } else if (u instanceof Medico) {
                        ((Medico) u).menuMedico();
                    } else if (u instanceof Administrador) {
                        ((Administrador) u).mostrarMenuAdmin();
                    } else {
                        System.out.println("Usuario devuelto es nulo");
                    }
                    break;

                case 1:
                    try {
                        String[] tipos = {"paciente", "medico", "administrador"};
                        String tipo = (String) JOptionPane.showInputDialog(
                                null,
                                "Seleccione tipo de usuario:",
                                "Registro",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                tipos,
                                tipos[0]
                        );


                        String nombreRegistro = JOptionPane.showInputDialog("Ingrese nombre:");
                        String apellidoRegistro = JOptionPane.showInputDialog("Ingrese apellido:");
                        String mailRegistro = JOptionPane.showInputDialog("Ingrese mail:");
                        String dniRegistro = JOptionPane.showInputDialog("Ingrese DNI:");
                        String contraseniaRegistro = JOptionPane.showInputDialog("Ingrese contraseña:");
                        Date fechaNacimientoRegistro = Date.valueOf(JOptionPane.showInputDialog("Ingrese fecha de nacimiento (YYYY-MM-DD):"));

                        switch (tipo) {
                            case "paciente":
                                String[] planes = {"1 - Básico", "2 - Premium", "3 - Avanzado"};
                                String seleccionado = (String) JOptionPane.showInputDialog(
                                        null, "Seleccione plan de salud:", "Planes",
                                        JOptionPane.QUESTION_MESSAGE, null, planes, planes[0]);
                                int planId = Integer.parseInt(seleccionado.split(" ")[0]);

                                Paciente paciente = new Paciente(
                                        0, nombreRegistro, apellidoRegistro, mailRegistro, dniRegistro, contraseniaRegistro,
                                        fechaNacimientoRegistro, tipo, null, null, planId
                                );
                                Usuario.RegistrarUsuario(paciente);
                                break;
                            case "medico":
                                String especialidad = JOptionPane.showInputDialog("Ingrese especialidad:");
                                Medico medico = new Medico(
                                        0, nombreRegistro, apellidoRegistro, mailRegistro, dniRegistro, contraseniaRegistro,
                                        fechaNacimientoRegistro, tipo, especialidad
                                );
                                Usuario.RegistrarUsuario(medico);
                                break;

                            case "administrador":
                                String cargo = JOptionPane.showInputDialog("Ingrese cargo:");
                                Administrador admin = new Administrador(
                                        0, nombreRegistro, apellidoRegistro, mailRegistro, dniRegistro, contraseniaRegistro,
                                        fechaNacimientoRegistro, tipo, cargo
                                );
                                Usuario.RegistrarUsuario(admin);
                                break;
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "error de registro" + e.getMessage());
                    }
                case 2:

                    JOptionPane.showMessageDialog(null, "Adios masterrr");
                    flag = false;
                    break;
            }

        } while (flag);
    }
}