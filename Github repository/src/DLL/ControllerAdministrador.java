package DLL;

import BLL.Usuario;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;

public class ControllerAdministrador {

    private static final Connection con = Conexion.getInstance().getConnection();

    public static LinkedList<Usuario> obtenerUsuarios() {
        LinkedList<Usuario> lista = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("mail"),
                        rs.getString("dni"),
                        rs.getString("contrasenia"),
                        rs.getDate("fechaNacimiento"),
                        rs.getString("tipoUsuario")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios");
        }
        return lista;
    }

    public static boolean actualizarUsuario(int id, String nombre, String apellido,
                                            String mail, String dni, String pass) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, dni = ?, contrasenia = ? " +
                            "WHERE idUsuario = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, mail);
            stmt.setString(4, dni);
            stmt.setString(5, pass);
            stmt.setInt(6, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario");
            return false;
        }
    }

    public static boolean eliminarUsuario(int id) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM usuarios WHERE idUsuario = ?"
            );
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario");
            return false;
        }
    }
    public static String agregarUsuario(Usuario u) throws Exception {
        String sql = """
                 INSERT INTO usuarios (nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario)
                 VALUES (?, ?, ?, ?, ?, ?, ?)
                 """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getMail());
            stmt.setString(4, u.getDni());
            stmt.setString(5, u.getContrasenia());
            stmt.setDate(6, new java.sql.Date(u.getFechaNacimiento().getTime()));
            stmt.setString(7, u.getTipoUsuario());

            int filas = stmt.executeUpdate();
            return (filas > 0) ? "Usuario agregado correctamente." : "No se pudo agregar el usuario.";
        }
    }

}
