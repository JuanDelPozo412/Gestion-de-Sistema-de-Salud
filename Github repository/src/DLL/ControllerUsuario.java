package DLL;
import BLL.Administrador;
import BLL.Medico;
import BLL.Paciente;
import BLL.Usuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;

public class ControllerUsuario {

    private static Connection con = Conexion.getInstance().getConnection();

    public static Usuario login(String nombre, String password) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?"
            );
            stmt.setString(1, nombre);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("idUsuario");
                String apellido = rs.getString("apellido");
                String mail = rs.getString("mail");
                String dni = rs.getString("dni");
                Date fechaNacimiento = rs.getDate("fechaNacimiento");
                String tipo = rs.getString("tipoUsuario");

                switch (tipo) {
                    case "paciente":
                        PreparedStatement psPaciente = con.prepareStatement(
                                "SELECT * FROM pacientes WHERE usuario_id = ?"
                        );
                        psPaciente.setInt(1, id);
                        ResultSet rsPaciente = psPaciente.executeQuery();

                        if (rsPaciente.next()) {
                            int planId = rsPaciente.getInt("plan_id");
                            return new Paciente(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, null, null, planId);
                        }
                        break;

                    case "medico":
                        PreparedStatement psMedico = con.prepareStatement(
                                "SELECT * FROM medicos WHERE usuario_id = ?"
                        );
                        psMedico.setInt(1, id);
                        ResultSet rsMedico = psMedico.executeQuery();

                        if (rsMedico.next()) {
                            String especialidad = rsMedico.getString("especialidad");
                            return new Medico(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, especialidad);
                        }
                        break;

                    case "administrador":
                        PreparedStatement psAdmin = con.prepareStatement(
                                "SELECT * FROM administradores WHERE usuario_id = ?"
                        );
                        psAdmin.setInt(1, id);
                        ResultSet rsAdmin = psAdmin.executeQuery();

                        if (rsAdmin.next()) {
                            String cargo = rsAdmin.getString("cargo");
                            return new Administrador(id, nombre, apellido, mail, dni, password, fechaNacimiento, tipo, cargo);
                        }
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }
    public static void agregarUsuario(Usuario usuario) {
        try {

            String sqlInsertUsuario = "INSERT INTO usuarios (nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipoUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getMail());
            statement.setString(4, usuario.getDni());
            statement.setString(5, usuario.getContrasenia());
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
            statement.setDate(6, sqlDate);

            statement.setString(7, usuario.getTipoUsuario());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    usuario.setIdUsuario(idGenerado);
                    System.out.println("BLL.Usuario agregado con ID: " + idGenerado);
                }
            } else {
                System.out.println("No se pudo insertar el usuario.");
                return;
            }


            String tipo = usuario.getTipoUsuario();
            PreparedStatement insertTabla = null;
            switch (tipo) {
                case "paciente":
                    if (usuario instanceof Paciente) {
                        int planId = ((Paciente) usuario).getPlanId();
                        insertTabla = con.prepareStatement("INSERT INTO pacientes (usuario_id, plan_id) VALUES (?, ?)");
                        insertTabla.setInt(1, usuario.getIdUsuario());
                        insertTabla.setInt(2, planId);
                    } else {
                        System.out.println("el objeto no es una instancia de Paciente.");
                        return;
                    }
                    break;

                case "medico":
                    if (usuario instanceof Medico) {
                        String especialidad = ((Medico) usuario).getEspecialidad();
                        insertTabla = con.prepareStatement("INSERT INTO medicos (usuario_id, especialidad) VALUES (?, ?)");
                        insertTabla.setInt(1, usuario.getIdUsuario());
                        insertTabla.setString(2, especialidad);
                    } else {
                        System.out.println("el objeto no es una instancia de Medico.");
                        return;
                    }
                    break;

                case "administrador":
                    if (usuario instanceof Administrador) {
                        String cargo = ((Administrador) usuario).getCargo();
                        insertTabla = con.prepareStatement("INSERT INTO administradores (usuario_id, cargo) VALUES (?, ?)");
                        insertTabla.setInt(1, usuario.getIdUsuario());
                        insertTabla.setString(2, cargo);
                    } else {
                        System.out.println("el objeto no es una instancia de Administrador.");
                        return;
                    }
                    break;
            }

            if (insertTabla != null) {
                insertTabla.executeUpdate();
                System.out.println("Insertado correctamente en tabla tipo " + tipo);
            }

        } catch (Exception e) {
            System.out.println("error en agregarUsuario: " + e.getMessage());
        }
    }


    public static LinkedList<Usuario> mostrarUsuarios() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String mail = rs.getString("mail");
                String dni = rs.getString("dni");
                String contrasenia = rs.getString("contrasenia");
                Date fechaNacimiento = rs.getDate("fechaNacimiento");
                String tipo = rs.getString("tipoUsuario");

                usuarios.add(new Usuario(id, nombre, apellido, mail, dni, contrasenia, fechaNacimiento, tipo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }


}
