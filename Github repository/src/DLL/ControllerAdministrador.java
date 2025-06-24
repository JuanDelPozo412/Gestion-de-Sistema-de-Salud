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

    static {
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Error CRÍTICO: La conexión a la base de datos es nula en ControllerAdministrador. La aplicación no funcionará correctamente.");
        }
    }

    public static LinkedList<Usuario> obtenerUsuarios() {
        LinkedList<Usuario> lista = new LinkedList<>();
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Error de conexión: No se pueden obtener usuarios.");
            return lista;
        }

        String sql = "SELECT u.*, a.cargo, m.especialidad, p.plan_id " +
                "FROM usuarios u " +
                "LEFT JOIN administradores a ON u.idUsuario = a.usuario_id " +
                "LEFT JOIN medicos m ON u.idUsuario = m.usuario_id " +
                "LEFT JOIN pacientes p ON u.idUsuario = p.usuario_id";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String tipoUsuario = rs.getString("tipoUsuario");
                Usuario u;

                if (tipoUsuario == null) {
                    u = new Usuario(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("mail"),
                            rs.getString("dni"),
                            rs.getString("contrasenia"),
                            rs.getDate("fechaNacimiento"),
                            null
                    );
                } else {
                    switch (tipoUsuario.toLowerCase()) {
                        case "administrador":
                            u = new Administrador(
                                    rs.getInt("idUsuario"),
                                    rs.getString("nombre"),
                                    rs.getString("apellido"),
                                    rs.getString("mail"),
                                    rs.getString("dni"),
                                    rs.getString("contrasenia"),
                                    rs.getDate("fechaNacimiento"),
                                    tipoUsuario,
                                    rs.getString("cargo")
                            );
                            break;
                        case "medico":
                            u = new Medico(
                                    rs.getInt("idUsuario"),
                                    rs.getString("nombre"),
                                    rs.getString("apellido"),
                                    rs.getString("mail"),
                                    rs.getString("dni"),
                                    rs.getString("contrasenia"),
                                    rs.getDate("fechaNacimiento"),
                                    tipoUsuario,
                                    rs.getString("especialidad")
                            );
                            break;
                        case "paciente":
                            u = new Paciente(
                                    rs.getInt("idUsuario"),
                                    rs.getString("nombre"),
                                    rs.getString("apellido"),
                                    rs.getString("mail"),
                                    rs.getString("dni"),
                                    rs.getString("contrasenia"),
                                    rs.getDate("fechaNacimiento"),
                                    tipoUsuario,
                                    rs.getBytes("foto_perfil"),
                                    rs.getInt("plan_id")
                            );
                            break;
                        default:
                            u = new Usuario(
                                    rs.getInt("idUsuario"),
                                    rs.getString("nombre"),
                                    rs.getString("apellido"),
                                    rs.getString("mail"),
                                    rs.getString("dni"),
                                    rs.getString("contrasenia"),
                                    rs.getDate("fechaNacimiento"),
                                    tipoUsuario
                            );
                            break;
                    }
                }

                lista.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean actualizarUsuario(int id, String nombre, String apellido,
                                            String mail, String dni, String pass) {
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Error de conexión: No se puede actualizar usuario.");
            return false;
        }
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
            e.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarUsuario(int id) {
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Error de conexión: No se puede eliminar usuario.");
            return false;
        }
        String sqlUsuario = "DELETE FROM usuarios WHERE idUsuario = ?";

        try {
            con.setAutoCommit(false);

            String sqlAdmin = "DELETE FROM administradores WHERE usuario_id = ?";
            try (PreparedStatement stmtAdmin = con.prepareStatement(sqlAdmin)) {
                stmtAdmin.setInt(1, id);
                stmtAdmin.executeUpdate();
            }

            String sqlMedico = "DELETE FROM medicos WHERE usuario_id = ?";
            try (PreparedStatement stmtMedico = con.prepareStatement(sqlMedico)) {
                stmtMedico.setInt(1, id);
                stmtMedico.executeUpdate();
            }

            String sqlPaciente = "DELETE FROM pacientes WHERE usuario_id = ?";
            try (PreparedStatement stmtPaciente = con.prepareStatement(sqlPaciente)) {
                stmtPaciente.setInt(1, id);
                stmtPaciente.executeUpdate();
            }

            try (PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario)) {
                stmtUsuario.setInt(1, id);
                int filasAfectadas = stmtUsuario.executeUpdate();
                if (filasAfectadas > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error al realizar rollback: " + rollbackEx.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                System.err.println("Error al restaurar auto-commit: " + autoCommitEx.getMessage());
            }
        }
    }

    public static boolean agregarUsuario(Usuario usuario) {
        try {
            String sqlInsertUsuario = "INSERT INTO usuarios (nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario, foto_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = Conexion.getInstance().getConnection().prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getMail());
            statement.setString(4, usuario.getDni());
            statement.setString(5, usuario.getContrasenia());
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
            statement.setDate(6, sqlDate);
            statement.setString(7, usuario.getTipoUsuario());
            statement.setBytes(8, usuario.getFotoPerfil());

            int filas = statement.executeUpdate();
            if (filas == 0) return false;

            ResultSet rs = statement.getGeneratedKeys();
            if (!rs.next()) return false;
            int idGenerado = rs.getInt(1);
            usuario.setIdUsuario(idGenerado);

            PreparedStatement insertTabla = null;
            String tipo = usuario.getTipoUsuario();

            switch (tipo) {
                case "paciente":
                    if (usuario instanceof Paciente) {
                        insertTabla = Conexion.getInstance().getConnection().prepareStatement("INSERT INTO pacientes (usuario_id, plan_id) VALUES (?, ?)");
                        insertTabla.setInt(1, idGenerado);
                        insertTabla.setInt(2, ((Paciente) usuario).getPlanId());
                    }
                    break;
                case "medico":
                    if (usuario instanceof Medico) {
                        insertTabla = Conexion.getInstance().getConnection().prepareStatement("INSERT INTO medicos (usuario_id, especialidad) VALUES (?, ?)");
                        insertTabla.setInt(1, idGenerado);
                        insertTabla.setString(2, ((Medico) usuario).getEspecialidad());
                    }
                    break;
                case "administrador":
                    if (usuario instanceof Administrador) {
                        insertTabla = Conexion.getInstance().getConnection().prepareStatement("INSERT INTO administradores (usuario_id, cargo) VALUES (?, ?)");
                        insertTabla.setInt(1, idGenerado);
                        insertTabla.setString(2, ((Administrador) usuario).getCargo());
                    }
                    break;
            }

            if (insertTabla != null) insertTabla.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error en agregarUsuario desde ControllerAdministrador: " + e.getMessage());
            return false;
        }
    }

    public static Administrador obtenerAdministradorPorIdUsuario(int idUsuario) {
        Administrador admin = null;
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Error de conexión: No se puede obtener el administrador por ID.");
            return null;
        }
        String sql = "SELECT u.*, a.cargo FROM usuarios u JOIN administradores a ON u.idUsuario = a.usuario_id WHERE u.idUsuario = ? AND u.tipoUsuario = 'administrador'";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin = new Administrador(
                            rs.getInt("idUsuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("mail"),
                            rs.getString("dni"),
                            rs.getString("contrasenia"),
                            rs.getDate("fechaNacimiento"),
                            rs.getString("tipoUsuario"),
                            rs.getString("cargo")
                    );
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener administrador por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return admin;
    }
}
