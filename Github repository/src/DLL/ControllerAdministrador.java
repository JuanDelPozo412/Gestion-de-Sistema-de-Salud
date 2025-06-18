package DLL;

import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;
import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;

public class ControllerAdministrador {

    private static final Connection con = Conexion.getInstance().getConnection();

    public static LinkedList<Usuario> obtenerUsuarios() {
        LinkedList<Usuario> lista = new LinkedList<>();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios");
             ResultSet rs = stmt.executeQuery()) {
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
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    public static boolean actualizarUsuario(int id, String nombre, String apellido,
                                            String mail, String dni, String pass) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, mail = ?, dni = ?, contrasenia = ? WHERE idUsuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, mail);
            stmt.setString(4, dni);
            stmt.setString(5, pass);
            stmt.setInt(6, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    public static String agregarUsuario(Usuario u) {
        String sql = """
        INSERT INTO usuarios (nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getMail());
            stmt.setString(4, u.getDni());
            stmt.setString(5, u.getContrasenia());
            stmt.setDate(6, new java.sql.Date(u.getFechaNacimiento().getTime()));
            stmt.setString(7, u.getTipoUsuario());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                return "No se pudo agregar el usuario.";
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idUsuario = rs.getInt(1);
                String tipo = u.getTipoUsuario().toLowerCase();

                switch (tipo) {
                    case "administrador":
                        if (u instanceof Administrador) {
                            Administrador admin = (Administrador) u;
                            String insert = "INSERT INTO administradores (usuario_id, cargo) VALUES (?, ?)";
                            try (PreparedStatement stmt2 = con.prepareStatement(insert)) {
                                stmt2.setInt(1, idUsuario);
                                stmt2.setString(2, admin.getCargo());
                                stmt2.executeUpdate();
                            }
                        } else {
                            return "Error: El usuario no es un Administrador válido.";
                        }
                        break;

                    case "medico":
                        if (u instanceof Medico) {
                            Medico medico = (Medico) u;
                            String insert = "INSERT INTO medicos (usuario_id, especialidad) VALUES (?, ?)";
                            try (PreparedStatement stmt2 = con.prepareStatement(insert)) {
                                stmt2.setInt(1, idUsuario);
                                stmt2.setString(2, medico.getEspecialidad());
                                stmt2.executeUpdate();
                            }
                        } else {
                            return "Error: El usuario no es un Médico válido.";
                        }
                        break;

                    case "paciente":
                        if (u instanceof Paciente) {
                            Paciente paciente = (Paciente) u;

                            String insert = "INSERT INTO pacientes (usuario_id, plan_id) VALUES (?, ?)";
                            try (PreparedStatement stmt2 = con.prepareStatement(insert)) {
                                stmt2.setInt(1, idUsuario);
                                stmt2.setInt(2, paciente.getPlanId());
                                stmt2.executeUpdate();
                            }
                        } else {
                            return "Error: El usuario no es un Paciente válido.";
                        }
                        break;

                    default:
                        return "Tipo de usuario no reconocido.";
                }

                return "Usuario agregado correctamente.";
            } else {
                return "Error al obtener el ID del usuario.";
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + e.getMessage());
            return "Error al agregar usuario.";
        }
    }

}

