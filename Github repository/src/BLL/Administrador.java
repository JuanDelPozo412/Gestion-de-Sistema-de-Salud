package BLL;

import DLL.ControllerAdministrador;
import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;

public class Administrador extends Usuario {

    private String cargo;

    public Administrador(int idUsuario, String nombre, String apellido,
                         String mail, String dni, String contrasenia,
                         java.util.Date fechaNacimiento, String tipoUsuario,
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

    public Administrador(int idUsuario, String mail, String tipoUsuario, String cargo, Date fechaNacimiento) {
        super(idUsuario, null, null, mail, null, null, fechaNacimiento, tipoUsuario);
        this.cargo = cargo;
    }

    public Administrador() {
        super();
    }

    public String getCargo() { return cargo; }
    public void   setCargo(String cargo) { this.cargo = cargo; }

    public static Administrador obtenerAdministradorPorIdUsuario(int idUsuario) {
        return ControllerAdministrador.obtenerAdministradorPorIdUsuario(idUsuario);
    }

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
                //case 0 -> agregarUsuario();
                case 1 -> modificarUsuario();
                case 2 -> eliminarUsuario();
                case 3 -> verUsuarios();
            }
        } while (opcion != 4);
    }

    public void agregarUsuario(String tipo) {
        try {
            String nombre   = JOptionPane.showInputDialog("Nombre:");
            String apellido = JOptionPane.showInputDialog("Apellido:");
            String mail     = JOptionPane.showInputDialog("Mail:");
            String dni      = JOptionPane.showInputDialog("DNI:");
            String pass     = JOptionPane.showInputDialog("Contraseña:");
            String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-mm-dd):");

            if (nombre == null || apellido == null || mail == null ||
                    dni == null || pass == null || fechaStr == null || tipo == null) return;

            fechaStr = fechaStr.trim();
            if (fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento es obligatoria.");
                return;
            }

            Date fechaNacimiento;
            try {
                fechaNacimiento = java.sql.Date.valueOf(fechaStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use yyyy-mm-dd (ej: 2000-05-25)");
                return;
            }

            Usuario nuevoUsuario = null;

            switch (tipo.toLowerCase()) {
                case "administrador":
                    String cargo = JOptionPane.showInputDialog("Ingrese el cargo:");
                    if (cargo == null) return;
                    nuevoUsuario = new Administrador(0, nombre, apellido, mail, dni, pass,
                            fechaNacimiento, tipo, cargo);
                    break;
                case "medico":
                    String especialidad = JOptionPane.showInputDialog("Ingrese la especialidad:");
                    if (especialidad == null) return;
                    nuevoUsuario = new Medico(0, nombre, apellido, mail, dni, pass,
                            fechaNacimiento, tipo, especialidad);
                    break;
                case "paciente":
                    int planId = 1;
                    byte[] imagen = null;
                    nuevoUsuario = new Paciente(0, nombre, apellido, mail, dni, pass,
                            fechaNacimiento, tipo, imagen, planId);
                    break;
            }

            if (nuevoUsuario != null) {
                boolean exito = ControllerAdministrador.agregarUsuario(nuevoUsuario);
                String mensaje = exito ? "Usuario agregado correctamente." : "No se pudo agregar el usuario.";
                JOptionPane.showMessageDialog(null, mensaje);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void modificarUsuario() {
        try {
            LinkedList<Usuario> usuarios = ControllerAdministrador.obtenerUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios para modificar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario u : usuarios) {
                descripciones.add(u.getNombre() + " - " + u.getMail() + " - ID: " + u.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                    null, "Seleccione usuario a modificar:", "Modificar usuario",
                    JOptionPane.PLAIN_MESSAGE, null, descripciones.toArray(), descripciones.get(0));

            if (seleccion == null) return;
            int index = descripciones.indexOf(seleccion);
            Usuario seleccionado = usuarios.get(index);

            String nuevoNombre   = JOptionPane.showInputDialog("Nuevo nombre (actual: " + seleccionado.getNombre() + "):");
            String nuevoApellido = JOptionPane.showInputDialog("Nuevo apellido (actual: " + seleccionado.getApellido() + "):");
            String nuevoMail     = JOptionPane.showInputDialog("Nuevo mail (actual: " + seleccionado.getMail() + "):");
            String nuevoDni      = JOptionPane.showInputDialog("Nuevo DNI (actual: " + seleccionado.getDni() + "):");
            String nuevaPass     = JOptionPane.showInputDialog("Nueva contraseña:");

            nuevoNombre = (nuevoNombre == null || nuevoNombre.isEmpty()) ? seleccionado.getNombre() : nuevoNombre;
            nuevoApellido = (nuevoApellido == null || nuevoApellido.isEmpty()) ? seleccionado.getApellido() : nuevoApellido;
            nuevoMail = (nuevoMail == null || nuevoMail.isEmpty()) ? seleccionado.getMail() : nuevoMail;
            nuevoDni = (nuevoDni == null || nuevoDni.isEmpty()) ? seleccionado.getDni() : nuevoDni;
            nuevaPass = (nuevaPass == null || nuevaPass.isEmpty()) ? seleccionado.getContrasenia() : nuevaPass;

            boolean ok = ControllerAdministrador.actualizarUsuario(
                    seleccionado.getIdUsuario(),
                    nuevoNombre, nuevoApellido, nuevoMail,
                    nuevoDni, nuevaPass);

            JOptionPane.showMessageDialog(null,
                    ok ? "Usuario modificado correctamente."
                            : "No se pudo modificar el usuario. Verifique la información.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al modificar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarUsuario() {
        try {
            LinkedList<Usuario> usuarios = ControllerAdministrador.obtenerUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios para eliminar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario u : usuarios) {
                descripciones.add(u.getNombre() + " - " + u.getMail() + " - ID: " + u.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                    null, "Seleccione usuario a eliminar:", "Eliminar usuario",
                    JOptionPane.PLAIN_MESSAGE, null,
                    descripciones.toArray(), descripciones.get(0));

            if (seleccion == null) return;

            int index = descripciones.indexOf(seleccion);
            Usuario seleccionado = usuarios.get(index);

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea eliminar a " + seleccionado.getNombre() + " (ID: " + seleccionado.getIdUsuario() + ")?",
                    "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = ControllerAdministrador.eliminarUsuario(seleccionado.getIdUsuario());

                JOptionPane.showMessageDialog(null,
                        ok ? "Usuario eliminado correctamente."
                                : "No se pudo eliminar el usuario. Puede haber datos relacionados en otras tablas.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void verUsuarios() {
        try {
            LinkedList<Usuario> lista = ControllerAdministrador.obtenerUsuarios();

            StringBuilder salida = new StringBuilder();
            for (Usuario u : lista) {
                salida.append("ID: ").append(u.getIdUsuario()).append("\n")
                        .append("Nombre: ").append(u.getNombre()).append("\n")
                        .append("Apellido: ").append(u.getApellido()).append("\n")
                        .append("Mail: ").append(u.getMail()).append("\n")
                        .append("DNI: ").append(u.getDni()).append("\n")
                        .append("Tipo: ").append(u.getTipoUsuario()).append("\n");

                if (u instanceof Administrador) {
                    salida.append("  Cargo: ").append(((Administrador) u).getCargo()).append("\n");
                } else if (u instanceof Medico) {
                    salida.append("  Especialidad: ").append(((Medico) u).getEspecialidad()).append("\n");
                } else if (u instanceof Paciente) {
                    salida.append("  Plan ID: ").append(((Paciente) u).getPlanId()).append("\n");
                }
                salida.append("----------------------------------\n");
            }

            JOptionPane.showMessageDialog(null,
                    salida.isEmpty() ? "No hay usuarios registrados." : salida.toString(),
                    "Listado de Usuarios", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Administrador{cargo='" + cargo + "'}";
    }
}