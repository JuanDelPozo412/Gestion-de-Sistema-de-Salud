
package BLL;

import DLL.ControllerAdministrador;
import DLL.ControllerPaciente;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Administrador extends Usuario {

    private String cargo;


    public Administrador(int idUsuario, String nombre, String apellido,
                         String mail, String dni, String contrasenia,
                         Date fechaNacimiento, String tipoUsuario,
                         String cargo) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia,
                fechaNacimiento, tipoUsuario);
        this.cargo = cargo;
    }

    public Administrador(int idUsuario, String mail,
                         String tipoUsuario, String cargo) {
        super(idUsuario, mail, tipoUsuario);
        this.cargo = cargo;
    }


    public String getCargo() { return cargo; }
    public void   setCargo(String cargo) { this.cargo = cargo; }


    public void mostrarMenuAdmin() {
        String[] opciones = { "Agregar", "Modificar", "Eliminar",
                "Ver usuarios", "Salir" };
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(
                    null, "Seleccione una opcion:", "CRUD de usuarios",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            switch (opcion) {
                case 0 -> agregarUsuario();
                case 1 -> modificarUsuario();
                case 2 -> eliminarUsuario();
                case 3 -> verUsuarios();
            }
        } while (opcion != 4);
    }

    private void agregarUsuario() {
        try {
            String nombre   = JOptionPane.showInputDialog("Nombre:");
            String apellido = JOptionPane.showInputDialog("Apellido:");
            String mail     = JOptionPane.showInputDialog("Mail:");
            String dni      = JOptionPane.showInputDialog("DNI:");
            String pass     = JOptionPane.showInputDialog("Contraseña:");
            String fecha    = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-mm-dd):");
            String tipo     = JOptionPane.showInputDialog("Tipo de usuario (administrador, medico, paciente):");

            if (nombre == null || apellido == null || mail == null ||
                    dni == null || pass == null || fecha == null || tipo == null) return;

            Usuario nuevo;

            switch (tipo.toLowerCase()) {
                case "administrador" -> {
                    String cargo = JOptionPane.showInputDialog("Ingrese el cargo:");
                    nuevo = new Administrador(0, nombre, apellido, mail, dni, pass,
                            java.sql.Date.valueOf(fecha), tipo, cargo);
                }
                case "medico" -> {
                    String especialidad = JOptionPane.showInputDialog("Ingrese la especialidad:");
                    nuevo = new Medico(0, nombre, apellido, mail, dni, pass,
                            java.sql.Date.valueOf(fecha), tipo, especialidad);
                }
                case "paciente" -> {
                    List<PlanSalud> planes = ControllerPaciente.obtenerPlanes();

                    if (planes.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay planes disponibles.");
                        return;
                    }

                    String[] nombresPlanes = planes.stream()
                            .map(PlanSalud::getNombrePlan)
                            .toArray(String[]::new);

                    String planElegido = (String) JOptionPane.showInputDialog(
                            null,
                            "Seleccione un plan de salud:",
                            "Planes disponibles",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            nombresPlanes,
                            nombresPlanes[0]
                    );

                    if (planElegido == null) return;

                    int planId = planes.stream()
                            .filter(p -> p.getNombrePlan().equals(planElegido))
                            .findFirst()
                            .map(PlanSalud::getPlanId)
                            .orElse(-1);


                    if (planId == -1) {
                        JOptionPane.showMessageDialog(null, "Plan no encontrado.");
                        return;
                    }

                    nuevo = new Paciente(0, nombre, apellido, mail, dni, pass,
                            java.sql.Date.valueOf(fecha), tipo, null, null, planId);
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Tipo de usuario no reconocido.");
                    return;
                }
            }

            String mensaje = ControllerAdministrador.agregarUsuario(nuevo);
            JOptionPane.showMessageDialog(null, mensaje);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar: " + e.getMessage());
        }
    }




    private void modificarUsuario() {
        try {
            LinkedList<Usuario> usuarios =
                    ControllerAdministrador.obtenerUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No hay usuarios para modificar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario u : usuarios) {
                descripciones.add(
                        u.getNombre() + " - " + u.getMail() + " - ID: "
                                + u.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                    null, "Seleccione usuario a modificar:",
                    "Modificar usuario",
                    JOptionPane.PLAIN_MESSAGE, null,
                    descripciones.toArray(), descripciones.get(0));

            if (seleccion == null) return;
            int index = descripciones.indexOf(seleccion);
            Usuario seleccionado = usuarios.get(index);

            String nuevoNombre   = JOptionPane.showInputDialog("Nuevo nombre:");
            String nuevoApellido = JOptionPane.showInputDialog("Nuevo apellido:");
            String nuevoMail     = JOptionPane.showInputDialog("Nuevo mail:");
            String nuevoDni      = JOptionPane.showInputDialog("Nuevo DNI:");
            String nuevaPass     = JOptionPane.showInputDialog("Nueva contraseña:");

            boolean ok = ControllerAdministrador.actualizarUsuario(
                    seleccionado.getIdUsuario(),
                    nuevoNombre, nuevoApellido, nuevoMail,
                    nuevoDni, nuevaPass);

            JOptionPane.showMessageDialog(null,
                    ok ? "Usuario modificado correctamente."
                            : "No se pudo modificar el usuario");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            LinkedList<Usuario> usuarios =
                    ControllerAdministrador.obtenerUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No hay usuarios para eliminar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario u : usuarios) {
                descripciones.add(
                        u.getNombre() + " - " + u.getMail() + " - ID: "
                                + u.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                    null, "Seleccione usuario a eliminar:",
                    "Eliminar usuario",
                    JOptionPane.PLAIN_MESSAGE, null,
                    descripciones.toArray(), descripciones.get(0));

            if (seleccion == null) return;

            int index = descripciones.indexOf(seleccion);
            Usuario seleccionado = usuarios.get(index);

            boolean ok = ControllerAdministrador.eliminarUsuario(
                    seleccionado.getIdUsuario());

            JOptionPane.showMessageDialog(null,
                    ok ? "Usuario eliminado correctamente."
                            : "No se pudo eliminar el usuario");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar: " + e.getMessage());
        }
    }

    private void verUsuarios() {
        try {
            LinkedList<Usuario> lista =
                    ControllerAdministrador.obtenerUsuarios();

            StringBuilder salida = new StringBuilder();
            for (Usuario u : lista) {
                salida.append(u.getIdUsuario()).append(" - ")
                        .append(u.getNombre()).append(" ")
                        .append(u.getApellido()).append(" - ")
                        .append(u.getTipoUsuario()).append("\n");
            }

            JOptionPane.showMessageDialog(null,
                    salida.isEmpty() ? "No hay usuarios" : salida.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al listar: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "Administrador{cargo='" + cargo + "'}";
    }
}
