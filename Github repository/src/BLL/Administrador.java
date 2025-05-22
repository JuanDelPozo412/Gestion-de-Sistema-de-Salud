package BLL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Administrador extends Usuario {
    private String cargo;
    private static Connection con = Conexion.getInstance().getConnection();

    public Administrador(int idUsuario, String nombre, String apellido, String mail, String dni, String contrasenia, Date fechaNacimiento, String tipoUsuario, String cargo) {
        super(idUsuario, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario);
        this.cargo = cargo;
    }

    public Administrador(int idUsuario, String mail, String tipoUsuario, String cargo) {
        super(idUsuario, mail, tipoUsuario);
        this.cargo = cargo;
    }

    public Administrador(String cargo, String email, String tipo, String password, int i) {
        this.cargo = cargo;
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

//    @Override
//    public void login(String mailIngresado, String contraseniaIngresada) {
//        super.login(mailIngresado, contraseniaIngresada);
//        menuadministrador();
//    }

    public void mostrarMenuAdmin() {
        String[] opciones = {"Agregar", "Modificar", "Eliminar", "Ver usuarios", "Salir"};
        int opcion;

        do {
            opcion = JOptionPane.showOptionDialog(null,
                    "Seleccione una opcion:",
                    "CRUD de usuarios",
                    0, 0, null, opciones, opciones[0]);

            switch (opcion) {
                case 0:
                    JOptionPane.showMessageDialog(null, "agregar lo que hizo juan");
                    break;
                case 1:
                    modificarUsuario();
                    break;
                case 2:
                    eliminarUsuario();
                    break;
                case 3:
                    verUsuarios();
                    break;
            }

        } while (opcion != 4);
    }

    private void modificarUsuario() {
        try {
            LinkedList<Usuario> usuarios = Usuario.mostrarUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios para modificar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                descripciones.add(usuario.getNombre() + " - " + usuario.getMail() + " - ID: " + usuario.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(null,
                    "Seleccione usuario a modificar:",
                    "Modificar Usuario",
                    0,
                    null,
                    descripciones.toArray(),
                    descripciones.get(0));

            if (seleccion != null) {
                int index = descripciones.indexOf(seleccion);
                Usuario seleccionado = usuarios.get(index);

                String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:");
                String nuevoApellido = JOptionPane.showInputDialog("Nuevo apellido:");
                String nuevoMail = JOptionPane.showInputDialog("Nuevo mail:");
                String nuevoDni = JOptionPane.showInputDialog("Nuevo DNI:");
                String nuevaPass = JOptionPane.showInputDialog("Nueva contraseña:");

                PreparedStatement stmt = con.prepareStatement(
                        "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, dni = ?, contrasenia = ? WHERE idUsuario = ?");
                stmt.setString(1, nuevoNombre);
                stmt.setString(2, nuevoApellido);
                stmt.setString(3, nuevoMail);
                stmt.setString(4, nuevoDni);
                stmt.setString(5, nuevaPass);
                stmt.setInt(6, seleccionado.getIdUsuario());

                int filas = stmt.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        filas > 0 ? "Usuario modificado correctamente." : "No se pudo modificar el usuario");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            LinkedList<Usuario> usuarios = Usuario.mostrarUsuarios();
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios para eliminar");
                return;
            }

            ArrayList<String> descripciones = new ArrayList<>();
            for (Usuario u : usuarios) {
                descripciones.add(u.getNombre() + " - " + u.getMail() + " - ID: " + u.getIdUsuario());
            }

            String seleccion = (String) JOptionPane.showInputDialog(null,
                    "Seleccione usuario a eliminar:",
                    "Eliminar Usuario",
                    0,
                    null,
                    descripciones.toArray(),
                    descripciones.get(0));

            if (seleccion != null) {
                int index = descripciones.indexOf(seleccion);
                Usuario seleccionado = usuarios.get(index);
                PreparedStatement stmt = con.prepareStatement(
                        "DELETE FROM usuarios WHERE idUsuario = ?");
                stmt.setInt(1, seleccionado.getIdUsuario());
                int filas = stmt.executeUpdate();

                JOptionPane.showMessageDialog(null,
                        filas > 0 ? "Usuario eliminado correctamente" : "No se pudo eliminar el usuario");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }

    private void verUsuarios() {
        LinkedList<Usuario> lista = Usuario.mostrarUsuarios();
        String salida = "";
        for (Usuario usuario : lista) {
            salida += usuario.getIdUsuario() + " - " + usuario.getNombre() + " " + usuario.getApellido() + " - " + usuario.getTipoUsuario() + "\n";
        }
        JOptionPane.showMessageDialog(null, salida.isEmpty() ? "No hay usuarios" : salida);
    }
}